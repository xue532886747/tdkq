package com.example.towerdriver.appcation;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.setting.DefaultRequestSetting;
import com.example.rxhttp.request.setting.ParameterGetter;
import com.example.towerdriver.BuildConfig;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.NightModeUtils;
import com.example.towerdriver.utils.ThreadPoolUtil;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
//import com.umeng.commonsdk.UMConfigure;
//import com.umeng.socialize.PlatformConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.jpush.android.api.JPushInterface;


import static com.example.rxhttp.core.RxHttp.getAppContext;


/**
 * @author 53288
 * @description
 * @date 2021/5/10
 */
public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static int screenWidth;
    private static int screenHeight;
    public static int BadgeNumber = 0;                //华为角标

    @Override
    public void onCreate() {
        super.onCreate();
        initWidthAndHeight();
        registerActivityListener();
        initRxHttp();
        initMap();
        initDarkMode();
        initPush();
        ToastUtils.init(this);
        initBugly();
        initMeiQia();
        initUmeng();

    }


    private void initUmeng() {
        UMConfigure.preInit(this, "60d29d7e8a102159db7563fe", "Umeng");
        UMConfigure.init(this, "60d29d7e8a102159db7563fe", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // UMConfigure.preInit(this, "60d29d7e8a102159db7563fe", "Umeng");
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        PlatformConfig.setWeixin(Constant.WxAppId, Constant.WxAPPKey);
        PlatformConfig.setWXFileProvider("com.tencent.sample2.fileprovider");
        PlatformConfig.setQQZone("1111214814", "c7394704798a158208a74ab60104f0ba");

    }

    private void initMeiQia() {
        MQConfig.init(this, "a4f6248128587fb2c97e2881ddb2baf1", new OnInitCallback() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFailure(int i, String s) {
                LogUtils.d(s);
            }
        });
    }

    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(getApplicationContext(), "4fc58e6972", false);
    }


    private void initWidthAndHeight() {
        DisplayMetrics mDisplayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();
        screenWidth = mDisplayMetrics.widthPixels;
        screenHeight = mDisplayMetrics.heightPixels;
    }

    /**
     * 实例化地图
     */
    private void initMap() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * 实例化网络请求
     */
    private void initRxHttp() {
        RxHttp.init(this);
        RxHttp.initRequest(new DefaultRequestSetting() {
            @Override
            public String getBaseUrl() {
                return FreeApi.Config.BASE_URL;
            }

            @Override
            public int getSuccessCode() {
                return FreeApi.Code.SUCCESS;
            }

            @Override
            public Map<String, String> getRedirectBaseUrl() {
                Map<String, String> urls = new HashMap<>(1);
                urls.put(FreeApi.Config.ANOTHER_BASE_URL, FreeApi.Config.ANOTHER_BASE_URL);
                return urls;
            }

            @Nullable
            @Override
            public Map<String, String> getStaticHeaderParameter() {
                return null;
            }

            @Override
            public boolean isDebug() {
                return BuildConfig.DEBUG;
            }


        });
    }

    /**
     * 实例化推送
     */
    private void initPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }


    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void initDarkMode() {
        NightModeUtils.initNightMode();
    }

    public static Context getContext() {
        return getAppContext();
    }

    public static int getBadgeNumber() {
        return BadgeNumber;
    }

    public static void setBadgeNumber(int badgeNumber) {
        BadgeNumber = badgeNumber;
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.main_light_color, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        LogUtils.d(activity.getClass().getName());
        if (activity == null) {
            return;
        }
        ActivityManager.getActivities().add(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        LogUtils.d(activity.getClass().getName());
        if (ActivityManager.getActivities() == null || ActivityManager.getActivities().isEmpty()) {
            return;
        }
        ActivityManager.getActivities().remove(activity);
    }

    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(this);
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
