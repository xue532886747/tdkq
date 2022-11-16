package com.example.towerdriver.station.base_daka.presenter;

import android.text.TextUtils;

import com.baidu.liantian.ac.U;
import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.ResponseBean;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.station.base_daka.model.WorkStatusBean;
import com.example.towerdriver.station.base_daka.view.ISignInView;
import com.example.towerdriver.station.station_order.view.IStationOrderDetailView;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 站长打卡
 * @date 2021/7/1
 */
public class SignInPresenter extends BasePresenter<ISignInView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public SignInPresenter(ISignInView baseView) {
        super(baseView);
    }

    /**
     * 打卡
     *
     * @param type          打卡类型：1=上班，2=下班
     * @param lng           经度
     * @param lat           纬度
     * @param clock_address 详细地址
     */
    public void StationSignIn(String type, String lng, String lat, String clock_address) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(type))) {
            params.put("type", String.valueOf(type));
        }
        if (!TextUtils.isEmpty(String.valueOf(lng))) {
            params.put("lng", String.valueOf(lng));
        }
        if (!TextUtils.isEmpty(String.valueOf(lat))) {
            params.put("lat", String.valueOf(lat));
        }
        if (!TextUtils.isEmpty(String.valueOf(clock_address))) {
            params.put("clock_address", String.valueOf(clock_address));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().StationSignIn(params)).listener(new RxRequest.RequestListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ExceptionHandle handle) {
                if (isAttach()) {
                    getMvpView().showFailed(handle.getCode(), handle.getMsg());
                }

            }

            @Override
            public void onFinish() {

            }
        }).request(new RxRequest.ResultCallback<UsuallyBean>() {
            @Override
            public void onSuccess(int code, String msg, UsuallyBean data) {
                if (isAttach()) {
                    getMvpView().signInSuccess(type, msg,clock_address);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (code == FreeApi.Code.TOKENEXPIRED) {
                    if (isAttach()) {
                        getMvpView().LoadingClose();
                    }
                } else {
                    if (isAttach()) {
                        getMvpView().signInFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 获取打卡状态
     */
    public void StationStatus() {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().StationWorkStatus(params)).listener(new RxRequest.RequestListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ExceptionHandle handle) {
                if (isAttach()) {
                    getMvpView().showFailed(handle.getCode(), handle.getMsg());
                }

            }

            @Override
            public void onFinish() {

            }
        }).request(new RxRequest.ResultCallback<WorkStatusBean>() {
            @Override
            public void onSuccess(int code, String msg, WorkStatusBean data) {
                if (isAttach()) {
                    getMvpView().SignStatusSuccess(data.getWork_status());
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (code == FreeApi.Code.TOKENEXPIRED) {
                    if (isAttach()) {
                        getMvpView().LoadingClose();
                    }
                } else {
                    if (isAttach()) {
                        getMvpView().SignStatusFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 更新位置
     *
     * @param lng
     * @param lat
     */
    public void upDateLocation(String address,String lng, String lat) {
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
                if (isAttach()) {
                    getMvpView().showFailed(handle.getCode(), handle.getMsg());
                }

            }

            @Override
            public void onFinish() {

            }
        }).request(new RxRequest.ResultCallback<UsuallyBean>() {
            @Override
            public void onSuccess(int code, String msg, UsuallyBean data) {
                if (isAttach()) {
                    getMvpView().UpDateAddressSuccess(address,msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (code == FreeApi.Code.TOKENEXPIRED) {
                    if (isAttach()) {
                        getMvpView().LoadingClose();
                    }
                } else {
                    if (isAttach()) {
                        getMvpView().UpDateAddressFailure(msg);
                    }
                }
            }
        }));
    }
}