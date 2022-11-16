package com.example.towerdriver.base_order_list.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_order_list.model.OrderListBean;

import java.util.List;

/**
 * @author 53288
 * @description 订单列表
 * @date 2021/6/17
 */
public interface IOrderListView extends BaseView {

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

    /**
     * 取消订单成功
     *
     * @param msg
     * @param position
     */
    void cancelOrderSuccess(String msg, int position, OrderStatusBean orderStatusBean);

    /**
     * 取消订单失败
     *
     * @param msg
     */
    void cancelOrderFailure(String msg);

    /**
     * 删除订单成功
     *
     * @param msg
     * @param position
     */
    void deleteOrderSuccess(String msg, int id, int position);

    /**
     * 删除订单失败
     *
     * @param msg
     */
    void deleteOrderFailure(String msg);

    /**
     * 退款成功
     *
     * @param msg
     * @param orderStatusBean
     * @param position
     */
    void RefundOrderSuccess(String msg, int position, OrderStatusBean orderStatusBean);

    /**
     * 退款失败
     *
     * @param msg
     */
    void RefundOrderFailure(String msg);

}
