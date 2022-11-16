package com.example.towerdriver.base_notice.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_driver.model.bean.ReleaseListBean;
import com.example.towerdriver.base_notice.model.NoticeBean;
import com.example.towerdriver.base_notice.view.INoticeView;
import com.example.towerdriver.base_notice.view.ISystemNoticeView;
import com.example.towerdriver.base_setmenu.model.PriceBean;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description 系统消息
 * @date 2021/5/31
 */
public class SystemNoticePresenter extends BasePresenter<ISystemNoticeView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public SystemNoticePresenter(ISystemNoticeView baseView) {
        super(baseView);
    }


    /**
     * 获得小哥发布列表
     *
     * @param type  列表：1=系统消息，2=救援消息，3=订单消息
     * @param page  当前页数
     * @param fresh
     */
    public void getNoticeList(String type, @IntRange(from = 0) int page, boolean fresh) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(type))) {
            params.put("type", type);
        }
        if (!TextUtils.isEmpty(String.valueOf(page))) {
            params.put("page", String.valueOf(page));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            LogUtils.d(UserUtils.getInstance().getUserToken());
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getNoticeList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<NoticeBean>() {
            @Override
            public void onSuccess(int code, String msg, NoticeBean data) {
                if (data != null && isAttach()) {
                    Integer total_page = data.getTotal_page();
                    List<NoticeBean.NoticeListBean> order_list = data.getNotice_list();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(msg, order_list);
                    } else {
                        getMvpView().noticeListSuccess(order_list, page, total_page, fresh, type);
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
                        getMvpView().noticeListFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 消息标记为已读
     */
    public void getChangeNotice(String id, String type, int position) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(id)) {
            params.put("id", id);
        }
        if (!TextUtils.isEmpty(type)) {
            params.put("type", type);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            LogUtils.d(UserUtils.getInstance().getUserToken());
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getChangeNotice(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().noticeDotSuccess(position, id);
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
                        getMvpView().noticeDotFailure(msg);
                    }
                }
            }
        }));
    }
}
