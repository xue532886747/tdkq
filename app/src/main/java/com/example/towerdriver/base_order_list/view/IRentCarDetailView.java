package com.example.towerdriver.base_order_list.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.EntrepotBean;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;

/**
 * @author 53288
 * @description 提车信息
 * @date 2021/6/25
 */
public interface IRentCarDetailView extends BaseView {

    /**
     * 提车信息
     */
    void onRentCarDetailSuccess(String msg, OrderStatusBean orderStatusBean);


    void onRentCarDetailFailure(String msg);


    /**
     * 提车信息
     */
    void onEntrepotSuccess(String msg, EntrepotBean entrepotBean);


    void onEntrepotFailure(String msg);



    /**
     * 文件转为url成功
     */
    void ImageToUrlSuccess(String url);

    /**
     * 文件转为url失败
     */
    void ImageToUrlFailure(String msg);
}
