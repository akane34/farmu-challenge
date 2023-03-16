package com.farmu.webtoolsapi.beans;

import com.farmu.webtoolsapi.persistence.daos.MongoDbUrlDao;
import com.farmu.webtoolsapi.persistence.daos.UrlDao;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.farmu.webtoolsapi.beans.MongoDbBeans.SHORT_URL_DATABASE;

@Configuration
public class UrlDaoBeans {
    public static final String URL_DAO = "urlDao";

    @Bean(name = URL_DAO)
    public UrlDao getUrlDao (@Qualifier(SHORT_URL_DATABASE) MongoDatabase mongoDatabase){
        return new MongoDbUrlDao(mongoDatabase);
    }
}
