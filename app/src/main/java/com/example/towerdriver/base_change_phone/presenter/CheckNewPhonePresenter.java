package com.example.towerdriver.base_change_phone.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_change_phone.model.NewPhoneBean;
import com.example.towerdriver.base_change_phone.view.ICheckNewPhoneView;
import com.example.towerdriver.base_change_phone.view.ICheckOldPhoneView;
import com.example.towerdriver.base_sendcode.model.ISendCodeModel;
import com.example.towerdriver.base_sendcode.model.SendCodeModel;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 修改绑定手机, 验证新手机
 * @date 2021/6/18
 */
public class CheckNewPhonePresenter extends BasePresenter<ICheckNewPhoneView> implements ISendCodeModel {
    private SendCodeModel sendCodeModel;

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public CheckNewPhonePresenter(ICheckNewPhoneView baseView) {
        super(baseView);

    }

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @param type  类型
     */
    public void getVerificationCode(String phone,int login_type, String type) {
        sendCodeModel = new SendCodeModel(this);
        sendCodeModel.sendCode(phone,login_type, type);
    }

    @Override
    public void getCodeSuccess(String phone, String msg) {
        if (isAttach()) {
            getMvpView().onSendCodeSuccess(phone, msg);
        }
    }

    @Override
    public void getCodeFailure(String msg) {
        if (isAttach()) {
            getMvpView().onSendCodeFailure(msg);
        }
    }

    @Override
    public void getCodeElseFailure(int code, String msg) {
        if (isAttach()) {
            getMvpView().showFailed(code, msg);
        }
    }

    /**
     * 验证新手机
     *
     * @param code 验证码
     */
    public void checkNewPhone(String newphone, String code, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (!TextUtils.isEmpty(newphone)) {
            params.put("newphone", newphone);
        }
        if (!TextUtils.isEmpty(code)) {
            params.put("code", code);
        }
        if (type == 1) {
            ClientCheckOldPhone(params, type);
        }
    }

    /**
     * 修改客户端
     *
     * @param params
     * @param type
     */
    private void ClientCheckOldPhone(Map<String, String> params, int type) {
        addToRxLife(RxHttp.request(FreeApi.api().getCheckNewPhone(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<NewPhoneBean>() {
            @Override
            public void onSuccess(int code, String msg, NewPhoneBean data) {
                if (isAttach()) {
                    getMvpView().checkNewPhoneSuccess(type, msg, data.getPhone());
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().checkNewPhoneFailure(type, msg);
                    }
                }
            }
        }));
    }

    @Override
    public void detachView() {
        super.detachView();
        if (sendCodeModel != null) {
            sendCodeModel.unCodeModel();
        }
    }
}
