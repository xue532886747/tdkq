package com.example.towerdriver.base_change_phone.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 验证老手机
 * @date 2021/6/18
 */
public interface ICheckOldPhoneView extends BaseView {

    /**
     * 验证手机成功
     *
     * @param type
     * @param msg
     */
    void checkOldPhoneSuccess(int type, String msg);

    /**
     * 修改手机失败
     *
     * @param type
     * @param msg
     */
    void checkOldPhoneFailure(int type, String msg);

    void onSendCodeSuccess(String phone,String msg);

    void onSendCodeFailure(String msg);

}
