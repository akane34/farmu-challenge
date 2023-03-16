package com.farmu.webtoolsapi.beans;

import com.farmu.webtoolsapi.configuration.dtos.MongoDbConfig;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDbBeans {
    public final static String SHORT_URL_DATABASE = "shortUrlDatabase";

    @Bean(name = SHORT_URL_DATABASE)
    public MongoDatabase getMongoDatabase (MongoDbConfig mongoDbConfig){
        MongoClient mongoClient = MongoClients.create(mongoDbConfig.getUri());
        return mongoClient.getDatabase(mongoDbConfig.getDatabase());
    }
}
