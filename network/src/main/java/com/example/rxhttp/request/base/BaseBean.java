package com.example.rxhttp.request.base;

import com.example.rxhttp.request.utils.JsonFormatUtils;
import com.google.gson.Gson;

/**
 * @author 53288
 * @description
 * @date 2021/5/18
 */
public class BaseBean {

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toFormatJson() {
        return JsonFormatUtils.format(toJson());
    }
}
