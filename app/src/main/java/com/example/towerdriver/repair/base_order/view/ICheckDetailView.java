package com.example.towerdriver.repair.base_order.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;

/**
 * @author 53288
 * @description
 * @date 2021/7/5
 */
public interface ICheckDetailView extends BaseView {
    /**
     * 提车信息
     */
    void onRentCarDetailSuccess(String msg, OrderStatusBean orderStatusBean);


    void onRentCarDetailFailure(String msg);


    /**
     * 文件转为url成功
     */
    void ImageToUrlSuccess(String url);

    /**
     * 文件转为url失败
     */
    void ImageToUrlFailure(String msg);
}
