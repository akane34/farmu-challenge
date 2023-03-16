package com.farmu.webtoolsapi.controllers;

import com.farmu.webtoolsapi.commons.result.ErrorCode;
import com.farmu.webtoolsapi.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import com.farmu.webtoolsapi.services.UrlShortenerService;

import java.util.stream.Collectors;

import static com.farmu.webtoolsapi.beans.UrlShortenerBeans.URL_SHORTENER_SERVICE;

@Slf4j
@RestController
@RequestMapping("/")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(
            @Qualifier(URL_SHORTENER_SERVICE) UrlShortenerService urlShortenerService){
        this.urlShortenerService = urlShortenerService;
    }

    @CrossOrigin
    @PostMapping("/url-shortener/short")
    public ResponseEntity<Result<String>> createShortUrl(@RequestBody MultiValueMap<String, String> formData){

        String url = formData.get("url") != null ? formData.get("url").get(0) : null;

        Result<String> result = urlShortenerService.getShortUrl(url);
        if (result.isSuccess()){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        HttpStatus status = getHttpStatus(result);
        log.error(getError(result));
        return new ResponseEntity<>(result, status);
    }

    @CrossOrigin
    @PostMapping("/url-shortener/original")
    public ResponseEntity<Result<String>> getOriginalUrl(@RequestBody MultiValueMap<String, String> formData){

        String hashUrl = formData.get("hashUrl") != null ? formData.get("hashUrl").get(0) : null;

        Result<String> result = urlShortenerService.getOriginalUrl(hashUrl);
        if (result.isSuccess()){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        HttpStatus status = getHttpStatus(result);
        log.error(getError(result));
        return new ResponseEntity<>(result, status);
    }

    @CrossOrigin
    @GetMapping("/{hashUrl}")
    public ResponseEntity<String> getOriginalUrlWithRedirect(@PathVariable("hashUrl") String hashUrl){
        Result<String> result = urlShortenerService.getOriginalUrl(hashUrl);
        if (result.isSuccess()){
            return new ResponseEntity<>(result.getResult(), HttpStatus.MOVED_PERMANENTLY);
        }

        HttpStatus status = getHttpStatus(result);
        String error = getError(result);
        log.error(error);
        return new ResponseEntity<>(error, status);
    }

    private String getError(Result<String> result) {
        return result.getErrors().stream().map(e -> e.getMessage()).collect(Collectors.joining());
    }

    private HttpStatus getHttpStatus(Result<String> result) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        boolean isServerError = result.getErrors().stream().anyMatch(e -> e.getCode() == ErrorCode.SERVER_ERROR);
        if (isServerError){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return status;
    }
}
