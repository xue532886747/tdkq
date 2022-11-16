package com.example.towerdriver.station.station_rescue.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_rescue.model.RescueListBean;
import com.example.towerdriver.base_rescue.view.IRescueListView;
import com.example.towerdriver.station.station_rescue.model.RescueBean;
import com.example.towerdriver.station.station_rescue.model.StationRescueBean;
import com.example.towerdriver.station.station_rescue.view.IStationRescueListView;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description 救援列表
 * @date 2021/6/30
 */
public class StationRescueListPresenter extends BasePresenter<IStationRescueListView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public StationRescueListPresenter(IStationRescueListView baseView) {
        super(baseView);
    }


    /**
     * 救援列表
     *
     * @param page  当前页数
     * @param fresh
     */
    public void getRescueList(@IntRange(from = 0) int page, boolean fresh) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(page))) {
            params.put("page", String.valueOf(page));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getStationRescueList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<StationRescueBean>() {
            @Override
            public void onSuccess(int code, String msg, StationRescueBean data) {
                if (data != null && isAttach()) {
                    Integer total_page = data.getTotal_page();
                    List<StationRescueBean.OrderBean> order_list = data.getOrder();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(msg, order_list);
                    } else {
                        getMvpView().rescueListSuccess(order_list, page, total_page, fresh);
                    }
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
                        getMvpView().rescueListFailure(msg);
                    }
                }
            }
        }));
    }


    /**
     * 救援接单
     *
     * @param rescue_id
     * @param position
     */
    public void getReceiverOrder(String rescue_id, int position) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(rescue_id))) {
            params.put("rescue_id", String.valueOf(rescue_id));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().stationReceiverOrder(params)).listener(new RxRequest.RequestListener() {
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
                if (data != null && isAttach()) {
                    getMvpView().receiverOrderSuccess(msg, position, data);
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
                        getMvpView().receiverOrderFailure(msg);
                    }
                }
            }
        }));
    }
}
