package com.example.towerdriver.base_setting.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_setting.bean.UserLogoutBean;
import com.example.towerdriver.base_setting.view.SetView;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 用户设置（退出）
 * @date 2021/5/19
 */
public class SettingPresenter extends BasePresenter<SetView> {

    /**
     * 绑定view层与p层
     *
     * @param setView
     */
    public SettingPresenter(SetView setView) {
        super(setView);
    }


    public void LoginOut(int type) {
        if (type == 1) {
            UserLoginOut();
        } else if (type == 2) {
            StationLoginOut();
        } else if (type == 3) {
            RepairLoginOut();
        } else if (type == 4) {
            StaffLogout();
        }
    }


    /**
     * 客户端用户退出
     */
    private void UserLoginOut() {
        String userToken = UserUtils.getInstance().getUserToken();
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(userToken)) {
            params.put("token", userToken);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getUserLogout(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserLogoutBean>() {
            @Override
            public void onSuccess(int code, String msg, UserLogoutBean data) {
                if (isAttach()) {
                    new LoginEvent(false, 0).post();
                    getMvpView().UserLogoutSuccess(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().UserLogoutFailure(code, msg);
                    }
                }
            }
        }));
    }

    /**
     * 站长端用户退出
     */
    private void StationLoginOut() {
        String userToken = UserUtils.getInstance().getUserToken();
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(userToken)) {
            params.put("token", userToken);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getStationLogout(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserLogoutBean>() {
            @Override
            public void onSuccess(int code, String msg, UserLogoutBean data) {
                if (isAttach()) {
                    new LoginEvent(false, 0).post();
                    getMvpView().UserLogoutSuccess(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().UserLogoutFailure(code, msg);
                    }
                }
            }
        }));
    }

    /**
     * 维修端用户退出
     */
    private void RepairLoginOut() {
        String userToken = UserUtils.getInstance().getUserToken();
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(userToken)) {
            params.put("token", userToken);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getRepairLogout(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserLogoutBean>() {
            @Override
            public void onSuccess(int code, String msg, UserLogoutBean data) {
                if (isAttach()) {
                    new LoginEvent(false, 0).post();
                    getMvpView().UserLogoutSuccess(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().UserLogoutFailure(code, msg);
                    }
                }
            }
        }));
    }
    /**
     * 员工端用户退出
     */
    private void StaffLogout() {
        String userToken = UserUtils.getInstance().getUserToken();
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(userToken)) {
            params.put("token", userToken);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getStaffLogout(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<UserLogoutBean>() {
            @Override
            public void onSuccess(int code, String msg, UserLogoutBean data) {
                if (isAttach()) {
                    new LoginEvent(false, 0).post();
                    getMvpView().UserLogoutSuccess(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().UserLogoutFailure(code, msg);
                    }
                }
            }
        }));
    }
}
