package com.example.towerdriver.base_forget.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_forget.view.IForgetPassView;
import com.example.towerdriver.base_register.model.bean.RegisterBean;
import com.example.towerdriver.base_register.view.IRegisterView;
import com.example.towerdriver.base_sendcode.model.ISendCodeModel;
import com.example.towerdriver.base_sendcode.model.SendCodeModel;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 忘记密码
 * @date 2021/5/24
 */
public class ForgetPassPresenter extends BasePresenter<IForgetPassView> implements ISendCodeModel {
    private SendCodeModel sendCodeModel;

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public ForgetPassPresenter(IForgetPassView baseView) {
        super(baseView);

    }

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @param type  类型
     */
    public void getVerificationCode(String phone, int login_type, String type) {
        sendCodeModel = new SendCodeModel(this);
        sendCodeModel.sendCode(phone, login_type, type);
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

    /**
     * 忘记密码
     *
     * @param phone      手机号
     * @param code       验证码
     * @param password   密码
     * @param repetition 重复密码
     */
    public void forgetPass(String phone, String code, String password, String repetition, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(code)) {
            params.put("code", code);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put("newpassword", password);
        }
        if (!TextUtils.isEmpty(repetition)) {
            params.put("repassword", repetition);
        }
        if (type == 1) {
            ClientChangePass(params, type);
        } else if (type == 2) {
            StationChangePass(params, type);
        } else if (type == 3) {
            RepairChangePass(params, type);
        } else if (type == 4) {
            StaffChangePass(params, type);
        }
    }

    /**
     * 修改客户端
     *
     * @param params
     * @param type
     */
    private void ClientChangePass(Map<String, String> params, int type) {
        addToRxLife(RxHttp.request(FreeApi.api().ForgetPassClient(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().onForgetPassSuccess(type, msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().onForgetPassFailure(type, msg);
                }
            }
        }));
    }

    /**
     * 修改站长端
     *
     * @param params
     * @param type
     */
    private void StationChangePass(Map<String, String> params, int type) {
        addToRxLife(RxHttp.request(FreeApi.api().ForgetStationPassClient(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().onForgetPassSuccess(type, msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().onForgetPassFailure(type, msg);
                }
            }
        }));
    }

    /**
     * 修改站长端
     *
     * @param params
     * @param type
     */
    private void RepairChangePass(Map<String, String> params, int type) {
        addToRxLife(RxHttp.request(FreeApi.api().ForgetRepairPassClient(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().onForgetPassSuccess(type, msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().onForgetPassFailure(type, msg);
                }
            }
        }));
    }

    /**
     * 修改员工端
     *
     * @param params
     * @param type
     */
    private void StaffChangePass(Map<String, String> params, int type) {
        addToRxLife(RxHttp.request(FreeApi.api().ForgetStaffPassClient(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().onForgetPassSuccess(type, msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().onForgetPassFailure(type, msg);
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
