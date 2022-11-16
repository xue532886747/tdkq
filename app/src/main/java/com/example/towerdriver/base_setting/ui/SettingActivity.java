package com.example.towerdriver.base_setting.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_login.bean.UserBean;
import com.example.towerdriver.base_login.ui.activity.LoginActivity;
import com.example.towerdriver.base_setting.presenter.SettingPresenter;
import com.example.towerdriver.base_setting.view.SetView;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.DataCleanManager;
import com.example.towerdriver.utils.LightAndDarkModeUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.SettingUtils;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.webview.MyWebViewActivity;
import com.hjq.toast.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


/**
 * @author 53288
 * @description 设置页面
 * @date 2021/5/20
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements SetView {
    @BindView(R.id.switch_btn)
    SwitchCompat mSwitchBtn;
    @BindView(R.id.bt_quit)
    AppCompatButton bt_quit;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.cl_treaty)
    ConstraintLayout cl_treaty;
    @BindView(R.id.cl_clean)
    ConstraintLayout cl_clean;
    @BindView(R.id.cl_account)
    ConstraintLayout cl_account;
    @BindView(R.id.tv_number)
    AppCompatTextView tv_number;

    @BindView(R.id.cl_about)
    ConstraintLayout cl_about;
    LightAndDarkModeUtils lightAndDarkModeUtils;
    private String totalCacheSize;
    private CenterDialog centerDialog;

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_setting;
    }

    @Override
    protected void initView() {

        lightAndDarkModeUtils = new LightAndDarkModeUtils(this);
        mSwitchBtn.setChecked(SettingUtils.getInstance().isDarkTheme());
        lightAndDarkModeUtils.setMode();
        mSwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingUtils.getInstance().setDarkTheme(isChecked);
                MyApplication.initDarkMode();
                buttonView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ActivityManager.recreate();
                    }
                }, 300);
                lightAndDarkModeUtils.setMode();
            }
        });
        getNumber();

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.bt_quit, R.id.ll_back, R.id.cl_treaty, R.id.cl_clean, R.id.cl_account,R.id.cl_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_treaty:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_treaty)) {
                    MyWebViewActivity.launch(this, false, "用户协议", Constant.USER_PRIVACY);
                }
                break;
            case R.id.bt_quit:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_quit)) {
                    quit();
                }
                break;
            case R.id.ll_back:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_back)) {
                    finish();
                }
                break;
            case R.id.cl_clean:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_clean)) {
                    centerDialog = new CenterDialog(this).Builder("清理缓存", "清理", "取消").setDialogClickListener(new CenterDialog.DialogClickListener() {
                        @Override
                        public void top(int type) {
                            DataCleanManager.clearAllCache(SettingActivity.this);
                            getNumber();
                        }

                        @Override
                        public void bottom(int type) {
                            centerDialog.cancle();
                        }
                    });
                    centerDialog.show();
                }
                break;
            case R.id.cl_account:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_account)) {
                    Intent intent = new Intent(this, AccountManagerActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.cl_about:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_treaty)) {
                    MyWebViewActivity.launch(this, false, "关于我们", Constant.RENTAL_ABOUT);
                }
                break;
        }
    }

    /**
     * 用户退出
     */
    private void quit() {
        if (UserUtils.getInstance().isLogin()) {
            UserBean loginBean = UserUtils.getInstance().getLoginBean();
            if (presenter != null) {
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在退出...");
                presenter.LoginOut(loginBean.getLogin_type());
            }
        }
    }

    /**
     * 退出成功
     *
     * @param msg
     */
    @Override
    public void UserLogoutSuccess(String msg) {
        closeDialog();
        ToastUtils.show("退出成功");
        Set<String> tags = new HashSet<>();
        tags.add("user");
        JPushInterface.deleteTags(this, 0, tags);
        int l = (int) System.currentTimeMillis();
        JPushInterface.deleteAlias(this,l);
        UserUtils.getInstance().logout();
        ActivityManager.finishAllActivity();
        LoginActivity.launch(this, 0);
    }

    /**
     * 退出失败
     *
     * @param code
     * @param msg
     */
    @Override
    public void UserLogoutFailure(int code, String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        LogUtils.d(event.getType() + " , " + event.isLogin());
    }


    @Override
    public void LoadingClose() {
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        lightAndDarkModeUtils.setMode();
    }

    /**
     * 得到缓存的大小
     */
    public void getNumber() {
        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(getApplicationContext());
            tv_number.setText(totalCacheSize + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (centerDialog != null) {
            centerDialog.shutDownDialog();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (UserUtils.getInstance().isLogin()) {
            int loginType = UserUtils.getInstance().getLoginType();
            if (loginType == 1) {
                cl_account.setVisibility(View.VISIBLE);
            }else {
                cl_account.setVisibility(View.GONE);
            }
        }
    }
}
