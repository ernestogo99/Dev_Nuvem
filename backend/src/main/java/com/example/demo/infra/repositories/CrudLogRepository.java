package com.example.demo.infra.repositories;

import com.example.demo.domain.enums.LogActions;
import com.example.demo.domain.model.CrudLog;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Repository;

@Repository
public class CrudLogRepository {

    private final DynamoDbTable<CrudLog> logTable;

    public CrudLogRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient){
        this.logTable = dynamoDbEnhancedClient.table("pretty-cake-logs", 
            TableSchema.fromBean(CrudLog.class));          
    }

    public CompletableFuture<Void> saveLogAsync(CrudLog log){
        return CompletableFuture.runAsync(() ->{
            try{
                logTable.putItem(log);
            } catch(Exception e){
                System.err.println(("Failed to save log to DynamoDB: " + e.getMessage()));
            }
        });
    }

    public List<CrudLog> getLogsByTimeRange(LogActions actionType, String startTime, String endTime){
        Key startKey = Key.builder()
                .partitionValue(actionType.toString())
                .sortValue(startTime)
                .build();

        Key endKey = Key.builder()
        .partitionValue(actionType.toString())
        .sortValue(endTime)
        .build();        

        var queryRequest = QueryEnhancedRequest.builder()
                    .queryConditional(QueryConditional.sortBetween(startKey, endKey))
                    .build();   
        
        return logTable.query(queryRequest)
                .items()
                .stream()
                .toList();
    }

    public CrudLog saveLog(CrudLog log){
        try{
           logTable.putItem(log);
        } catch(Exception e){
            System.err.println(("Failed to save log to DynamoDB: " + e.getMessage()));
        } 
        return log;
    }

    public List<CrudLog> getAllLogs(){
        return logTable.scan().items().stream().toList();
    }



}
