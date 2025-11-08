package com.example.demo.infra.aws.s3;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

import javax.management.RuntimeErrorException;

@Service
public class S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private S3Client s3Client;

    @Autowired 
    private  S3Presigner presigner;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException {
        String key = "candies/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

        return key;
    }

    public String getFileUrl(String key) {

        String newKey = key.replace("candies", "resized");
  
        try{
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(newKey)
                .build();
            
            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(objectRequest)
                .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
            logger.info("Presigned URL with key: [{}]", newKey);
            logger.info("HTTP method: [{}]", presignedRequest.httpRequest().method());

            return presignedRequest.url().toExternalForm();
        } catch(Exception e){
            logger.error("Error while getting file url: ", e);
            return "";
        }
    }

    public void deleteFile(String key){
       try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build());

            String newKey = key.replaceFirst("candies/", "resized/");
            s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(newKey)
                .build());
        } catch (S3Exception e) {
            logger.error("Erro ao deletar arquivo do S3: {}", e.awsErrorDetails().errorMessage());
            throw e;
        }
    }

    public String updateFile(MultipartFile newFile, String key) throws IOException{
        try{
            deleteFile(key);
            String newKey = uploadFile(newFile);
            return newKey;
        } catch(IOException e){
            throw new RuntimeException("Error while updating the image", e);
        }
        
    }

    public byte[] downloadFile(String key) throws IOException{

        String newKey = key.replace("candies", "resized");
        
        ResponseInputStream<GetObjectResponse> object = this.s3Client.getObject(GetObjectRequest.builder()
        .bucket(bucketName)
        .key(newKey)
        .build());
        try{
            byte[] byte_object = object.readAllBytes();
            return byte_object;
            
        } catch(IOException e){
            throw e;
        }
        

    }
        


}
