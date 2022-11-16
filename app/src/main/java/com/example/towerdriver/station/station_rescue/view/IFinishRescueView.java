package com.example.towerdriver.station.station_rescue.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.station.station_rescue.model.RescueBean;

/**
 * @author 53288
 * @description 完成救援
 * @date 2021/7/2
 */
public interface IFinishRescueView extends BaseView {

    /**
     * 完成救援成功
     *
     * @param msg
     */
    void finishRescueSuccess(String msg, RescueBean rescueBean);

    /**
     * 完成救援失败
     *
     * @param msg
     */
    void finishRescueFailure(String msg);


    /**
     * 图片转为url成功
     */
    void imageToUrlSuccess(String url);

    /**
     * 图片转为url失败
     */
    void imageToUrlFailure(String msg);
}
