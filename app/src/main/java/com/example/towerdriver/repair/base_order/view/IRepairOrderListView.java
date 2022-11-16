package com.example.towerdriver.repair.base_order.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.repair.base_order.model.RepairListBean;

import java.util.List;

/**
 * @author 53288
 * @description 维修端订单
 * @date 2021/7/5
 */
public interface IRepairOrderListView extends BaseView {

    /**
     * 列表成功
     *
     * @param orderBeans 列表数据
     * @param cur_page   当前页面
     * @param total_page 总页面
     * @param orderBeans fresh
     */
    void repairListSuccess(List<RepairListBean.OrderBean> orderBeans, int cur_page, int total_page, boolean fresh);

    /**
     * 列表失败
     *
     * @param msg
     */
    void repairListFailure(String msg);

    /**
     * 列表为空
     *
     * @param msg
     * @param orderBeans
     */
    void showRefreshNoDate(String msg, List<RepairListBean.OrderBean> orderBeans);


    void repairAddSuccess(String msg);


    void repairAddFailure(String msg);


    void deleteOrderSuccess(int position,String msg);


    void deleteOrderFailure(String msg);
}
