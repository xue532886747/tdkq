package com.example.towerdriver.station.base.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_main.ui.activity.MainActivity;
import com.example.towerdriver.base_notice.ui.fragment.OrderNoticeFragment;
import com.example.towerdriver.bean.VersionBean;
import com.example.towerdriver.dialog.UpdateDialog;
import com.example.towerdriver.staff.base.activity.StaffMainActivity;
import com.example.towerdriver.station.base.presenter.StationMainPresenter;
import com.example.towerdriver.station.base.view.StationMainView;
import com.example.towerdriver.station.base_mine.ui.fragment.StationMineFragment;
import com.example.towerdriver.station.service.SignService;
import com.example.towerdriver.station.station_order.ui.fragment.StationOrderListFragment;
import com.example.towerdriver.station.station_rescue.ui.fragment.StationRescueListFragment;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.MyTool;
import com.hjq.toast.ToastUtils;
import com.umeng.commonsdk.debug.I;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @author 53288
 * @description 站长端的主页面
 * @date 2021/6/30
 */
public class StationMainActivity extends BaseActivity<StationMainPresenter> implements StationMainView {
    @BindView(R.id.ll_home)
    LinearLayout ll_home;
    @BindView(R.id.ll_rescue)
    LinearLayout ll_rescue;
    @BindView(R.id.ll_mine)
    LinearLayout ll_mine;
    private StationOrderListFragment mOrderListFragment;
    private StationRescueListFragment mRescueListFragment;
    private StationMineFragment mineFragment;
    private Fragment[] fragments;
    private LinearLayout[] mTabs;
    private int index;
    private int currentTabIndex;
    private FragmentManager fragmentManager;
    private long clickTime = 0; //记录第一次点击的时间


    @Override
    protected StationMainPresenter createPresenter() {
        return new StationMainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_station_main;
    }

    @Override
    protected void initView() {
        mTabs = new LinearLayout[3];
        mTabs[0] = ll_home;
        mTabs[1] = ll_rescue;
        mTabs[2] = ll_mine;
        mTabs[0].setSelected(true);
    }

    @Override
    protected void initData() {
        mOrderListFragment = StationOrderListFragment.newInstance();
        mRescueListFragment = StationRescueListFragment.newInstance();
        mineFragment = StationMineFragment.newInstance();
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        fragments = new Fragment[]{mOrderListFragment, mRescueListFragment, mineFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main, mOrderListFragment)
                .add(R.id.fl_main, mRescueListFragment).hide(mRescueListFragment)
                .add(R.id.fl_main, mineFragment).hide(mineFragment)
                .show(mOrderListFragment)
                .commitAllowingStateLoss();
    }


    @OnClick({R.id.ll_home, R.id.ll_rescue, R.id.ll_mine})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                index = 0;
                break;
            case R.id.ll_rescue:
                index = 1;
                break;
            case R.id.ll_mine:
                index = 2;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fl_main, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    @Override
    public void getVersionSuccess(VersionBean version) {
        try {
            String versionName = MyTool.getVersionName(this);         //我自己的版本号
            int my_versionName = Integer.parseInt(MyTool.getVersionName(this).replace(".", ""));
            int my_update_version = Integer.parseInt(version.getVersion().replace(".", ""));
            if (my_versionName < my_update_version) {
                new UpdateDialog(this).Builder(null, versionName, version.getVersion(), version.getContent()).setDialogClickListener(new UpdateDialog.DialogClickListener() {
                    @Override
                    public void bottom() {
                        goToAppMarket(StationMainActivity.this);
                    }

                    @Override
                    public void finish() {

                    }
                }).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到应用市场
     *
     * @param context
     */
    public void goToAppMarket(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + context.getApplicationContext().getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            String channelName = MyTool.getChannel(context);
            PackageManager pm = context.getPackageManager();
            List<ResolveInfo> resInfo = pm.queryIntentActivities(intent, 0);
            String pkgName = "";
            if (!TextUtils.isEmpty(channelName)) {
                switch (channelName) {
                    case "huawei":
                        pkgName = MyTool.MARKET_PKG_NAME_HUAWEI;
                        break;
                    case "oppo":
                        pkgName = MyTool.MARKET_PKG_NAME_OPPO;
                        break;
                    case "vivo":
                        pkgName = MyTool.MARKET_PKG_NAME_VIVO;
                        break;
                    case "xiaomi":
                        pkgName = MyTool.MARKET_PKG_NAME_MI;
                        break;
                    default:
                        pkgName = MyTool.MARKET_PKG_NAME_YINGYONGBAO;
                        break;
                }
                if (resInfo.size() > 0) {
                    for (int i = 0; i < resInfo.size(); i++) {
                        ResolveInfo resolveInfo = resInfo.get(i);
                        String packageName = resolveInfo.activityInfo.packageName;
                        if (packageName.toLowerCase().equals(pkgName)) {
                            Intent intentFilterItem = new Intent(Intent.ACTION_VIEW, uri);
                            intentFilterItem.setComponent(new ComponentName(packageName, resolveInfo.activityInfo.name));
                            context.startActivity(intentFilterItem);
                            return;
                        }
                    }
                    if (!MyTool.isMobile_spExist(this)) {
                        Uri uri1 = Uri.parse("https://a.app.qq.com/o/simple.jsp?pkgname=com.nfc.shop");
                        Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                        startActivity(intent1);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void LoadingClose() {

    }

    @Override
    public void showFailed(int code, String msg) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.getVersion();
        }
    }

    /**
     * 退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - clickTime) > 2000) {
                ToastUtils.show("再按一次退出塔电快骑!");
                clickTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(StationMainActivity.this, SignService.class);
                stopService(intent);
                ActivityManager.finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
