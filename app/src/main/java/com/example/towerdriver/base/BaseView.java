package com.example.towerdriver.base;

/**
 * @author 53288
 * @description view层的基类
 * @date 2021/5/17
 */
public interface BaseView {
    /**
     * 关闭加载框
     */
    void LoadingClose();

    /**
     * 其他问题
     *
     * @param code
     * @param msg
     */
    void showFailed(int code, String msg);
}
