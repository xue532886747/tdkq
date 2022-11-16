package com.example.towerdriver.utils.tools;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author 53288
 * @description px转dp 或 dp转px
 * @date 2021/5/22
 */
public class DisPlayUtils {

    public static float dp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }


    public static float px2dp(float value) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (value / scale + 0.5f);
    }
}
