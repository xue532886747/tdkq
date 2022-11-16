package com.example.towerdriver.utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author 53288
 * @description
 * @date 2021/1/7
 */
public class RxPartMapUtils {

    public static RequestBody toRequestBodyOfText (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), value);
        return body ;
    }

    public static RequestBody toRequestBodyOfImage(File pFile){
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data;charset=UTF-8"), pFile);
        return fileBody;
    }
}
