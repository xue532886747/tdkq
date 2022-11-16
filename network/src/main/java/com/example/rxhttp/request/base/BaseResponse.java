package com.example.rxhttp.request.base;

/**
 * @author 53288
 * @description 网络接口返回json格式对应的实体类
 * @date 2021/5/18
 */
public interface BaseResponse<T> {
    int getCode();

    void setCode(int code);

    T getData();

    void setData(T data);

    String getMsg();

    void setMsg(String msg);

}
