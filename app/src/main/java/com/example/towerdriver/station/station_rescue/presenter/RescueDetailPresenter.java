package com.example.towerdriver.station.station_rescue.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.station.station_order.view.IStationOrderDetailView;
import com.example.towerdriver.station.station_rescue.model.RescueBean;
import com.example.towerdriver.station.station_rescue.model.RescueDetailBean;
import com.example.towerdriver.station.station_rescue.view.IStationRescueDetailView;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 救援详情
 * @date 2021/6/30
 */
public class RescueDetailPresenter extends BasePresenter<IStationRescueDetailView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public RescueDetailPresenter(IStationRescueDetailView baseView) {
        super(baseView);
    }


    /**
     * 站长端救援详情
     *
     * @param rescue_id
     */
    public void getStationRescueOrderDetail(String rescue_id) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(rescue_id))) {
            params.put("rescue_id", String.valueOf(rescue_id));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().stationRescueDetail(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<RescueDetailBean>() {
            @Override
            public void onSuccess(int code, String msg, RescueDetailBean data) {
                if (isAttach()) {
                    getMvpView().stationDetailSuccess(data);
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
                        getMvpView().stationDetailFailure(msg);
                    }
                }
            }
        }));
    }

}