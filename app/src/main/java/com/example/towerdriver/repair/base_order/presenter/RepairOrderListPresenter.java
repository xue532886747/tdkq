package com.example.towerdriver.repair.base_order.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.repair.base_order.model.RepairListBean;
import com.example.towerdriver.repair.base_order.view.IRepairOrderListView;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description 维修端订单
 * @date 2021/7/5
 */
public class RepairOrderListPresenter extends BasePresenter<IRepairOrderListView> {
    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public RepairOrderListPresenter(IRepairOrderListView baseView) {
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
        addToRxLife(RxHttp.request(FreeApi.api().getAgentOrderList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<RepairListBean>() {
            @Override
            public void onSuccess(int code, String msg, RepairListBean data) {
                if (data != null && isAttach()) {
                    Integer total_page = data.getTotal_page();
                    List<RepairListBean.OrderBean> order_list = data.getOrder();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(msg, order_list);
                    } else {
                        getMvpView().repairListSuccess(order_list, page, total_page, fresh);
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
                        getMvpView().repairListFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 添加订单
     */
    public void addOrder(String phone) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getAgentAddOrder(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().repairAddSuccess(msg);
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
                        getMvpView().repairAddFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 删除订单
     *
     * @param order_id
     * @param position
     */
    public void deleteOrder(String order_id, int position) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(order_id)) {
            params.put("order_id", order_id);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().deleteAgentOrder(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().deleteOrderSuccess(position, msg);
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
                        getMvpView().deleteOrderFailure(msg);
                    }
                }
            }
        }));
    }
}
