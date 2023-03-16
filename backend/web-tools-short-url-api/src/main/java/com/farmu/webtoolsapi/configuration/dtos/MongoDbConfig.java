package com.farmu.webtoolsapi.configuration.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MongoDbConfig {
    private String uri;
    private String database;
}
