package com.example.base_ui.file_picker.util;

import android.content.Context;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * UiUtils
 * Created by 李波 on 2018/2/8.
 */

public class UiUtils {

    /***
     * DP 转 PX
     * @param dipValue
     * @return
     */
    public static int dpToPx(float dipValue) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().getDisplayMetrics());
    }

    public static int getImageResize(RecyclerView recyclerView) {
        int mImageResize;
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        int spanCount = ((GridLayoutManager) lm).getSpanCount();
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        mImageResize = screenWidth / spanCount;
        return mImageResize;
    }

}
