package com.example.base_ui.bigimageview.glide.progress;

/**
 * @author 53288
 * @description
 * @date 2021/6/7
 */
public interface OnProgressListener {

    void onProgress(String url, boolean isComplete, int percentage, long bytesRead, long totalBytes);
}
