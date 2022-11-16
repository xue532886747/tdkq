package com.example.rxhttp.request;

import android.util.Log;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.core.manager.BaseClientManager;
import com.example.rxhttp.core.utils.BaseUrlUtils;
import com.example.rxhttp.core.utils.SDCardUtils;
import com.example.rxhttp.request.interceptor.BaseUrlRedirectInterceptor;
import com.example.rxhttp.request.interceptor.CacheControlInterceptor;
import com.example.rxhttp.request.interceptor.CacheControlNetworkInterceptor;
import com.example.rxhttp.request.interceptor.PublicHeadersInterceptor;
import com.example.rxhttp.request.interceptor.PublicQueryParameterInterceptor;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 53288
 * @description
 * @date 2021/5/18
 */
public class RequestClientManager extends BaseClientManager {
    private static final RequestClientManager INSTANCE = new RequestClientManager();
    private final Retrofit mRetrofit;

    private RequestClientManager() {
        mRetrofit = create();
    }

    /**
     * 采用单例模式
     *
     * @return RequestClientManager
     */
    private static RequestClientManager getInstance() {
        return INSTANCE;
    }

    /**
     * 创建Api接口实例
     *
     * @param clazz Api接口类
     * @param <T>   Api接口
     * @return Api接口实例
     */
    static <T> T getService(Class<T> clazz) {
        return getInstance().mRetrofit.create(clazz);
    }


    /**
     * 创建Retrofit实例
     */
    @Override
    protected Retrofit create() {
        return create(RxHttp.getRequestSetting().getBaseUrl());
    }

    /**
     * 真正创建Retrofit实例
     */
    private Retrofit create(String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder().client(createOkHttpClient())
                .baseUrl(BaseUrlUtils.checkBaseUrl(baseUrl));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }

    /**
     * 创建OkHttpClient的实例
     *
     * @return OkHttpClient
     */
    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置缓存
        builder.cache(createCache());

        long timeout = RxHttp.getRequestSetting().getTimeout();
        long connectTimeout = RxHttp.getRequestSetting().getConnectTimeout();
        long readTimeout = RxHttp.getRequestSetting().getReadTimeout();
        long writeTimeout = RxHttp.getRequestSetting().getWriteTimeout();
        builder.connectTimeout(connectTimeout > 0 ? connectTimeout : timeout, TimeUnit.MILLISECONDS);
        builder.readTimeout(readTimeout > 0 ? readTimeout : timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(writeTimeout > 0 ? writeTimeout : timeout, TimeUnit.MILLISECONDS);
        // 设置应用层拦截器
        BaseUrlRedirectInterceptor.addTo(builder);
        PublicHeadersInterceptor.addTo(builder);
        PublicQueryParameterInterceptor.addTo(builder);
        CacheControlInterceptor.addTo(builder);
        Interceptor[] interceptors = RxHttp.getRequestSetting().getInterceptors();
        if (interceptors != null && interceptors.length > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        // 设置网络层拦截器
        CacheControlNetworkInterceptor.addTo(builder);
        Interceptor[] networkInterceptors = RxHttp.getRequestSetting().getNetworkInterceptors();
        if (networkInterceptors != null && networkInterceptors.length > 0) {
            for (Interceptor interceptor : networkInterceptors) {
                builder.addNetworkInterceptor(interceptor);
            }
        }
        RxHttp.getRequestSetting().setOkHttpClient(builder);
        //设置调试模式打印日志
        if (RxHttp.getRequestSetting().isDebug()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();  //日志拦截器
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }
        return builder.build();
    }

    /**
     * 创建缓存
     *
     * @return Cache
     */
    private Cache createCache() {
        File cacheFile = new File(SDCardUtils.getCacheDir(), RxHttp.getRequestSetting().getCacheDirName());
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        return new Cache(cacheFile, RxHttp.getRequestSetting().getCacheSize());
    }

}
