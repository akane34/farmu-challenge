package com.farmu.webtoolsapi.configuration;

import com.farmu.webtoolsapi.configuration.dtos.MongoDbConfig;
import com.farmu.webtoolsapi.configuration.dtos.ShortenerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationFile {

    @Bean()
    @ConfigurationProperties(prefix = "shortener")
    public ShortenerConfig getShortenerConfig(){
        return new ShortenerConfig();
    }

    @Bean()
    @ConfigurationProperties(prefix = "mongodb")
    public MongoDbConfig getMongoDbConfig(){
        return new MongoDbConfig();
    }
}
