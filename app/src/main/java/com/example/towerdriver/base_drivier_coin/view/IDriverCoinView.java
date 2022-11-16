package com.example.towerdriver.base_drivier_coin.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_drivier_coin.model.DriverCoinBean;
import com.example.towerdriver.base_invite.model.InviteBean;

import java.util.List;

/**
 * @author 53288
 * @description 骑行币列表
 * @date 2021/6/19
 */
public interface IDriverCoinView extends BaseView {

    /**
     * 列表成功
     *
     * @param withdrawBeans 列表数据
     * @param cur_page      当前页面
     * @param total_page    总页面
     */
    void IDriverCoinSuccess(List<DriverCoinBean.WithdrawBean> withdrawBeans, int cur_page, int total_page, boolean fresh, String count);

    /**
     * 列表失败
     *
     * @param msg
     */
    void IDriverCoinFailure(String msg);

    /**
     * 列表为空
     *
     * @param msg
     * @param
     */
    void showRefreshNoDate(String msg, List<DriverCoinBean.WithdrawBean> withdrawBeans,String banalce);


    /**
     * 提现成功
     * @param msg
     */
    void tixianSuccess(String msg);

    /**
     * 提现失败
     * @param msg
     */
    void tixianFailure(String msg);
}
