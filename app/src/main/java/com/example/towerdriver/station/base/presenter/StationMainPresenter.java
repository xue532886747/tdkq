package com.example.towerdriver.station.base.presenter;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.bean.VersionBean;
import com.example.towerdriver.station.base.view.StationMainView;

/**
 * @author 53288
 * @description
 * @date 2021/8/2
 */
public class StationMainPresenter extends BasePresenter<StationMainView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public StationMainPresenter(StationMainView baseView) {
        super(baseView);
    }

    /**
     * 更新
     */
    public void getVersion() {
        addToRxLife(RxHttp.request(FreeApi.api().getVersion()).listener(new RxRequest.RequestListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ExceptionHandle handle) {
                if (isAttach()) {
//                    getMvpView().showFailed(handle.getCode(), handle.getMsg());
                }
            }

            @Override
            public void onFinish() {

            }
        }).request(new RxRequest.ResultCallback<VersionBean>() {
            @Override
            public void onSuccess(int code, String msg, VersionBean data) {
                if (isAttach()) {
                    getMvpView().getVersionSuccess(data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {

            }
        }));
    }

}
