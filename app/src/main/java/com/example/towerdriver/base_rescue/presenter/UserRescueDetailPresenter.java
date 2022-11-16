package com.example.towerdriver.base_rescue.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_rescue.model.RescueBean;
import com.example.towerdriver.base_rescue.view.IRescueDetailView;
import com.example.towerdriver.station.station_rescue.model.RescueDetailBean;
import com.example.towerdriver.station.station_rescue.view.IStationRescueDetailView;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 救援详情
 * @date 2021/7/2
 */
public class UserRescueDetailPresenter extends BasePresenter<IRescueDetailView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public UserRescueDetailPresenter(IRescueDetailView baseView) {
        super(baseView);
    }



    /**
     * 救援详情
     *
     * @param rescue_id
     */
    public void getRescueOrderDetail(String rescue_id) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(rescue_id))) {
            params.put("rescue_id", String.valueOf(rescue_id));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().RescueDetail(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<RescueBean>() {
            @Override
            public void onSuccess(int code, String msg, RescueBean data) {
                if (isAttach()) {
                    getMvpView().UserDetailSuccess(data);
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
                        getMvpView().UserDetailFailure(msg);
                    }
                }
            }
        }));
    }
}