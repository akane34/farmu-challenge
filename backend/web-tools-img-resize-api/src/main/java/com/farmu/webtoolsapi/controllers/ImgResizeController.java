package com.farmu.webtoolsapi.controllers;

import com.farmu.webtoolsapi.commons.result.ErrorCode;
import com.farmu.webtoolsapi.commons.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import com.farmu.webtoolsapi.services.ImgResizeService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

import static com.farmu.webtoolsapi.beans.ImgResizeBeans.IMG_RESIZE_SERVICE;

@Slf4j
@RestController
@RequestMapping("/")
public class ImgResizeController {
    private final ImgResizeService imgResizeService;

    public ImgResizeController(
            @Qualifier(IMG_RESIZE_SERVICE) ImgResizeService imgResizeService){
        this.imgResizeService = imgResizeService;
    }

    @CrossOrigin
    @PostMapping("/img-resize/upload")
    public ResponseEntity<Result<byte[]>> imgResize(
            @RequestParam("imgFile") MultipartFile file,
            @RequestParam("imgWidth") int width,
            @RequestParam("imgHeigth") int heigth,
            @RequestParam("imgType") String imgType) throws IOException {

        String name = file.getOriginalFilename();
        log.info("name: " + name);

        Result<byte[]> result = imgTypeValidation(imgType);
        if (!result.isSuccess()) {
            log.error(result.getErrors().stream().map(e -> e.getMessage()).collect(Collectors.joining()));
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        result = imgResizeService.resizeImg(file.getBytes(), width, heigth, imgType);
        if (result.isSuccess()){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        HttpStatus status = getHttpStatus(result);

        return new ResponseEntity<>(result, status);
    }

    private Result<byte[]> imgTypeValidation(String imgType) {
        Result<byte[]> result = new Result<>();

        if (!"png".equalsIgnoreCase(imgType)
         && !"jpg".equalsIgnoreCase(imgType)
         && !"jpeg".equalsIgnoreCase(imgType)){
            result.addError("Formato no permitido, los formatos permitidos son: PNG, JPG", ErrorCode.CLIENT_ERROR);
        }

        return result;
    }

    private HttpStatus getHttpStatus(Result<byte[]> result) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        boolean isServerError = result.getErrors().stream().anyMatch(e -> e.getCode() == ErrorCode.SERVER_ERROR);
        if (isServerError){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return status;
    }
}
