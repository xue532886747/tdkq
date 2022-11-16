package com.example.rxhttp.core;

import android.content.Context;

import com.example.rxhttp.core.exception.RxHttpUninitializedException;
import com.example.rxhttp.download.DownloadInfo;
import com.example.rxhttp.download.RxDownload;
import com.example.rxhttp.download.setting.DefaultDownloadSetting;
import com.example.rxhttp.download.setting.DownloadSetting;
import com.example.rxhttp.request.RxOriginRequest;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.base.BaseResponse;
import com.example.rxhttp.request.exception.NullRequestSettingException;
import com.example.rxhttp.request.setting.RequestSetting;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author 53288
 * @description
 * @date 2021/5/18
 */
public class RxHttp {
    private static RxHttp INSTANCE = null;
    private final Context mAppContext;
    private RequestSetting mRequestSetting = null;
    private DownloadSetting mDownloadSetting = null;

    private RxHttp(Context context) {
        mAppContext = context;
    }

    public static void init(@NonNull Context context) {
        INSTANCE = new RxHttp(context.getApplicationContext());
    }

    public static RxHttp getInstance() {
        if (INSTANCE == null) {
            throw new RxHttpUninitializedException();
        }
        return INSTANCE;
    }

    public static void initRequest(@NonNull RequestSetting setting) {
        getInstance().mRequestSetting = setting;
    }

    public static void initDownload(@NonNull DownloadSetting setting) {
        getInstance().mDownloadSetting = setting;
    }

    @NonNull
    public static Context getAppContext() {
        return getInstance().mAppContext;
    }

    @NonNull
    public static RequestSetting getRequestSetting() {
        RequestSetting setting = getInstance().mRequestSetting;
        if (setting == null) {
            throw new NullRequestSettingException();
        }
        return setting;
    }

    @NonNull
    public static DownloadSetting getDownloadSetting() {
        DownloadSetting setting = getInstance().mDownloadSetting;
        if (setting == null) {
            setting = new DefaultDownloadSetting();
        }
        return setting;
    }


    //有序数据的请求
    public static <T, R extends BaseResponse<T>> RxRequest<T, R> request(@NotNull Observable<R> observable) {
        return RxRequest.create(observable);
    }

    //无序数据的请求
    public static <T> RxOriginRequest<T> origin_request(@NotNull Observable<Response<ResponseBody>> observable, Class<T> tClass) {
        return RxOriginRequest.origin_create(observable, tClass);
    }

    public static RxDownload download(@NonNull DownloadInfo info) {
        return RxDownload.create(info);
    }

}
