package com.example.towerdriver.base_order_list.model;

import java.io.Serializable;

/**
 * @author 53288
 * @description
 * @date 2021/8/2
 */
public class ChangeCarBean implements Serializable {


    /**
     * code : 200
     * msg : 成功
     * data : {"frame_number":"L5XDE1Z53L6031233","car_number":"陕A临1589181"}
     */

    /**
     * frame_number : L5XDE1Z53L6031233
     * car_number : 陕A临1589181
     */

    private String frame_number;
    private String car_number;

    public String getFrame_number() {
        return frame_number;
    }

    public void setFrame_number(String frame_number) {
        this.frame_number = frame_number;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

}
