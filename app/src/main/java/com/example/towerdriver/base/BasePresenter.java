package com.example.towerdriver.base;

import com.example.rxhttp.core.RxLife;

import io.reactivex.disposables.Disposable;

/**
 * @author 53288
 * @description p层的基类
 * @date 2021/5/17
 */
public abstract class BasePresenter<V extends BaseView> {
    private V baseView;
    private RxLife rxLife;

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public BasePresenter(V baseView) {
        this.baseView = baseView;
        rxLife = RxLife.create();
    }

    /**
     * 解绑view
     */
    public void detachView() {
        baseView = null;
        if (rxLife != null) {
            rxLife.destroy();
            rxLife = null;
        }
    }


    public boolean isAttach() {
        return baseView != null;
    }

    /**
     * 获取view
     *
     * @return
     */
    public V getMvpView() {
        return baseView;
    }

    public RxLife getRxLife() {
        return rxLife;
    }

    public void addToRxLife(Disposable disposable) {
        if (rxLife != null) {
            rxLife.add(disposable);
        }
    }
}
