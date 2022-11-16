package com.example.towerdriver.weight;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;

import com.youth.banner.Banner;

/**
 * @author 53288
 * @description
 * @date 2021/6/28
 */
public class MyBanner extends Banner {
    public MyBanner(Context context) {
        this(context,null);
    }

    public MyBanner(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);

    }
}
