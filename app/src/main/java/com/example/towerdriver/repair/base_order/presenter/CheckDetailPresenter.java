package com.example.towerdriver.repair.base_order.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.model.bean.ImageToUrlBean;
import com.example.towerdriver.base_order_list.model.EntrepotBean;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_order_list.model.RentCarBean;
import com.example.towerdriver.base_order_list.view.IRentCarDetailView;
import com.example.towerdriver.repair.base_order.view.ICheckDetailView;
import com.example.towerdriver.utils.RxPartMapUtils;
import com.example.towerdriver.utils.sp.UserUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @author 53288
 * @description
 * @date 2021/7/5
 */
public class CheckDetailPresenter extends BasePresenter<ICheckDetailView> {
    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public CheckDetailPresenter(ICheckDetailView baseView) {
        super(baseView);
    }


    public void getCheckDetail(String order_id, String frame_number,
                               String battery_number, List<RentCarBean> list, String remark, String images) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(order_id))) {
            params.put("order_id", order_id);
        }
        if (!TextUtils.isEmpty(frame_number)) {
            params.put("frame_number", frame_number);
        }
        if (!TextUtils.isEmpty(battery_number)) {
            params.put("battery_number", battery_number);
        }
        for (int i = 0; i < list.size(); i++) {
            params.put("name" + (i + 1), list.get(i).getType());
        }
        if (!TextUtils.isEmpty(remark)) {
            params.put("remark", remark);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (!TextUtils.isEmpty(images)) {
            params.put("images", images);
        }
        addToRxLife(RxHttp.request(FreeApi.api().checkCarOrder(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<OrderStatusBean>() {
            @Override
            public void onSuccess(int code, String msg, OrderStatusBean data) {
                if (isAttach()) {
                    getMvpView().onRentCarDetailSuccess(msg, data);
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
                        getMvpView().onRentCarDetailFailure(msg);
                    }
                }
            }
        }));
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
        addToRxLife(RxHttp.request(FreeApi.api().repairUrl(builder.build())).listener(new RxRequest.RequestListener() {
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
}
