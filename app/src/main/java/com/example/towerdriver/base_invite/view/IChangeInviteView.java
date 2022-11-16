package com.example.towerdriver.base_invite.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_invite.model.InviteBean;

import java.util.List;

/**
 * @author 53288
 * @description 更换联系人
 * @date 2021/8/2
 */
public interface IChangeInviteView extends BaseView {

    /**
     * 更换联系人
     */
    void changeInviteSuccess(String msg);

    /**
     * 更换联系人
     */
    void changeInviteFailure(String msg);


}
