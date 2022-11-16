package com.example.towerdriver.utils;

import android.app.Activity;

import com.example.towerdriver.utils.tools.LogUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author 53288
 * @description activity的管理类
 * @date 2021/5/20
 */
public class ActivityManager {

    private static List<Activity> activities = Collections.synchronizedList(new LinkedList<Activity>());


    public static List<Activity> getActivities() {
        return activities;
    }

    /**
     * 获取当前Activity
     */
    @Nullable
    public static Activity currentActivity() {
        if (activities == null || activities.isEmpty()) {
            return null;
        }
        return activities.get(activities.size() - 1);
    }
    /**
     * 按照指定类名找到activity
     */
    @Nullable
    public static Activity findActivity(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        if (activities == null || activities.isEmpty()) {
            return null;
        }
        for (Activity activity : activities) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }
    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity == null) {
            return;
        }
        if (activities == null || activities.isEmpty()) {
            return;
        }
        activities.remove(activity);
        activity.finish();
        activity = null;
    }
    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<? extends Activity> cls) {
        if (cls == null) {
            return;
        }
        if (activities == null || activities.isEmpty()) {
            return;
        }
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (cls.equals(activity.getClass())) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        LogUtils.d("finishAllActivity()");
        if (activities == null || activities.isEmpty()) {
            return;
        }
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    public static void recreate() {
        if (activities != null && !activities.isEmpty()) {
            for (Activity activity : activities) {
                activity.recreate();
            }
        }
    }
}
