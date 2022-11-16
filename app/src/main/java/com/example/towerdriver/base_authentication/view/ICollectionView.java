package com.example.towerdriver.base_authentication.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_authentication.model.bean.IdCardBean;
import com.example.towerdriver.base_authentication.model.bean.PersonVerifyBean;

/**
 * @author 53288
 * @description 百度身份证认证
 * @date 2021/5/25
 */
public interface ICollectionView extends BaseView {

    /**
     * 验证身份成功
     *
     * @param personVerifyBean
     */
    void onVerifySuccess(PersonVerifyBean personVerifyBean);

    /**
     * 验证身份失败
     *
     * @param msg
     */
    void onVerifyFailure(String msg,int code);

    /**
     * 上传验证认证成功
     * @param msg
     */
    void onApproveSuccess(String msg);


    void onApproveFailure(String msg);

}
