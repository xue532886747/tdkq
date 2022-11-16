package com.example.towerdriver.map;

import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.towerdriver.R;
import com.example.towerdriver.base_main.bean.LngBean;
import com.example.towerdriver.base_main.bean.PickUpBean;
import com.example.towerdriver.base_main.bean.RepairBean;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 53288
 * @description 地图展示模块
 * @date 2021/5/21
 */
public class BDmap implements BaiduMap.OnMapLoadedCallback, BaiduMap.OnMarkerClickListener {
    private MapView mapView;        //从view层获取的id
    private boolean isFirstLoc = true;  //是否是第一次加载地图
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;       //纬度
    private double mCurrentLon = 0.0;       //经度
    private float mCurrentAccracy;          //角度
    private int type = 1;                   //维修点==1，提车点==2；
    private BitmapDescriptor bitmapDot = BitmapDescriptorFactory.fromResource(R.mipmap.loc_pick);  //地图上动态绘制的点
    private BitmapDescriptor bitmapNewDot = BitmapDescriptorFactory.fromResource(R.mipmap.loc_repair);  //地图上动态绘制的点
    private BitmapDescriptor bitmapRescueDot = BitmapDescriptorFactory.fromResource(R.mipmap.log_resuce_point);  //地图上动态绘制的点

    public BDmap(MapView mapView) {
        this.mapView = mapView;
        init();
    }

    /**
     * 实例化
     */
    private void init() {
        if (mapView != null) {
            mapView.showScaleControl(false);                                            //是否显示比例尺控件，默认显示
            mapView.showZoomControls(false);                                            //是否显示缩放按钮控件，默认允许
            mapView.getMap().setMyLocationEnabled(true);                                //开启地图的定位图层
            mapView.getMap().setOnMapLoadedCallback(this);                              //地图加载完成的回调
            mapView.getMap().setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
            mapView.getMap().setOnMarkerClickListener(this);
            mapView.getMap().getUiSettings().setOverlookingGesturesEnabled(false);
            mapView.getMap().getUiSettings().setCompassEnabled(false);                  //指南针
        }
    }

    public void setFirstLoc(boolean firstLoc) {
        isFirstLoc = firstLoc;
    }

    /**
     * 设置提车点
     *
     * @param mList
     */
    public void setPickList(List<PickUpBean.WarehouseBean> mList) {
        if (mapView != null) {
            mapView.getMap().clear();
        }
        for (PickUpBean.WarehouseBean lngBean : mList) {
            LatLng point = new LatLng(Double.parseDouble(lngBean.getLat()), Double.parseDouble(lngBean.getLng()));
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmapDot)
                    .clickable(true).perspective(true);
            if (mapView != null) {
                mapView.getMap().addOverlay(option);
            }
        }
    }

    /**
     * 设置救援点
     *
     * @param lat
     * @param lng
     */
    public void setRescue(String lat, String lng) {
        if (mapView != null) {
            mapView.getMap().clear();
        }
        LatLng point = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmapRescueDot).clickable(true).perspective(true);
        if (mapView != null) {
            mapView.getMap().addOverlay(option);
        }
    }

    /**
     * 设置维修点
     *
     * @param mList
     */
    public void setRepairList(List<RepairBean.StationBean> mList) {
        if (mapView != null) {
            mapView.getMap().clear();
        }
        for (RepairBean.StationBean lngBean : mList) {
            LatLng point = new LatLng(Double.parseDouble(lngBean.getLat()), Double.parseDouble(lngBean.getLng()));
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmapNewDot).clickable(true).perspective(true);
            if (mapView != null) {
                mapView.getMap().addOverlay(option);
            }
        }
    }

    /**
     * 设置传感器
     *
     * @param sensorEvent
     */
    public void setSensorEvent(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            MyLocationData myLocationData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)// 设置定位数据的精度信息，单位：米
                    .direction(mCurrentDirection)// 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(mCurrentLat)
                    .longitude(mCurrentLon)
                    .build();
            if (mapView != null) {
                mapView.getMap().setMyLocationData(myLocationData);
            }

        }
        lastX = x;
    }

    /**
     * 地图加载完成的回调
     */
    @Override
    public void onMapLoaded() {
        LogUtils.d("onMapLoaded()");        //just do it once;
    }

    /**
     * 地图展示在中心位置
     *
     * @param bdLocation
     */
    public void setMyLocation(BDLocation bdLocation, boolean FirstLoc) {
        isFirstLoc = FirstLoc;
        setLocationCenter(bdLocation);
        if (isFirstLoc) {
            isMoveToLoc(bdLocation);
        }
    }

    /**
     * 设置地图上定位的点
     */
    private void setLocationCenter(BDLocation bdLocation) {
        mCurrentLat = bdLocation.getLatitude();
        mCurrentLon = bdLocation.getLongitude();
        mCurrentAccracy = bdLocation.getRadius();
        MyLocationData myLocationData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())                //此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection)
                .latitude(bdLocation.getLatitude())              //纬度   30
                .longitude(bdLocation.getLongitude()).build();   //经度   108
        if (mapView != null) {
            mapView.getMap().setMyLocationData(myLocationData);
        }
    }


    public void setMarkers(int type) {
        this.type = type;
    }

    /**
     * 是否移动到定位点地图处
     *
     * @param bdLocation
     */
    private void isMoveToLoc(BDLocation bdLocation) {
        LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        if (mapView != null) {
            mapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    /**
     * 地图中心
     *
     * @param lat
     * @param lon
     */
    public void MoveToLoc(double lat, double lon) {
        LatLng ll = new LatLng(lat, lon);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        if (mapView != null) {
            mapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }

    }

    public void onResume() {
        if (mapView != null) {
            mapView.onResume();
        }
    }

    public void onPause() {
        if (mapView != null) {
            mapView.onPause();
        }
    }

    public void onDestroy() {
        if (mapView != null) {
//            mapView.getMap().setMyLocationEnabled(false);
//            mapView.getMap().setMyLocationConfiguration(null);
            mapView.onDestroy();
            mapView = null;
        }
    }

    /**
     * Mark的点击事件
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (getMarkerListener != null) {
            getMarkerListener.getMarker(marker);
        }
        return false;
    }

    /**
     *
     */
    public interface getMarkerListener {
        void getMarker(Marker marker);
    }

    public getMarkerListener getMarkerListener;

    public void setGetMarkerListener(BDmap.getMarkerListener getMarkerListener) {
        this.getMarkerListener = getMarkerListener;
    }
}
