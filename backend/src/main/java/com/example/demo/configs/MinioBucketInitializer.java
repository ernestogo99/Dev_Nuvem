package com.example.demo.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;

@Component
public class MinioBucketInitializer {
    
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public MinioBucketInitializer(MinioClient minioClient){
        this.minioClient = minioClient;
    }

    @PostConstruct
    public void createBucketIfNotExists(){
        try{
            boolean exists = minioClient
                .bucketExists(
                    BucketExistsArgs.builder()
                        .bucket(bucketName)   
                        .build()    
                );
            if(!exists){
                minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()  
                );
            }  
             String policy = """
                {
                    "Version": "2012-10-17",
                    "Statement": [
                        {
                            "Effect": "Allow",
                            "Principal": {"AWS": "*"},
                            "Action": ["s3:GetObject"],
                            "Resource": ["arn:aws:s3:::%s/*"]
                        }
                    ]
                }
                """.formatted(bucketName);
            
            minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder()
                    .bucket(bucketName)
                    .config(policy)
                    .build()
            );  
        } catch(Exception e){
            throw new RuntimeException("Failed to create MinIO bucket", e);
        }
    }

}
