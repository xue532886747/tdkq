package com.example.towerdriver.staff.base_approval.model;

/**
 * @author 53288
 * @description
 * @date 2021/7/9
 */
public class ApprovalStatusBean {


    /**
     * id : 1
     * status : 2
     * status_name : 通过
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
