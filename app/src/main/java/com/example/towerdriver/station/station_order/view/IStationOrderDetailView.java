package com.example.towerdriver.station.station_order.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;

/**
 * @author 53288
 * @description 订单详情
 * @date 2021/6/30
 */
public interface IStationOrderDetailView extends BaseView {

    /**
     * 订单详情
     *
     * @param orderBeans
     */
    void orderDetailSuccess(OrderDetailBean orderBeans);

    /**
     * 列表失败
     *
     * @param msg
     */
    void orderDetailFailure(String msg);

}
