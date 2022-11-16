package com.example.towerdriver.map;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.baidu.location.BDLocation;

/**
 * @author 53288
 * @description 获取传感器接口回调
 * @date 2021/5/21
 */
public interface ISensorCallBack {


    void getSensor(SensorEvent sensorEvent);

    void getAccuracy(Sensor sensor, int i);
}
