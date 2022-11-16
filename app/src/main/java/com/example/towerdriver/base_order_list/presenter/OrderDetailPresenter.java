package com.example.towerdriver.base_order_list.presenter;

import android.icu.lang.UScript;
import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_order_list.model.ChangeCarBean;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_order_list.view.IOrderDetailView;
import com.example.towerdriver.base_order_list.view.IOrderListView;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description 订单列表
 * @date 2021/5/31
 */
public class OrderDetailPresenter extends BasePresenter<IOrderDetailView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public OrderDetailPresenter(IOrderDetailView baseView) {
        super(baseView);
    }


    /**
     * 订单详情
     *
     * @param order_id
     */
    public void getOrderList(String order_id) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(order_id))) {
            params.put("order_id", String.valueOf(order_id));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getOrderDetail(params)).listener(new RxRequest.RequestListener() {
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

    /**
     * 换车
     *
     * @param order_id
     */
    public void getReplaceOrder(String order_id, String frame_number) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(order_id)) {
            params.put("order_id", order_id);
        }
        if (!TextUtils.isEmpty(frame_number)) {
            params.put("frame_number", frame_number);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getReplaceCar(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ChangeCarBean>() {
            @Override
            public void onSuccess(int code, String msg, ChangeCarBean data) {
                if (isAttach()) {
                    getMvpView().changeCarSuccess(msg,data);
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
                        getMvpView().changeCarFailure(msg);
                    }
                }
            }
        }));
    }
}
