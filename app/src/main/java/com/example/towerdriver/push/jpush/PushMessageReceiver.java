package com.example.towerdriver.push.jpush;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.rxhttp.request.utils.JsonFormatUtils;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base_main.ui.activity.MainActivity;
import com.example.towerdriver.push.bean.NoticeBean;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.webview.NoticeWebViewActivity;
import com.google.gson.Gson;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * @author 53288
 * @description 极光核心类
 * @date 2021/5/28
 */
public class PushMessageReceiver extends JPushMessageReceiver {


    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        LogUtils.d("[onMessage] " + customMessage);
        Intent intent = new Intent("com.jiguang.demo.message");
        intent.putExtra("msg", customMessage.message);
        context.sendBroadcast(intent);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        LogUtils.d("[onNotifyMessageOpened] " + message);
        try {
            //打开自定义的Activity
            Intent i = new Intent(context, NoticeWebViewActivity.class);
            String notificationExtras = message.notificationExtras;
//            String format = JsonFormatUtils.format(notificationExtras);
            Gson gson = new Gson();
            NoticeBean noticeBean = gson.fromJson(notificationExtras, NoticeBean.class);
            LogUtils.d(noticeBean.toString());
            Bundle bundle = new Bundle();
            bundle.putString(JPushInterface.EXTRA_NOTIFICATION_TITLE, message.notificationTitle);
            bundle.putString(JPushInterface.EXTRA_ALERT, message.notificationContent);
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("title", message.notificationTitle);
            i.putExtra("html", noticeBean.getMessage());
            i.putExtra("isNeedShare", true);
            context.startActivity(i);
        } catch (Throwable throwable) {
            LogUtils.d(throwable.toString());
        }
    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        LogUtils.d("[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if (nActionExtra == null) {
            LogUtils.d("ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        if (nActionExtra.equals("my_extra1")) {
            LogUtils.d("[onMultiActionClicked] 用户点击通知栏按钮一");
        } else if (nActionExtra.equals("my_extra2")) {
            LogUtils.d("[onMultiActionClicked] 用户点击通知栏按钮二");
        } else if (nActionExtra.equals("my_extra3")) {
            LogUtils.d("[onMultiActionClicked] 用户点击通知栏按钮三");
        } else {
            LogUtils.d("[onMultiActionClicked] 用户点击通知栏按钮未定义");
        }
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        int badgeNumber = MyApplication.getBadgeNumber();
        badgeNumber++;
        MyApplication.setBadgeNumber(badgeNumber);
        setBadgeNum(badgeNumber, context);
        LogUtils.d("[onNotifyMessageArrived] " + message);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        LogUtils.e("[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        LogUtils.d("[onRegister] " + registrationId);
        Intent intent = new Intent("com.jiguang.demo.register");
        context.sendBroadcast(intent);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        LogUtils.d("[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        LogUtils.d("[onCommandResult] " + cmdMessage);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onTagOperatorResult(context, jPushMessage);
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onCheckTagOperatorResult(context, jPushMessage);
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onAliasOperatorResult(context, jPushMessage);
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        TagAliasOperatorHelper.getInstance().onMobileNumberOperatorResult(context, jPushMessage);
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        super.onNotificationSettingsCheck(context, isOn, source);
        LogUtils.d("[onNotificationSettingsCheck] isOn:" + isOn + ",source:" + source);
    }


    /**
     * 华为手机添加角标
     *
     * @param num
     * @param context
     */
    public void setBadgeNum(int num, Context context) {
        try {
            Bundle bunlde = new Bundle();
            bunlde.putString("package", "com.example.towerdriver");
            bunlde.putInt("badgenumber", num);  //MainActivity WelcomeActivity
            bunlde.putString("class", "com.example.towerdriver.base_welcome.ui.WelComeActivity");
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
