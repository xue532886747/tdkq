package com.example.towerdriver.base_main.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_main.bean.AdvertiBean;
import com.example.towerdriver.base_main.bean.PickUpBean;
import com.example.towerdriver.base_main.bean.RepairBean;
import com.example.towerdriver.bean.VersionBean;
import com.example.towerdriver.member_model.MemberInfoBean;

import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/5/19
 */
public interface MainView extends BaseView {

    /**
     * 获取轮播图成功
     *
     * @param msg
     * @param advBean
     */
    void showLunboSuccess(String msg, List<AdvertiBean.AdvBean> advBean);

    void showLunboFailed(String msg);

    /**
     * 获取提车点成功
     *
     * @param list
     */
    void showPickUpPointSuccess(List<PickUpBean.WarehouseBean> list);

    void showPickUpPointFailure(String msg);

    /**
     * 获取维修点成功
     *
     * @param list
     */
    void showRepairPointSuccess(List<RepairBean.StationBean> list);

    void showRepairFailure(String msg);

    /**
     * 发起救援成功
     */
    void showCreateRescueSuccess(String msg);


    void showCreateRescueFailure(String msg);

    /**
     * 获得用户信息成功
     *
     * @param memberInfoBean
     */
    void getMemberInfoSuccess(MemberInfoBean memberInfoBean);

    /**
     *
     */
    void getMemberInfoFailure(String msg);


    void getVersionSuccess(VersionBean version);

    /**
     * 消息红点
     */
    void showRedDotSuccess(int number);


    void showRedDotFailure(String msg);

}
