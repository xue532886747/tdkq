package com.example.towerdriver.base_order_list.model;

import java.io.Serializable;

/**
 * @author 53288
 * @description 取消订单
 * @date 2021/6/25
 */
public class OrderStatusBean implements Serializable {


    /**
     * id : 4
     * order_sn : 11624503879724
     * order_status : 0
     * status_name : 已取消
     */

    private Integer id;
    private String order_sn;
    private Integer order_status;
    private String status_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    @Override
    public String toString() {
        return "OrderStatusBean{" +
                "id=" + id +
                ", order_sn='" + order_sn + '\'' +
                ", order_status=" + order_status +
                ", status_name='" + status_name + '\'' +
                '}';
    }
}
