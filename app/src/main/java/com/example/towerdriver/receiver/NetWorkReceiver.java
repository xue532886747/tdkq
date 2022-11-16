package com.example.towerdriver.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.widget.Toast;

import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.utils.tools.LogUtils;

/**
 * @author 53288
 * @description 监听网络状态
 * @date 2021/6/3
 */
public class NetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (parcelableExtra != null) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state== NetworkInfo.State.CONNECTED;
                LogUtils.d("isConnected = "+isConnected);
                if(isConnected){

                }else {

                }
            }
        }
    }

}
