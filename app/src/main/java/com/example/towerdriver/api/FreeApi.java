package com.example.towerdriver.api;

import com.example.rxhttp.request.Api;
import com.example.towerdriver.Constant;

/**
 * @author 53288
 * @description
 * @date 2021/5/19
 */
public class FreeApi extends Api {

    public static ApiService api() {
        return Api.api(ApiService.class);
    }

    public interface Code {
        int SUCCESS = 200;          //请求成功

        int TOKENEXPIRED = 202;     //token过期
    }

    public interface Config {
        long HTTP_TIMEOUT = 10000;
        String BASE_URL = Constant.BASE_URL;

        String ANOTHER_BASE_URL = "";
    }
}
