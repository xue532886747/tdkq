package com.example.towerdriver.base_change_phone.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 验证新手机
 * @date 2021/6/18
 */
public interface ICheckNewPhoneView extends BaseView {

    /**
     * 验证手机成功
     *
     * @param type
     * @param msg
     */
    void checkNewPhoneSuccess(int type, String msg,String phone);

    /**
     * 修改手机失败
     *
     * @param type
     * @param msg
     */
    void checkNewPhoneFailure(int type, String msg);

    void onSendCodeSuccess(String phone,String msg);

    void onSendCodeFailure(String msg);

}
