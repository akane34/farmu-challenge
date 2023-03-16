package com.farmu.webtoolsapi.services;

import com.farmu.webtoolsapi.commons.result.Result;

public interface ImgResizeService {
    Result<byte[]> resizeImg(byte[] original, int width, int height, String imgType);
}
