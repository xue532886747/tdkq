package com.example.towerdriver.event;

import com.example.towerdriver.base_login.bean.UserBean;

/**
 * @author 53288
 * @description 用户完善信息
 * @date 2021/6/2
 */
public class UserInformationEvent extends BaseEvent {
    private boolean isSuccess;  //用户


    public UserInformationEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
