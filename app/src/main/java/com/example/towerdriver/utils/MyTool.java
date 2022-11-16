package com.example.towerdriver.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;

import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/8/2
 */
public class MyTool {
    // 跳转到应用宝的网页版地址
    //    private final static String WEB_YINGYONGBAO_MARKET_URL = "https://a.app.qq.com/o/simple.jsp?pkgname=com.dk.collage";
    public final static String MARKET_PKG_NAME_MI = "com.xiaomi.market";       //小米
    public final static String MARKET_PKG_NAME_360 = "com.qihoo.appstore";     //360
    public final static String MARKET_PKG_NAME_VIVO = "com.bbk.appstore";        //vivo
    public final static String MARKET_PKG_NAME_OPPO = "com.oppo.market";       //oppo
    public final static String MARKET_PKG_NAME_YINGYONGBAO = "com.tencent.android.qqdownloader";  //应用宝
    public final static String MARKET_PKG_NAME_HUAWEI = "com.huawei.appmarket";        //华为
    public final static String MARKET_PKG_NAME_BAIDU = "com.baidu.appsearch";              //百度
    public final static String WEB_YINGYONGBAO_MARKET_URL = "https://a.app.qq.com/o/simple.jsp?pkgname=com.nfc.shop";

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取项目的渠道号
     *
     * @return
     */
    public static String getChannel(Context context) {
        ApplicationInfo ai = null;
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            return bundle.getString("APP_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 检测是否安装支付宝
     *
     * @param context
     * @return
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;
    }

    public static boolean isMobile_spExist(Context context) {
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase("com.tencent.android.qqdownloader"))
                return true;
        }
        return false;
    }

    /**
     * 检测是否安装微信
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Animation shakeAnimation(int counts) {
        Animation rotateAnimation = new RotateAnimation(0, 20, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new CycleInterpolator(counts));
        rotateAnimation.setRepeatCount(1);
        rotateAnimation.setDuration(300);
        return rotateAnimation;
    }

}
