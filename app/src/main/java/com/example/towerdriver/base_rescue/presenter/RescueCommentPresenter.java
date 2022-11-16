package com.example.towerdriver.base_rescue.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_rescue.model.RescueListBean;
import com.example.towerdriver.base_rescue.view.IRescueCommentView;
import com.example.towerdriver.base_rescue.view.IRescueListView;
import com.example.towerdriver.utils.sp.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description 救援评价
 * @date 2021/6/21
 */
public class RescueCommentPresenter extends BasePresenter<IRescueCommentView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public RescueCommentPresenter(IRescueCommentView baseView) {
        super(baseView);
    }


    public void getRescueComment(String rescue_id, String score, String evaluate) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(rescue_id))) {
            params.put("rescue_id", String.valueOf(rescue_id));
        }
        if (!TextUtils.isEmpty(String.valueOf(score))) {
            params.put("score", String.valueOf(score));
        }
        if (!TextUtils.isEmpty(String.valueOf(evaluate))) {
            params.put("evaluate", String.valueOf(evaluate));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getRescueComment(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().rescueCommentSuccess(msg);
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
                        getMvpView().rescueCommentFailure(msg);
                    }
                }
            }
        }));
    }
}
