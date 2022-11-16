package com.example.base_ui.badgeview;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author 53288
 * @description px转dp 或 dp转px
 * @date 2021/5/22
 */
public class DisPlayUtils {

    public static int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }

    public static int px2dp(float value){
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (value / scale + 0.5f);
    }
}
