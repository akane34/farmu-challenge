package com.farmu.webtoolsapi.services;

import com.farmu.webtoolsapi.commons.result.Result;

public interface UrlShortenerService {
    Result<String> getShortUrl(String url);
    Result<String> getOriginalUrl(String hashUrl);
}
