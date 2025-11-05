package com.example.demo.domain.model;


import com.example.demo.domain.enums.LogActions;
import jakarta.persistence.*;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.rmi.server.UID;
import java.time.Instant;
import java.util.UUID;


@DynamoDbBean
public class CrudLog {

    private UUID id;   
    private String actionType;
    private Long candyId;
    private String timestamp;

     public CrudLog(String actionType, String timestamp, Long candyId) {
        this.id = UUID.randomUUID();
        this.actionType = actionType;
        this.timestamp = timestamp;
        this.candyId = candyId;
    }

    public CrudLog() {}


    @DynamoDbPartitionKey
    public String getActionType(){
        return this.actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @DynamoDbSortKey
    public String getTimestamp(){
        return this.timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDbAttribute("id")
    public UUID getId() {
        return id;
    }
 
    public void setId(UUID id) {
        this.id = id;
    }

    @DynamoDbAttribute("candy_id")
    public Long getCandyId() {
        return candyId;
    }

    public void setCandyId(Long candyId) {
        this.candyId = candyId;
    }



}
