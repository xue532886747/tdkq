package com.example.towerdriver.api;

import com.example.rxhttp.request.base.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * @author 53288
 * @description
 * @date 2021/5/19
 */
public class ResponseBean<T> implements BaseResponse<T> {
    @SerializedName(value = "code")
    private int code;
    @SerializedName(value = "data")
    private T data;
    @SerializedName(value = "msg")
    private String message;


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public void setData(T t) {
        this.data = data;
    }

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public void setMsg(String msg) {
        this.message = msg;
    }
}
