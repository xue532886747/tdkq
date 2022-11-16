package com.example.rxhttp.download.setting;


import com.example.rxhttp.download.DownloadInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2018/10/16
 */
public class DefaultDownloadSetting implements DownloadSetting {

    @NonNull
    @Override
    public String getBaseUrl() {
        return "http://rent.tdpower.net/api/";
    }

    @Override
    public long getTimeout() {
        return 60000;
    }

    @Override
    public long getConnectTimeout() {
        return 5000;
    }

    @Override
    public long getReadTimeout() {
        return 5000;
    }

    @Override
    public long getWriteTimeout() {
        return 5000;
    }

    @Nullable
    @Override
    public String getSaveDirPath() {
        return null;
    }

    @NonNull
    @Override
    public DownloadInfo.Mode getDefaultDownloadMode() {
        return DownloadInfo.Mode.APPEND;
    }
}
