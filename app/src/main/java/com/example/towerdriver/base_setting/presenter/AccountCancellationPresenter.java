package com.example.towerdriver.base_setting.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_setting.view.AccountCancellation;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 用户注销
 * @date 2021/7/28
 */
public class AccountCancellationPresenter extends BasePresenter<AccountCancellation> {

    /**
     * 绑定view层与p层
     *
     * @param setView
     */
    public AccountCancellationPresenter(AccountCancellation setView) {
        super(setView);
    }


    /**
     * 客户端用户注销
     */
    public void userAccountCancel(String password) {
        String userToken = UserUtils.getInstance().getUserToken();
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(userToken)) {
            params.put("token", userToken);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getWriteOff(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().cancellationSuccess(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().cancellationFailure(msg);
                    }
                }
            }
        }));
    }

}
