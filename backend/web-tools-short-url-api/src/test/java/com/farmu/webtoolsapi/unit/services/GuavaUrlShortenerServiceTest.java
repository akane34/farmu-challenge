package com.farmu.webtoolsapi.unit.services;

import com.farmu.webtoolsapi.commons.result.ErrorCode;
import com.farmu.webtoolsapi.commons.result.Result;
import com.farmu.webtoolsapi.configuration.dtos.ShortenerConfig;
import com.farmu.webtoolsapi.domain.UrlDto;
import com.farmu.webtoolsapi.persistence.daos.UrlDao;
import com.farmu.webtoolsapi.services.GuavaUrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GuavaUrlShortenerServiceTest {

    @Mock
    private UrlDao urlDaoMock;
    @Mock
    private ShortenerConfig shortenerConfigMock;

    private GuavaUrlShortenerService shortenerService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        shortenerService = new GuavaUrlShortenerService(urlDaoMock, shortenerConfigMock);
    }

    @Test
    void shortHttpUrl(){
        //Given
        String url = "http://www.mydomain.com?param1=aaaaa&param2=12121212";
        String baseUrl = "https://localhost/";
        String hash = "065cf576";

        when(urlDaoMock.save(any(UrlDto.class))).thenReturn(new Result<>());
        when(shortenerConfigMock.getBaseUrl()).thenReturn(baseUrl);

        //When
        Result<String> result = shortenerService.getShortUrl(url);

        //Then
        assertTrue(result.isSuccess());
        assertEquals(baseUrl + hash, result.getResult());
        verify(urlDaoMock, times(1)).save(any(UrlDto.class));
        verify(shortenerConfigMock, times(1)).getBaseUrl();
    }

    @Test
    void shortHttpsUrl(){
        //Given
        String url = "HTTPS://www.mydomain.com?param1=aaaaa&param2=12121212";
        String baseUrl = "https://localhost/";
        String hash = "57973663";

        when(urlDaoMock.save(any(UrlDto.class))).thenReturn(new Result<>());
        when(shortenerConfigMock.getBaseUrl()).thenReturn(baseUrl);

        //When
        Result<String> result = shortenerService.getShortUrl(url);

        //Then
        assertTrue(result.isSuccess());
        assertEquals(baseUrl + hash, result.getResult());
        verify(urlDaoMock, times(1)).save(any(UrlDto.class));
        verify(shortenerConfigMock, times(1)).getBaseUrl();
    }

    @Test
    void shortNullUrl(){
        //Given
        String url = null;

        //When
        Result<String> result = shortenerService.getShortUrl(url);

        //Then
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrors().size());
        assertEquals(ErrorCode.CLIENT_ERROR, result.getErrors().get(0).getCode());
        verify(urlDaoMock, times(0)).save(any(UrlDto.class));
        verify(shortenerConfigMock, times(0)).getBaseUrl();
    }

    @Test
    void shortEmptyUrl(){
        //Given
        String url = "";

        //When
        Result<String> result = shortenerService.getShortUrl(url);

        //Then
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrors().size());
        assertEquals(ErrorCode.CLIENT_ERROR, result.getErrors().get(0).getCode());
        verify(urlDaoMock, times(0)).save(any(UrlDto.class));
        verify(shortenerConfigMock, times(0)).getBaseUrl();
    }

    @Test
    void shortInvalidUrl(){
        //Given
        String url = "my_invalid url";

        //When
        Result<String> result = shortenerService.getShortUrl(url);

        //Then
        assertFalse(result.isSuccess());
        assertEquals(1, result.getErrors().size());
        assertEquals(ErrorCode.CLIENT_ERROR, result.getErrors().get(0).getCode());
        verify(urlDaoMock, times(0)).save(any(UrlDto.class));
        verify(shortenerConfigMock, times(0)).getBaseUrl();
    }

    @Test
    void shortLargeUrl(){
        //Given
        String url = "HTTPS://www.mydomain.com?param1=aaaakasjhkjashdkahsdkuwhduawkhswuhskahkuwhskauwhsuwhskuwhskaha&param2=12121212kashdkajduedhkahajshdjhsdkajshdkajshdkajshdajshdkajshdkajshdkajsdhkajsdhkajsdhkajshdkajshdkajshdkajshdkajhsdkajhsdjashdukkaejdhajdhkajshdkajdhkajdhkajshdkajshdkajshdkajshdkajshdkajshdkasjhdkajshdkjahsdkjaoiasudaskhdkjashd9898798798798ygjhgjhgjhgjhgjhghftdgdgfdgfdgrgrdgfdfdgfdgdgdgfdgdgfdgrdgrdgfdgfdgrdgdgfdrgdgrdgfdgrdrgdfdfkajhsdkahdajhsdkjahdkajhsdka";
        String baseUrl = "https://localhost/";
        String hash = "99d0c8d9";

        when(urlDaoMock.save(any(UrlDto.class))).thenReturn(new Result<>());
        when(shortenerConfigMock.getBaseUrl()).thenReturn(baseUrl);

        //When
        Result<String> result = shortenerService.getShortUrl(url);

        //Then
        assertTrue(result.isSuccess());
        assertEquals(baseUrl + hash, result.getResult());
        verify(urlDaoMock, times(1)).save(any(UrlDto.class));
        verify(shortenerConfigMock, times(1)).getBaseUrl();
    }
}
