package com.farmu.webtoolsapi.beans;

import com.farmu.webtoolsapi.configuration.dtos.ShortenerConfig;
import com.farmu.webtoolsapi.persistence.daos.UrlDao;
import com.farmu.webtoolsapi.services.GuavaUrlShortenerService;
import com.farmu.webtoolsapi.services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.farmu.webtoolsapi.beans.UrlDaoBeans.URL_DAO;

@Configuration
public class UrlShortenerBeans {
    public static final String URL_SHORTENER_SERVICE = "urlShortenerService";

    @Bean(name = URL_SHORTENER_SERVICE)
    public UrlShortenerService getUrlShortenerService (
            @Qualifier(URL_DAO) UrlDao urlDao,
            ShortenerConfig shortenerConfig){
        return new GuavaUrlShortenerService(urlDao, shortenerConfig);
    }
}
