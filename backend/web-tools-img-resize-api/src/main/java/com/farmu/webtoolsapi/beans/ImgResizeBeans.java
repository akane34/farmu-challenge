package com.farmu.webtoolsapi.beans;

import com.farmu.webtoolsapi.services.DefaultImgResizeService;
import com.farmu.webtoolsapi.services.ImgResizeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImgResizeBeans {
    public static final String IMG_RESIZE_SERVICE = "imgResizeService";

    @Bean(name = IMG_RESIZE_SERVICE)
    public ImgResizeService getImgResizeService (){
        return new DefaultImgResizeService();
    }
}
