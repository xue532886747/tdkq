package com.example.towerdriver.repair.base_person.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 维修端
 * @date 2021/7/4
 */
public interface IRepairCenterView extends BaseView {


    /**
     * 更换头像成功
     *
     * @param type 登陆类型
     * @param img  头像
     * @param msg
     */
    void changeImgSuccess(int type, String img, String msg);

    /**
     * 更换头像失败
     *
     * @param type
     * @param msg
     */
    void changeImgFailure(int type, String msg);



}
