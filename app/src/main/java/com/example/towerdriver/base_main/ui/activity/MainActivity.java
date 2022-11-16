package com.example.towerdriver.base_main.ui.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.example.base_ui.badgeview.QBadgeView;
import com.example.base_ui.bigimageview.ImagePreview;
import com.example.meiqia.NewCustomerServiceActivity;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_authentication.ui.activity.RealAuthActivity;
import com.example.towerdriver.base_code.ui.InviteActivity;
import com.example.towerdriver.base_driver.ui.activity.DriverListActivity;
import com.example.towerdriver.base_login.bean.UserBean;
import com.example.towerdriver.base_main.bean.AdvertiBean;
import com.example.towerdriver.base_main.bean.ListBean;
import com.example.towerdriver.base_main.bean.PickUpBean;
import com.example.towerdriver.base_main.bean.RepairBean;
import com.example.towerdriver.base_main.presenter.MainPresenter;
import com.example.towerdriver.base_main.ui.adapter.MainListAdapter;
import com.example.towerdriver.base_main.view.MainView;
import com.example.towerdriver.base_member_level.ui.activity.MemberLevelActivity;
import com.example.towerdriver.base_notice.ui.activity.NoticeActivity;
import com.example.towerdriver.base_order_list.ui.activity.OrderListActivity;
import com.example.towerdriver.base_person.ui.activity.PersonCenterActivity;
import com.example.towerdriver.base_rescue.ui.activity.RescueListActivity;
import com.example.towerdriver.base_setmenu.ui.activity.SelectMenuActivity;
import com.example.towerdriver.base_setting.ui.SettingActivity;

import com.example.towerdriver.bean.VersionBean;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.LocCenterDialog;
import com.example.towerdriver.dialog.RescueDialog;
import com.example.towerdriver.dialog.SheetButtomDialog;
import com.example.towerdriver.dialog.UpdateDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.ChangeUserEvent;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.map.NewLocationManager;
import com.example.towerdriver.member_model.MemberInfoBean;

import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.LightAndDarkModeUtils;
import com.example.towerdriver.utils.MapUtil;
import com.example.towerdriver.utils.MyTool;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.TimeUtil;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.timeutil.ITimeOver;
import com.example.towerdriver.utils.timeutil.MainCodeTimeUtils;
import com.example.towerdriver.utils.timeutil.TimeTools;
import com.example.towerdriver.utils.tools.StatusBarUtil;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.webview.MyWebViewActivity;
import com.example.towerdriver.weight.CheckUtils;
import com.example.towerdriver.weight.CircleImageView;

import com.example.towerdriver.weight.CountDownView;
import com.example.zxing.android.CaptureActivity;
import com.example.zxing.bean.ZxingConfig;
import com.example.zxing.common.Constant;
import com.hjq.toast.ToastUtils;
import com.meiqia.core.MQManager;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.example.base_ui.bigimageview.ImagePreview.LoadStrategy.AlwaysThumb;
import static com.example.towerdriver.Constant.REQUEST_CODE_SCAN;

