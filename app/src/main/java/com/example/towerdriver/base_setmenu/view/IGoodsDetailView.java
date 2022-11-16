package com.example.towerdriver.base_setmenu.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_setmenu.model.GoodsDetailBean;
import com.example.towerdriver.base_setmenu.model.SelectBean;

/**
 * @author 53288
 * @description 产品详情
 * @date 2021/6/10
 */
public interface IGoodsDetailView extends BaseView {

    /**
     * 产品详情
     */
    void onGoodsDetailSuccess(GoodsDetailBean goodsDetailBean);


    void onGoodsDetailFailure(String msg);

    /**
     * 产品价格
     *
     * @param price
     */
    void onGoodsPriceSuccess(String price,String number);


    void onGoodsPriceFailure(String msg);

    /**
     * 生成订单
     *
     * @param price
     */
    void onCreateOrderSuccess(String order_sn,String price);


    void onCreateOrderFailure(String msg);
}
