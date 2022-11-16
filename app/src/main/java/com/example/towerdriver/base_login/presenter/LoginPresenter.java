package com.example.towerdriver.base_login.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.ResponseBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_login.bean.UserBean;
import com.example.towerdriver.base_login.view.LoginView;
import com.example.towerdriver.base_register.model.bean.RegisterBean;
import com.example.towerdriver.base_sendcode.model.ISendCodeModel;
import com.example.towerdriver.base_sendcode.model.SendCodeModel;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * @author 53288
 * @description 登陆
 * @date 2021/5/21
 */
public class LoginPresenter extends BasePresenter<LoginView> implements ISendCodeModel {
    private SendCodeModel sendCodeModel;

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public LoginPresenter(LoginView baseView) {
        super(baseView);
    }


    /**
     * 发送验证码
     *
     * @param phone      手机号
     * @param login_type 登陆类型
     * @param type       验证码类型
     */
    public void getVerificationCode(String phone, int login_type, String type) {
        sendCodeModel = new SendCodeModel(this);
        sendCodeModel.sendCode(phone, login_type, type);
    }

    /**
     * 用户登陆
     *
     * @param userName   用户名
     * @param password   用户密码
     * @param login_type 用户登陆方式(1密码 2.验证码)
     * @param type       用户登陆类型(1.客户端，2站长端，3员工端，4.维修端)
     */
    public void sendLogin(String userName, String password, int login_type, int type) {
        if (login_type == 1) {
            if (type == 1) {
                sendClientLogin(userName, password, 1);
            } else if (type == 2) {
                sendStationLogin(userName, password, 2);
            } else if (type == 3) {
                sendRepairLogin(userName, password, 3);
            } else if (type == 4) {
                sendStaffLogin(userName, password, 4);
            }
        } else if (login_type == 2) {
            if (type == 1) {
                sendClientPhoneLogin(userName, password, 1);
            } else if (type == 2) {
                sendStationPhoneLogin(userName, password, 2);
            } else if (type == 3) {
                sendRepairPhoneLogin(userName, password, 3);
            } else if (type == 4) {
                sendStaffPhoneLogin(userName, password, 4);
            }
        }
    }


    /**
     * 客户端登陆
     *
     * @param phone    用户名
     * @param password 用户密码
     */
    private void sendClientLogin(String phone, String password, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getUserLogin(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserBean>() {
            @Override
            public void onSuccess(int code, String msg, UserBean data) {
                if (isAttach()) {
                    if (data != null) {
                        LogUtils.d(data.toString());
                        Login(data, type, msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().LoginFailed(msg);
                }
            }
        }));
    }

    /**
     * 站长端登陆
     *
     * @param phone    用户名
     * @param password 用户密码
     */
    private void sendStationLogin(String phone, String password, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getStationUserLogin(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserBean>() {
            @Override
            public void onSuccess(int code, String msg, UserBean data) {
                if (isAttach()) {
                    if (data != null) {
                        LogUtils.d(data.toString());
                        Login(data, type, msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().LoginFailed(msg);
                }
            }
        }));
    }

    /**
     * 维修端登陆
     *
     * @param phone
     * @param password
     * @param type
     */
    private void sendRepairLogin(String phone, String password, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getAgentUserLogin(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserBean>() {
            @Override
            public void onSuccess(int code, String msg, UserBean data) {
                if (isAttach()) {
                    if (data != null) {
                        LogUtils.d(data.toString());
                        Login(data, type, msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().LoginFailed(msg);
                }
            }
        }));
    }

    /**
     * 站长端登陆
     *
     * @param phone
     * @param password
     * @param type
     */
    private void sendStaffLogin(String phone, String password, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(password)) {
            params.put("password", password);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getStaffUserLogin(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserBean>() {
            @Override
            public void onSuccess(int code, String msg, UserBean data) {
                if (isAttach()) {
                    if (data != null) {
                        LogUtils.d(data.toString());
                        Login(data, type, msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().LoginFailed(msg);
                }
            }
        }));
    }


    /**
     * 客户端登陆(验证码)
     *
     * @param phone 用户名
     * @param code  用户密码
     */
    private void sendClientPhoneLogin(String phone, String code, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(code)) {
            params.put("code", code);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getUserPhoneLogin(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserBean>() {
            @Override
            public void onSuccess(int code, String msg, UserBean data) {
                if (isAttach()) {
                    if (data != null) {
                        Login(data, type, msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().LoginFailed(msg);
                }
            }
        }));
    }

    /**
     * 站长端登陆(验证码)
     *
     * @param phone 用户名
     * @param code  用户密码
     */
    private void sendStationPhoneLogin(String phone, String code, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(code)) {
            params.put("code", code);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getStationPhoneLogin(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserBean>() {
            @Override
            public void onSuccess(int code, String msg, UserBean data) {
                if (isAttach()) {
                    if (data != null) {
                        Login(data, type, msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().LoginFailed(msg);
                }
            }
        }));
    }

    /**
     * 站长端登陆(验证码)
     *
     * @param phone 用户名
     * @param code  用户密码
     */
    private void sendRepairPhoneLogin(String phone, String code, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(code)) {
            params.put("code", code);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getRepairPhoneLogin(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserBean>() {
            @Override
            public void onSuccess(int code, String msg, UserBean data) {
                if (isAttach()) {
                    if (data != null) {
                        Login(data, type, msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().LoginFailed(msg);
                }
            }
        }));
    }

    /**
     * 员工端登陆(验证码)
     *
     * @param phone 用户名
     * @param code  用户密码
     */
    private void sendStaffPhoneLogin(String phone, String code, int type) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(code)) {
            params.put("code", code);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getStaffPhoneLogin(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserBean>() {
            @Override
            public void onSuccess(int code, String msg, UserBean data) {
                if (isAttach()) {
                    if (data != null) {
                        Login(data, type, msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().LoginFailed(msg);
                }
            }
        }));
    }

    private void Login(UserBean data, int type, String msg) {
        data.setLogin_type(type);
        UserUtils.getInstance().login(data);
        if (isAttach()) {
            getMvpView().LoginSuccess(msg, type);
        }
        new LoginEvent(true, type).post();
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


    @Override
    public void detachView() {
        super.detachView();
        if (sendCodeModel != null) {
            sendCodeModel.unCodeModel();
        }
    }
}
