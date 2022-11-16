package com.example.towerdriver.station.station_rescue.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.base_ui.bigimageview.ImagePreview;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.dialog.SheetButtomDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.map.NewLocationManager;
import com.example.towerdriver.station.station_rescue.model.NewRescueDetailBean;
import com.example.towerdriver.station.station_rescue.model.RescueDetailBean;
import com.example.towerdriver.station.station_rescue.presenter.RescueDetailPresenter;
import com.example.towerdriver.station.station_rescue.ui.adapter.StationRescueDetailAdapter;
import com.example.towerdriver.station.station_rescue.view.IStationRescueDetailView;
import com.example.towerdriver.utils.MapUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.base_ui.bigimageview.ImagePreview.LoadStrategy.AlwaysThumb;

/**
 * @author 53288
 * @description 救援详情
 * @date 2021/7/1
 */
public class StationRescueDetailActivity extends BaseActivity<RescueDetailPresenter> implements IStationRescueDetailView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.nestscrollview)
    NestedScrollView nestscrollview;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    private StationRescueDetailAdapter mAdapter;
    private SmartRefreshUtils mSmartRefreshUtils;
    private String rescue_id;
    private NewLocationManager newLocationManager;

    public static void launch(Activity activity, String rescue_id) {
        Intent intent = new Intent(activity, StationRescueDetailActivity.class);
        intent.putExtra("rescue_id", rescue_id);
        activity.startActivity(intent);
    }

    @Override
    protected RescueDetailPresenter createPresenter() {
        return new RescueDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_station_rescue_detail;
    }

    @Override
    protected void initView() {
        rescue_id = getIntent().getStringExtra("rescue_id");
        toolbar.bringToFront();
        newLocationManager = new NewLocationManager(mMapView, 5000);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new StationRescueDetailAdapter();
        rv_list.setAdapter(mAdapter);
        mAdapter.setAnimationEnable(true);
        mAdapter.setItemClickListener(new StationRescueDetailAdapter.ItemClickListener() {
            @Override
            public void getImagePosition(int current_position, String current_url, List<String> all_image) {
                seePhoto(current_position, current_url, all_image);
            }
        });
        mAdapter.addChildClickViewIds(R.id.tv_content);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Object o = adapter.getData().get(position);
                if (o instanceof NewRescueDetailBean) {
                    if (position == 2) {
                        String content = ((NewRescueDetailBean) o).getContent();
                        showPhoneDialog(1, 0, 0, content, null);
                    }
                }
            }
        });
        newLocationManager.setGetLocationListener(new NewLocationManager.getLocationListener() {
            @Override
            public void getMarker(Marker marker) {
                //点击事件
                LatLng latLng = marker.getPosition();
                LogUtils.d("latLng = " + latLng.latitude + " , " + latLng.longitude);
                centerDialog(latLng.latitude, latLng.longitude);
            }

            @Override
            public void getLocationMsg(String msg) {

            }

            @Override
            public void getLocationSuccess(String addr, String address, double latitude, double longitude, boolean isFirstLoc, int type) {
                LogUtils.d(addr);
            }

            @Override
            public void getLocationRescue(String address, boolean isRescue) {

            }
        });
        mMapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //允许ScrollView截断点击事件，ScrollView可滑动
                    nestscrollview.requestDisallowInterceptTouchEvent(false);
                } else {
                    //不允许ScrollView截断点击事件，点击事件由子View处理
                    nestscrollview.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        refreshList();
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    /**
     * 下拉刷新
     */
    private void refreshList() {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在加载...");
            presenter.getStationRescueOrderDetail(rescue_id);
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

    private void centerDialog(double lat, double lng) {
        showPhoneDialog(2, lat, lng, null, mAdapter.getData().get(4).getContent());
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
                            if (MapUtil.checkMapAppsIsExist(StationRescueDetailActivity.this, MapUtil.BAIDU_PKG)) {
                                MapUtil.openBaidu(StationRescueDetailActivity.this, lat, lng, address);
                            }
                        }
                    }

                    @Override
                    public void bottom(int type) {
                        if (mtype == 2) {
                            if (MapUtil.checkMapAppsIsExist(StationRescueDetailActivity.this, MapUtil.GAODE_PKG)) {
                                MapUtil.openGaoDe(StationRescueDetailActivity.this, lat, lng);
                            }
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
     * 请求成功
     *
     * @param rescueDetailBean
     */
    @Override
    public void stationDetailSuccess(RescueDetailBean rescueDetailBean) {
        closeDialog();
        loadingSuccessOrFailure(1);
        String lat = rescueDetailBean.getLat();
        String lng = rescueDetailBean.getLng();
        newLocationManager.getbDmap().MoveToLoc(Double.parseDouble(lng), Double.parseDouble(lat));
        newLocationManager.setRescue(lng, lat);
        createData(rescueDetailBean);
    }

    private void createData(RescueDetailBean rescueDetailBean) {
        List<NewRescueDetailBean> mList = new ArrayList<>();
        NewRescueDetailBean newRescueDetailBean = new NewRescueDetailBean();
        newRescueDetailBean.setTitle("用户详情");
        newRescueDetailBean.setsType(1);
        mList.add(newRescueDetailBean);

        NewRescueDetailBean newRescueDetailBean1 = new NewRescueDetailBean();
        newRescueDetailBean1.setTitle("用户姓名:");
        newRescueDetailBean1.setContent(rescueDetailBean.getMember_name());
        newRescueDetailBean1.setsType(2);
        mList.add(newRescueDetailBean1);

        NewRescueDetailBean newRescueDetailBean2 = new NewRescueDetailBean();
        newRescueDetailBean2.setTitle("用户电话:");
        newRescueDetailBean2.setContent(rescueDetailBean.getMember_phone());
        newRescueDetailBean2.setsType(2);
        mList.add(newRescueDetailBean2);

        NewRescueDetailBean newRescueDetailBean3 = new NewRescueDetailBean();
        newRescueDetailBean3.setTitle("救援原因:");
        newRescueDetailBean3.setContent(rescueDetailBean.getReason());
        newRescueDetailBean3.setsType(2);
        mList.add(newRescueDetailBean3);

        NewRescueDetailBean newRescueDetailBean4 = new NewRescueDetailBean();
        newRescueDetailBean4.setTitle("救援详情:");
        newRescueDetailBean4.setContent(rescueDetailBean.getContent());
        newRescueDetailBean4.setsType(2);
        mList.add(newRescueDetailBean4);

        NewRescueDetailBean newRescueDetailBean5 = new NewRescueDetailBean();
        newRescueDetailBean5.setTitle("救援地址:");
        newRescueDetailBean5.setContent(rescueDetailBean.getAddress());
        newRescueDetailBean5.setsType(2);
        mList.add(newRescueDetailBean5);

        NewRescueDetailBean newRescueDetailBean6 = new NewRescueDetailBean();
        newRescueDetailBean6.setTitle("订单详情");
        newRescueDetailBean6.setsType(1);
        mList.add(newRescueDetailBean6);

        NewRescueDetailBean newRescueDetailBean7 = new NewRescueDetailBean();
        newRescueDetailBean7.setTitle("订单状态:");
        newRescueDetailBean7.setContent(rescueDetailBean.getStatus_name());
        newRescueDetailBean7.setsType(2);
        mList.add(newRescueDetailBean7);

        if (!TextUtils.isEmpty(rescueDetailBean.getCreatetime())) {
            NewRescueDetailBean newRescueDetailBean8 = new NewRescueDetailBean();
            newRescueDetailBean8.setTitle("创建时间:");
            newRescueDetailBean8.setContent(rescueDetailBean.getCreatetime());
            newRescueDetailBean8.setsType(2);
            mList.add(newRescueDetailBean8);
        }

        if (!TextUtils.isEmpty(rescueDetailBean.getAccept_time())) {
            NewRescueDetailBean newRescueDetailBean9 = new NewRescueDetailBean();
            newRescueDetailBean9.setTitle("派单时间:");
            newRescueDetailBean9.setContent(rescueDetailBean.getAccept_time());
            newRescueDetailBean9.setsType(2);
            mList.add(newRescueDetailBean9);
        }

        if (!TextUtils.isEmpty(rescueDetailBean.getRescue_time())) {
            NewRescueDetailBean newRescueDetailBean9 = new NewRescueDetailBean();
            newRescueDetailBean9.setTitle("接单时间:");
            newRescueDetailBean9.setContent(rescueDetailBean.getRescue_time());
            newRescueDetailBean9.setsType(2);
            mList.add(newRescueDetailBean9);
        }

        if (!TextUtils.isEmpty(rescueDetailBean.getEnd_time())) {
            NewRescueDetailBean newRescueDetailBean9 = new NewRescueDetailBean();
            newRescueDetailBean9.setTitle("结束时间:");
            newRescueDetailBean9.setContent(rescueDetailBean.getEnd_time());
            newRescueDetailBean9.setsType(2);
            mList.add(newRescueDetailBean9);
        }
        if (rescueDetailBean.getImages() != null && rescueDetailBean.getImages().size() > 0) {
            NewRescueDetailBean newRescueDetailBean10 = new NewRescueDetailBean();
            newRescueDetailBean10.setTitle("图片详情");
            newRescueDetailBean10.setsType(1);
            mList.add(newRescueDetailBean10);

            NewRescueDetailBean newRescueDetailBean11 = new NewRescueDetailBean();
            newRescueDetailBean11.setImages(rescueDetailBean.getImages());
            newRescueDetailBean11.setsType(3);
            mList.add(newRescueDetailBean11);

            NewRescueDetailBean newRescueDetailBean12 = new NewRescueDetailBean();
            newRescueDetailBean12.setTitle("救援备注:");
            newRescueDetailBean12.setContent(rescueDetailBean.getRemark());
            newRescueDetailBean12.setsType(2);
            mList.add(newRescueDetailBean12);
        }

        if (!TextUtils.isEmpty(rescueDetailBean.getScore())) {
            NewRescueDetailBean newRescueDetailBean13 = new NewRescueDetailBean();
            newRescueDetailBean13.setTitle("用户评价");
            newRescueDetailBean13.setsType(1);
            mList.add(newRescueDetailBean13);

            NewRescueDetailBean newRescueDetailBean14 = new NewRescueDetailBean();
            newRescueDetailBean14.setScore(rescueDetailBean.getScore());
            newRescueDetailBean14.setsType(4);
            if (!TextUtils.isEmpty(rescueDetailBean.getEvaluate())) {
                newRescueDetailBean14.setEvaluate(rescueDetailBean.getEvaluate());
            }
            mList.add(newRescueDetailBean14);
        }


        mAdapter.setList(mList);
    }


    @Override
    public void stationDetailFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        loadingSuccessOrFailure(2);
    }

    @Override
    public void LoadingClose() {
        loadingSuccessOrFailure(2);
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        loadingSuccessOrFailure(2);
        ToastUtils.show(msg);
    }

    /**
     * 根据返回的状态判断显示空还是列表
     *
     * @param loading_type
     */
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

    @Override
    protected void onResume() {
        super.onResume();
        if (newLocationManager != null) {
            newLocationManager.getbDmap().onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (newLocationManager != null) {
            newLocationManager.getbDmap().onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (newLocationManager != null) {
            newLocationManager.getbDmap().onDestroy();
            newLocationManager.setGetLocationListener(null);
        }
        super.onDestroy();
    }
}
