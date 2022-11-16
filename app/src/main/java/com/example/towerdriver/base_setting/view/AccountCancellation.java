package com.example.towerdriver.base_setting.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 账号注销
 * @date 2021/7/28
 */
public interface AccountCancellation extends BaseView {

    void cancellationSuccess(String msg);

    void cancellationFailure(String msg);

}
