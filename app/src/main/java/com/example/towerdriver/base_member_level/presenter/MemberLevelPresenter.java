package com.example.towerdriver.base_member_level.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_driver.model.bean.ReleaseDeleteBean;
import com.example.towerdriver.base_main.bean.AdvertiBean;
import com.example.towerdriver.base_member_level.model.bean.LevelBean;
import com.example.towerdriver.base_member_level.view.MemberLevelView;
import com.example.towerdriver.base_person.view.PersonCenterView;
import com.example.towerdriver.utils.sp.UserUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 53288
 * @description 会员等级
 * @date 2021/5/25
 */
public class MemberLevelPresenter extends BasePresenter<MemberLevelView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public MemberLevelPresenter(MemberLevelView baseView) {
        super(baseView);
    }


    /**
     * 获得小哥删除
     */
    public void getMemberLevel() {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getMemberLevel(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<LevelBean>() {
            @Override
            public void onSuccess(int code, String msg, LevelBean data) {
                if (isAttach()) {
                    getMvpView().getLevelSuccess(data.getMember_info(), data.getOperate_integral(), msg);
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
                        getMvpView().getLevelFailure(msg);
                    }
                }
            }
        }));
    }

}
