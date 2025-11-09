package com.example.demo.infra.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AWSConfig {

    @Value("${spring.cloud.aws.region.static}")
    private String awsRegion;

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String awsSecretAccess;

    @Value("${spring.cloud.aws.credentials.session-token}")
    private String sessionToken;

    @Bean
    public DynamoDbClient dynamoDbClient(){
        var awsCredentials = AwsSessionCredentials.create(awsAccessKey, awsSecretAccess, sessionToken);
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCredentials);

        return DynamoDbClient.builder()
                .region((Region.of(awsRegion)))
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient){
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient)
                .build();
    }

    @Bean
    public S3Client s3Client() {

        AwsSessionCredentials sessionCredentials = AwsSessionCredentials.create(
                awsAccessKey, 
                awsSecretAccess, 
                sessionToken
        );

        return S3Client.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(sessionCredentials))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {

        AwsSessionCredentials sessionCredentials = AwsSessionCredentials.create(
                awsAccessKey, 
                awsSecretAccess, 
                sessionToken
        );

        return S3Presigner.builder()
                .region(Region.of(awsRegion))
                .credentialsProvider(StaticCredentialsProvider.create(sessionCredentials))
                .build();
    }

    @Bean
    public SqsClient sqsClient(){
        var awsCredentials =  AwsSessionCredentials.create(awsAccessKey, awsSecretAccess, sessionToken);
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsCredentials);

        return SqsClient.builder()
                .region((Region.of(awsRegion)))
                .credentialsProvider(credentialsProvider)
                .build();
    }


}
