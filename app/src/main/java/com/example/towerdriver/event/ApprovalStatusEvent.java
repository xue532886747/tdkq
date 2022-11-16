package com.example.towerdriver.event;

import com.example.towerdriver.base_login.bean.UserBean;

/**
 * @author 53288
 * @description 审批状态改变
 * @date 2021/7/9
 */
public class ApprovalStatusEvent extends BaseEvent {

    private int type;
    private int id;

    public ApprovalStatusEvent(int type,int id) {
        this.type = type;
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ApprovalStatusEvent{" +
                "type=" + type +
                ", id=" + id +
                '}';
    }
}

