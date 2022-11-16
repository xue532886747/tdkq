package com.example.towerdriver.base_sendcode.model;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.core.RxLife;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.ResponseBean;
import com.example.towerdriver.base_sendcode.model.bean.VerificationBean;
import com.example.towerdriver.utils.Base64Utils;
import com.hjq.toast.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


/**
 * @author 53288
 * @description 发送验证码
 * @date 2021/6/2
 */
public class SendCodeModel {

    private ISendCodeModel iSendCodeModel;
    private RxLife rxLife;

    public SendCodeModel(ISendCodeModel iSendCodeModel) {
        this.iSendCodeModel = iSendCodeModel;
        rxLife = RxLife.create();
    }

    public void setSendCodeModel(ISendCodeModel iSendCodeModel) {
        this.iSendCodeModel = iSendCodeModel;
    }

    /**
     * @param phone      手机号
     * @param login_type 1=客户端，2=站长端，3=维修端,4 =员工端
     * @param type       1=注册，2=登录，3=忘记密码，4=修改手机号(旧手机号)，5=修改手机号(新手机号)
     */
    public void sendCode(String phone, int login_type, String type) {
        if (login_type == 1) {
            sendUserCode(phone, type);
        } else if (login_type == 2) {
            sendStationCode(phone, type);
        }else if(login_type==3){
            sendRepairCode(phone, type);
        }else if(login_type==4){
            sendStaffCode(phone, type);
        }
    }

    /**
     * 客户端
     * @param phone 手机号
     * @param type  1=注册，2=登录，3=忘记密码，4=修改手机号(旧手机号)，5=修改手机号(新手机号)
     */
    private void sendUserCode(String phone, String type) {
        Map<String, String> params = new HashMap<>();
        String newPhone = "";
        newPhone = Base64Utils.encodeToString(phone);
        newPhone = newPhone + "zuche";
        newPhone = Base64Utils.encodeToString(newPhone);
        if (!TextUtils.isEmpty(newPhone)) {
            params.put("phone", newPhone);
        }
        if (!TextUtils.isEmpty(type)) {
            params.put("type", type);
        }

        if (rxLife != null) {
            rxLife.add(RxHttp.request(FreeApi.api().getVerificationCode(params)).listener(new RxRequest.RequestListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onError(ExceptionHandle handle) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeElseFailure(handle.getCode(), handle.getMsg());
                    }
                }

                @Override
                public void onFinish() {

                }
            }).request(new RxRequest.ResultCallback<VerificationBean>() {
                @Override
                public void onSuccess(int code, String msg, VerificationBean data) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeSuccess(phone, "验证码发送成功");
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeFailure(msg);
                    }
                }
            }));
        }

    }

    /**
     * 站长端
     * @param phone 手机号
     * @param type  1=注册，2=登录，3=忘记密码，4=修改手机号(旧手机号)，5=修改手机号(新手机号)
     */
    private void sendStationCode(String phone, String type) {
        Map<String, String> params = new HashMap<>();
        String newPhone = "";
        newPhone = Base64Utils.encodeToString(phone);
        newPhone = newPhone + "zuche";
        newPhone = Base64Utils.encodeToString(newPhone);
        if (!TextUtils.isEmpty(newPhone)) {
            params.put("phone", newPhone);
        }
//        if (!TextUtils.isEmpty(type)) {
//            params.put("type", type);
//        }
        if (rxLife != null) {
            rxLife.add(RxHttp.request(FreeApi.api().getStationVerificationCode(params)).listener(new RxRequest.RequestListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onError(ExceptionHandle handle) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeElseFailure(handle.getCode(), handle.getMsg());
                    }
                }

                @Override
                public void onFinish() {

                }
            }).request(new RxRequest.ResultCallback<VerificationBean>() {
                @Override
                public void onSuccess(int code, String msg, VerificationBean data) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeSuccess(phone, "验证码发送成功");
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeFailure(msg);
                    }
                }
            }));
        }

    }


    /**
     * 维修端
     * @param phone 手机号
     * @param type  1=注册，2=登录，3=忘记密码，4=修改手机号(旧手机号)，5=修改手机号(新手机号)
     */
    private void sendRepairCode(String phone, String type) {
        Map<String, String> params = new HashMap<>();
        String newPhone = "";
        newPhone = Base64Utils.encodeToString(phone);
        newPhone = newPhone + "zuche";
        newPhone = Base64Utils.encodeToString(newPhone);
        if (!TextUtils.isEmpty(newPhone)) {
            params.put("phone", newPhone);
        }
//        if (!TextUtils.isEmpty(type)) {
//            params.put("type", type);
//        }
        if (rxLife != null) {
            rxLife.add(RxHttp.request(FreeApi.api().getRepairVerificationCode(params)).listener(new RxRequest.RequestListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onError(ExceptionHandle handle) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeElseFailure(handle.getCode(), handle.getMsg());
                    }
                }

                @Override
                public void onFinish() {

                }
            }).request(new RxRequest.ResultCallback<VerificationBean>() {
                @Override
                public void onSuccess(int code, String msg, VerificationBean data) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeSuccess(phone, "验证码发送成功");
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeFailure(msg);
                    }
                }
            }));
        }

    }

    /**
     * 维修端
     * @param phone 手机号
     * @param type  1=注册，2=登录，3=忘记密码，4=修改手机号(旧手机号)，5=修改手机号(新手机号)
     */
    private void sendStaffCode(String phone, String type) {
        Map<String, String> params = new HashMap<>();
        String newPhone = "";
        newPhone = Base64Utils.encodeToString(phone);
        newPhone = newPhone + "zuche";
        newPhone = Base64Utils.encodeToString(newPhone);
        if (!TextUtils.isEmpty(newPhone)) {
            params.put("phone", newPhone);
        }
//        if (!TextUtils.isEmpty(type)) {
//            params.put("type", type);
//        }
        if (rxLife != null) {
            rxLife.add(RxHttp.request(FreeApi.api().getStaffVerificationCode(params)).listener(new RxRequest.RequestListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onError(ExceptionHandle handle) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeElseFailure(handle.getCode(), handle.getMsg());
                    }
                }

                @Override
                public void onFinish() {

                }
            }).request(new RxRequest.ResultCallback<VerificationBean>() {
                @Override
                public void onSuccess(int code, String msg, VerificationBean data) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeSuccess(phone, "验证码发送成功");
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    if (iSendCodeModel != null) {
                        iSendCodeModel.getCodeFailure(msg);
                    }
                }
            }));
        }

    }

    /**
     * 销毁
     */
    public void detachView() {
        if (rxLife != null) {
            rxLife.destroy();
            rxLife = null;
        }
    }

    public void unCodeModel() {
        if (iSendCodeModel != null) {
            iSendCodeModel = null;
        }
        detachView();
    }
}
