package com.example.rxhttp.core.manager;

import retrofit2.Retrofit;

/**
 * @author 53288
 * @description 用于管理Retrofit实例
 * 子类继承后自行判断是否采用单例模式
 * @date 2021/5/18
 */
public abstract class BaseClientManager {
    protected abstract Retrofit create();
}
