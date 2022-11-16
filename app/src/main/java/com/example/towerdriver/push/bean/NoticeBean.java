package com.example.towerdriver.push.bean;

import java.io.Serializable;

/**
 * @author 53288
 * @description
 * @date 2021/6/23
 */
public class NoticeBean implements Serializable {


    /**
     * message : <p>阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德阿萨德<p>
     * type : 1
     */

    private String message;
    private String type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NotificationExtrasBean{" +
                "message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
