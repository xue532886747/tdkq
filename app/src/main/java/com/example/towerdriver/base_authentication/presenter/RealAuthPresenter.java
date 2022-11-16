package com.example.towerdriver.base_authentication.presenter;

import android.net.Uri;
import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxOriginRequest;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.model.bean.BaiduTokenBean;
import com.example.towerdriver.base_authentication.model.bean.IdCardBean;
import com.example.towerdriver.base_authentication.model.bean.ImageToUrlBean;
import com.example.towerdriver.base_authentication.view.IRealAuthView;
import com.example.towerdriver.base_sendcode.model.SendCodeModel;
import com.example.towerdriver.member_model.IMemberModel;
import com.example.towerdriver.member_model.MemberInfoBean;
import com.example.towerdriver.member_model.MemberModel;
import com.example.towerdriver.utils.RxPartMapUtils;
import com.example.towerdriver.utils.sp.BaiDuAccessUtils;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import okhttp3.MultipartBody;

import static com.example.towerdriver.Constant.BAIDU_ASE_URL;
import static com.example.towerdriver.Constant.BAIDU_IDCARD_URL;
import static com.example.towerdriver.Constant.CLIENT_ID;
import static com.example.towerdriver.Constant.CLIENT_SECRET;

/**
 * @author 53288
 * @description 身份认证
 * @date 2021/5/25
 */
public class RealAuthPresenter extends BasePresenter<IRealAuthView> implements IMemberModel {
    private MemberModel memberModel;

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public RealAuthPresenter(IRealAuthView baseView) {
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
     * image转url
     *
     * @param type
     * @param name
     */
    public void getImageToUrl(String type, @NotNull Uri name) {
        File file = null;
        try {
            file = new File(new URI(name.toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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
                if (code == 200) {
                    if (data != null) {
                        LogUtils.d(data.getImage_file());
                        if (isAttach()) {
                            getMvpView().ImageToUrlSuccess(type, data.getImage_file());
                        }
                    } else {
                        if (isAttach()) {
                            getMvpView().ImageToUrlFailure(msg);
                        }
                    }
                } else {
                    if (isAttach()) {
                        if (code == FreeApi.Code.TOKENEXPIRED) {
                            getMvpView().LoadingClose();
                        } else {
                            getMvpView().ImageToUrlFailure(msg);
                        }
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().ImageToUrlFailure(msg);
                }
            }
        }));
    }

    /**
     * 获取token
     */
    public void getBaiduAuth() {
        HashMap<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        addToRxLife(RxHttp.origin_request(FreeApi.api().getToken(BAIDU_ASE_URL, params), BaiduTokenBean.class).listener(new RxOriginRequest.RequestListener() {
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
        }).request(new RxOriginRequest.OriginResultCallback<BaiduTokenBean>() {
            @Override
            public void onSuccess(int code, String msg, BaiduTokenBean baiduTokenBean) {
                baiduTokenBean.setExpires_in(baiduTokenBean.getExpires_in() + System.currentTimeMillis() / 1000);
                BaiDuAccessUtils.getInstance().AccessTokenLogin(baiduTokenBean);
                if (isAttach()) {
                    getMvpView().onAccessTokenSuccess();
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getMvpView().onAccessTokenFailure();
                }
            }
        }, BaiduTokenBean.class));
    }


    /**
     * 获取身份证信息
     *
     * @param url  图片地址
     * @param side 身份证正反面
     */
    public void getIdCard(String url, String side) {
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", BaiDuAccessUtils.getInstance().getAccessToken());
        params.put("url", url);
        params.put("id_card_side", side);
        params.put("detect_risk", "true");
        addToRxLife(RxHttp.origin_request(FreeApi.api().getToken(BAIDU_IDCARD_URL, params), IdCardBean.class).listener(new RxOriginRequest.RequestListener() {
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
        }).request(new RxOriginRequest.OriginResultCallback<IdCardBean>() {
            @Override
            public void onSuccess(int code, String msg, IdCardBean idCardBean) {
                LogUtils.d(idCardBean.toString());
                if (idCardBean != null) {
                    if (!TextUtils.isEmpty(idCardBean.getError_msg())) {
                        if (isAttach()) {
                            getMvpView().onIdCardAuthFailure("身份证认证失败！");
                        }
                    } else {
                        if (isAttach()) {
                            getMvpView().onIdCardAuthSuccess(side, idCardBean);
                        }
                    }
                } else {
                    if (isAttach()) {
                        getMvpView().onIdCardAuthFailure("身份证认证失败！");
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                LogUtils.d("code=" + code + ", msg =  " + msg);
                if (isAttach()) {
                    getMvpView().onIdCardAuthFailure("身份证认证失败！");
                }
            }
        }, IdCardBean.class));
    }

    /**
     * 获得用户信息
     *
     * @param memberInfoBean
     * @param msg
     */
    @Override
    public void getMemberSuccess(MemberInfoBean memberInfoBean, String msg) {
        if (isAttach()) {
            getMvpView().getMemberInfoSuccess(memberInfoBean);
        }
    }

    @Override
    public void getMemberFailure(String msg) {
        if (isAttach()) {
            getMvpView().getMemberInfoFailure(msg);
        }
    }

    @Override
    public void getTokenFailure() {
        if (isAttach()) {
            getMvpView().LoadingClose();
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
        if (memberModel != null) {
            memberModel.unMemberModel();
        }
    }
}
