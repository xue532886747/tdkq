package com.example.towerdriver.station.base_daka.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;

/**
 * @author 53288
 * @description 签到
 * @date 2021/7/1
 */
public interface ISignInView extends BaseView {

    /**
     * 获取打卡状态
     *
     * @param work_status
     */
    void SignStatusSuccess(int work_status);


    void SignStatusFailure(String msg);

    /**
     * 更新位置
     *
     * @param msg
     */
    void UpDateAddressSuccess(String address,String msg);


    void UpDateAddressFailure(String msg);

    /**
     * 签到成功
     *
     * @param msg
     */
    void signInSuccess(String type, String msg,String addr);

    /**
     * 签到失败
     *
     * @param msg
     */
    void signInFailure(String msg);

}
