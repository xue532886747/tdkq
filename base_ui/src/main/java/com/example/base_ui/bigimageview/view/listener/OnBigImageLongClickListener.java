package com.example.base_ui.bigimageview.view.listener;

import android.app.Activity;
import android.view.View;

/**
 * @author 53288
 * @description
 * @date 2021/6/7
 */
public interface OnBigImageLongClickListener {

    /**
     * 长按事件
     */
    boolean onLongClick(Activity activity, View view, int position);
}
