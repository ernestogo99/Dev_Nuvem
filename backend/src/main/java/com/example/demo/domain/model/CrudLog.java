package com.example.demo.domain.model;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@DynamoDbBean
public class CrudLog {

    private UUID id;   
    private String actionType;
    private List<Map<String, String>> candies;
    private String timestamp;

     public CrudLog(String actionType, String timestamp, List<Map<String, String>> candies) {
        this.id = UUID.randomUUID();
        this.actionType = actionType;
        this.timestamp = timestamp;
        this.candies = candies;
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

    @DynamoDbAttribute("candies")
    public List<Map<String, String>> getCandies() {
        return candies;
    }

    public void setCandies(List<Map<String, String>> candies) {
        this.candies = candies;
    }



}
