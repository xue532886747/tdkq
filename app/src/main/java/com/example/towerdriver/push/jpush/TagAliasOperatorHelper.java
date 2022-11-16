package com.example.towerdriver.push.jpush;


import android.content.Context;
import android.util.Log;

import com.example.towerdriver.utils.tools.LogUtils;

import cn.jpush.android.api.JPushMessage;

/**
 * @author 53288
 * @description 处理tagalias相关的逻辑
 * @date 2021/5/28
 */
public class TagAliasOperatorHelper {

    private Context context;

    private volatile static TagAliasOperatorHelper mInstance;

    private TagAliasOperatorHelper() {
    }

    public static TagAliasOperatorHelper getInstance() {
        if (mInstance == null) {
            synchronized (TagAliasOperatorHelper.class) {
                if (mInstance == null) {
                    mInstance = new TagAliasOperatorHelper();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        if (context != null) {
            this.context = context.getApplicationContext();
        }
    }


    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        LogUtils.d("action - onTagOperatorResult, sequence:" + sequence + ",tags:" + jPushMessage.getTags());
        LogUtils.d("tags size:" + jPushMessage.getTags().size());
        init(context);

        if (jPushMessage.getErrorCode() == 0) {
            LogUtils.d("action - modify tag Success,sequence:" + sequence);
//            ToastHelper.showOk(context,"modify success");
        } else {
            String logs = "Failed to modify tags";
            if (jPushMessage.getErrorCode() == 6018) {
                //tag数量超过限制,需要先清除一部分再add
                logs += ", tags is exceed limit need to clean";
            }
            logs += ", errorCode:" + jPushMessage.getErrorCode();
            LogUtils.d(logs);
            //      ToastHelper.showOther(context,logs);
        }
    }

    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        LogUtils.d("action - onCheckTagOperatorResult, sequence:" + sequence + ",checktag:" + jPushMessage.getCheckTag());
        init(context);
        if (jPushMessage.getErrorCode() == 0) {
            String logs = "modify tag " + jPushMessage.getCheckTag() + " bind state success,state:" + jPushMessage.getTagCheckStateResult();
            LogUtils.d(logs);
            //ToastHelper.showOk(context,"modify success");
        } else {
            String logs = "Failed to modify tags, errorCode:" + jPushMessage.getErrorCode();
            LogUtils.d(logs);
            //    ToastHelper.showOther(context,logs);
        }
    }

    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        LogUtils.d("action - onAliasOperatorResult, sequence:" + sequence + ",alias:" + jPushMessage.getAlias());
        init(context);

        if (jPushMessage.getErrorCode() == 0) {
            LogUtils.d("action - modify alias Success,sequence:" + sequence);
            //    ToastHelper.showOk(context,"modify success");
        } else {
            String logs = "Failed to modify alias, errorCode:" + jPushMessage.getErrorCode();
            LogUtils.d(logs);
            // ToastHelper.showOther(context,logs);
            //MMKV.defaultMMKV().putString(AdvActivity.ALIAS_DATA, "");
        }
    }

    //设置手机号码回调
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        int sequence = jPushMessage.getSequence();
        LogUtils.d("action - onMobileNumberOperatorResult, sequence:" + sequence + ",mobileNumber:" + jPushMessage.getMobileNumber());
        init(context);
        if (jPushMessage.getErrorCode() == 0) {
            LogUtils.d("action - set mobile number Success,sequence:" + sequence);
            // ToastHelper.showOk(context,"modify success");
        } else {
            String logs = "Failed to set mobile number, errorCode:" + jPushMessage.getErrorCode();
            LogUtils.d(logs);
            //ToastHelper.showOther(context,logs);
            //   MMKV.defaultMMKV().putString(AdvActivity.MN_DATA, "");
        }
    }

}
