package com.example.towerdriver.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.meiqia.receiver.NoticeMessageReceiver;
import com.example.towerdriver.base_login.ui.activity.LoginActivity;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.receiver.NetWorkReceiver;
import com.example.towerdriver.station.service.SignService;
import com.example.towerdriver.utils.sp.UserUtils;
import com.hjq.toast.ToastUtils;
import com.meiqia.core.MQMessageManager;
import com.meiqia.meiqiasdk.controller.MQController;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.UMShareAPI;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

import static com.example.towerdriver.Constant.TOKEN_EXPIRED;

/**
 * @author 53288
 * @description activity的基类
 * @date 2021/5/19
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected P presenter;
    protected Unbinder unBind;
    protected RxPermissions rxPermissions;
    protected Dialog mWeiboDialog;
    private NoticeMessageReceiver mMessageReceiver;

    /**
     * 是否注册事件分发，默认不绑定
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unBind = ButterKnife.bind(this);
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
        presenter = createPresenter();
        initView();
        initData();

    }

    /**
     * 抽象方法：实例化Presenter
     */
    protected abstract P createPresenter();


    /**
     * 抽象方法：得到布局id
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 抽象方法：初始化控件，一般在BaseActivity中通过ButterKnife来绑定，所以该方法内部一般我们初始化界面相关的操作
     *
     * @return 控件
     */
    protected abstract void initView();


    /**
     * 更新数据
     */
    protected abstract void initData();

    /**
     * 检查权限,并且请求定位
     */
    protected void checkLocationPermission(String... args) {
        rxPermissions = new RxPermissions(this);
        presenter.addToRxLife(rxPermissions.requestEachCombined(args).
                subscribe(permission -> {
                    if (permission.granted) {   //如果权限中有
                        if (permissionListener != null) {
                            permissionListener.requestPermissionSuccess();
                        }
                    } else {
                        if (permissionListener != null) {
                            permissionListener.requestPermissionFailure();
                        }
                    }
                }));
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }


    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
    }

    /**
     * 注册
     */
    private void registerReceiver() {
        mMessageReceiver = new NoticeMessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MQController.ACTION_AGENT_INPUTTING);
        intentFilter.addAction(MQController.ACTION_NEW_MESSAGE_RECEIVED);
        intentFilter.addAction(MQController.ACTION_CLIENT_IS_REDIRECTED_EVENT);
        intentFilter.addAction(MQController.ACTION_INVITE_EVALUATION);
        intentFilter.addAction(MQController.ACTION_AGENT_STATUS_UPDATE_EVENT);
        intentFilter.addAction(MQController.ACTION_BLACK_ADD);
        intentFilter.addAction(MQController.ACTION_BLACK_DEL);
        intentFilter.addAction(MQController.ACTION_QUEUEING_REMOVE);
        intentFilter.addAction(MQController.ACTION_QUEUEING_INIT_CONV);
        intentFilter.addAction(MQMessageManager.ACTION_END_CONV_AGENT);
        intentFilter.addAction(MQMessageManager.ACTION_END_CONV_TIMEOUT);
        intentFilter.addAction(MQMessageManager.ACTION_SOCKET_OPEN);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, intentFilter);
    }

    private void unRegisterReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    protected void onDestroy() {
        closeDialog();
        mWeiboDialog = null;
        if (unBind != null) {
            unBind.unbind();
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (presenter != null) {
            presenter.detachView();
        }
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    private PermissionListener permissionListener;


    public void setPermissionListener(PermissionListener permissionListener) {
        this.permissionListener = permissionListener;
    }


    /**
     * 登陆过期，前往登录页
     */
    public void goToLogin() {
        closeDialog();
        ToastUtils.show(TOKEN_EXPIRED);
        if (UserUtils.getInstance().getLoginBean() != null) {
            int login_type = UserUtils.getInstance().getLoginBean().getLogin_type();
            Intent intent = new Intent(this, SignService.class);
            stopService(intent);
            UserUtils.getInstance().logout();
            new LoginEvent(false, login_type).post();
            LoginActivity.launch(this, login_type);
        } else {
            new LoginEvent(false, 0).post();
            LoginActivity.launch(this, 0);
        }
        Set<String> tags = new HashSet<>();
        tags.add("user");
        JPushInterface.deleteTags(this, 0, tags);
        int l = (int) System.currentTimeMillis();
        JPushInterface.deleteAlias(this,l);
    }


    /**
     * 权限弹窗
     *
     * @param title
     * @param message
     */
    public void showPermissionDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(title)
                .setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (Build.VERSION.SDK_INT >= 9) {
                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                        } else if (Build.VERSION.SDK_INT <= 8) {
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                            intent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
                        }
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public interface PermissionListener {
        /**
         * 权限请求成功
         */
        void requestPermissionSuccess();

        /**
         * 权限请求失败
         */
        void requestPermissionFailure();
    }


    /**
     * 关闭弹窗
     */
    protected void closeDialog() {
        if (mWeiboDialog != null && mWeiboDialog.isShowing()) {
            WeiboDialogUtils.closeDialog(mWeiboDialog);
        }
    }
}
