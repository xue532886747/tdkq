package com.example.towerdriver.base_forget.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 忘记密码
 * @date 2021/5/24
 */
public interface IForgetPassView extends BaseView {

    /**
     * 忘记密码修改成功
     * @param type 登陆类型
     * @param msg
     */
    void onForgetPassSuccess(int type, String msg);

    void onForgetPassFailure(int type, String msg);

    void onSendCodeSuccess(String msg);

    void onSendCodeFailure(String msg);

}
