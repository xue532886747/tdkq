package com.example.towerdriver.station.station_order.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.base_order_list.view.IOrderDetailView;
import com.example.towerdriver.station.station_order.view.IStationOrderDetailView;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 站长订单详情
 * @date 2021/6/30
 */
public class StationOrderDetailPresenter extends BasePresenter<IStationOrderDetailView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public StationOrderDetailPresenter(IStationOrderDetailView baseView) {
        super(baseView);
    }


    /**
     * 订单详情
     *
     * @param order_id
     */
    public void getOrderDetail(String order_id) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(order_id))) {
            params.put("order_id", String.valueOf(order_id));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getStationOrderDetail(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<OrderDetailBean>() {
            @Override
            public void onSuccess(int code, String msg, OrderDetailBean data) {
                if (isAttach()) {
                    getMvpView().orderDetailSuccess(data);
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
                        getMvpView().orderDetailFailure(msg);
                    }
                }
            }
        }));
    }
}