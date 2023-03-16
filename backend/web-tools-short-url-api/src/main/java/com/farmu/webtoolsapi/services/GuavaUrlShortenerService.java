package com.farmu.webtoolsapi.services;

import com.farmu.webtoolsapi.commons.result.ErrorCode;
import com.farmu.webtoolsapi.commons.result.Result;
import com.farmu.webtoolsapi.configuration.dtos.ShortenerConfig;
import com.farmu.webtoolsapi.domain.UrlDto;
import com.farmu.webtoolsapi.persistence.daos.UrlDao;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.util.Strings;

import java.nio.charset.Charset;

@Slf4j
public class GuavaUrlShortenerService implements UrlShortenerService {
    private final UrlDao urlDao;
    private final ShortenerConfig shortenerConfig;

    public GuavaUrlShortenerService (UrlDao urlDao, ShortenerConfig shortenerConfig){
        this.urlDao = urlDao;
        this.shortenerConfig = shortenerConfig;
    }

    @Override
    public Result<String> getShortUrl(String url) {
        Result<String> result = new Result<>();

        try {
            validUrl(url, result);
            if (!result.isSuccess()){
                return result;
            }

            String hashUrl = getHashUrl(url);

            Result<Boolean> saveResult = urlDao.save(new UrlDto().setUrl(url).setHashUrl(hashUrl));
            if (saveResult.isSuccess()) {
                result.setResult(shortenerConfig.getBaseUrl() + hashUrl);
            } else {
                result.addError("Error Saving URL in Database", ErrorCode.SERVER_ERROR);
            }

        } catch (Exception ex){
            result.addError(ex.getMessage(), ErrorCode.SERVER_ERROR);
            log.error(ex.getMessage(), ex);
        }

        return result;
    }

    @Override
    public Result<String> getOriginalUrl(String hashUrl) {
        Result<String> result = new Result<>();

        try {
            if (Strings.isBlank(hashUrl)){
                result.addError("Url not found", ErrorCode.CLIENT_ERROR);
                return result;
            }

            Result<UrlDto> findResult = urlDao.find(hashUrl);
            if (findResult.isSuccess()){
                result.setResult(findResult.getResult().getUrl());
            }
            else {
                result.addError("Url not found", ErrorCode.CLIENT_ERROR);
            }

        } catch (Exception ex){
            result.addError(ex.getMessage(), ErrorCode.SERVER_ERROR);
        }

        return result;
    }

    private void validUrl(String url, Result<String> result) {
        UrlValidator validator = new UrlValidator(
                new String[]{"http", "https"}
        );

        if (!validator.isValid(url)) {
            result.addError("Invalid URL", ErrorCode.CLIENT_ERROR);
        }
    }

    private String getHashUrl(String url) {
        return Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();
    }
}
