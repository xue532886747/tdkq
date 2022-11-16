package com.example.zxing.android;

import android.app.Activity;
import android.content.DialogInterface;

/**
 * @author 53288
 * @description 相机会在手电筒可能被占用的情况下退出
 * @date 2021/5/28
 */
public final class FinishListener implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    private final Activity activityToFinish;

    public FinishListener(Activity activityToFinish) {
        this.activityToFinish = activityToFinish;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        run();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        run();
    }

    private void run() {
        activityToFinish.finish();
    }
}