/**
 * 客户端的主页
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainView, SensorEventListener, NewLocationManager.getLocationListener, ITimeOver {
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.tv_repair)
    AppCompatTextView tv_repair;
    @BindView(R.id.tv_get_car)
    AppCompatTextView tv_get_car;
    @BindView(R.id.iv_head_image)
    AppCompatImageView iv_head_image;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.rv_main_menu_list)
    RecyclerView rv_list;
    @BindView(R.id.iv_setting)
    AppCompatImageView iv_setting;
    @BindView(R.id.ll_loc)
    LinearLayout ll_loc;
    @BindView(R.id.user_head)
    ConstraintLayout user_head;
    @BindView(R.id.bt_interest)
    AppCompatButton bt_interest;
    @BindView(R.id.iv_qr_code)
    AppCompatImageView iv_qr_code;
    @BindView(R.id.bt_invite)
    AppCompatButton bt_invite;
    @BindView(R.id.bt_rescue)
    AppCompatButton bt_rescue;
    @BindView(R.id.my_main_banner)
    Banner banner;
    @BindView(R.id.tv_main_name)
    AppCompatTextView tv_name;
    @BindView(R.id.tv_main_tel)
    AppCompatTextView tv_tel;
    @BindView(R.id.circleimageView)
    CircleImageView mHeadImage;
    @BindView(R.id.user_level)
    AppCompatImageView user_level;

    CountDownView tv_count_down;
    private MainListAdapter mAdapter;
    private List<ListBean> mList = new ArrayList<>();
    LightAndDarkModeUtils lightAndDarkModeUtils;                //暗夜模式
    private SensorManager mSensorManager;                       //传感器
    private boolean isBannerSuccess;                            //加载banner是否成功
    private long clickTime = 0;                                 //记录第一次点击的时间
    private boolean isDrawerState = false;                      //记录抽屉布局是否打开
    private boolean isSelectType;                               //选中的模式，维修还是提车
    private String mAddress;                                    //定位获得的城市
    private List<RepairBean.StationBean> mRepairList = new ArrayList<>();
    private List<PickUpBean.WarehouseBean> mPickList = new ArrayList<>();
    private LocCenterDialog locCenterDialog;                    //弹窗
    private int if_rescue = 0;          //是否允许救援
    private NewLocationManager newLocationManager;
    private boolean isRunning = true;
    private MainCodeTimeUtils mainCodeTimeUtils;
    private long mCurrentTime = 0;

    private QBadgeView badgeView;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_main;
    }


    @SuppressLint("ResourceType")
    @Override
    protected void initView() {
        tv_count_down = findViewById(R.id.count_down);
        newLocationManager = new NewLocationManager(mMapView, 5000);
        try {
            Field field = banner.getViewPager2().getClass().getDeclaredField("mRecyclerView");
            field.setAccessible(true);
            RecyclerView recyclerView = (RecyclerView) field.get(banner.getViewPager2());
            recyclerView.setId(2); //只要修改为跟 Baidu MapView里面aa的两个ImageView的id 不相同就可以了.
        } catch (Exception e) {
            e.printStackTrace();
        }
        StatusBarUtil.setColor(this, getResources().getColor(R.color.main_light_color), 0);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);   // 获取传感器管理服务
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);   // // 为系统的方向传感器注册监听器
        lightAndDarkModeUtils = new LightAndDarkModeUtils(this);
        rv_list.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mAdapter = new MainListAdapter(this);
        rv_list.setAdapter(mAdapter);
        tabSelected(tv_repair, true);
        newLocationManager.setGetLocationListener(this);
        banner.bringToFront();
        banner.addBannerLifecycleObserver(this);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                isDrawerState = true;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                isDrawerState = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
//        checkUserIdentity();
        if (presenter != null) {
            presenter.getVersion();
        }

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onTick(long millisUntilFinished) {
        tv_count_down.getTime(millisUntilFinished - mCurrentTime);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void initData() {
        initUser();
        requirePermissionIntent(1, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        initList();
        mAdapter.OnItemClickListener(new MainListAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(int position) {
                switch (position) {
                    case 0:
                        if (!RepeatClickResolveUtil.isFastDoubleClick(0)) {
                            Intent intent = new Intent(MainActivity.this, PersonCenterActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        if (!RepeatClickResolveUtil.isFastDoubleClick(1)) {
                            Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        if (!RepeatClickResolveUtil.isFastDoubleClick(2)) {
                            Intent intent2 = new Intent(MainActivity.this, SelectMenuActivity.class);
                            startActivity(intent2);
                        }
                        break;
                    case 3:
                        if (!RepeatClickResolveUtil.isFastDoubleClick(3)) {
                            Intent intent2 = new Intent(MainActivity.this, OrderListActivity.class);
                            startActivity(intent2);
                        }
                        break;
                    case 4:
                        if (!RepeatClickResolveUtil.isFastDoubleClick(4)) {
                            Intent intent5 = new Intent(MainActivity.this, RescueListActivity.class);
                            startActivity(intent5);
                        }
                        break;
                    case 5:
                        if (!RepeatClickResolveUtil.isFastDoubleClick(5)) {
                            Intent intent5 = new Intent(MainActivity.this, DriverListActivity.class);
                            startActivity(intent5);
                        }
                        break;
                    case 6:
                        if (!RepeatClickResolveUtil.isFastDoubleClick(6)) {
                            if (UserUtils.getInstance().isLogin()) {
                                UserBean loginBean = UserUtils.getInstance().getLoginBean();
                                HashMap<String, String> info = new HashMap<>();
                                info.put("name", loginBean.getName() + "");
                                info.put("avatar", loginBean.getMember_img());
                                info.put("tel", loginBean.getPhone());
                                Intent intent = new MQIntentBuilder(MainActivity.this, NewCustomerServiceActivity.class)
                                        .setClientInfo(info)
                                        .updateClientInfo(info)
                                        .setCustomizedId(loginBean.getMember_id() + "").build();
                                startActivity(intent);
                            }
                        }
                        break;
                    case 7:
                        ToastUtils.show("敬请期待！");
                        break;
                }
            }
        });
        setLunBo();
    }

    @Override
    public void getMarker(Marker marker) {
        LatLng latLng = marker.getPosition();
        checkPosition(latLng);
    }

    @Override
    public void getLocationMsg(String msg) {
        if (isRunning) {
            ToastUtils.show(msg);
        }

    }

    @Override
    public void getLocationSuccess(String addr, String address, double latitude, double longitude, boolean isFirstLoc, int type) {
        mAddress = address;
        if (isFirstLoc) {
            LogUtils.d("addr = " + addr);
            if (type == 1) {
                getPoint(address);
            } else if (type == 2) {
                if (if_rescue != 0) {
                    getRescue(addr, address, latitude, longitude);
                } else {
                    ToastUtils.show("您还无法救援!");
                }
            }
        }
    }

    @Override
    public void getLocationRescue(String address, boolean isRescue) {
        mAddress = address;
    }

    /**
     * 设置轮播图
     */
    private void setLunBo() {
        if (presenter != null) {
            presenter.sendLunBo();
        }
    }


    /**
     * 实例化头像
     */
    private void initUser() {
        if (UserUtils.getInstance().isLogin()) {
            String name = UserUtils.getInstance().getLoginBean().getName();
            tv_name.setText(name);
            String phone = UserUtils.getInstance().getLoginBean().getPhone();
            tv_tel.setText(phone + "");
            String member_img = UserUtils.getInstance().getLoginBean().getMember_img();
            Glide.with(this).load(member_img).into(mHeadImage);
            String integral_image = UserUtils.getInstance().getLoginBean().getIntegral_image();
            Glide.with(this).load(integral_image).into(user_level);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        LogUtils.d(event.getType() + " , " + event.isLogin());
        if (event.getType() == 1) {
            if (event.isLogin()) {
                updateIndentity();
            } else {
                Glide.with(this).load(R.mipmap.log_image_bg).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(mHeadImage);
                tv_tel.setText("游客");
            }
        }

    }

    /**
     * 请求救援
     *
     * @param addr      详细地址
     * @param address
     * @param latitude
     * @param longitude
     */
    private void getRescue(String addr, String address, double latitude, double longitude) {
        LogUtils.d("addr = " + addr + " , address = " + address + " , latitude = " + latitude + " , longitude = " + longitude);
        RescueDialog rescueDialog = new RescueDialog(this).Builder(latitude, longitude).setDialogClickListener(new RescueDialog.DialogClickListener() {
            @Override
            public void getImagePosition(double lat, double lng, String member_phone, String check, String content) {
                if (presenter != null) {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(MainActivity.this, "正在请求...");
                    presenter.sendCreateRescue(member_phone, check, content, String.valueOf(lat), String.valueOf(lng), addr);
                }
            }
        }).show();
    }

    /**
     * 检查是否存在这个点,并且展示弹窗
     *
     * @param latLng
     */
    private void checkPosition(LatLng latLng) {
        int i = -1;
        if (isSelectType) {
            i = CheckUtils.locationCoordinate(latLng.latitude, latLng.longitude, mPickList);
        } else {
            i = CheckUtils.locationCoordinate(latLng.latitude, latLng.longitude, mRepairList);
        }
        if (i >= 0) {
            String name = null;
            String address = null;
            String phone = null;
            String major = null;
            List<String> imagelist = new ArrayList<>();
            if (isSelectType) {
                PickUpBean.WarehouseBean warehouseBean = mPickList.get(i);
                name = warehouseBean.getName();
                address = warehouseBean.getAddress();
                phone = warehouseBean.getPhone();
                major = warehouseBean.getImage();
                imagelist.addAll(warehouseBean.getImages());
            } else {
                RepairBean.StationBean stationBean = mRepairList.get(i);
                name = stationBean.getName();
                address = stationBean.getAddress();
                phone = stationBean.getPhone();
                major = stationBean.getImage();
                imagelist.addAll(stationBean.getImages());
            }
            locCenterDialog = new LocCenterDialog(this).
                    Builder(latLng.latitude, latLng.longitude, name, address, phone, major, imagelist).
                    setDialogClickListener(new LocCenterDialog.DialogClickListener() {
                        @Override
                        public void getPhone(String phone, String address) {
                            showPhoneDialog(1, 0, 0, phone, address);
                        }

                        @Override
                        public void getNavigation(double lat, double lng, String address) {
                            showPhoneDialog(2, lat, lng, null, address);
                        }

                        @Override
                        public void getImagePosition(int current_position, String current_url, List<String> all_image) {
                            seePhoto(current_position, current_url, all_image);
                        }
                    }).show();
        }
    }

    /**
     * 阅读照片
     *
     * @param current_position
     * @param current_url
     * @param all_image
     */
    private void seePhoto(int current_position, String current_url, List<String> all_image) {
        ImagePreview.getInstance().
                setContext(this).
                setIndex(current_position).
                setImageList(all_image).
                setLoadStrategy(AlwaysThumb).
                setEnableClickClose(true).
                setEnableDragClose(true).
                setEnableUpDragClose(true).
                setEnableDragCloseIgnoreScale(true).
                setShowCloseButton(true).
                setShowDownButton(false).
                setShowIndicator(true).
                setErrorPlaceHolder(R.drawable.load_failed).
                start();
    }

    /**
     * 拨打电话的弹窗
     *
     * @param phone
     */
    private void showPhoneDialog(int mtype, double lat, double lng, String phone, String address) {
        String top = "", buttom = "";
        if (mtype == 1) {
            top = " 拨打 " + phone + "的电话";
            buttom = "取消";
        } else if (mtype == 2) {
            top = "百度地图";
            buttom = "高德地图";
        }
        SheetButtomDialog sheetButtomDialog = new SheetButtomDialog(this);
        sheetButtomDialog.Builder(top, buttom).
                setDialogClickListener(new SheetButtomDialog.DialogClickListener() {
                    @Override
                    public void top(int type) {
                        if (mtype == 1) {
                            callPhone(phone);
                        } else if (mtype == 2) {
                            if (MapUtil.checkMapAppsIsExist(MainActivity.this, MapUtil.BAIDU_PKG)) {
                                MapUtil.openBaidu(MainActivity.this, lat, lng, address);
                            }
                        }
                        if (locCenterDialog != null) {
                            locCenterDialog.cancle();
                        }
                    }

                    @Override
                    public void bottom(int type) {
                        if (mtype == 2) {
                            if (MapUtil.checkMapAppsIsExist(MainActivity.this, MapUtil.GAODE_PKG)) {
                                MapUtil.openGaoDe(MainActivity.this, lat, lng);
                            }
                        }
                        if (locCenterDialog != null) {
                            locCenterDialog.cancle();
                        }
                    }
                });
    }

    /**
     * 拨打电话
     *
     * @param phone
     */
    private void callPhone(String phone) {
        if (presenter != null) {
            presenter.addToRxLife(new RxPermissions(this).
                    requestEach(Manifest.permission.CALL_PHONE).
                    subscribe(permission -> {
                        if (permission.granted) {   //如果权限中有
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + phone);
                            intent.setData(data);
                            startActivity(intent);
                        } else {
                            showPermissionDialog("需要电话权限开通", "有些权限未开启，是否前往设置?");
                        }
                    }));
        }
    }

    /**
     * 更改用户信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserChangeEvent(ChangeUserEvent event) {
        String member_img = event.getUserBean().getMember_img();
        Glide.with(this).load(member_img).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(mHeadImage);
        tv_name.setText(event.getUserBean().getName() + "");
        tv_tel.setText(event.getUserBean().getPhone() + "");
    }


    @OnClick({R.id.tv_repair, R.id.tv_get_car, R.id.iv_head_image, R.id.iv_setting, R.id.ll_loc,
            R.id.bt_interest, R.id.iv_qr_code, R.id.bt_invite, R.id.bt_rescue})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_repair:
                tabSelected(tv_repair, false);
                break;
            case R.id.tv_get_car:
                tabSelected(tv_get_car, false);
                break;
            case R.id.iv_head_image:
                if (mDrawerLayout != null) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.iv_setting:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.iv_setting)) {
                    Intent intent = new Intent(this, SettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_loc:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_loc)) {
                    requirePermissionIntent(2, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                break;
            case R.id.bt_interest:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_interest)) {
                    Intent intent = new Intent(this, MemberLevelActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_qr_code:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.iv_qr_code)) {
                    requirePermissionIntent(3, Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                break;
            case R.id.bt_invite:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_invite)) {
                    InviteActivity.launch(this, 1);
                }
                break;
            case R.id.bt_rescue:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_rescue)) {
                    requirePermissionIntent(4, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                break;
        }
    }

    /**
     * 根据所需要的权限跳到不同的地方
     *
     * @param type 标记符
     * @param args 需要的权限
     */
    private void requirePermissionIntent(int type, String... args) {
        if (presenter != null) {
            presenter.addToRxLife(new RxPermissions(this).
                    requestEachCombined(args).
                    subscribe(permission -> {
                        if (permission.granted) {   //如果权限中有
                            if (type == 1) {
                                newLocationManager.start();
                            } else if (type == 2) {
                                if (!isBannerSuccess) {
                                    setLunBo();
                                }
                                newLocationManager.gotoCenter(1);
                            } else if (type == 3) {
                                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                                ZxingConfig config = new ZxingConfig();
                                config.setShowAlbum(false);
                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                startActivityForResult(intent, REQUEST_CODE_SCAN);
                            } else if (type == 4) {
                                newLocationManager.gotoCenter(2);
                            } else if (type == 5) {
                                startActivity(new Intent(MainActivity.this, RealAuthActivity.class));
                            }
                        } else {            //权限不通过就获取权限
                            showPermissionDialog("需要权限开通", "有些权限未开启，是否前往设置?");
                        }
                    }));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                ToastUtils.show(content);
            }
        }
    }

    /**
     * 选中变色
     *
     * @param view
     * @param isFirst 第一次选中
     */
    private void tabSelected(View view, boolean isFirst) {
        if (!isBannerSuccess) {
            setLunBo();
        }
        if (view instanceof TextView) {
            tv_repair.setSelected(false);
            tv_get_car.setSelected(false);
            view.setSelected(true);
        }
        if (tv_repair.isSelected()) {
            isSelectType = false;
        } else {
            isSelectType = true;
        }
        if (isFirst) {
            return;
        }
        if (!TextUtils.isEmpty(mAddress)) {
            getPoint(mAddress);
        } else {
            ToastUtils.show("请您重新定位获取当前位置!");
        }
    }

    /**
     * @param address 定位的城市
     */
    private void getPoint(String address) {
        if (presenter != null) {
            if (isSelectType) {
                presenter.sendPickUpPoint(address);
            } else {
                presenter.sendRepairPoint(address);
            }
        }
    }

    /**
     * 获取轮播图成功
     *
     * @param msg
     * @param advBean
     */
    @Override
    public void showLunboSuccess(String msg, List<AdvertiBean.AdvBean> advBean) {
        isBannerSuccess = true;
        banner.setAdapter(new BannerImageAdapter<AdvertiBean.AdvBean>(advBean) {
            @Override
            public void onBindView(BannerImageHolder holder, AdvertiBean.AdvBean data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.getImage())
                        .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
                        //.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(holder.imageView);
            }
        });
        banner.setIndicator(new CircleIndicator(this));
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                if (data instanceof AdvertiBean.AdvBean) {
                    MyWebViewActivity.launch(MainActivity.this, true, ((AdvertiBean.AdvBean) data).getTitle(), ((AdvertiBean.AdvBean) data).getUrl());
                }
            }
        });
    }

    /**
     * 获取轮播图失败
     *
     * @param msg
     */
    @Override
    public void showLunboFailed(String msg) {
        isBannerSuccess = false;
        ToastUtils.show(msg);
    }

    /**
     * 获取提车点列表
     *
     * @param list
     */
    @Override
    public void showPickUpPointSuccess(List<PickUpBean.WarehouseBean> list) {
//        LogUtils.d(list.toString());
        mPickList = list;
        newLocationManager.setPickList(mPickList);
    }

    /**
     * 获取提车点失败
     *
     * @param msg
     */
    @Override
    public void showPickUpPointFailure(String msg) {
        ToastUtils.show(msg);
    }

    /**
     * 获取维修点列表
     *
     * @param list
     */
    @Override
    public void showRepairPointSuccess(List<RepairBean.StationBean> list) {
//        LogUtils.d(list.toString());
        mRepairList = list;
        newLocationManager.setRepairList(mRepairList);
    }

    /**
     * 获取提车点失败
     *
     * @param msg
     */
    @Override
    public void showRepairFailure(String msg) {
        ToastUtils.show(msg);
    }

    /**
     * 发起救援成功
     *
     * @param msg
     */
    @Override
    public void showCreateRescueSuccess(String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    @Override
    public void showCreateRescueFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    /**
     * 获得用户信息
     *
     * @param memberInfoBean
     */
    @Override
    public void getMemberInfoSuccess(MemberInfoBean memberInfoBean) {
        if (UserUtils.getInstance().isLogin()) {
            UserBean userBean = UserUtils.getInstance().getLoginBean();
            userBean.setName(memberInfoBean.getName());
            userBean.setIf_intact(memberInfoBean.getIf_intact());
            userBean.setMember_img(memberInfoBean.getMember_img());
            userBean.setIntegral_image(memberInfoBean.getIntegral_image());
            userBean.setPhone(memberInfoBean.getPhone());
            userBean.setQr_image(memberInfoBean.getQr_image());
            userBean.setLogin_type(memberInfoBean.getType());
            if_rescue = memberInfoBean.getIf_rescue();
            updateIndentity();
            checkUserIdentity();
            if (mainCodeTimeUtils != null) {
                mainCodeTimeUtils.cancel();
            }
            if (memberInfoBean.getIf_residue() != 0) {
                tv_count_down.setVisibility(View.VISIBLE);
                mCurrentTime = System.currentTimeMillis();
                long overTime = memberInfoBean.getResidue_num() * 1000;
                mainCodeTimeUtils = new MainCodeTimeUtils(this, overTime);
                mainCodeTimeUtils.start();
            } else {
                tv_count_down.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getMemberInfoFailure(String msg) {

    }

    /**
     * 获得版本号
     *
     * @param version
     */
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
                        goToAppMarket(MainActivity.this);
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

    @Override
    public void showRedDotSuccess(int number) {
        LogUtils.d("number = "+number);
        mList.get(1).setNumber(number);
        mAdapter.notifyItemChanged(1);
    }

    @Override
    public void showRedDotFailure(String msg) {
        ToastUtils.show(msg);
        LogUtils.d("msg = "+msg);
    }

    @Override
    public void showFailed(int code, String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    @Override
    public void LoadingClose() {
        goToLogin();
    }

    /**
     * 传感器的回调
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        newLocationManager.getSensor(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void initList() {
        ListBean listBean = new ListBean();
        listBean.setImage(R.mipmap.main_person);
        listBean.setTitle("个人信息");
        mList.add(listBean);
        ListBean listBean1 = new ListBean();
        listBean1.setImage(R.mipmap.main_recommend);
        listBean1.setTitle("消息列表");
        mList.add(listBean1);
        ListBean listBean2 = new ListBean();
        listBean2.setImage(R.mipmap.main_select);
        listBean2.setTitle("套餐选择");
        mList.add(listBean2);
        ListBean listBean3 = new ListBean();
        listBean3.setImage(R.mipmap.main_order);
        listBean3.setTitle("我的订单");
        mList.add(listBean3);
        ListBean listBean5 = new ListBean();
        listBean5.setImage(R.mipmap.main_resuce);
        listBean5.setTitle("救援信息");
        mList.add(listBean5);
        ListBean listBean6 = new ListBean();
        listBean6.setImage(R.mipmap.main_bro);
        listBean6.setTitle("小哥发布");
        mList.add(listBean6);
        ListBean listBean7 = new ListBean();
        listBean7.setImage(R.mipmap.main_kefu);
        listBean7.setTitle("塔电客服");
        mList.add(listBean7);
        ListBean listBean8 = new ListBean();
        listBean8.setImage(R.mipmap.main_coupon);
        listBean8.setTitle("代金券");
        mList.add(listBean8);
        mAdapter.setData(mList);
    }

    /**
     * 检查用户是否是实名认证
     */
    private void checkUserIdentity() {
        if (UserUtils.getInstance().isLogin()) {
            UserBean userBean = UserUtils.getInstance().getLoginBean();
            if (userBean.getIf_intact() == 0) {
                IdentityDialog();
            }
        }
    }

    /**
     * 更新用户身份
     */
    private void updateIndentity() {
        UserBean loginBean = UserUtils.getInstance().getLoginBean();
        String member_img = loginBean.getMember_img();
        Glide.with(this).load(member_img).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(mHeadImage);
        String phone = loginBean.getPhone();
        tv_tel.setText(phone + "");
    }

    private void IdentityDialog() {
        new CenterDialog(this).Builder("您还未认证身份信息", "前往认证", "暂不认证").setDialogClickListener(new CenterDialog.DialogClickListener() {
            @Override
            public void top(int type) {
                requirePermissionIntent(5, Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            @Override
            public void bottom(int type) {

            }
        }).show();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d("onResume()");
        isRunning = true;
        if (banner != null) {
            banner.start();
        }
        if (newLocationManager != null) {
            newLocationManager.getbDmap().onResume();
        }
        if (presenter != null) {
            presenter.getMemberInfo();
        }
        if (presenter != null) {
            presenter.getNoticeNumber();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d("onPause()");
        isRunning = false;
        if (newLocationManager != null) {
            newLocationManager.getbDmap().onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        if (newLocationManager != null) {
            newLocationManager.getbDmap().onDestroy();
            newLocationManager.stop();
            newLocationManager.setGetLocationListener(null);
        }
        if (banner != null) {
            banner.onDestroy(this);
        }
        if (locCenterDialog != null) {
            locCenterDialog.shutDownDialog();
        }
        if (mainCodeTimeUtils != null) {
            mainCodeTimeUtils.cancel();
        }
        MQManager.getInstance(this).setClientOffline();
        MQManager.getInstance(this).closeMeiqiaService();
        super.onDestroy();
    }

    /**
     * 退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isDrawerState) {
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawers();
                }
                return true;
            }
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
                }
            }else {
                ToastUtils.show("安装包只在华为，小米，oppo，vivo，应用宝上线，请您前往下载任何一个应用市场更新安装");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}