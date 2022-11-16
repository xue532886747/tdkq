package com.example.towerdriver.base_login.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description
 * @date 2021/5/21
 */
public interface LoginView extends BaseView {


    /**
     * 登陆成功
     */
    void LoginSuccess(String msg,int type);

    /**
     * 登陆失败
     *
     * @param msg
     */
    void LoginFailed(String msg);

    /**
     * 获取验证码成功
     * @param msg
     */
    void onSendCodeSuccess(String phone,String msg);

    /**
     * 获取验证码失败
     * @param msg
     */
    void onSendCodeFailure(String msg);

}
