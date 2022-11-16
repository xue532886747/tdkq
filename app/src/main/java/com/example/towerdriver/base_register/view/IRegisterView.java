package com.example.towerdriver.base_register.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 注册页面
 * @date 2021/5/24
 */
public interface IRegisterView extends BaseView {

    void onSendCodeSuccess(String msg);

    void onSendCodeFailure(String msg);

    void onRegisterSuccess(String msg);

    void onRegisterFailure(String msg);

}
