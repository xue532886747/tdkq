package com.example.towerdriver.base_sendcode.model;

/**
 * @author 53288
 * @description 发送验证码的中间类
 * @date 2021/6/2
 */
public interface ISendCodeModel {

    void getCodeSuccess(String phone, String msg);

    void getCodeFailure(String msg);

    void getCodeElseFailure(int code, String msg);

}
