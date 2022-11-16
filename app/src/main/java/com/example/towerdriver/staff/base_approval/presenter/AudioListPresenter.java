package com.example.towerdriver.staff.base_approval.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.staff.base_approval.model.AudioListBean;
import com.example.towerdriver.staff.base_approval.model.SponsorListBean;
import com.example.towerdriver.staff.base_approval.view.IApprovalListView;
import com.example.towerdriver.staff.base_approval.view.IAudioListView;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description 审批人的列表
 * @date 2021/7/8
 */
public class AudioListPresenter extends BasePresenter<IAudioListView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public AudioListPresenter(IAudioListView baseView) {
        super(baseView);
    }


    /**
     * 审批人的列表
     */
    public void getAudioList() {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getAudioList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<AudioListBean>() {
            @Override
            public void onSuccess(int code, String msg, AudioListBean data) {
                if (data != null && isAttach()) {
                    List<AudioListBean.DepartmentListBean> order_list = data.getDepartment_list();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(msg, order_list);
                    } else {
                        getMvpView().audioListSuccess(order_list);
                    }
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
                        getMvpView().audioListFailure(msg);
                    }
                }
            }
        }));
    }

}
