package com.example.towerdriver.utils;

import com.example.towerdriver.R;
import com.example.towerdriver.utils.sp.SettingUtils;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * @author 53288
 * @description 夜间模式切换
 * @date 2021/5/20
 */
public class NightModeUtils {

    public static void initNightMode() {
        if (SettingUtils.getInstance().isDarkTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }
}
