package com.example.towerdriver.base_main.bean;

/**
 * @author 53288
 * @description
 * @date 2021/5/21
 */
public class LngBean {
    private double x;

    private double y;

    public LngBean(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
