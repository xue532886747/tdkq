package com.example.rxhttp.core.exception;

/**
 * @author 53288
 * @description 如果网络未初始化，那么就报此异常
 * @date 2021/5/18
 */
public class RxHttpUninitializedException extends RuntimeException {
    public RxHttpUninitializedException() {
        super("Rxhttp未初始化");
    }
}
