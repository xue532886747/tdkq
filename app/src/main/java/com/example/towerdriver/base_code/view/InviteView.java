package com.example.towerdriver.base_code.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 邀请好友二维码
 * @date 2021/5/31
 */
public interface InviteView extends BaseView {

    /**
     * 二维码页面
     */
    void getCodeSuccess(String type, String url);

    /**
     * 二维码页面
     */
    void getCodeFailure(String msg);

}
