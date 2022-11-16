package com.example.towerdriver.base_order_list.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.ChangeCarBean;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.base_order_list.model.OrderListBean;

import java.util.List;

/**
 * @author 53288
 * @description 订单列表
 * @date 2021/6/21
 */
public interface IOrderDetailView extends BaseView {

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


    /**
     * 订单详情
     *
     * @param msg
     */
    void changeCarSuccess(String msg, ChangeCarBean data);

    /**
     * 列表失败
     *
     * @param msg
     */
    void changeCarFailure(String msg);

}
