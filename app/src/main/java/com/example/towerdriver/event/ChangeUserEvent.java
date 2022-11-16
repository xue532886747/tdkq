package com.example.towerdriver.event;

import com.example.towerdriver.base_login.bean.UserBean;

/**
 * @author 53288
 * @description 更换用户信息的全局通知
 * @date 2021/6/2
 */
public class ChangeUserEvent extends BaseEvent {
    private UserBean userBean;  //用户


    public ChangeUserEvent(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}
