package com.example.towerdriver.event;

/**
 * @author 53288
 * @description 微信登陆
 * @date 2021/7/12
 */
public class WxEntryEvent extends BaseEvent{

    private String code;  //是否登陆
    private int type;       //登陆类型

    public WxEntryEvent(String code,int type) {
        this.code = code;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
