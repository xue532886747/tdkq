package com.example.base_ui.bigimageview.view.listener;

import android.view.View;

/**
 * @author 53288
 * @description 原图加载百分比接口
 * @date 2021/6/7
 */
public interface OnOriginProgressListener {


    /**
     * 加载中
     */
    void progress(View parentView, int progress);

    /**
     * 加载完成
     */
    void finish(View parentView);
}
