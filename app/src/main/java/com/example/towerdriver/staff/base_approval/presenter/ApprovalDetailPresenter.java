package com.example.towerdriver.staff.base_approval.presenter;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.baidu.liantian.ac.F;
import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.download.DownloadInfo;
import com.example.rxhttp.download.RxDownload;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.Constant;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.base_order_list.view.IOrderDetailView;
import com.example.towerdriver.staff.base_approval.model.ApprovalDetailBean;
import com.example.towerdriver.staff.base_approval.view.ApprovalDetailView;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author 53288
 * @description 审批详情
 * @date 2021/7/10
 */
public class ApprovalDetailPresenter extends BasePresenter<ApprovalDetailView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public ApprovalDetailPresenter(ApprovalDetailView baseView) {
        super(baseView);
    }


    /**
     * 审批详情
     *
     * @param id
     */
    public void getApprovalDetail(String id) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(id)) {
            params.put("id", (id));
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().getApprovalDetail(params)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<ApprovalDetailBean>() {
            @Override
            public void onSuccess(int code, String msg, ApprovalDetailBean data) {
                if (isAttach()) {
                    getMvpView().approvalDetailSuccess(data);
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
                        getMvpView().approvalDetailFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 下载
     *
     * @param url
     */
    public void getDownLoad(String url) {
        String name = url.substring(url.lastIndexOf("/") + 1);    //文件名称
        LogUtils.d("url = " + name);
        String loc = Constant.OUTPUTDIR + File.separator + "TOWERDRIVER";
        LogUtils.d("name = " + loc);
        String names = loc + File.separator + name;
        File file = new File(names);
        if (file.exists()) {
            boolean delete = file.delete();
            ToastUtils.show("文件存在" + delete);
            return;
        }
        DownloadInfo downloadInfo = DownloadInfo.create(url, loc, name);
        RxDownload rxDownload = RxDownload.create(downloadInfo);
        rxDownload.setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                LogUtils.d("onStarting");
            }

            @Override
            public void onDownloading(DownloadInfo info) {
                LogUtils.d("onDownloading");
            }

            @Override
            public void onStopped(DownloadInfo info) {
                LogUtils.d("onStopped");
                if (isAttach()) {
                    getMvpView().getDownLoadFailure(info.saveDirPath + File.separator + info.saveFileName);
                }
            }

            @Override
            public void onCanceled(DownloadInfo info) {
                LogUtils.d("onCanceled");
                if (isAttach()) {
                    getMvpView().getDownLoadFailure(info.saveDirPath + File.separator + info.saveFileName);
                }
            }

            @Override
            public void onCompletion(DownloadInfo info) {
                LogUtils.d("onCompletion = " + info.saveDirPath);
                if (isAttach()) {
                    getMvpView().getDownLoadSuccess(info.saveDirPath + File.separator + info.saveFileName);
                }
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                LogUtils.d("onError");
                if (isAttach()) {
                    getMvpView().getDownLoadFailure(info.saveDirPath + File.separator + info.saveFileName);
                }
            }
        });
        rxDownload.setProgressListener(new RxDownload.ProgressListener() {
            @Override
            public void onProgress(float progress, long downloadLength, long contentLength) {
                if (isAttach()) {
                    getMvpView().getDownloadProgress(downloadLength, contentLength);
                }
            }
        });
        rxDownload.start();
    }


}
