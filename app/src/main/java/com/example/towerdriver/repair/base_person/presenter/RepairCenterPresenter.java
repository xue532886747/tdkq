package com.example.towerdriver.repair.base_person.presenter;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.repair.base_person.view.IRepairCenterView;
import com.example.towerdriver.station.station_person.model.ImageBean;
import com.example.towerdriver.station.station_person.view.StationCenterView;
import com.example.towerdriver.utils.RxPartMapUtils;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.io.File;

import okhttp3.MultipartBody;

/**
 * @author 53288
 * @description 维修端
 * @date 2021/7/4
 */
public class RepairCenterPresenter extends BasePresenter<IRepairCenterView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public RepairCenterPresenter(IRepairCenterView baseView) {
        super(baseView);
    }

    /**
     * 更换头像
     *
     * @param file
     */
    public void changeHeadImage(File file) {
        if (UserUtils.getInstance().isLogin()) {
            int loginType = UserUtils.getInstance().getLoginType();
            if (loginType == 3) {
                ChangeStationImg(loginType, file);
            } else {
                if (isAttach()) {
                    getMvpView().LoadingClose();
                }
            }
        }
    }


    /**
     * 客户端修改头像
     *
     * @param type
     * @param file
     */
    private void ChangeStationImg(int type, File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("image", file.getName(), RxPartMapUtils.toRequestBodyOfImage(file));
        builder.addFormDataPart("token", UserUtils.getInstance().getUserToken());
        LogUtils.d(UserUtils.getInstance().getUserToken());
        addToRxLife(RxHttp.request(FreeApi.api().ChangeRepairImg(builder.build())).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ImageBean>() {
            @Override
            public void onSuccess(int code, String msg, ImageBean data) {
                if (data != null) {
                    if (isAttach()) {
                        getMvpView().changeImgSuccess(type, data.getImage(), msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().changeImgFailure(type, msg);
                    }
                }
            }
        }));
    }
}
