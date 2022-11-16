package com.example.towerdriver.base_member_level.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_main.bean.AdvertiBean;
import com.example.towerdriver.base_member_level.model.bean.LevelBean;

import java.util.List;

/**
 * @author 53288
 * @description 会员等级
 * @date 2021/5/25
 */
public interface MemberLevelView extends BaseView {

    /**
     * 获取等级成功
     * @param memberInfoBean  用户信息
     * @param list 用户列表
     * @param msg
     */
    void getLevelSuccess(LevelBean.MemberInfoBean memberInfoBean, List<LevelBean.OperateIntegralBean> list, String msg);

    /**
     * 失败
     * @param msg
     */
    void getLevelFailure(String msg);

}
