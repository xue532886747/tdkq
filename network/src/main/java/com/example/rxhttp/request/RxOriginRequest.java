package com.example.rxhttp.request;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.core.RxLife;
import com.example.rxhttp.request.base.BaseResponse;
import com.example.rxhttp.request.exception.ApiException;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author 53288
 * @description 针对一些第三方的请求，返回的数据不规范，所以只能修改了
 * @date 2021/5/25
 */
public class RxOriginRequest<T> {
    private final Observable<Response<ResponseBody>> mObservable;
    private OriginResultCallback<T> mCallback = null;
    private RequestListener mListener = null;
    private RxLife mRxLife = null;

    private RxOriginRequest(Observable<Response<ResponseBody>> observable, Class<T> clazz) {
        mObservable = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> RxOriginRequest<T> origin_create(@NotNull Observable<Response<ResponseBody>> observable, Class<T> clazz) {
        return new RxOriginRequest<>(observable, clazz);
    }


    public RxOriginRequest<T> listener(RequestListener listener) {
        mListener = listener;
        return this;
    }

    public RxOriginRequest<T> autoLife(RxLife rxLife) {
        mRxLife = rxLife;
        return this;
    }

    public Disposable request(@NotNull OriginResultCallback<T> callback,Class<T> clazz) {
        mCallback = callback;
        Disposable disposable = mObservable.subscribe(new Consumer<Response<ResponseBody>>() {
            @Override
            public void accept(Response<ResponseBody> responseBody) throws Exception {
                int code = responseBody.code();
                String message = responseBody.message();
                if (code != 200) {
                    throw new ApiException(code, message);
                } else {
                    ResponseBody body = responseBody.body();
                    String string = body.string();
                    T t = new Gson().fromJson(string, clazz);
                    mCallback.onSuccess(code, message, t);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable e) throws Exception {
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    mCallback.onFailed(apiException.getCode(), apiException.getMsg());
                } else {
                    if (mListener != null) {
                        ExceptionHandle handle = RxHttp.getRequestSetting().getExceptionHandle();
                        if (handle == null) {
                            handle = new ExceptionHandle();
                        }
                        handle.handle(e);
                        mListener.onError(handle);
                    }
                }
                if (mListener != null) {
                    mListener.onFinish();
                }
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                if (mListener != null) {
                    mListener.onFinish();
                }
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                if (mListener != null) {
                    mListener.onStart();
                }
            }
        });
        if (mRxLife != null) {
            mRxLife.add(disposable);
        }
        return disposable;
    }

    private boolean isSuccess(int code) {
        if (code == 200) {
            return true;
        }
        return false;
    }


    public interface OriginResultCallback<T> {
        void onSuccess(int code, String msg, T t);

        void onFailed(int code, String msg);
    }

    public interface RequestListener {
        void onStart();

        void onError(ExceptionHandle handle);

        void onFinish();
    }
}
