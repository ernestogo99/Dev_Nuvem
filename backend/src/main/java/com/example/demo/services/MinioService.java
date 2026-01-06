package com.example.demo.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

import javax.management.RuntimeErrorException;

@Service
public class MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.url}")
    private String minioUrl;

    public String uploadFile(MultipartFile file) throws IOException {
        String objectName = "candies/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        try(InputStream inputStream = file.getInputStream()){
            minioClient.putObject(
                PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, file.getSize(), -1)
                .contentType(file.getContentType())
                .build()
            );

            return objectName;

        } catch(Exception e ){
            logger.error("Error while uploading to MinIO", e);
            throw new RuntimeException(e);
        }
    }

    public void uploadResizedFile(byte[] file, String key) throws IOException {
        String newKey = key.replaceFirst("candies/", "resized/");

        try(InputStream inputStream = new ByteArrayInputStream(file)){
            minioClient.putObject(
                PutObjectArgs.builder()
                .bucket(bucketName)
                .object(newKey)
                .stream(inputStream, file.length, -1)
                .contentType("application/octet-stream")
                .build()
            );

        } catch(Exception e ){
            logger.error("Error while uploading to MinIO", e);
            throw new RuntimeException(e);
        }
    }

    public String getFileUrl(String key) {

        String newKey = key.replaceFirst("candies/", "resized/");
  
        try{
   
            logger.info(" URL with key: [{}]", minioUrl,newKey);

            String fileUrl = String.format("%s/%s/%s", 
                minioUrl,
                bucketName,
                newKey
            );
            
            return fileUrl;

        } catch(Exception e){
            logger.error("Error while getting file url: ", e);
            return "";
        }
    }

    public void deleteFile(String key){
       try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(key)
                .build());

            String newKey = key.replaceFirst("candies/", "resized/");
            minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(newKey)
                .build());

        } catch (Exception e) {
            logger.error("Error while deleting file in MinIO", e);
            throw new RuntimeException(e);
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

        String newKey = key.replaceFirst("candies/", "resized/");
        
        try(InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(newKey)
                    .build()))
        {
            return inputStream.readAllBytes();
        } catch (Exception e){
            throw new RuntimeException("Error while downloading file", e);
        }           
    }


     public byte[] getOriginalFile(String key) throws IOException{
   
        try(InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(key)
                    .build()))
        {
            return inputStream.readAllBytes();
        } catch (Exception e){
            throw new RuntimeException("Error while fetching file", e);
        }
        
    }
        


}
