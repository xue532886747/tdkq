package com.example.towerdriver.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;

/**
 * @author 53288
 * @description 地图跳转工具类
 * @date 2021/6/16
 */
public class MapUtil {
    public final static String BAIDU_PKG = "com.baidu.BaiduMap"; //百度地图的包名

    public final static String GAODE_PKG = "com.autonavi.minimap";//高德地图的包名

    public static void openGaoDe(Context context, double latitude, double longitude) {
        double[] doubles = GPSUtil.bd09_To_Gcj02(latitude, longitude);
        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://navi?sourceApplication=你的APP名称&lat=" + doubles[0] + "&lon=" + doubles[1] + "&dev=0"));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

    public static void openBaidu(Context context, double latitude, double longitude, String name) {
        Intent i1 = new Intent();
        double[] position = GPSUtil.gcj02_To_Bd09(latitude, longitude);
        //   i1.setData(Uri.parse("baidumap://map/direction?region=beijing&origin=39.98871,116.43234&destination=西直门&coord_type=bd09ll&mode=driving&src=andr.baidu.openAPIdemo"));
        //i1.setData(Uri.parse("baidumap://map/direction?region=beijing&origin=" + markerOption.getPosition().latitude + "," + markerOption.getPosition().longitude+"&destination=太白南路&coord_type=bd09ll&mode=driving&src=andr.baidu.openAPIdemo"));
        i1.setData(Uri.parse("baidumap://map/direction?destination=latlng:" + latitude + "," + longitude + "|name:" + name + "&mode=driving"));
        context.startActivity(i1);
    }

    /**
     * 检测地图应用是否安装
     *
     * @param context
     * @param packagename
     * @return
     */
    public static boolean checkMapAppsIsExist(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
}
