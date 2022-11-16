package com.example.towerdriver.utils;

import com.example.towerdriver.utils.sp.SettingUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * @author 53288
 * @description
 * @date 2021/5/20
 */
public class LightAndDarkModeUtils {

    private AppCompatActivity appCompatActivity;

    public LightAndDarkModeUtils(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;

    }

    public void setMode() {
        if (SettingUtils.getInstance().isDarkTheme()) {
            appCompatActivity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            appCompatActivity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}
