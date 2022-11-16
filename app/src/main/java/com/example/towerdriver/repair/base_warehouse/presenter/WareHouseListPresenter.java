package com.example.towerdriver.repair.base_warehouse.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_setmenu.model.PriceBean;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.repair.base_warehouse.model.MountBean;
import com.example.towerdriver.repair.base_warehouse.model.MountNumberBean;
import com.example.towerdriver.repair.base_warehouse.view.IRepairWareHouseView;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 53288
 * @description 配件列表
 * @date 2021/7/5
 */
public class WareHouseListPresenter extends BasePresenter<IRepairWareHouseView> {
    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public WareHouseListPresenter(IRepairWareHouseView baseView) {
        super(baseView);
    }

    /**
     * 获取列表
     *
     * @param variety_id
     * @param isRefresh
     */
    public void getMenuList(int variety_id, boolean isRefresh) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(variety_id))) {
            params.put("variety_id", String.valueOf(variety_id));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            LogUtils.d(UserUtils.getInstance().getUserToken());
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getMountList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<MountBean>() {
            @Override
            public void onSuccess(int code, String msg, MountBean data) {
                if (data != null && isAttach()) {
                    getMvpView().onRepairWareHouseSuccess(data, isRefresh);
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
                        getMvpView().onRepairWareHouseFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 计算价格
     *
     * @param position
     * @param mountings_id
     * @param num
     */
    public void getMountNumber(int position, String mountings_id, String num) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(mountings_id))) {
            params.put("mountings_id", mountings_id);
        }
        if (!TextUtils.isEmpty(num)) {
            params.put("num", num);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            LogUtils.d(UserUtils.getInstance().getUserToken());
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getMountNumber(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<MountNumberBean>() {
            @Override
            public void onSuccess(int code, String msg, MountNumberBean data) {
                if (isAttach()) {
                    getMvpView().onRepairNumSuccess(data.getNum(), position);
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
                        getMvpView().onRepairNumFailure(msg);
                    }
                }
            }
        }));
    }
}
