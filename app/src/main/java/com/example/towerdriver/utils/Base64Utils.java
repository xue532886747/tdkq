package com.example.towerdriver.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author 53288
 * @description 用于字符Base64的加密
 * @date 2021/6/2
 */
public class Base64Utils {

    /**
     * 字符Base64加密
     *
     * @param str
     * @return
     */
    public static String encodeToString(String str) {
        return Base64.encodeToString(str.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
    }

    /**
     * 字符Base64解密
     *
     * @param str
     * @return
     */
    public static String decodeToString(String str) {
        return new String(Base64.decode(str.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT));
    }
}
