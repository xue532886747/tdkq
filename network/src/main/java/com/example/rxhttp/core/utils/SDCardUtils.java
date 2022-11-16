package com.example.rxhttp.core.utils;

import android.content.ContextWrapper;
import android.os.Environment;

import com.example.rxhttp.core.RxHttp;

import java.io.File;

/**
 * @author 53288
 * @description 这里需要改 要适配>=29
 * @date 2021/5/18
 */
public class SDCardUtils {

    public static String getCacheDir() {
        File cacheFile = null;
        if (isSDCardAlive()) {
            cacheFile = RxHttp.getAppContext().getExternalCacheDir();
        }
        if (cacheFile == null) {
            cacheFile = RxHttp.getAppContext().getCacheDir();
        }
        return cacheFile.getAbsolutePath();
    }

    public static String getDownloadCacheDir() {
        ContextWrapper cw = new ContextWrapper(RxHttp.getAppContext());
        File directory = cw.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        return directory.getAbsolutePath();
    }

    private static boolean isSDCardAlive() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
