package com.example.towerdriver.utils.timeutil;

import com.example.towerdriver.utils.tools.LogUtils;

/**
 * @author 53288
 * @description 废弃
 * @date 2021/8/18
 */
@Deprecated
public class TimeTools {

    private final static long yearLevelValue = 365 * 24 * 60 * 60 * 1000;

    private final static long monthLevelValue = 30 * 24 * 60 * 60 * 1000;

    private final static long dayLevelValue = 24 * 60 * 60 * 1000;

    private final static long hourLevelValue = 60 * 60 * 1000;

    private final static long minuteLevelValue = 60 * 1000;

    private final static long secondLevelValue = 1000;

    public static String getDifference(long nowTime, long targetTime) {//目标时间与当前时间差

        long period = targetTime - nowTime;

        return getDifference(period);

    }

    private static String getDifference(long period) {//根据毫秒差计算时间差

        String result = null;

/*******计算出时间差中的年、月、日、天、时、分、秒*******/

        int year = getYear(period);

        int month = getMonth(period - year * yearLevelValue);

        int day = getDay(period - year * yearLevelValue - month * monthLevelValue);

        int hour = getHour(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue);

        int minute = getMinute(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue);

        int second = getSecond(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue - minute * minuteLevelValue);

//        LogUtils.d("day =" + day + " , hour = " + hour + " , minute = " + minute + " , second = " + second);

        result = year + "年" + month + "月" + day + "天" + hour + "时" + minute + "分" + second + "秒";
        return result;
    }

    public static int getYear(long period) {
        return (int) (period / yearLevelValue);

    }

    public static int getMonth(long period) {
        return (int) (period / monthLevelValue);

    }

    public static int getDay(long period) {
        return (int) (period / dayLevelValue);

    }

    public static int getHour(long period) {
        return (int) (period / hourLevelValue);

    }

    public static int getMinute(long period) {
        return (int) (period / minuteLevelValue);

    }

    public static int getSecond(long period) {
        return (int) (period / secondLevelValue);

    }


}
