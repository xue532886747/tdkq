package com.example.towerdriver.base_invite.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_invite.model.InviteBean;
import com.example.towerdriver.base_invite.view.IChangeInviteView;
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
 * @description 更换联系人
 * @date 2021/8/2
 */
public class ChangeInvitePresenter extends BasePresenter<IChangeInviteView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public ChangeInvitePresenter(IChangeInviteView baseView) {
        super(baseView);
    }


    /**
     * 邀请人
     */
    public void getInviteList(@NotNull String phone) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            LogUtils.d(UserUtils.getInstance().getUserToken());
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getChangeInvite(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().changeInviteSuccess(msg);

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
                        getMvpView().changeInviteFailure(msg);
                    }
                }
            }
        }));
    }
}
