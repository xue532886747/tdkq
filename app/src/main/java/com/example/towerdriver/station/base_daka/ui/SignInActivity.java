package com.example.towerdriver.station.base_daka.ui;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.Marker;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_pay.model.PayResult;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.map.LocationManager;
import com.example.towerdriver.map.NewLocationManager;
import com.example.towerdriver.station.base_daka.presenter.SignInPresenter;
import com.example.towerdriver.station.base_daka.view.ISignInView;
import com.example.towerdriver.station.interfaces.UpdateUiCallBack;
import com.example.towerdriver.station.service.SignService;
import com.example.towerdriver.utils.MyTool;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author 53288
 * @description 打卡(地图逻辑修改)
 * @date 2021/7/1
 */
public class SignInActivity extends BaseActivity<SignInPresenter> implements ISignInView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.ll_dot)
    LinearLayoutCompat ll_dot;
    @BindView(R.id.tv_location)
    AppCompatTextView tv_location;
    @BindView(R.id.bt_shangban)
    AppCompatButton bt_shangban;
    @BindView(R.id.bt_xiaban)
    AppCompatButton bt_xiaban;
    @BindView(R.id.tv_name)
    AppCompatTextView tv_name;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_update)
    LinearLayout ll_update;
    @BindView(R.id.iv_image)
    AppCompatImageView iv_image;
    private SmartRefreshUtils mSmartRefreshUtils;
    private boolean isSelectType = false;
    private NewLocationManager newLocationManager;
    private SignService mSignService;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mSignService = ((SignService.SignBinder) service).getService();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            }
            mSignService.setUpdateUiCallBack(new UpdateUiCallBack() {
                @Override
                public void updateUi(String address, double latitude, double longitude) {
                    LogUtils.d(address + " , " + latitude + " , " + longitude);
                    if (presenter != null) {
                        if (iv_image != null) {
                            iv_image.startAnimation(MyTool.shakeAnimation(2));
                        }
                        presenter.upDateLocation(address, latitude + "", longitude + "");
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private boolean isBindService;

    @Override
    protected SignInPresenter createPresenter() {
        return new SignInPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_sign_in;
    }

    @Override
    protected void initView() {

        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        newLocationManager = new NewLocationManager(null,30000);
        newLocationManager.setGetLocationListener(new NewLocationManager.getLocationListener() {
            @Override
            public void getMarker(Marker marker) {
            }

            @Override
            public void getLocationMsg(String msg) {
                tv_location.setText("位置更新失败...");
                ToastUtils.show(msg);
            }

            @Override
            public void getLocationSuccess(String addr, String address, double latitude, double longitude, boolean isFirstLoc, int type) {
                if (isFirstLoc) {
                    if (type == 1) {
                        if (presenter != null) {
                            tv_location.setText(addr + "");
                            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(SignInActivity.this, "正在打卡...");
                            presenter.StationSignIn("1", latitude + "", longitude + "", addr);
                        }
                    } else if (type == 2) {
                        if (presenter != null) {
                            tv_location.setText(addr + "");
                            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(SignInActivity.this, "正在打卡...");
                            presenter.StationSignIn("2", latitude + "", longitude + "", addr);
                        }
                    } else {
                        if (presenter != null) {
                            tv_location.setText("位置更新成功！");
                            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(SignInActivity.this, "正在更新...");
                            presenter.upDateLocation(addr, latitude + "", longitude + "");
                        }
                    }
                }
            }

            @Override
            public void getLocationRescue(String address, boolean isRescue) {

            }
        });

    }

    /**
     * 下拉刷新
     */
    private void refreshList() {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在加载...");
            presenter.StationStatus();
        }
    }

    @Override
    protected void initData() {
        refreshList();
        requirePermissionIntent(3, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @OnClick({R.id.ll_back, R.id.ll_dot, R.id.bt_shangban, R.id.bt_xiaban, R.id.ll_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_shangban:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_shangban)) {
                    dialog(1);
                    isSelectType = true;
                }
                break;
            case R.id.bt_xiaban:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_xiaban)) {
                    dialog(2);
                    isSelectType = false;
                }
                break;
            case R.id.ll_update:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_update)) {
                    iv_image.setAnimation(MyTool.shakeAnimation(2));
                    requirePermissionIntent(3, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                break;
        }
    }


    private void dialog(int stype) {
        new CenterDialog(this).Builder("确认打卡?", "确定", "取消").setDialogClickListener(new CenterDialog.DialogClickListener() {
            @Override
            public void top(int type) {
                requirePermissionIntent(stype, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            @Override
            public void bottom(int type) {

            }
        }).show();
    }

    /**
     * 根据所需要的权限跳到不同的地方
     *
     * @param type 标记符 1==上班，2==下班，3==更新位置
     * @param args 需要的权限
     */
    private void requirePermissionIntent(int type, String... args) {
        if (presenter != null) {
            presenter.addToRxLife(new RxPermissions(this).
                    requestEachCombined(args).
                    subscribe(permission -> {
                        if (permission.granted) {
                            tv_location.setText("正在更新位置...");
                            if (newLocationManager != null) {
                                newLocationManager.gotoCenter(type);
                            }
                        } else {
                            showPermissionDialog("需要权限开通", "有些权限未开启，是否前往设置?");
                        }
                    }));
        }

    }

    private void tabSelected() {
        ll_dot.setSelected(isSelectType);
        if (isSelectType) {
            tv_name.setText("上班中");
        } else {
            tv_name.setText("休息中");
        }
    }

    /**
     * 启动service的两个条件
     * 1.是用户必须是在上班中，
     * 2.用户必须登录
     */
    private void startSignService() {
        bindSignService();
        Intent intent = new Intent(this, SignService.class);
        startService(intent);
    }

    /**
     * 停止Service,
     */
    private void stopSignService() {
        unSignBindService();
        Intent intent = new Intent(this, SignService.class);
        stopService(intent);
    }

    /**
     * 绑定service
     */
    private void bindSignService() {
        Intent intent = new Intent(this, SignService.class);
        isBindService = bindService(intent, mConn, BIND_AUTO_CREATE);
    }

    /**
     * 解绑service
     */
    private void unSignBindService() {
        if (mConn != null && isBindService) {
            unbindService(mConn);
            isBindService = false;
        }
    }

    /**
     * 获取状态成功
     *
     * @param work_status
     */
    @Override

    public void SignStatusSuccess(int work_status) {
        closeDialog();
        loadingSuccessOrFailure(1);
        if (work_status == 2) {
            startSignService();
            isSelectType = true;
        } else {
            stopSignService();
            isSelectType = false;
        }
        tabSelected();
    }

    @Override
    public void SignStatusFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
        loadingSuccessOrFailure(2);
    }

    /**
     * 更新位置
     *
     * @param msg
     */
    @Override
    public void UpDateAddressSuccess(String address, String msg) {
        closeDialog();
        tv_location.setText(address + "");
        ToastUtils.show("更新位置成功！");
    }

    @Override
    public void UpDateAddressFailure(String msg) {
        closeDialog();
        ToastUtils.show("更新位置失败！");
    }

    /**
     * 用户打卡
     *
     * @param type
     * @param msg
     */
    @Override
    public void signInSuccess(String type, String msg, String addr) {
        closeDialog();
        int status = Integer.parseInt(type);
        if (status == 1) {
            ToastUtils.show("打卡成功！");
            startSignService();
            isSelectType = true;
        } else {
            ToastUtils.show("签退成功！");
            stopSignService();
            isSelectType = false;
        }
        tabSelected();
    }

    @Override
    public void signInFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void LoadingClose() {
        unSignBindService();
        goToLogin();
        loadingSuccessOrFailure(2);
    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        ToastUtils.show(msg);
        loadingSuccessOrFailure(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newLocationManager != null) {
            newLocationManager.setGetLocationListener(null);
            newLocationManager = null;
        }
        unSignBindService();
    }


    private void loadingSuccessOrFailure(int loading_type) {
        if (loading_type == 1) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.success();
            }
        } else if (loading_type == 2) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.fail();
            }
        }
    }


}
