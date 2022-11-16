package com.example.towerdriver.base_main.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_login.bean.UserBean;
import com.example.towerdriver.base_main.bean.AdvertiBean;
import com.example.towerdriver.base_main.bean.MessageNumberBean;
import com.example.towerdriver.base_main.bean.PickUpBean;
import com.example.towerdriver.base_main.bean.RepairBean;
import com.example.towerdriver.base_main.view.MainView;
import com.example.towerdriver.bean.VersionBean;
import com.example.towerdriver.member_model.IMemberModel;
import com.example.towerdriver.member_model.MemberInfoBean;
import com.example.towerdriver.member_model.MemberModel;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 53288
 * @description
 * @date 2021/5/19
 */
public class MainPresenter extends BasePresenter<MainView> implements IMemberModel {
    private MemberModel memberModel;

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public MainPresenter(MainView baseView) {
        super(baseView);
    }

    /**
     * 获得用户信息
     */
    public void getMemberInfo() {
        memberModel = new MemberModel(this);
        memberModel.getMemberInfo();
    }

    /**
     * 请求轮播图
     */
    public void sendLunBo() {
        Map<String, String> params = new HashMap<>();
        addToRxLife(RxHttp.request(FreeApi.api().getLunBo(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<AdvertiBean>() {
            @Override
            public void onSuccess(int code, String msg, AdvertiBean data) {
                if (isAttach()) {
                    List<AdvertiBean.AdvBean> adv = data.getAdv();
                    getMvpView().showLunboSuccess(msg, adv);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().showLunboFailed(msg);
                }
            }
        }));
    }

    /**
     * 请求提车点
     */
    public void sendPickUpPoint(String address) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(address)) {
            params.put("address", address);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getPickUpPoint(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<PickUpBean>() {
            @Override
            public void onSuccess(int code, String msg, PickUpBean data) {
                if (isAttach()) {
                    List<PickUpBean.WarehouseBean> warehouse = data.getWarehouse();
                    getMvpView().showPickUpPointSuccess(warehouse);
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
                        getMvpView().showPickUpPointFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 请求维修点
     */
    public void sendRepairPoint(String address) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(address)) {
            params.put("address", address);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getRepairPoint(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<RepairBean>() {
            @Override
            public void onSuccess(int code, String msg, RepairBean data) {
                if (isAttach()) {
                    List<RepairBean.StationBean> warehouse = data.getStation();
                    getMvpView().showRepairPointSuccess(warehouse);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().showRepairFailure(msg);
                }
            }
        }));
    }


    /**
     * 更新
     */
    public void getVersion() {
        addToRxLife(RxHttp.request(FreeApi.api().getVersion()).listener(new RxRequest.RequestListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ExceptionHandle handle) {
                if (isAttach()) {
//                    getMvpView().showFailed(handle.getCode(), handle.getMsg());
                }
            }

            @Override
            public void onFinish() {

            }
        }).request(new RxRequest.ResultCallback<VersionBean>() {
            @Override
            public void onSuccess(int code, String msg, VersionBean data) {
                if (isAttach()) {
                    getMvpView().getVersionSuccess(data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        }));
    }

    /**
     * 发起救援
     *
     * @param member_phone 电话
     * @param reason       原因
     * @param content      理由
     * @param lng          经度
     * @param lat          纬度
     */
    public void sendCreateRescue(String member_phone, String reason, String content, String lng, String lat, String addr) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(member_phone)) {
            params.put("member_phone", member_phone);
        }
        if (!TextUtils.isEmpty(reason)) {
            params.put("reason", reason);
        }
        if (!TextUtils.isEmpty(content)) {
            params.put("content", content);
        }
        if (!TextUtils.isEmpty(lng)) {
            params.put("lng", lng);
        }
        if (!TextUtils.isEmpty(lat)) {
            params.put("lat", lat);
        }
        if (!TextUtils.isEmpty(addr)) {
            params.put("address", addr);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getCreateRescue(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().showCreateRescueSuccess(msg);
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
                        getMvpView().showCreateRescueFailure(msg);
                    }
                }
            }
        }));
    }

    @Override
    public void getMemberSuccess(MemberInfoBean memberInfoBean, String msg) {
        if (isAttach()) {
            getMvpView().getMemberInfoSuccess(memberInfoBean);
        }
    }

    @Override
    public void getMemberFailure(String msg) {

    }

    @Override
    public void getTokenFailure() {
        if (isAttach()) {
            getMvpView().LoadingClose();
        }
    }

    @Override
    public void getCodeElseFailure(int code, String msg) {

    }


    /**
     * 请求红点
     */
    public void getNoticeNumber() {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getNoticeNumber(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<MessageNumberBean>() {
            @Override
            public void onSuccess(int code, String msg, MessageNumberBean data) {
                if (isAttach()) {
                    int message = data.getMessage_num();
                    getMvpView().showRedDotSuccess(message);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().showRedDotFailure(msg);
                }
            }
        }));
    }

    @Override
    public void detachView() {
        super.detachView();
        if (memberModel != null) {
            memberModel.unMemberModel();
        }
    }
}
