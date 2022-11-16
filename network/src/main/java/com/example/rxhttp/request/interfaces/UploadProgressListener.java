package com.example.rxhttp.request.interfaces;

/**
 * @author 53288
 * @description
 * @date 2021/5/18
 */
public interface UploadProgressListener {

    void onAllProgress(long contentLength);

    void onProgress(long contentLength, long mCurrentLength);
}
