package com.example.towerdriver.station.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.baidu.mapapi.map.Marker;
import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.core.RxLife;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.map.NewLocationManager;
import com.example.towerdriver.station.interfaces.UpdateUiCallBack;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.disposables.Disposable;

/**
 * @author 53288
 * @description 开启一个服务，用于用户实时更新坐标
 * @date 2021/8/3
 */
public class SignService extends Service implements Runnable {
    private SignBinder signBinder;
    private UpdateUiCallBack updateUiCallBack;  //用户与绑定的activity页面更新ui
    private NewLocationManager newLocationManager;  //定位的管理类
    private RxLife rxLife;
    private boolean isBinder = false;
    private boolean isUpdateFailure = false;
    private String mAddress;
    private double mLatitude;
    private double mLongitude;
    private boolean timeIsCanDo = false;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            upDateLocation(mAddress, mLatitude + "", mLongitude + "");
        }
    };

    public void setUpdateUiCallBack(UpdateUiCallBack updateUiCallBack) {
        this.updateUiCallBack = updateUiCallBack;
    }


    public class SignBinder extends Binder {
        public SignService getService() {
            return SignService.this;
        }
    }

    @Override
    public void run() {
        Message message = Message.obtain();
        message.obj = 1;
        mHandler.sendMessage(message);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d("onCreate()");
        signBinder = new SignBinder();
        newLocationManager = new NewLocationManager(null, 5000);
        newLocationManager.setGetLocationListener(new NewLocationManager.getLocationListener() {
            @Override
            public void getMarker(Marker marker) {

            }

            @Override
            public void getLocationMsg(String msg) {

            }

            @Override
            public void getLocationSuccess(String addr, String address, double latitude, double longitude, boolean isFirstLoc, int type) {
                LogUtils.d(addr);
                mAddress = addr;
                mLatitude = longitude;
                mLongitude = longitude;
                if (updateUiCallBack != null) {
                    updateUiCallBack.updateUi(addr, latitude, longitude);
                }
                if (!isBinder && isUpdateFailure) {    //如果解绑成功，则开始在后台刷新接口
                    upDateLocation(addr, latitude + "", longitude + "");
                }
            }

            @Override
            public void getLocationRescue(String address, boolean isRescue) {

            }
        });
        newLocationManager.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("onStartCommand()");
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d("onBind()");
        isBinder = true;
        isUpdateFailure = true;
        return signBinder;
    }

    /**
     * 当服务与activity解绑时，我们就在本页面启动调用接口
     *
     * @param intent
     * @return
     */
    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.d("onUnbind()");
        if (updateUiCallBack != null) {
            updateUiCallBack = null;
        }
        isBinder = false;
        timeIsCanDo = true;
        startUpdateLocation();
        return super.onUnbind(intent);
    }

    /**
     * 启动更新位置
     */
    private void startUpdateLocation() {
        rxLife = RxLife.create();
    }

    public void addToRxLife(Disposable disposable) {
        if (rxLife != null) {
            rxLife.add(disposable);
        }
    }

    /**
     * 更新位置
     *
     * @param lng
     * @param lat
     */
    public void upDateLocation(String address, String lng, String lat) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(lng))) {
            params.put("lng", String.valueOf(lng));
        }
        if (!TextUtils.isEmpty(String.valueOf(lat))) {
            params.put("lat", String.valueOf(lat));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().StationUpdateAddress(params)).listener(new RxRequest.RequestListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ExceptionHandle handle) {
                isUpdateFailure = false;
                timeIsCanDo = false;
            }

            @Override
            public void onFinish() {

            }
        }).request(new RxRequest.ResultCallback<UsuallyBean>() {
            @Override
            public void onSuccess(int code, String msg, UsuallyBean data) {
                isUpdateFailure = true;
                timeIsCanDo = false;
                LogUtils.d(code + "");
            }

            @Override
            public void onFailed(int code, String msg) {
                isUpdateFailure = false;
                timeIsCanDo = false;
                if (code == FreeApi.Code.TOKENEXPIRED) {

                } else {

                }
            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy()");
        if (newLocationManager != null) {
            newLocationManager.setGetLocationListener(null);
            newLocationManager.stop();
            newLocationManager = null;
        }
        if (rxLife != null) {
            rxLife.destroy();
            rxLife = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(this);
        }
    }
}
