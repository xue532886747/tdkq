package com.example.towerdriver.base_drivier_coin.present;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_driver.model.bean.ReleaseDeleteBean;
import com.example.towerdriver.base_drivier_coin.model.DriverCoinBean;
import com.example.towerdriver.base_drivier_coin.view.IDriverCoinView;
import com.example.towerdriver.base_invite.model.InviteBean;
import com.example.towerdriver.base_invite.view.InviteListView;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description 骑行币列表
 * @date 2021/6/19
 */
public class IDriverCoinPresenter extends BasePresenter<IDriverCoinView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public IDriverCoinPresenter(IDriverCoinView baseView) {
        super(baseView);
    }


    /**
     * 邀请列表
     *
     * @param page  当前页数
     * @param fresh
     */
    public void getDriverCoinList(@IntRange(from = 0) int page, boolean fresh) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(page))) {
            params.put("page", String.valueOf(page));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        params.put("type", UserUtils.getInstance().getLoginType() + "");

        addToRxLife(RxHttp.request(FreeApi.api().getCommissionList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<DriverCoinBean>() {
            @Override
            public void onSuccess(int code, String msg, DriverCoinBean data) {
                if (data != null && isAttach()) {
                    Integer total_page = data.getTotal_page();
                    List<DriverCoinBean.WithdrawBean> order_list = data.getWithdraw();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(msg, order_list,data.getBalance());
                    } else {
                        getMvpView().IDriverCoinSuccess(order_list, page, total_page, fresh, data.getBalance());
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
                        getMvpView().IDriverCoinFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 提现
     * @param code price
     * @param code id
     */
    public void tixian(@NotNull String price,@NotNull String code, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (!TextUtils.isEmpty(code)) {
            params.put("code", code);
        }
        if (!TextUtils.isEmpty(price)) {
            params.put("price", price);
        }
        params.put("type", UserUtils.getInstance().getLoginType() + "");
        addToRxLife(RxHttp.request(FreeApi.api().getTixian(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().tixianSuccess(msg);
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
                        getMvpView().tixianFailure(msg);
                    }
                }
            }
        }));
    }


}
