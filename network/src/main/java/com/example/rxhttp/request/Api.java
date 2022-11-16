package com.example.rxhttp.request;

/**
 * @author 53288
 * @description
 * @date 2021/5/18
 */
public class Api {

    public interface Header {
        /**
         * 添加以这个为名的Header可以让这个Request使用另一个BaseUrl
         */
        String BASE_URL_REDIRECT = "RxHttp-BaseUrl-Redirect";
        /**
         * 添加以这个为名的Header可以让这个Request支持缓存（有网联网获取，无网读取缓存）
         * 如//@Headers({Header.CACHE_ALIVE_SECOND + ":" + 10})
         */
        String CACHE_ALIVE_SECOND = "RxHttp-Cache-Alive-Second";
    }
    /**
     * 创建一个接口实例
     *
     * @param clazz Retrofit的ServiceInterface，建议定义为子类的内部接口
     * @param <T>   ServiceInterface的名字
     * @return 接口实例
     */
    protected static <T> T api(Class<T> clazz) {
        return RequestClientManager.getService(clazz);
    }
}
