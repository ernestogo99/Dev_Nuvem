package com.example.demo.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
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
        } catch(Exception e){
            throw new RuntimeException("Failed to create MinIO bucket", e);
        }
    }

}
