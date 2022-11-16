package com.example.zxing.decode;

import com.google.zxing.Result;

/**
 * @author 53288
 * @description
 * @date 2021/5/28
 */

public interface DecodeImgCallback {
    void onImageDecodeSuccess(Result result);

    void onImageDecodeFailed();
}