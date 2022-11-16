package com.example.towerdriver.utils.tools;

import android.util.Log;

import com.example.towerdriver.BuildConfig;

/**
 * @author 53288
 * @description 日志封装
 * @date 2021/5/19
 */
public class LogUtils {
    static String className;    //类名
    static String methodName;   //方法名
    static int lineNumber;      //行数

    /**
     * 判断是否可以调试
     *
     * @return
     */
    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log) {
        StringBuilder sb = new StringBuilder();
        sb.append("===");
        sb.append(className.split("\\.")[0]);
        sb.append("1");
        sb.append("-->");
        sb.append(methodName);
        sb.append("(").append(className).append(":").append(lineNumber).append(")===:");
        sb.append(log);
        return sb.toString();
    }

    /**
     * 获取文件名、方法名、所在行数
     *
     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }

    public static void i(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }

}
