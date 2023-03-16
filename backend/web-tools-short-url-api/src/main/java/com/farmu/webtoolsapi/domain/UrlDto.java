package com.farmu.webtoolsapi.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class UrlDto {
    private String url;
    private String hashUrl;
}
