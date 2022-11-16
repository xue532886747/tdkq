package com.example.towerdriver.base_register.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.ResponseBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_register.model.bean.RegisterBean;
import com.example.towerdriver.base_sendcode.model.ISendCodeModel;
import com.example.towerdriver.base_sendcode.model.SendCodeModel;
import com.example.towerdriver.base_register.view.IRegisterView;
import com.hjq.toast.ToastUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * @author 53288
 * @description 注册页面
 * @date 2021/5/24
 */
public class RegisterPresenter extends BasePresenter<IRegisterView> implements ISendCodeModel {

    private SendCodeModel sendCodeModel;

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public RegisterPresenter(IRegisterView baseView) {
        super(baseView);

    }

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @param type  类型
     */
    public void getVerificationCode(String phone, String type) {
        sendCodeModel = new SendCodeModel(this);
        sendCodeModel.sendCode(phone,1, type);
    }

    /**
     * 用户注册
     *
     * @param phone      手机号
     * @param code       验证码
     * @param password   密码
     * @param repetition 重复密码
     */
    public void register(String phone, String code, String password, String repetition) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(code)) {
            params.put("code", code);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }
        if (!TextUtils.isEmpty(repetition)) {
            params.put("repetition", repetition);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getUserRegister(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<RegisterBean>() {
            @Override
            public void onSuccess(int code, String msg, RegisterBean data) {
                if (isAttach()) {
                    getMvpView().onRegisterSuccess(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().onRegisterFailure(msg);
                }

            }
        }));
    }

    @Override
    public void getCodeSuccess(String phone, String msg) {
        if (isAttach()) {
            getMvpView().onSendCodeSuccess(msg);
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


    @Override
    public void detachView() {
        super.detachView();
        if (sendCodeModel != null) {
            sendCodeModel.unCodeModel();
        }
    }
}
