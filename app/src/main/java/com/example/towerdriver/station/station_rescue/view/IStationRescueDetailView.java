package com.example.towerdriver.station.station_rescue.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.station.station_rescue.model.RescueDetailBean;

/**
 * @author 53288
 * @description 救援详情
 * @date 2021/7/1
 */
public interface IStationRescueDetailView extends BaseView {

    /**
     * 救援详情
     *
     * @param rescueDetailBean
     */
    void stationDetailSuccess(RescueDetailBean rescueDetailBean);

    /**
     * @param msg
     */
    void stationDetailFailure(String msg);

}
