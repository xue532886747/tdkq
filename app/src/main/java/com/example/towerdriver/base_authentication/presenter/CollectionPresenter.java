package com.example.towerdriver.base_authentication.presenter;

import android.net.Uri;
import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxOriginRequest;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.model.bean.ImageToUrlBean;
import com.example.towerdriver.base_authentication.model.bean.PersonVerifyBean;
import com.example.towerdriver.base_authentication.view.ICollectionView;
import com.example.towerdriver.base_driver.model.bean.ReleaseDeleteBean;
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

import static com.example.towerdriver.Constant.BAUDU_PERSON_VERIFY;


/**
 * @author 53288
 * @description 身份认证
 * @date 2021/5/25
 */
public class CollectionPresenter extends BasePresenter<ICollectionView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public CollectionPresenter(ICollectionView baseView) {
        super(baseView);
    }


    /**
     * 验证身份
     */
    public void getPersonVerify(String bitmap, String code, String name) {
        HashMap<String, String> params = new HashMap<>();
        params.put("image", bitmap);
        params.put("image_type", "BASE64");
        params.put("id_card_number", code);
        params.put("name", name);
        params.put("liveness_control","HIGH");
        params.put("quality_control","HIGH");
        LogUtils.d(BAUDU_PERSON_VERIFY);
        addToRxLife(RxHttp.origin_request(FreeApi.api().getPersonVerify(BAUDU_PERSON_VERIFY, params), PersonVerifyBean.class).listener(new RxOriginRequest.RequestListener() {
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
        }).request(new RxOriginRequest.OriginResultCallback<PersonVerifyBean>() {
            @Override
            public void onSuccess(int code, String msg, PersonVerifyBean personVerifyBean) {
                LogUtils.d(personVerifyBean.toString());
                if (isAttach()) {
                    if (personVerifyBean != null) {
                        Integer error_code = personVerifyBean.getError_code();
                        if (error_code == 0) {
                            getMvpView().onVerifySuccess(personVerifyBean);
                        } else if (error_code == 4 || error_code == 6 || error_code == 17 || error_code == 18 || error_code == 19) {
                            getMvpView().onVerifyFailure("认证失败,请联系平台人工认证！",code);
                        } else if (error_code == 100 || error_code == 110 || error_code == 111) {
                            getMvpView().onVerifyFailure("用户过期,请退回实名认证页面重新进入",code);
                        } else {
                            getMvpView().onVerifyFailure("认证失败,请重新认证或联系平台人工认证！",code);
                        }
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().onVerifyFailure(msg,code);
                }
            }
        }, PersonVerifyBean.class));
    }

    /**
     * 提交身份信息给后台
     *
     * @param name         姓名
     * @param card_number  身份证号
     * @param card_front   身份证正面
     * @param card_reverse 身份证反面
     */
    public void commitApprove(@NotNull String name, @NotNull String card_number, @NotNull String card_front, @NotNull String card_reverse) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (!TextUtils.isEmpty(name)) {
            params.put("name", name);
        }
        if (!TextUtils.isEmpty(card_number)) {
            params.put("card_number", card_number);
        }
        if (!TextUtils.isEmpty(card_front)) {
            params.put("card_front", card_front);
        }
        if (!TextUtils.isEmpty(card_reverse)) {
            params.put("card_reverse", card_reverse);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getApprove(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().onApproveSuccess(msg);
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
                        getMvpView().onApproveFailure( msg);
                    }
                }
            }
        }));
    }
}
