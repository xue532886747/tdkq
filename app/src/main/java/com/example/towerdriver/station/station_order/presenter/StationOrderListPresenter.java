package com.example.towerdriver.station.station_order.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_order_list.view.IOrderListView;
import com.example.towerdriver.station.station_order.view.IStationOrderListView;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description
 * @date 2021/6/30
 */
public class StationOrderListPresenter extends BasePresenter<IStationOrderListView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public StationOrderListPresenter(IStationOrderListView baseView) {
        super(baseView);
    }


    /**
     * 订单列表
     *
     * @param page  当前页数
     * @param fresh
     */
    public void getOrderList(@IntRange(from = 0) int page, boolean fresh) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(page))) {
            params.put("page", String.valueOf(page));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getStationOrderList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<OrderListBean>() {
            @Override
            public void onSuccess(int code, String msg, OrderListBean data) {
                if (data != null && isAttach()) {
                    Integer total_page = data.getTotal_page();
                    List<OrderListBean.OrderBean> order_list = data.getOrder();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(msg, order_list);
                    } else {
                        getMvpView().orderListSuccess(order_list, page, total_page, fresh);
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
                        getMvpView().orderListFailure(msg);
                    }
                }
            }
        }));
    }

}
