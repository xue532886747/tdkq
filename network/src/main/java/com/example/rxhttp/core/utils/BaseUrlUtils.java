package com.example.rxhttp.core.utils;

/**
 * @author 53288
 * @description
 * @date 2021/5/18
 */
public class BaseUrlUtils {

    public static String checkBaseUrl(String url) {
        if (url.endsWith("/")) {
            return url;
        } else {
            return url + "/";
        }
    }
}
