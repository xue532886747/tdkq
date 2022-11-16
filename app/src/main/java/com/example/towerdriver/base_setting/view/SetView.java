package com.example.towerdriver.base_setting.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 设置的view
 * @date 2021/5/19
 */
public interface SetView extends BaseView {

    void UserLogoutSuccess(String msg);

    void UserLogoutFailure(int code, String msg);


}
