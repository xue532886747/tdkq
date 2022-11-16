package com.example.towerdriver.event;

import org.greenrobot.eventbus.EventBus;

/**
 * @author 53288
 * @description event的基类
 * @date 2021/6/2
 */
public class BaseEvent {
    public void post() {
        EventBus.getDefault().post(this);
    }

    public void postSticky() {
        EventBus.getDefault().postSticky(this);
    }

    public void removeSticky() {
        EventBus.getDefault().removeStickyEvent(this);
    }
}
