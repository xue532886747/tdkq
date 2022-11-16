package com.example.towerdriver.base_driver.presenter;

import android.text.TextUtils;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.body.UpLoadRequestBody;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.rxhttp.request.interfaces.UploadProgressListener;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.api.UsuallyBean;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.model.bean.ImageToUrlBean;
import com.example.towerdriver.base_driver.model.bean.ReleaseBean;
import com.example.towerdriver.base_driver.view.IDriveReleaseView;
import com.example.towerdriver.staff.base_approval.model.FileToUrlBean;
import com.example.towerdriver.staff.base_approval.view.CreateApprovalView;
import com.example.towerdriver.utils.RxPartMapUtils;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * @author 53288
 * @description 新建文件
 * @date 2021/7/8
 */
public class CreateApprovalPresenter extends BasePresenter<CreateApprovalView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public CreateApprovalPresenter(CreateApprovalView baseView) {
        super(baseView);
    }

    /**
     * 文件变为url
     *
     * @param type 0==图片,1==其他文件
     * @param name
     */
    public void getImageToUrl(int type, @NotNull String name) {
        if (type == 0) {
            FileToUrl(0, name);
        } else {
            FileToUrl(1, name);
        }
    }

    private void FileToUrl(int type, @NotNull String name) {
        File file = null;
        file = new File(name);
        if (file == null) {
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("image", file.getName(), RxPartMapUtils.toRequestBodyOfImage(file));
        builder.addFormDataPart("token", UserUtils.getInstance().getUserToken());
        UpLoadRequestBody upLoadRequestBody = new UpLoadRequestBody(builder.build(), new UploadProgressListener() {
            @Override
            public void onAllProgress(long contentLength) {

            }

            @Override
            public void onProgress(long contentLength, long mCurrentLength) {
                if (isAttach()) {
                    getMvpView().FileToUrlProgress(contentLength, mCurrentLength);
                }
            }
        });
        addToRxLife(RxHttp.request(FreeApi.api().FileToUrl(upLoadRequestBody)).listener(new RxRequest.RequestListener() {
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
        }).request(new RxRequest.ResultCallback<FileToUrlBean>() {
            @Override
            public void onSuccess(int code, String msg, FileToUrlBean data) {
                if (data != null) {
                    if (isAttach()) {
                        getMvpView().FileToUrlSuccess(type, data.getImage());
                    }
                } else {
                    if (isAttach()) {
                        getMvpView().FileToUrlFailure("上传失败");
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().FileToUrlFailure(msg);
                    }
                }
            }
        }));
    }

    /**
     * 小哥发布新建
     *
     * @param audit_id 审批人id
     * @param title    标题
     * @param content  内容
     * @param files    文件
     * @param image    照片
     */
    public void release(@NotNull String audit_id, @NotNull String title, @NotNull String content, String image, String files) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(title)) {
            params.put("title", title);
        }
        if (!TextUtils.isEmpty(content)) {
            params.put("content", content);
        }
        if (!TextUtils.isEmpty(audit_id)) {
            params.put("audit_id", audit_id);
        }
        if (!TextUtils.isEmpty(files)) {
            params.put("files", files);
        }
        if (!TextUtils.isEmpty(image)) {
            params.put("images", image);
        }
        if (!TextUtils.isEmpty(UserUtils.getInstance().getUserToken())) {
            params.put("token", UserUtils.getInstance().getUserToken());
        }
        addToRxLife(RxHttp.request(FreeApi.api().createApprovalRelease(params)).listener(new RxRequest.RequestListener() {
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
                    getMvpView().createApprovalSuccess(msg);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().createApprovalFailure(msg);
                    }
                }
            }
        }));
    }
}
