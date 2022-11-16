package com.example.towerdriver.station.station_order.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;

import java.util.List;

/**
 * @author 53288
 * @description 站长端订单列表
 * @date 2021/6/30
 */
public interface IStationOrderListView extends BaseView {

    /**
     * 列表成功
     *
     * @param orderBeans 列表数据
     * @param cur_page   当前页面
     * @param total_page 总页面
     * @param orderBeans fresh
     */
    void orderListSuccess(List<OrderListBean.OrderBean> orderBeans, int cur_page, int total_page, boolean fresh);

    /**
     * 列表失败
     *
     * @param msg
     */
    void orderListFailure(String msg);

    /**
     * 列表为空
     *
     * @param msg
     * @param orderBeans
     */
    void showRefreshNoDate(String msg, List<OrderListBean.OrderBean> orderBeans);

}
