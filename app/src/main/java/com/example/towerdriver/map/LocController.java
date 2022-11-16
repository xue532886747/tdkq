package com.example.towerdriver.map;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.utils.tools.LogUtils;

/**
 * @author 53288
 * @description 此模块只负责定位，定位于view层无关，所以设计成单例模式，只有一个实例对象
 * @date 2021/5/21
 */
public class LocController extends BDAbstractLocationListener {
    private ILocationCallBack iLocationCallBack;        //回调给view层的接口
  //  private static final LocController INSTANCE = new LocController();    //采用单例模式
    private LocationClient locationClient;              //获取定位的核心类
    private LocationClientOption option;                //配置定位SDK参数
    private boolean isFirstLoc = true;                  //是否是首次定位
    private int type = 1;
    private LocationClient locationClient_one;              //获取定位的核心类
    private LocationClientOption option_one;                //配置定位SDK参数

    public LocController(int time) {
        initOption(time);
    }


    public void setILocationCallBack(ILocationCallBack iLocationCallBack) {
        this.iLocationCallBack = iLocationCallBack;
    }

    /**
     * LocationMode.High_Accuracy：高精度；
     * LocationMode. Battery_Saving：低功耗；
     * LocationMode. Device_Sensors：仅使用设备；
     * GCJ02：国测局坐标；
     * BD09ll：百度经纬度坐标；
     * BD09：百度墨卡托坐标；
     * 可选，设置发起定位请求的间隔，int类型，单位ms
     * 如果设置为0，则代表单次定位，即仅定位一次，默认为0
     * 如果设置非0，需设置1000ms以上才有效
     */
    private void initOption(int time) {
        option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);                  //是否需要地址信息
        option.setCoorType("bd09ll");                   //坐标系
        option.setOpenGps(true);                        //可选，设置是否使用gps，默认false 使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setIgnoreKillProcess(false);             //可选，定位SDK内部是一个service，并放到了独立进程。
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        option.setNeedNewVersionRgc(true);               //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        option.setScanSpan(time);                       //定位间隔
        locationClient = new LocationClient(MyApplication.getContext(), option);
        locationClient.registerLocationListener(this);
        LogUtils.d("initOption locationClient = " + locationClient.toString());
    }

//    public static LocController getInstance() {
//        return INSTANCE;
//    }

    public void setFirstLoc(boolean firstLoc, int type) {
        isFirstLoc = firstLoc;
        this.type = type;
    }

    public LocationClient getLocationClient() {
        return locationClient;
    }

    public LocationClientOption getOption() {
        return option;
    }

    /**
     * 获取到到定位的回调
     *
     * @param bdLocation
     */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        int errorCode = bdLocation.getLocType();
        LogUtils.d("onReceiveLocation = " + errorCode);
        if (locationClient_one != null) {
            locationClient_one.stop();
            option_one = null;
            locationClient_one = null;
        }
        if (errorCode == BDLocation.TypeNetWorkLocation || errorCode == BDLocation.TypeGpsLocation) {
            double latitude = bdLocation.getLatitude();    //获取纬度信息
            double longitude = bdLocation.getLongitude();    //获取经度信息
            float radius = bdLocation.getRadius();//获取定位精度，默认值为0.0f
            String coorType = bdLocation.getCoorType();  //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String addr = bdLocation.getAddrStr();    //获取详细地址信息
            String country = bdLocation.getCountry();    //获取国家
            String province = bdLocation.getProvince();    //获取省份
            String city = bdLocation.getCity();    //获取城市
            String district = bdLocation.getDistrict();    //获取区县
            String street = bdLocation.getStreet();    //获取街道信息
            String adcode = bdLocation.getAdCode();    //获取adcode
            String town = bdLocation.getTown();    //获取乡镇信息
            if (iLocationCallBack != null) {
                iLocationCallBack.getLocationSuccess(addr,latitude, longitude, city, bdLocation, isFirstLoc, type);
            }
        } else if (iLocationCallBack != null && errorCode == BDLocation.TypeOffLineLocationFail) {
            iLocationCallBack.getLocationFailure("定位失败，请您检查您的网络状态!", isFirstLoc);
        } else if (iLocationCallBack != null && errorCode == BDLocation.TypeCriteriaException) {
            iLocationCallBack.getLocationFailure("定位失败，无法获取任何有效定位依据,请确认您定位的开关打开！", isFirstLoc);
        } else if (iLocationCallBack != null && errorCode == BDLocation.TypeServerError) {
            iLocationCallBack.getLocationFailure("定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限！", isFirstLoc);
        } else if (iLocationCallBack != null) {
            iLocationCallBack.getLocationFailure("定位失败！", isFirstLoc);
        }
        isFirstLoc = false;
    }


    /**
     * 一个变量
     */
    public void setCenter() {
        option_one = new LocationClientOption();
        option_one.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option_one.setIsNeedAddress(true);                  //是否需要地址信息
        option_one.setCoorType("bd09ll");                   //坐标系
        option_one.setOpenGps(true);                        //可选，设置是否使用gps，默认false 使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option_one.setIgnoreKillProcess(false);             //可选，定位SDK内部是一个service，并放到了独立进程。
        option_one.setWifiCacheTimeOut(5 * 60 * 1000);
        option_one.setEnableSimulateGps(false);
        option_one.setNeedNewVersionRgc(true);               //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        option_one.setScanSpan(0);                       //定位间隔
        locationClient_one = new LocationClient(MyApplication.getContext(), option);
        locationClient_one.registerLocationListener(this);
        locationClient_one.start();
    }

    public void start() {
        if (locationClient != null) {
            locationClient.start();
        }
    }

    public void stop() {
        isFirstLoc = true;
        if (locationClient != null) {
            locationClient.stop();
        }
        if (locationClient_one != null) {
            locationClient_one.stop();
        }
    }
}
