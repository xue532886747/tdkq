package com.example.towerdriver.base_order_list.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_order_list.model.ChangeCarBean;
import com.example.towerdriver.base_order_list.model.OrderDean;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.base_order_list.model.ScanBean;
import com.example.towerdriver.base_order_list.presenter.OrderDetailPresenter;
import com.example.towerdriver.base_order_list.ui.adapter.OrderDetailAdapter;
import com.example.towerdriver.base_order_list.ui.adapter.OrderListAdapter;
import com.example.towerdriver.base_order_list.view.IOrderDetailView;
import com.example.towerdriver.base_order_list.view.IOrderListView;
import com.example.towerdriver.dialog.ButtomDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.webview.MyWebViewActivity;
import com.example.zxing.android.CaptureActivity;
import com.example.zxing.bean.ZxingConfig;
import com.example.zxing.common.Constant;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.towerdriver.Constant.REQUEST_CODE_SCAN;

/**
 * @author 53288
 * @description 订单详情
 * @date 2021/6/21
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements IOrderDetailView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_code)
    ConstraintLayout ll_code;
    @BindView(R.id.iv_qr_code)
    AppCompatImageView iv_qr_code;
    private SmartRefreshUtils mSmartRefreshUtils;
    private OrderDetailAdapter mAdapter;
    private String order_id;

    public static void launch(Activity activity, String order_id) {
        Intent intent = new Intent(activity, OrderDetailActivity.class);
        intent.putExtra("order_id", order_id);
        activity.startActivity(intent);
    }

    @Override
    protected OrderDetailPresenter createPresenter() {
        return new OrderDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_order_detail;
    }

    @Override
    protected void initView() {
        order_id = getIntent().getStringExtra("order_id");
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new OrderDetailAdapter();
        rv_list.setAdapter(mAdapter);
        mAdapter.setAnimationEnable(true);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
    }

    @Override
    protected void initData() {
        if (rv_list.getItemDecorationCount() == 0) {
            rv_list.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.bottom = 20;
                }
            });
        }
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        refreshList();
    }

    /**
     * 下拉刷新
     */
    private void refreshList() {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在加载...");
            presenter.getOrderList(order_id);
        }
    }


    @OnClick({R.id.ll_back, R.id.iv_qr_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_qr_code:
                requirePermissionIntent(1, Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                break;
        }
    }


    /**
     * 根据所需要的权限跳到不同的地方
     *
     * @param args 需要的权限
     */
    private void requirePermissionIntent(int type, String... args) {
        if (presenter != null) {
            presenter.addToRxLife(new RxPermissions(this).
                    requestEachCombined(args).
                    subscribe(permission -> {
                        if (permission.granted) {   //如果权限中有
                            if (type == 1) {
                                Intent intent = new Intent(OrderDetailActivity.this, CaptureActivity.class);
                                ZxingConfig config = new ZxingConfig();
                                config.setShowAlbum(false);
                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                startActivityForResult(intent, REQUEST_CODE_SCAN);
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
                try {
                    if (content.length() == 17) {
                        if (presenter != null) {
                            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
                            presenter.getReplaceOrder(order_id, content);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show("无法识别二维码，请手动输入");
                }
            }
        }
    }

    /**
     * 订单详情
     *
     * @param orderBeans
     */
    @Override
    public void orderDetailSuccess(OrderDetailBean orderBeans) {
        closeDialog();
        loadingSuccessOrFailure(1);
        if ("4".equals(orderBeans.getOrder_status())) {
            ll_code.setVisibility(View.VISIBLE);
        } else {
            ll_code.setVisibility(View.GONE);
        }
        List<OrderDean> orderDeans = new ArrayList<>();
        orderDeans.add(new OrderDean("订单号:", orderBeans.getOrder_sn()));
        orderDeans.add(new OrderDean("套餐分类:", orderBeans.getClassify_name()));
        orderDeans.add(new OrderDean("套餐名称:", orderBeans.getRent_name()));
        orderDeans.add(new OrderDean("车牌号:", orderBeans.getCar_number()));
        orderDeans.add(new OrderDean("车架号:", orderBeans.getFrame_number()));
        orderDeans.add(new OrderDean("租金:", orderBeans.getRent_price() + ""));
        orderDeans.add(new OrderDean("押金:", orderBeans.getCash_price() + ""));
        orderDeans.add(new OrderDean("订单状态:", orderBeans.getStatus_name()));
        orderDeans.add(new OrderDean("支付方式:", orderBeans.getPayment_code()));
        orderDeans.add(new OrderDean("创建时间:", orderBeans.getCreatetime()));
        orderDeans.add(new OrderDean("开始时间:", orderBeans.getStart_time()));
        orderDeans.add(new OrderDean("归还时间:", orderBeans.getReturn_time()));
        orderDeans.add(new OrderDean("到期时间:", orderBeans.getExpire_time()));
        orderDeans.add(new OrderDean("续费价格:", orderBeans.getRenew_price() + ""));
        mAdapter.setList(orderDeans);
    }

    @Override
    public void orderDetailFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        loadingSuccessOrFailure(2);
    }

    @Override
    public void changeCarSuccess(String msg, ChangeCarBean data) {
        ToastUtils.show(msg);
        closeDialog();
        mAdapter.setData(3, new OrderDean("车牌号:", data.getCar_number()));
        mAdapter.setData(4, new OrderDean("车架号:", data.getFrame_number()));

    }

    @Override
    public void changeCarFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
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
}
