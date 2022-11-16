package com.example.towerdriver.member_model;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.core.RxLife;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base_member_level.model.bean.LevelBean;
import com.example.towerdriver.base_person.bean.MemberImageBean;
import com.example.towerdriver.base_sendcode.model.ISendCodeModel;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 用户bean
 * @date 2021/6/9
 */
public class MemberModel {

    private IMemberModel iMemberModel;
    private RxLife rxLife;

    public MemberModel(IMemberModel iSendCodeModel) {
        this.iMemberModel = iSendCodeModel;
        rxLife = RxLife.create();
    }

    public void getMemberInfo() {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (rxLife != null) {
            rxLife.add(RxHttp.request(FreeApi.api().getMemberInfo(params)).listener(new RxRequest.RequestListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onError(ExceptionHandle handle) {
                    if (iMemberModel != null) {
                        iMemberModel.getCodeElseFailure(handle.getCode(), handle.getMsg());
                    }
                }

                @Override
                public void onFinish() {

                }
            }).request(new RxRequest.ResultCallback<MemberInfoBean>() {
                @Override
                public void onSuccess(int code, String msg, MemberInfoBean data) {
                    if (iMemberModel != null) {
                        iMemberModel.getMemberSuccess(data, msg);
                    }
                }

                @Override
                public void onFailed(int code, String msg) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        if (iMemberModel != null) {
                            iMemberModel.getTokenFailure();
                        }
                    } else {
                        if (iMemberModel != null) {
                            iMemberModel.getMemberFailure(msg);
                        }
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

    public void unMemberModel() {
        if (iMemberModel != null) {
            iMemberModel = null;
        }
        detachView();
    }
}
