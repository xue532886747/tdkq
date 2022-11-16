package com.example.towerdriver.station.station_rescue.model;

import java.io.Serializable;

/**
 * @author 53288
 * @description 救援接单
 * @date 2021/7/1
 */
public class RescueBean implements Serializable {

    /**
     * id : 208
     * status : 3
     * status_name : 救援中
     */

    private String id;
    private Integer status;
    private String status_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
