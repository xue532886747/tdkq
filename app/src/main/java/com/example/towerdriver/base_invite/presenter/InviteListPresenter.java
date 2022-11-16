package com.example.towerdriver.base_invite.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_invite.model.InviteBean;
import com.example.towerdriver.base_invite.view.InviteListView;
import com.example.towerdriver.base_order_list.model.OrderListBean;
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
 * @date 2021/6/18
 */
public class InviteListPresenter extends BasePresenter<InviteListView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public InviteListPresenter(InviteListView baseView) {
        super(baseView);
    }


    /**
     * 邀请列表
     *
     * @param page  当前页数
     * @param fresh
     */
    public void getInviteList(@IntRange(from = 0) int page, boolean fresh) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(page))) {
            params.put("page", String.valueOf(page));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            LogUtils.d(UserUtils.getInstance().getUserToken());
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getInviteList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<InviteBean>() {
            @Override
            public void onSuccess(int code, String msg, InviteBean data) {
                if (data != null && isAttach()) {
                    Integer total_page = data.getTotal_page();
                    List<InviteBean.ListBean> order_list = data.getList();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(msg, order_list);
                    } else {
                        getMvpView().InviteListSuccess(order_list, page, total_page, fresh,data.getCount());
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
                        getMvpView().InviteListFailure(msg);
                    }
                }
            }
        }));
    }
}
