package com.example.towerdriver.base_order_list.model;

/**
 * @author 53288
 * @description
 * @date 2021/6/25
 */
public class RentCarBean {

    private String name;
    private String type;

    public RentCarBean(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RentCarBean{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
