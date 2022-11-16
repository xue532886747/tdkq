package com.example.towerdriver.base_setmenu.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_setmenu.model.CreateOrderBean;
import com.example.towerdriver.base_setmenu.model.GoodsDetailBean;
import com.example.towerdriver.base_setmenu.model.PriceBean;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.base_setmenu.view.IGoodsDetailView;
import com.example.towerdriver.base_setmenu.view.ISelectMenuView;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 套餐详情
 * @date 2021/6/10
 */
public class GoodsDetailPresenter extends BasePresenter<IGoodsDetailView> {
    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public GoodsDetailPresenter(IGoodsDetailView baseView) {
        super(baseView);
    }

    /**
     * 获取数据
     *
     * @param rent_id
     */
    public void getGoodsDetail(String rent_id) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(rent_id))) {
            params.put("rent_id", rent_id);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getGoodsDetail(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<GoodsDetailBean>() {
            @Override
            public void onSuccess(int code, String msg, GoodsDetailBean data) {
                if (isAttach()) {
                    getMvpView().onGoodsDetailSuccess(data);
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
                        getMvpView().onGoodsDetailFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 计算价格
     *
     * @param rent_id
     */
    public void getGoodsPrice(String rent_id, String num) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(rent_id))) {
            params.put("rent_id", rent_id);
        }
        if (!TextUtils.isEmpty(num)) {
            params.put("num", num);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getPrice(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<PriceBean>() {
            @Override
            public void onSuccess(int code, String msg, PriceBean data) {
                if (isAttach()) {
                    getMvpView().onGoodsPriceSuccess(data.getPay_price(), num);
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
                        getMvpView().onGoodsPriceFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 立即购买
     *
     * @param rent_id
     */
    public void getCreateOrder(String rent_id, String num) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(rent_id))) {
            params.put("rent_id", rent_id);
        }
        if (!TextUtils.isEmpty(num)) {
            params.put("num", num);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getCreateOrder(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<CreateOrderBean>() {
            @Override
            public void onSuccess(int code, String msg, CreateOrderBean data) {
                if (isAttach()) {
                    getMvpView().onCreateOrderSuccess(data.getOrder_sn(), data.getPay_price());
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
                        getMvpView().onCreateOrderFailure(msg);
                    }
                }
            }
        }));
    }


    /**
     * 计算续费价格
     *
     * @param rent_id
     */
    public void getGoodsComputePrice(String rent_id, String num) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(rent_id))) {
            params.put("order_id", rent_id);
        }
        if (!TextUtils.isEmpty(num)) {
            params.put("num", num);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getComputePrice(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<PriceBean>() {
            @Override
            public void onSuccess(int code, String msg, PriceBean data) {
                if (isAttach()) {
                    getMvpView().onGoodsPriceSuccess(data.getPay_price(), num);
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
                        getMvpView().onGoodsPriceFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 立即续费
     *
     * @param rent_id
     */
    public void getComputeOrder(String rent_id, String num) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(rent_id))) {
            params.put("order_id", rent_id);
        }
        if (!TextUtils.isEmpty(num)) {
            params.put("num", num);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            LogUtils.d(UserUtils.getInstance().getUserToken());
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getReCreateOrder(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<CreateOrderBean>() {
            @Override
            public void onSuccess(int code, String msg, CreateOrderBean data) {
                if (isAttach()) {
                    getMvpView().onCreateOrderSuccess(data.getOrder_sn(), data.getPay_price());
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
                        getMvpView().onCreateOrderFailure(msg);
                    }
                }
            }
        }));
    }

}
