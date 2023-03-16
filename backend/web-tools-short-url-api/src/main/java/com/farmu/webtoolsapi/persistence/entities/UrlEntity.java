package com.farmu.webtoolsapi.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UrlEntity {
    private String url;
    private String hashUrl;
    private Date creationDate;
}
