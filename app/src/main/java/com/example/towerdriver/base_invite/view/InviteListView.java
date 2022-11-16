package com.example.towerdriver.base_invite.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_invite.model.InviteBean;
import com.example.towerdriver.base_order_list.model.OrderListBean;

import java.util.List;

/**
 * @author 53288
 * @description  订单列表
 * @date 2021/6/17
 */
public interface InviteListView extends BaseView {

    /**
     * 列表成功
     *
     * @param listBeans 列表数据
     * @param cur_page    当前页面
     * @param total_page  总页面
     * @param listBeans fresh
     */
    void InviteListSuccess(List<InviteBean.ListBean> listBeans, int cur_page, int total_page, boolean fresh,int count);

    /**
     * 列表失败
     *
     * @param msg
     */
    void InviteListFailure(String msg);

    /**
     * 列表为空
     *
     * @param msg
     * @param orderBeans
     */
    void showRefreshNoDate(String msg, List<InviteBean.ListBean> listBeans);


}
