package com.example.towerdriver.base_order_list.model;

/**
 * @author 53288
 * @description
 * @date 2021/6/21
 */
public class OrderDean {

    private String title;
    private String name;

    public OrderDean(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
