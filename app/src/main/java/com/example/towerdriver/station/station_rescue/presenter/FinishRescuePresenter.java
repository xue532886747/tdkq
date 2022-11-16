package com.example.towerdriver.station.station_rescue.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.model.bean.ImageToUrlBean;
import com.example.towerdriver.base_driver.model.bean.ReleaseBean;
import com.example.towerdriver.station.station_rescue.model.RescueBean;
import com.example.towerdriver.station.station_rescue.view.IFinishRescueView;
import com.example.towerdriver.utils.RxPartMapUtils;
import com.example.towerdriver.utils.sp.UserUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @author 53288
 * @description 完成救援
 * @date 2021/7/2
 */
public class FinishRescuePresenter extends BasePresenter<IFinishRescueView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public FinishRescuePresenter(IFinishRescueView baseView) {
        super(baseView);
    }

    /**
     * 上传图片
     *
     * @param name
     */
    public void getImageToUrl(@NotNull String name) {
        File file = null;
        file = new File(name);
        if (file == null) {
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("image", file.getName(), RxPartMapUtils.toRequestBodyOfImage(file));
        builder.addFormDataPart("token", UserUtils.getInstance().getUserToken());
        addToRxLife(RxHttp.request(FreeApi.api().changeStationImage(builder.build())).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ImageToUrlBean>() {
            @Override
            public void onSuccess(int code, String msg, ImageToUrlBean data) {
                if (data != null) {
                    if (isAttach()) {
                        getMvpView().imageToUrlSuccess(data.getImage_file());
                    }
                } else {
                    if (isAttach()) {
                        getMvpView().finishRescueFailure("上传失败");
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().finishRescueFailure(msg);
                    }
                }
            }
        }));
    }


    /**
     * 完成救援
     *
     * @param rescue_id 标题
     * @param remark    内容
     * @param image     照片
     */
    public void release(@NotNull String rescue_id, String remark, String image) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(rescue_id)) {
            params.put("rescue_id", rescue_id);
        }
        if (!TextUtils.isEmpty(remark)) {
            params.put("remark", remark);
        }
        if (!TextUtils.isEmpty(image)) {
            params.put("images", image);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().stationFinishRescue(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<RescueBean>() {
            @Override
            public void onSuccess(int code, String msg, RescueBean data) {
                if (isAttach()) {
                    getMvpView().finishRescueSuccess(msg,data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().finishRescueFailure(msg);
                    }
                }
            }
        }));
    }

}
