package com.example.towerdriver.base_driver.presenter;

import android.net.Uri;
import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.model.bean.ImageToUrlBean;
import com.example.towerdriver.base_driver.model.bean.ReleaseBean;
import com.example.towerdriver.base_driver.view.IDriveReleaseView;
import com.example.towerdriver.utils.RxPartMapUtils;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;


/**
 * @author 53288
 * @description 小哥发布编辑
 * @date 2021/6/3
 */
public class DriverReleasePresenter extends BasePresenter<IDriveReleaseView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public DriverReleasePresenter(IDriveReleaseView baseView) {
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
        addToRxLife(RxHttp.request(FreeApi.api().changeNewImageHead(builder.build())).listener(new RxRequest.RequestListener() {
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
                        getMvpView().ImageToUrlSuccess(data.getImage_file());
                    }
                } else {
                    if (isAttach()) {
                        getMvpView().ImageToUrlFailure("上传失败");
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().ImageToUrlFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 小哥发布新建
     *
     * @param title   标题
     * @param content 内容
     * @param name    姓名
     * @param phone   电话
     * @param image   照片
     */
    public void release(@NotNull String title, @NotNull String content, @NotNull String name, @NotNull String phone, String image) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(title)) {
            params.put("title", title);
        }
        if (!TextUtils.isEmpty(content)) {
            params.put("content", content);
        }
        if (!TextUtils.isEmpty(name)) {
            params.put("name", name);
        }
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);
        }
        if (!TextUtils.isEmpty(image)) {
            params.put("image", image);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getDriverRelease(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ReleaseBean>() {
            @Override
            public void onSuccess(int code, String msg, ReleaseBean data) {
                if (isAttach()) {
                    getMvpView().contentReleaseSuccess(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().contentReleaseFailure(msg);
                    }
                }
            }
        }));
    }

}
