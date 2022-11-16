package com.example.rxhttp.request;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.core.RxLife;
import com.example.rxhttp.request.base.BaseResponse;
import com.example.rxhttp.request.exception.ApiException;
import com.example.rxhttp.request.exception.ExceptionHandle;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 53288
 * @description
 * @date 2021/5/18
 */
public class RxRequest<T, R extends BaseResponse<T>> {
    private final Observable<R> mObservable;
    private ResultCallback<T> mCallback = null;
    private RequestListener mListener = null;
    private RxLife mRxLife = null;

    private RxRequest(Observable<R> observable) {
        mObservable = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T, R extends BaseResponse<T>> RxRequest<T, R> create(@NotNull Observable<R> observable) {
        return new RxRequest<>(observable);
    }


    public RxRequest<T, R> listener(RequestListener listener) {
        mListener = listener;
        return this;
    }

    public RxRequest<T, R> autoLife(RxLife rxLife) {
        mRxLife = rxLife;
        return this;
    }

    public Disposable request(@NotNull ResultCallback<T> callback) {
        mCallback = callback;
        Disposable disposable = mObservable.subscribe(new Consumer<BaseResponse<T>>() {
            @Override
            public void accept(BaseResponse<T> bean) throws Exception {
                if (!isSuccess(bean.getCode())) {
                    throw new ApiException(bean.getCode(), bean.getMsg());
                } else {
                    mCallback.onSuccess(bean.getCode(), bean.getMsg(), bean.getData());
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
        if (code == RxHttp.getRequestSetting().getSuccessCode()) {
            return true;
        }
        int[] codes = RxHttp.getRequestSetting().getMultiSuccessCode();
        if (codes == null || codes.length == 0) {
            return false;
        }
        for (int i : codes) {
            if (code == i) {
                return true;
            }
        }
        return false;
    }

    public interface ResultCallback<E> {
        void onSuccess(int code, String msg, E data);

        void onFailed(int code, String msg);
    }

    public interface RequestListener {
        void onStart();

        void onError(ExceptionHandle handle);

        void onFinish();
    }
}
