package com.example.rxhttp.request.exception;

/**

 */
public class NullRequestSettingException extends RuntimeException {
    public NullRequestSettingException() {
        super("RequestSetting未设置");
    }
}
