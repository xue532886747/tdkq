package com.example.towerdriver.base_pay.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_pay.model.PayBean;
import com.example.towerdriver.base_pay.model.ZfbBean;
import com.example.towerdriver.base_setmenu.model.SelectBean;

/**
 * @author 53288
 * @description 套餐选择
 * @date 2021/5/24
 */
public interface IPayStyleView extends BaseView {


    void onPayWxSuccess(PayBean payBean, String msg);


    void onPayWxFailure(String msg);


    void onPayZfbSuccess(ZfbBean zfbBean, String msg);


    void onPayZfbFailure(String msg);
}
