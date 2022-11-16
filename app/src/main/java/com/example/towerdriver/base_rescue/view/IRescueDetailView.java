package com.example.towerdriver.base_rescue.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_rescue.model.RescueBean;
import com.example.towerdriver.station.station_rescue.model.RescueDetailBean;

/**
 * @author 53288
 * @description  救援详情
 * @date 2021/7/2
 */
public interface IRescueDetailView extends BaseView {

    /**
     * 救援详情
     *
     * @param rescueBean
     */
    void UserDetailSuccess(RescueBean rescueBean);

    /**
     * @param msg
     */
    void UserDetailFailure(String msg);
}
