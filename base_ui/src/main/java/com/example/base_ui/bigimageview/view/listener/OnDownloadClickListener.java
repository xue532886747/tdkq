package com.example.base_ui.bigimageview.view.listener;

import android.app.Activity;
import android.view.View;

/**
 * @author 53288
 * @description
 * @date 2021/6/7
 */
public  abstract class OnDownloadClickListener {

    /**
     * 点击事件
     * 是否拦截下载行为
     */
    public abstract void onClick(Activity activity, View view, int position);

    /**
     * 是否拦截下载
     * @return
     */
    public abstract boolean isInterceptDownload();
}
