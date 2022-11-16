package com.example.towerdriver.staff.base_approval.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.staff.base_approval.model.ApprovalStatusBean;
import com.example.towerdriver.staff.base_approval.model.SponsorListBean;
import com.example.towerdriver.staff.base_approval.view.IApprovalListView;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.IntRange;

/**
 * @author 53288
 * @description 审批列表
 * @date 2021/7/8
 */
public class ApprovalListPresenter extends BasePresenter<IApprovalListView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public ApprovalListPresenter(IApprovalListView baseView) {
        super(baseView);
    }

    /**
     * 获得审批列表
     *
     * @param type  列表：1=我发起的，2=我审批的，3=订单消息
     * @param page  当前页数
     * @param fresh
     */

    public void getList(int type, @IntRange(from = 0) int page, boolean fresh) {
        if (type == 0) {
            getMyLaunchList(page, fresh);
        } else {
            getMyApprovalList(page, fresh);
        }
    }

    /**
     * 我发起的列表
     *
     * @param page  当前页数
     * @param fresh
     */
    private void getMyLaunchList(@IntRange(from = 0) int page, boolean fresh) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(page))) {
            params.put("page", String.valueOf(page));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            LogUtils.d(UserUtils.getInstance().getUserToken());
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getLaunchList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<SponsorListBean>() {
            @Override
            public void onSuccess(int code, String msg, SponsorListBean data) {
                if (data != null && isAttach()) {
                    Integer total_page = data.getTotal_page();
                    List<SponsorListBean.ListBean> order_list = data.getList();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(msg, order_list);
                    } else {
                        getMvpView().approvalListSuccess(order_list, page, total_page, fresh);
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
                        getMvpView().approvalListFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 我发起的列表
     *
     * @param page  当前页数
     * @param fresh
     */
    private void getMyApprovalList(@IntRange(from = 0) int page, boolean fresh) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(page))) {
            params.put("page", String.valueOf(page));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getApprovalList(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<SponsorListBean>() {
            @Override
            public void onSuccess(int code, String msg, SponsorListBean data) {
                if (data != null && isAttach()) {
                    Integer total_page = data.getTotal_page();
                    List<SponsorListBean.ListBean> order_list = data.getList();
                    if (order_list == null || order_list.size() == 0) {
                        getMvpView().showRefreshNoDate(msg, order_list);
                    } else {
                        getMvpView().approvalListSuccess(order_list, page, total_page, fresh);
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
                        getMvpView().approvalListFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 订单取消
     */
    public void getDeleteApproval(String id, int position) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(id))) {
            params.put("id", String.valueOf(id));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getDeleteApproval(params)).listener(new RxRequest.RequestListener() {
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
                if (data != null && isAttach()) {
                    getMvpView().deleteApprovalSuccess(msg, Integer.parseInt(id), position);
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
                        getMvpView().deleteApprovalFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 审批通过或没通过
     *
     * @param type     1=通过,2=拒绝
     * @param id
     * @param position
     */
    public void permissionApprovalOrder(int type, String id, int position) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(String.valueOf(type))) {
            params.put("type", String.valueOf(type));
        }
        if (!TextUtils.isEmpty(id)) {
            params.put("id", id);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().approvalOperate(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ApprovalStatusBean>() {
            @Override
            public void onSuccess(int code, String msg, ApprovalStatusBean data) {
                if (data != null && isAttach()) {
                    getMvpView().permissionApprovalSuccess(msg, position, data);
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
                        getMvpView().permissionApprovalFailure(msg);
                    }
                }
            }
        }));
    }

}
