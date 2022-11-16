package com.example.towerdriver.map;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.example.towerdriver.base_main.bean.PickUpBean;
import com.example.towerdriver.base_main.bean.RepairBean;
import com.example.towerdriver.utils.ThreadPoolUtil;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.List;

/**
 * @author 53288
 * @description 整个地图的管理类
 * @date 2021/5/21
 */
public class LocationManager implements ILocationCallBack, ISensorCallBack {
    @Override
    public void getLocationSuccess(String addr, double latitude, double longitude, String city, BDLocation location, boolean isFirstLoc, int type) {

    }

    @Override
    public void getLocationFailure(String msg, boolean isFirstLoc) {

    }

    @Override
    public void getSensor(SensorEvent sensorEvent) {

    }

    @Override
    public void getAccuracy(Sensor sensor, int i) {

    }
//    private static final LocationManager INSTANCE = new LocationManager();    //采用单例模式
//    private BDmap bDmap;
//
//    private LocationManager() {
//        LocController.getInstance().setILocationCallBack(this);
//    }
//
//    public static LocationManager getInstance() {
//        return INSTANCE;
//    }
//
//    public void setMapView(MapView mapView) {
//        bDmap = new BDmap(mapView);
//        bDmap.setGetMarkerListener(new BDmap.getMarkerListener() {
//            @Override
//            public void getMarker(Marker marker) {
//                if (getLocationListener != null) {
//                    getLocationListener.getMarker(marker);
//                }
//            }
//        });
//    }
//
//    public BDmap getbDmap() {
//        return bDmap;
//    }
//
//    /**
//     * 设置提车点
//     *
//     * @param list
//     */
//    public void setPickList(List<PickUpBean.WarehouseBean> list) {
//        if (bDmap != null) {
//            bDmap.setPickList(list);
//        }
//    }
//
//
//    /**
//     * 设置维修点
//     *
//     * @param list
//     */
//    public void setRepairList(List<RepairBean.StationBean> list) {
//        if (bDmap != null) {
//            bDmap.setRepairList(list);
//        }
//    }
//
//
//    /**
//     * 设置救援定位点
//     *
//     * @param lat
//     * @param lng
//     */
//    public void setRescue(String lat, String lng) {
//        if (bDmap != null) {
//            bDmap.setRescue(lat,lng);
//        }
//    }
//
//    /**
//     * 获取定位成功
//     *
//     * @param latitude   纬度
//     * @param longitude  经度
//     * @param city       城市
//     * @param location   BDLocation对象
//     * @param isFirstLoc 是否是首次定位
//     */
//    @Override
//    public void getLocationSuccess(String addr, double latitude, double longitude, String city, BDLocation location, boolean isFirstLoc, int type) {
//        LogUtils.d("addr = " + addr + "latitude =" + latitude + ", longitude = " + longitude + ", city = " + city + ", isFirstLoc = " + isFirstLoc);
//        if (bDmap != null) {
//            bDmap.setMyLocation(location, isFirstLoc);
//        }
//        if (getLocationListener != null) {
//            getLocationListener.getLocationSuccess(addr, city, latitude, longitude, isFirstLoc, type);
//        }
//    }
//
//    @Override
//    public void getLocationFailure(String msg, boolean isFirstLoc) {
//        if (getLocationListener != null) {
//            getLocationListener.getLocationMsg(msg);
//        }
//    }
//
//    /**
//     * 开启定位
//     */
//    public void start() {
//        ThreadPoolUtil.getInstance().execute(new Runnable() {
//            @Override
//            public void run() {
//                LocController.getInstance().start();
//            }
//        });
//    }
//
//    private boolean getLocCo() {
//        if (LocController.getInstance().getLocationClient() != null) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public void getSensor(SensorEvent sensorEvent) {
//        if (bDmap != null) {
//            bDmap.setSensorEvent(sensorEvent);
//        }
//    }
//
//    @Override
//    public void getAccuracy(Sensor sensor, int i) {
//
//    }
//
//    /**
//     * 停止定位
//     */
//    public void stop() {
//        if (getLocationListener != null) {
//            getLocationListener = null;
//        }
//        LocController.getInstance().stop();
//    }
//
//    /**
//     * 单次定位
//     */
//    public void gotoCenter(int type) {
//        LocController.getInstance().setFirstLoc(true, type);
//        LocController.getInstance().setCenter();
//    }
//
//
//    /**
//     * 回调给manager层
//     */
//    public interface getLocationListener {
//        /**
//         * 获得点位
//         *
//         * @param marker
//         */
//        void getMarker(Marker marker);
//
//        void getLocationMsg(String msg);
//
//        /**
//         * 获得定位成功
//         *
//         * @param address
//         * @param isFirstLoc
//         */
//        void getLocationSuccess(String addr, String address, double latitude, double longitude, boolean isFirstLoc, int type);
//
//        /**
//         * 救援获取定位
//         *
//         * @param address
//         * @param isRescue
//         */
//        void getLocationRescue(String address, boolean isRescue);
//
//    }
//
//    public getLocationListener getLocationListener;
//
//    public void setGetLocationListener(getLocationListener getLocationListener) {
//        this.getLocationListener = getLocationListener;
//    }


}
