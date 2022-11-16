package com.example.towerdriver.base_driver.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_driver.model.bean.ReleaseDeleteBean;
import com.example.towerdriver.base_driver.model.bean.ReleaseListBean;
import com.example.towerdriver.base_driver.view.IDriveListView;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;
import io.reactivex.Observable;

/**
 * @author 53288
 * @description 小哥发布列表
 * @date 2021/6/4
 */
public class DriverListPresenter extends BasePresenter<IDriveListView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public DriverListPresenter(IDriveListView baseView) {
        super(baseView);
    }


    /**
     * 获得小哥发布列表
     *
     * @param if_self 列表：1=总列表，2=自己的列表
     * @param page    当前页数
     * @param fresh
     */
    public void getDriverListPresenter(boolean if_self, @IntRange(from = 0) int page, boolean fresh) {
        Map<String, String> params = new HashMap<>();
        if (!if_self) {
            params.put("if_self", "1");
        } else {
            params.put("if_self", "2");
        }
        if (!TextUtils.isEmpty(String.valueOf(page))) {
            params.put("page", String.valueOf(page));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            LogUtils.d(UserUtils.getInstance().getUserToken());
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getDriverList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ReleaseListBean>() {
            @Override
            public void onSuccess(int code, String msg, ReleaseListBean data) {
                if (data != null && isAttach()) {
                    Integer total_page = data.getTotal_page();
                    List<ReleaseListBean.ArticleBean> order_list = data.getArticle();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(if_self, msg, order_list);
                    } else {
                        getMvpView().contentListSuccess(order_list, page, total_page, fresh, if_self);
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
                        getMvpView().contentListFailure(msg);
                    }
                }
            }
        }));
    }


    /**
     * 获得小哥删除
     *
     * @param article_id id
     */
    public void deleteItem(@NotNull String article_id, int position) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        if (!TextUtils.isEmpty(article_id)) {
            params.put("article_id", article_id);
        }
        addToRxLife(RxHttp.request(FreeApi.api().getDriverDeleteItem(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ReleaseDeleteBean>() {
            @Override
            public void onSuccess(int code, String msg, ReleaseDeleteBean data) {
                if (isAttach()) {
                    getMvpView().DeleteContentSuccess(position, Integer.parseInt(article_id), msg);
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
                        getMvpView().DeleteContentFailure(position, msg);
                    }
                }
            }
        }));
    }

}
