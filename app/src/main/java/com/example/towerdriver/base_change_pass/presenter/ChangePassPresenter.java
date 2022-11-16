package com.example.towerdriver.base_change_pass.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.ResponseBean;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_change_pass.view.IChangePassView;
import com.example.towerdriver.base_driver.model.bean.ReleaseDeleteBean;
import com.example.towerdriver.utils.sp.UserUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author 53288
 * @description 修改密码
 * @date 2021/6/3
 */
public class ChangePassPresenter extends BasePresenter<IChangePassView> {
    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public ChangePassPresenter(IChangePassView baseView) {
        super(baseView);
    }


    public void changePassNum(int type, String old_pass, String new_pass, String re_pass) {
        if (type == 1) {
            changeUserPass(1, old_pass, new_pass, re_pass);
        } else if (type == 2) {
            changeStationPass(2, old_pass, new_pass, re_pass);
        } else if (type == 3) {
            changeRepairPass(3, old_pass, new_pass, re_pass);
        } else if (type == 4) {
            changeStaffPass(4, old_pass, new_pass, re_pass);
        }
    }

    /**
     * 修改密码(客户端)
     *
     * @param old_pass 旧密码
     * @param new_pass 新密码
     * @param re_pass  重复密码
     */
    private void changeUserPass(int type, String old_pass, String new_pass, String re_pass) {

        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (!TextUtils.isEmpty(old_pass)) {
            params.put("old_pass", old_pass);
        }
        if (!TextUtils.isEmpty(new_pass)) {
            params.put("new_pass", new_pass);
        }
        if (!TextUtils.isEmpty(re_pass)) {
            params.put("re_pass", re_pass);
        }
        addToRxLife(RxHttp.request(FreeApi.api().ChangePass(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().changePassWordSuccess(type, msg);
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
                        getMvpView().changePassFailure(type, msg);
                    }
                }
            }
        }));

    }

    /**
     * 修改密码(站长端)
     *
     * @param old_pass 旧密码
     * @param new_pass 新密码
     * @param re_pass  重复密码
     */
    private void changeStationPass(int type, String old_pass, String new_pass, String re_pass) {

        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (!TextUtils.isEmpty(old_pass)) {
            params.put("old_pass", old_pass);
        }
        if (!TextUtils.isEmpty(new_pass)) {
            params.put("new_pass", new_pass);
        }
        if (!TextUtils.isEmpty(re_pass)) {
            params.put("re_pass", re_pass);
        }
        addToRxLife(RxHttp.request(FreeApi.api().ChangeStationPass(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().changePassWordSuccess(type, msg);
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
                        getMvpView().changePassFailure(type, msg);
                    }
                }
            }
        }));

    }

    /**
     * 修改密码(维修端)
     *
     * @param old_pass 旧密码
     * @param new_pass 新密码
     * @param re_pass  重复密码
     */
    private void changeRepairPass(int type, String old_pass, String new_pass, String re_pass) {

        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (!TextUtils.isEmpty(old_pass)) {
            params.put("old_pass", old_pass);
        }
        if (!TextUtils.isEmpty(new_pass)) {
            params.put("new_pass", new_pass);
        }
        if (!TextUtils.isEmpty(re_pass)) {
            params.put("re_pass", re_pass);
        }
        addToRxLife(RxHttp.request(FreeApi.api().ChangeRepairPass(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().changePassWordSuccess(type, msg);
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
                        getMvpView().changePassFailure(type, msg);
                    }
                }
            }
        }));

    }

    /**
     * 修改密码(员工端)
     *
     * @param old_pass 旧密码
     * @param new_pass 新密码
     * @param re_pass  重复密码
     */
    private void changeStaffPass(int type, String old_pass, String new_pass, String re_pass) {

        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (!TextUtils.isEmpty(old_pass)) {
            params.put("old_pass", old_pass);
        }
        if (!TextUtils.isEmpty(new_pass)) {
            params.put("new_pass", new_pass);
        }
        if (!TextUtils.isEmpty(re_pass)) {
            params.put("re_pass", re_pass);
        }
        addToRxLife(RxHttp.request(FreeApi.api().ChangeStaffPass(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().changePassWordSuccess(type, msg);
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
                        getMvpView().changePassFailure(type, msg);
                    }
                }
            }
        }));

    }
}
