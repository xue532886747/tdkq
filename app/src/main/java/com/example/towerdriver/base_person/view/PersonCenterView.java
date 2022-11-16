package com.example.towerdriver.base_person.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.member_model.MemberInfoBean;

/**
 * @author 53288
 * @description 个人中心的view
 * @date 2021/5/19
 */
public interface PersonCenterView extends BaseView {


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
}
