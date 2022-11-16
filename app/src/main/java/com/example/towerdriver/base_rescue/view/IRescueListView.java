package com.example.towerdriver.base_rescue.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_rescue.model.RescueListBean;

import java.util.List;

/**
 * @author 53288
 * @description 救援列表
 * @date 2021/6/21
 */
public interface IRescueListView extends BaseView {

    /**
     * 列表成功
     *
     * @param rescueOrderBeans 列表数据
     * @param cur_page   当前页面
     * @param total_page 总页面
     * @param rescueOrderBeans fresh
     */
    void rescueListSuccess(List<RescueListBean.RescueOrderBean> rescueOrderBeans, int cur_page, int total_page, boolean fresh);

    /**
     * 列表失败
     *
     * @param msg
     */
    void rescueListFailure(String msg);

    /**
     * 列表为空
     *
     * @param msg
     * @param orderBeans
     */
    void showRefreshNoDate(String msg, List<RescueListBean.RescueOrderBean> orderBeans);


}
