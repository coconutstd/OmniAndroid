package com.example.omniandroid;

import android.app.Application;
import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.ScanOperationConfig;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Search;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.DynamoDBEntry;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.security.Key;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.jar.Attributes;

public class DatabaseAccess {
    private final String COGNITO_IDENTITY_POOL_ID = "ap-northeast-2_21OIRcb1Q";
    private final Regions COGNITO_IDENTITY_POOL_REGION = Regions.AP_NORTHEAST_2;
    private final String DYNAMODB_TABLE = "sensors";
    public Document retrievedDoc;
    private Context context;
    private CognitoCachingCredentialsProvider credentialsProvider;
    private AmazonDynamoDBClient dbClient;
    private Table dbTable;
    private static volatile DatabaseAccess instance;


    public DatabaseAccess(Context context) {
        this.context = context;
        credentialsProvider = new CognitoCachingCredentialsProvider(context, COGNITO_IDENTITY_POOL_ID, COGNITO_IDENTITY_POOL_REGION);
        dbClient = new AmazonDynamoDBClient(credentialsProvider);
        dbClient.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_2));
        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
        retrievedDoc = dbTable.getItem(new Primitive("user_temp"));
    }

    public static synchronized DatabaseAccess getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    // gets a contact for given primary key
//    public Document getItem(String sensor) {
//        ScanOperationConfig scanConfig = new ScanOperationConfig();
//        List<String> attributeList = new ArrayList<>();
//        attributeList.
//    }

    // gets all the contacts
    public List<Document> getAllContacts() {
        ScanOperationConfig scanConfig = new ScanOperationConfig();
        List<String> attributeList = new ArrayList<>();
        attributeList.add("date");
        attributeList.add("temp");
        attributeList.add("humi");
        attributeList.add("emf");
        scanConfig.withAttributesToGet(attributeList);
        Search searchResult = dbTable.scan(scanConfig);

        return searchResult.getAllResults();
    }




    // get the set
    DynamoDBEntry theSet = retrievedDoc.get("Set");

//    resp = table.get_item(Key={"Author": "John Grisham", "Title": "The Rainmaker"})
//
//    print(resp['Item'])
}
