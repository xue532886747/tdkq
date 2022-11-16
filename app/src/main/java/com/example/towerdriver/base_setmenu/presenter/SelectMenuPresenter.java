package com.example.towerdriver.base_setmenu.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_driver.model.bean.ReleaseListBean;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.base_setmenu.view.ISelectMenuView;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description 套餐选择
 * @date 2021/5/24
 */
public class SelectMenuPresenter extends BasePresenter<ISelectMenuView> {
    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public SelectMenuPresenter(ISelectMenuView baseView) {
        super(baseView);
    }

    /**
     * 获取列表
     *
     * @param classify_id
     * @param isRefresh
     */
    public void getMenuList(int classify_id, boolean isRefresh) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(classify_id))) {
            params.put("classify_id", String.valueOf(classify_id));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getMenuList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<SelectBean>() {
            @Override
            public void onSuccess(int code, String msg, SelectBean data) {
                if (data != null && isAttach()) {
                    getMvpView().onSelectMenuSuccess(data, isRefresh);
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
                        getMvpView().onSelectMenuFailure(msg);
                    }
                }
            }
        }));
    }

}
