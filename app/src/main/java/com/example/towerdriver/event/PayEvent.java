package com.example.towerdriver.event;

/**
 * @author 53288
 * @description 支付成功
 * @date 2021/6/22
 */
public class PayEvent extends BaseEvent {
    private String msg;             //是否成功
    private int pay_type;           //支付类型 1=微信，2=支付宝
    private int code;

    public PayEvent(int pay_type, String msg,int code) {
        this.msg = msg;
        this.pay_type = pay_type;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPay_type() {
        return pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
