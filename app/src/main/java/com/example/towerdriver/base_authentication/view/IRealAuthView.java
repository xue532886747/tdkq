package com.example.towerdriver.base_authentication.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_authentication.model.bean.IdCardBean;
import com.example.towerdriver.member_model.MemberInfoBean;

/**
 * @author 53288
 * @description 百度身份证认证
 * @date 2021/5/25
 */
public interface IRealAuthView extends BaseView {

    /**
     * 图片转为url成功
     */
    void ImageToUrlSuccess(String type, String url);

    /**
     * 图片转为url失败
     */
    void ImageToUrlFailure(String msg);

    /**
     * 获得baidu的token成功
     */
    void onAccessTokenSuccess();

    void onAccessTokenFailure();

    /**
     * 验证身份证正反面
     *
     * @param type       正面或反面
     * @param idCardBean
     */
    void onIdCardAuthSuccess(String type, IdCardBean idCardBean);

    void onIdCardAuthFailure(String msg);

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
