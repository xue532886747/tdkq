package com.example.towerdriver.station.station_order.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_order_list.model.OrderDean;
import com.example.towerdriver.base_order_list.model.OrderDetailBean;
import com.example.towerdriver.base_order_list.ui.activity.OrderDetailActivity;
import com.example.towerdriver.base_order_list.ui.adapter.OrderDetailAdapter;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.station.station_order.presenter.StationOrderDetailPresenter;
import com.example.towerdriver.station.station_order.view.IStationOrderDetailView;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 站长端订单详情
 * @date 2021/6/30
 */
public class StationOrderDetailActivity extends BaseActivity<StationOrderDetailPresenter> implements IStationOrderDetailView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private SmartRefreshUtils mSmartRefreshUtils;
    private OrderDetailAdapter mAdapter;
    private String order_id;

    public static void launch(Activity activity, String order_id) {
        Intent intent = new Intent(activity, StationOrderDetailActivity.class);
        intent.putExtra("order_id", order_id);
        activity.startActivity(intent);
    }

    @Override
    protected StationOrderDetailPresenter createPresenter() {
        return new StationOrderDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_station_order_detail;
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
            presenter.getOrderDetail(order_id);
        }
    }


    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }
    @Override
    public void orderDetailSuccess(OrderDetailBean orderBeans) {
        closeDialog();
        loadingSuccessOrFailure(1);
        List<OrderDean> orderDeans = new ArrayList<>();
        orderDeans.add(new OrderDean("订单号:", orderBeans.getOrder_sn()));
        orderDeans.add(new OrderDean("套餐分类:", orderBeans.getClassify_name()));
        orderDeans.add(new OrderDean("套餐名称:", orderBeans.getRent_name()));
        orderDeans.add(new OrderDean("车牌号:", orderBeans.getCar_number()));
        orderDeans.add(new OrderDean("车架号:", orderBeans.getFrame_number()));
        orderDeans.add(new OrderDean("租金:", orderBeans.getRent_price() + ""));
        orderDeans.add(new OrderDean("押金:", orderBeans.getCash_price() + ""));
        orderDeans.add(new OrderDean("订单状态:", orderBeans.getOrder_status()));
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
