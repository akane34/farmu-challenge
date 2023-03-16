package com.farmu.webtoolsapi.services;

import com.farmu.webtoolsapi.commons.result.ErrorCode;
import com.farmu.webtoolsapi.commons.result.Result;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

@Slf4j
public class DefaultImgResizeService implements ImgResizeService {

    public DefaultImgResizeService(){

    }

    @Override
    public Result<byte[]> resizeImg(byte[] original, int width, int height, String imgType) {
        Result<byte[]> result = new Result<>();
        imgType = imgType.toLowerCase();

        try {
            InputStream inputStream = new ByteArrayInputStream(original);
            BufferedImage originalImg = ImageIO.read(inputStream);
            BufferedImage resizeImg = new BufferedImage(
                    width,
                    height,
                    originalImg.getType()
            );

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizeImg, imgType, baos);

            result.setResult(baos.toByteArray());

        } catch (Exception ex){
            result.addError(ex.getMessage(), ErrorCode.SERVER_ERROR);
            log.error(ex.getMessage(), ex);
        }

        return result;
    }
}
