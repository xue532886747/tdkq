package com.example.towerdriver.staff.base.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.bean.VersionBean;
import com.example.towerdriver.dialog.UpdateDialog;
import com.example.towerdriver.staff.base_approval.ui.fragment.MyApprovalFragment;
import com.example.towerdriver.staff.base_approval.ui.fragment.MyLaunchFragment;
import com.example.towerdriver.staff.base_order.ui.fragment.StaffOrderListFragment;
import com.example.towerdriver.staff.interfaces.IClickFreshListener;
import com.example.towerdriver.staff.staff_mine.fragment.StaffMineFragment;
import com.example.towerdriver.station.base.activity.StationMainActivity;
import com.example.towerdriver.station.base.presenter.StationMainPresenter;
import com.example.towerdriver.station.base.view.StationMainView;
import com.example.towerdriver.station.base_mine.ui.fragment.StationMineFragment;
import com.example.towerdriver.station.station_order.ui.fragment.StationOrderListFragment;
import com.example.towerdriver.station.station_rescue.ui.fragment.StationRescueListFragment;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.MyTool;
import com.hjq.toast.ToastUtils;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @author 53288
 * @description 员工端的主页面
 * @date 2021/6/30
 */
public class StaffMainActivity extends BaseActivity<StationMainPresenter> implements StationMainView {
    @BindView(R.id.ll_home)
    LinearLayout ll_home;
    @BindView(R.id.ll_order)
    LinearLayout ll_order;
    @BindView(R.id.ll_mine)
    LinearLayout ll_mine;
    private MyLaunchFragment myLaunchFragment;
    private MyApprovalFragment myApprovalFragment;
    private StaffMineFragment mineFragment;
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
        return R.layout.act_staff_main;
    }

    @Override
    protected void initView() {
        mTabs = new LinearLayout[3];
        mTabs[0] = ll_home;
        mTabs[1] = ll_order;
        mTabs[2] = ll_mine;
        mTabs[0].setSelected(true);
    }


    @Override
    protected void initData() {
        myLaunchFragment = MyLaunchFragment.newInstance();
        myApprovalFragment = MyApprovalFragment.newInstance();
        mineFragment = StaffMineFragment.newInstance();
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        fragments = new Fragment[]{myLaunchFragment, myApprovalFragment, mineFragment};
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main, myLaunchFragment)
                .add(R.id.fl_main, myApprovalFragment).hide(myApprovalFragment)
                .add(R.id.fl_main, mineFragment).hide(mineFragment)
                .show(myLaunchFragment)
                .commitAllowingStateLoss();
    }


    @OnClick({R.id.ll_home, R.id.ll_order, R.id.ll_mine})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                index = 0;
                if (myLaunchFragment != null) {
                    myLaunchFragment.clickRefresh(0);
                }
                break;
            case R.id.ll_order:
                index = 1;
                if (myApprovalFragment != null) {
                    myApprovalFragment.clickRefresh(1);
                }
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
                new UpdateDialog(this).Builder(null, versionName, version.getVersion(),version.getContent()).setDialogClickListener(new UpdateDialog.DialogClickListener() {
                    @Override
                    public void bottom() {
                        goToAppMarket(StaffMainActivity.this);
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
                ActivityManager.finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
