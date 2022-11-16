package com.example.meiqia;

import android.os.Bundle;
import android.util.Log;

import com.meiqia.core.MQManager;
import com.meiqia.meiqiasdk.activity.MQConversationActivity;
import com.meiqia.meiqiasdk.callback.MQActivityLifecycleCallback;
import com.meiqia.meiqiasdk.util.MQConfig;

/**
 * @author 53288
 * @description 客服
 * @date 2021/6/21
 */
public class NewCustomerServiceActivity extends MQConversationActivity implements MQActivityLifecycleCallback {
    private static final String TAG = "NewCustomerServiceActivity1";

    private ChangeNoticeDotListener changeNoticeDotListener;

    public void setChangeNoticeDotListener(ChangeNoticeDotListener changeNoticeDotListener) {
        this.changeNoticeDotListener = changeNoticeDotListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MQConfig.setActivityLifecycleCallback(this);
    }

    private void changeNotice() {
        if (changeNoticeDotListener != null) {
            changeNoticeDotListener.changeNotice();
        }
    }

    @Override
    protected void onDestroy() {
        if (changeNoticeDotListener != null) {
            changeNoticeDotListener = null;
        }
        MQConfig.setActivityLifecycleCallback(null);
        MQManager.getInstance(this).setClientOffline();
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(MQConversationActivity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated ");
    }

    @Override
    public void onActivityStarted(MQConversationActivity activity) {
        Log.d(TAG, "onActivityStarted ");
    }

    @Override
    public void onActivityResumed(MQConversationActivity activity) {
        Log.d(TAG, "onActivityResumed ");
    }

    @Override
    public void onActivityPaused(MQConversationActivity activity) {
        Log.d(TAG, "onActivityPaused ");
    }

    @Override
    public void onActivityStopped(MQConversationActivity activity) {
        Log.d(TAG, "onActivityStopped ");
    }

    @Override
    public void onActivitySaveInstanceState(MQConversationActivity activity, Bundle outState) {
        Log.d(TAG, "onActivitySaveInstanceState ");
    }

    @Override
    public void onActivityDestroyed(MQConversationActivity activity) {
        Log.d(TAG, "onActivityDestroyed");
        changeNotice();
    }

}
