package com.example.towerdriver.utils.sp;

/**
 * @author 53288
 * @description 保存最后一次定位的值
 * @date 2021/5/21
 */
public class LocationUtils {
    private static final String SP_NAME = "map_location";
    private static final String KEY_LAT = "KEY_LAT";        //纬度
    private static final String KEY_LNG = "KEY_LNG";        //经度

    private final SPUtils mSPUtils = SPUtils.newInstance(SP_NAME);

    public static LocationUtils newInstance() {
        return new LocationUtils();
    }

    private LocationUtils() {

    }

}
