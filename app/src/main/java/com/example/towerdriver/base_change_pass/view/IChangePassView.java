package com.example.towerdriver.base_change_pass.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 修改密码
 * @date 2021/6/3
 */
public interface IChangePassView extends BaseView {

    /**
     * 修改密码成功
     *
     * @param type
     * @param msg
     */
    void changePassWordSuccess(int type, String msg);

    /**
     * 修改密码失败
     *
     * @param type
     * @param msg
     */
    void changePassFailure(int type, String msg);
}
