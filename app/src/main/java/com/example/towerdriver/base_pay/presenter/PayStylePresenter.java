package com.example.towerdriver.base_pay.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_pay.model.PayBean;
import com.example.towerdriver.base_pay.model.ZfbBean;
import com.example.towerdriver.base_pay.view.IPayStyleView;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 套餐选择
 * @date 2021/5/24
 */
public class PayStylePresenter extends BasePresenter<IPayStyleView> {
    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public PayStylePresenter(IPayStyleView baseView) {
        super(baseView);
    }


    /**
     * 支付订单
     *
     * @param order_sn
     */
    public void getWxPay(String order_sn) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(order_sn))) {
            params.put("order_sn", String.valueOf(order_sn));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getPay(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<PayBean>() {
            @Override
            public void onSuccess(int code, String msg, PayBean data) {
                if (isAttach()) {
                    getMvpView().onPayWxSuccess(data, msg);
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
                        getMvpView().onPayWxFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 订单续费（微信）
     *
     * @param order_sn
     */
    public void getWxNewPay(String order_sn) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(order_sn))) {
            params.put("order_sn", String.valueOf(order_sn));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getWxNewPay(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<PayBean>() {
            @Override
            public void onSuccess(int code, String msg, PayBean data) {
                if (isAttach()) {
                    getMvpView().onPayWxSuccess(data, msg);
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
                        getMvpView().onPayWxFailure(msg);
                    }
                }
            }
        }));
    }


    /**
     * 订单续费（支付宝）
     *
     * @param order_sn
     */
    public void getZfbNewPay(String order_sn) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(order_sn))) {
            params.put("order_sn", String.valueOf(order_sn));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getZfbNewPay(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ZfbBean>() {
            @Override
            public void onSuccess(int code, String msg, ZfbBean data) {
                if (isAttach()) {
                    getMvpView().onPayZfbSuccess(data, msg);
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
                        getMvpView().onPayZfbFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 支付订单（支付宝）
     *
     * @param order_sn
     */
    public void getZfbPay(String order_sn) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(order_sn))) {
            params.put("order_sn", String.valueOf(order_sn));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getZfbPay(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ZfbBean>() {
            @Override
            public void onSuccess(int code, String msg, ZfbBean data) {
                if (isAttach()) {
                    getMvpView().onPayZfbSuccess(data, msg);
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
                        getMvpView().onPayZfbFailure(msg);
                    }
                }
            }
        }));
    }

}
