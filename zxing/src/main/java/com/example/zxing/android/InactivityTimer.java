package com.example.zxing.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.util.Log;

/**
 * @author 53288
 * @description 如果设备使用电池供电，一段时间不活动之后结束activity
 * @date 2021/5/28
 */
public class InactivityTimer {
    private static final String TAG = InactivityTimer.class.getSimpleName();

    private static final long INACTIVITY_DELAY_MS = 5 * 60 * 1000L;

    private final Activity activity;
    private final BroadcastReceiver powerStatusReceiver;
    private boolean registered;
    private AsyncTask<Object, Object, Object> inactivityTask;

    public InactivityTimer(Activity activity) {
        this.activity = activity;
        powerStatusReceiver = new PowerStatusReceiver();
        registered = false;
    }

    public synchronized void onActivity() {
        inactivityTask = new InactivityAsyncTask();
        inactivityTask.execute();
    }

    public synchronized void onPause() {
        if (registered) {
            activity.unregisterReceiver(powerStatusReceiver);
            registered = false;
        } else {
            Log.w(TAG, "PowerStatusReceiver was never registered?");
        }
    }

    public synchronized void onResume() {
        if (registered) {
            Log.w(TAG, "PowerStatusReceiver was already registered?");
        } else {
            activity.registerReceiver(powerStatusReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            registered = true;
        }
    }

    public synchronized void cancel() {
        AsyncTask<?, ?, ?> task = inactivityTask;
        if (task != null) {
            task.cancel(true);
            inactivityTask = null;
        }
    }

    public void shutdown() {
        cancel();
    }

    /**
     * 电量状态receiver
     *
     * @author qichunjie
     */
    private final class PowerStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                boolean onBatterNow = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) <= 0;
                if (onBatterNow) {
                    InactivityTimer.this.onActivity();
                } else {
                    InactivityTimer.this.cancel();
                }
            }
        }
    }

    /**
     * 设备休眠5分钟，关闭activity
     *
     * @author qichunjie
     */
    private final class InactivityAsyncTask extends
            AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... objects) {
            try {
                Thread.sleep(INACTIVITY_DELAY_MS);
                Log.i(TAG, "Finishing activity due to inactivity");
                activity.finish();
            } catch (InterruptedException e) {
                // continue without killing
            }
            return null;
        }
    }
}
