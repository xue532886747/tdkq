package com.example.towerdriver.map;

import com.baidu.location.BDLocation;

/**
 * @author 53288
 * @description 获取定位的接口回调
 * @date 2021/5/20
 */
public interface ILocationCallBack {

    /**
     * 获取定位成功
     * @param addr 详细地址
     * @param latitude   纬度
     * @param longitude  经度
     * @param city       城市
     * @param location   BDLocation对象
     * @param isFirstLoc 是否是首次定位
     */
    void getLocationSuccess(String addr,double latitude, double longitude, String city, BDLocation location, boolean isFirstLoc, int type);

    /**
     * 获取定位失败
     *
     * @param msg 失败原因
     */
    void getLocationFailure(String msg, boolean isFirstLoc);
}
