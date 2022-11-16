package com.example.rxhttp.request.setting;

import com.example.rxhttp.request.exception.ExceptionHandle;
import com.google.gson.Gson;

import java.util.Map;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author 53288
 * @description
 * @date 2021/5/18
 */
public abstract class DefaultRequestSetting implements RequestSetting {

    @Nullable
    @Override
    public Map<String, String> getRedirectBaseUrl() {
        return null;
    }

    @Nullable
    @Override
    public Map<Class<?>, String> getServiceBaseUrl() {
        return null;
    }

    @Override
    public int[] getMultiSuccessCode() {
        return null;
    }

    @Override
    public long getTimeout() {
        return 30000;
    }

    @IntRange(from = 0)
    @Override
    public long getConnectTimeout() {
        return 20000;
    }

    @Override
    public long getReadTimeout() {
        return 20000;
    }

    @Override
    public long getWriteTimeout() {
        return 20000;
    }

    @NonNull
    @Override
    public String getCacheDirName() {
        return "rxhttp_cache";
    }

    @Override
    public long getCacheSize() {
        return 10 * 1024 * 1024;
    }

    @Nullable
    @Override
    public Map<String, String> getStaticPublicQueryParameter() {
        return null;
    }

    @Nullable
    @Override
    public Map<String, ParameterGetter> getDynamicPublicQueryParameter() {
        return null;
    }

    @Nullable
    @Override
    public Map<String, String> getStaticHeaderParameter() {
        return null;
    }

    @Nullable
    @Override
    public Map<String, ParameterGetter> getDynamicHeaderParameter() {
        return null;
    }

    @Nullable
    @Override
    public <E extends ExceptionHandle> E getExceptionHandle() {
        return null;
    }

    @Nullable
    @Override
    public Interceptor[] getInterceptors() {
        return null;
    }

    @Nullable
    @Override
    public Interceptor[] getNetworkInterceptors() {
        return null;
    }

    @Override
    public boolean ignoreSslForHttps() {
        return false;
    }

    @Override
    public boolean enableTls12BelowAndroidKitkat() {
        return true;
    }

    @Override
    public void setOkHttpClient(OkHttpClient.Builder builder) {
    }

    @Nullable
    @Override
    public Gson getGson() {
        return null;
    }

    @Override
    public boolean isDebug() {
        return false;
    }
}