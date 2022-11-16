package com.example.towerdriver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author 53288
 * @description 时间戳
 * @date 2021/5/23
 */
public class TimeUtil {


    public static String getTimeStamp() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        return timeStamp;
    }
}
