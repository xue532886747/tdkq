package com.example.towerdriver.base_order_list.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_order_list.presenter.OrderListPresenter;
import com.example.towerdriver.base_order_list.ui.adapter.OrderListAdapter;
import com.example.towerdriver.base_order_list.view.IOrderListView;
import com.example.towerdriver.base_pay.ui.PayStyleActivity;
import com.example.towerdriver.base_setmenu.ui.activity.GoodsDetailActivity;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 订单列表
 * @date 2021/6/17
 */
public class OrderListActivity extends BaseActivity<OrderListPresenter> implements IOrderListView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.ll_noting)
    LinearLayout ll_noting;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private SmartRefreshUtils mSmartRefreshUtils;
    private static final int PAGE_START = 1;        // 第一页
    private static final int PAGE_SIZE = 6;         // 每页显示的数量
    private int currPage = 1;                       // 当前页面
    private int all_page = 1;                       //总页数
    private List<OrderListBean.OrderBean> mList = new ArrayList<>();
    private OrderListAdapter mAdapter;
    private String address;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, OrderListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected OrderListPresenter createPresenter() {
        return new OrderListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_order_list;
    }

    @Override
    protected void initView() {
        address = getIntent().getStringExtra("address");
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new OrderListAdapter();
        rv_list.setAdapter(mAdapter);
        mAdapter.getLoadMoreModule().setEnableLoadMore(true);
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
        mAdapter.addChildClickViewIds(R.id.bt_right, R.id.bt_left);
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                sendRequest(false);
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                OrderDetailActivity.launch(OrderListActivity.this, mAdapter.getData().get(position).getId() + "");
            }
        });
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Object o = adapter.getData().get(position);
                if (o instanceof OrderListBean.OrderBean) {
                    Integer order_status = ((OrderListBean.OrderBean) o).getOrder_status();
                    if (order_status == 0 || order_status == 7) {
                        getDialog(1, ((OrderListBean.OrderBean) o).getId() + "", position, null, ((OrderListBean.OrderBean) o).getPayment_code());
                    } else if (order_status == 1) {
                        if (view.getId() == R.id.bt_right) {
                            getDialog(2, ((OrderListBean.OrderBean) o).getId() + "", position, null, ((OrderListBean.OrderBean) o).getPayment_code());
                        }
                        if (view.getId() == R.id.bt_left) {
                            if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_left)) {
                                PayStyleActivity.launch(OrderListActivity.this,
                                        ((OrderListBean.OrderBean) o).getId() + "",
                                        ((OrderListBean.OrderBean) o).getPay_price(),
                                        ((OrderListBean.OrderBean) o).getOrder_sn(), 1);
                            }
                        }
                    } else if (order_status == 2) {
                        if (view.getId() == R.id.bt_right) {
                            if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_right)) {
                                getDialog(3, ((OrderListBean.OrderBean) o).getId() + "", position, null, ((OrderListBean.OrderBean) o).getPayment_code());
                            }
                        }
                        if (view.getId() == R.id.bt_left) {
                            if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_left)) {
                                RentCarDetailActivity.launch(OrderListActivity.this,
                                        ((OrderListBean.OrderBean) o).getId() + "", position);
                            }
                        }
                    } else if (order_status == 4) {
                        if (view.getId() == R.id.bt_right) {
                            if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_right)) {
                                GoodsDetailActivity.launch(OrderListActivity.this,
                                        ((OrderListBean.OrderBean) o).getRent_id() + "", false, ((OrderListBean.OrderBean) o).getId() + "", null);
                                LogUtils.d("getRent_id = " + ((OrderListBean.OrderBean) o).getRent_id() + "");
                            }
                        }
                    }
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            refreshList();
        } else if (requestCode == 400) {
            if (resultCode == 401) {
                if (data == null) {
                    return;
                }
                OrderStatusBean data1 = (OrderStatusBean) data.getSerializableExtra("data");
                int position = data.getIntExtra("position", 0);
                if (mList != null && mList.size() > position) {
                    OrderListBean.OrderBean orderBean = mList.get(position);
                    orderBean.setOrder_status(data1.getOrder_status());
                    orderBean.setStatus_name(data1.getStatus_name());
                    OrderListBean.OrderBean orderBean1 = mAdapter.getData().get(position);
                    orderBean1.setOrder_status(data1.getOrder_status());
                    orderBean1.setStatus_name(data1.getStatus_name());
                    mAdapter.notifyItemChanged(position);
                }
            }
        }
    }

    /**
     * 弹窗
     *
     * @param type_status
     * @param id
     * @param position
     * @param rent_id
     */
    private void getDialog(int type_status, String id, int position, String rent_id, int payment_code) {
        String title = "";
        if (type_status == 1) {
            title = "是否删除订单?";
        } else if (type_status == 2) {
            title = "是否取消订单?";
        } else if (type_status == 3) {
            title = "是否退款?";
        }
        CenterDialog centerDialog = new CenterDialog(this).Builder(title, "确认", "取消");
        centerDialog.setDialogClickListener(new CenterDialog.DialogClickListener() {
            @Override
            public void top(int type) {
                if (type_status == 1) {
                    if (presenter != null) {
                        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(OrderListActivity.this, "正在删除...");
                        presenter.getDeleteOrder(id, position);
                    }
                } else if (type_status == 2) {
                    if (presenter != null) {
                        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(OrderListActivity.this, "正在取消...");
                        presenter.getCancelOrder(id, position);
                    }
                } else if (type_status == 3) {
                    if (presenter != null) {
                        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(OrderListActivity.this, "正在退款...");
                        if (payment_code == 1) {
                            presenter.getWxRefundOrder(id, position);
                        } else if (payment_code == 2) {
                            presenter.getZfbRefundOrder(id, position);
                        } else {

                        }
                    }
                }
                centerDialog.shutDownDialog();
            }

            @Override
            public void bottom(int type) {
                centerDialog.shutDownDialog();
            }
        });
        centerDialog.show();
    }

    /**
     * 网络请求
     *
     * @param fresh
     */
    private void sendRequest(boolean fresh) {
        if (presenter != null) {
            presenter.getOrderList(currPage, fresh);
        }
    }

    /**
     * 下拉刷新
     */
    private void refreshList() {
        currPage = PAGE_START;
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在加载...");
        sendRequest(true);
    }

    /**
     * @param orderBeans fresh
     * @param cur_page   当前页面
     * @param total_page 总页面
     * @param fresh
     */
    @Override
    public void orderListSuccess(List<OrderListBean.OrderBean> orderBeans, int cur_page, int total_page, boolean fresh) {
        closeDialog();
        if (mSmartRefreshUtils != null) {
            mSmartRefreshUtils.success();
        }
        all_page = total_page;
        ll_noting.setVisibility(View.GONE);
        rv_list.setVisibility(View.VISIBLE);
        currPage = cur_page + 1;
        if (fresh) {
            mList = new ArrayList<>();
        }
        int size = orderBeans.size();
        mList.addAll(orderBeans);
        if (fresh) {
            mAdapter.setList(mList);
        } else {
            mAdapter.addData(orderBeans);
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.getLoadMoreModule().loadMoreEnd(false);
        } else {
            mAdapter.getLoadMoreModule().loadMoreComplete();
        }
        if (currPage > total_page) {
            mAdapter.getLoadMoreModule().loadMoreEnd();
        }
    }

    @Override
    public void orderListFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        loadingSuccessOrFailure(2);
    }

    @Override
    public void showRefreshNoDate(String msg, List<OrderListBean.OrderBean> orderBeans) {
        closeDialog();
        loadingSuccessOrFailure(1);
    }

    /**
     * 取消订单成功
     *
     * @param msg
     * @param position
     */
    @Override
    public void cancelOrderSuccess(String msg, int position, OrderStatusBean orderStatusBean) {
        closeDialog();
        ToastUtils.show(msg);
        if (mList != null && mList.size() > position) {
            OrderListBean.OrderBean orderBean = mList.get(position);
            orderBean.setOrder_status(orderStatusBean.getOrder_status());
            orderBean.setStatus_name(orderStatusBean.getStatus_name());
            OrderListBean.OrderBean orderBean1 = mAdapter.getData().get(position);
            orderBean1.setOrder_status(orderStatusBean.getOrder_status());
            orderBean1.setStatus_name(orderStatusBean.getStatus_name());
            mAdapter.notifyItemChanged(position);

        }
    }

    @Override
    public void cancelOrderFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    /**
     * 删除订单
     *
     * @param msg
     * @param id
     * @param position
     */
    @Override
    public void deleteOrderSuccess(String msg, int id, int position) {
        closeDialog();
        ToastUtils.show(msg);
        checkDeleteList(position, id);
    }

    @Override
    public void deleteOrderFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void RefundOrderSuccess(String msg, int position, OrderStatusBean orderStatusBean) {
        closeDialog();
        ToastUtils.show(msg);
        if (mList != null && mList.size() > position) {
            OrderListBean.OrderBean orderBean = mList.get(position);
            orderBean.setOrder_status(orderStatusBean.getOrder_status());
            orderBean.setStatus_name(orderStatusBean.getStatus_name());
            OrderListBean.OrderBean orderBean1 = mAdapter.getData().get(position);
            orderBean1.setOrder_status(orderStatusBean.getOrder_status());
            orderBean1.setStatus_name(orderStatusBean.getStatus_name());
            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void RefundOrderFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
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
     * 删除item
     *
     * @param position
     * @param id
     */
    private void checkDeleteList(int position, int id) {
        if (mList != null && mList.size() > 0) {
            Iterator<OrderListBean.OrderBean> iterator = mList.iterator();
            while (iterator.hasNext()) {
                OrderListBean.OrderBean next = iterator.next();
                if (next.getId() == id) {
                    iterator.remove();
                    mAdapter.removeAt(position);
                }
            }
        }
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
            ll_noting.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
            mAdapter.getLoadMoreModule().loadMoreComplete();
        } else if (loading_type == 2) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.fail();
            }
            mAdapter.getLoadMoreModule().loadMoreFail();
        }
    }
}
