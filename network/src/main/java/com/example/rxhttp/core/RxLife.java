package com.example.rxhttp.core;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author 53288
 * @description rxjava防止内存泄露
 * @date 2021/5/18
 */
public class RxLife {
    private CompositeDisposable mCompositeDisposable = null;

    private RxLife() {
        mCompositeDisposable = new CompositeDisposable();
    }

    public static RxLife create(){
        return new RxLife();
    }
    public void destroy(){
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            return;
        }
        mCompositeDisposable.dispose();
    }
    public void add(Disposable d) {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }
}
