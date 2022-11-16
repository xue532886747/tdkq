package com.example.towerdriver.event;

/**
 * @author 53288
 * @description 登陆和退出的全局通知
 * @date 2021/6/2
 */
public class LoginEvent extends BaseEvent{
    private boolean login;  //是否登陆
    private int type;       //登陆类型

    public LoginEvent(boolean login,int type) {
        this.login = login;
        this.type = type;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
