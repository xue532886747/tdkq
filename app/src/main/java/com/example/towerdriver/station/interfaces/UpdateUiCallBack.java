package com.example.towerdriver.station.interfaces;

/**
 * @author 53288
 * @description
 * @date 2021/8/3
 */
public interface UpdateUiCallBack {

    /**
     * 获取定位的精确位置
     *
     * @param address   位置
     * @param latitude  精度
     * @param longitude 纬度
     */
    void updateUi(String address, double latitude, double longitude);


}
