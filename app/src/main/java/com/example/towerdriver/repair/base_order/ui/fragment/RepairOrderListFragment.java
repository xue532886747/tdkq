package com.example.towerdriver.repair.base_order.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseFragment;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_driver.ui.activity.DriverReleaseActivity;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_order_list.ui.activity.OrderListActivity;
import com.example.towerdriver.base_order_list.ui.activity.RentCarDetailActivity;
import com.example.towerdriver.base_pay.ui.PayStyleActivity;
import com.example.towerdriver.base_setmenu.ui.activity.GoodsDetailActivity;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.InputDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.repair.base_order.model.RepairListBean;
import com.example.towerdriver.repair.base_order.presenter.RepairOrderListPresenter;
import com.example.towerdriver.repair.base_order.ui.activity.CheckCarDetailActivity;
import com.example.towerdriver.repair.base_order.ui.adapter.RepairOrderListAdapter;
import com.example.towerdriver.repair.base_order.view.IRepairOrderListView;
import com.example.towerdriver.station.station_order.ui.activity.StationOrderDetailActivity;
import com.example.towerdriver.station.station_order.ui.adapter.StationOrderListAdapter;
import com.example.towerdriver.station.station_order.ui.fragment.StationOrderListFragment;
import com.example.towerdriver.station.station_rescue.model.StationRescueBean;
import com.example.towerdriver.station.station_rescue.ui.fragment.StationRescueListFragment;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 维修订单列表
 * @date 2021/7/4
 */
public class RepairOrderListFragment extends BaseFragment<RepairOrderListPresenter> implements IRepairOrderListView {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.ll_noting)
    LinearLayout ll_noting;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    private SmartRefreshUtils mSmartRefreshUtils;
    private static final int PAGE_START = 1;        // 第一页
    private static final int PAGE_SIZE = 6;         // 每页显示的数量
    private int currPage = 1;                       // 当前页面
    private int all_page = 1;                       //总页数
    private List<RepairListBean.OrderBean> mList = new ArrayList<>();
    private RepairOrderListAdapter mAdapter;

    public static RepairOrderListFragment newInstance() {
        return new RepairOrderListFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_repair_order_list;
    }

    @Override
    protected RepairOrderListPresenter createPresenter() {
        return new RepairOrderListPresenter(this);
    }

    @Override
    protected void initView() {
        rv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new RepairOrderListAdapter();
        rv_list.setAdapter(mAdapter);
        mAdapter.getLoadMoreModule().setEnableLoadMore(true);
        mAdapter.setAnimationEnable(true);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        mAdapter.addChildClickViewIds(R.id.bt_right);
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

            }
        });
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Object o = adapter.getData().get(position);
                if (o instanceof RepairListBean.OrderBean) {
                    if (view.getId() == R.id.bt_right) {
                        if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_right)) {
                            getDialog(position, ((RepairListBean.OrderBean) o).getId() + "");
                        }
                    }
                    if (view.getId() == R.id.bt_left) {
                        if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_right)) {
                            Intent intent = new Intent(RepairOrderListFragment.this.getContext(), CheckCarDetailActivity.class);
                            intent.putExtra("order_id", ((RepairListBean.OrderBean) o).getId() + "");
                            intent.putExtra("position", position);
                            startActivityForResult(intent, 400);
                        }
                    }
                }
            }
        });
    }

    /**
     * 弹窗
     *
     * @param position
     * @param order_id
     */
    private void getDialog(int position, String order_id) {
        String title = "是否删除订单?";
        CenterDialog centerDialog = new CenterDialog(mContext).Builder(title, "确认", "取消");
        centerDialog.setDialogClickListener(new CenterDialog.DialogClickListener() {
            @Override
            public void top(int type) {
                if (presenter != null) {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(mContext, "正在删除...");
                    presenter.deleteOrder(order_id, position);
                }
            }

            @Override
            public void bottom(int type) {

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
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(mContext, "正在加载...");
        sendRequest(true);
    }


    @Override
    protected void onVisible(boolean isFirstVisible) {
        if (isFirstVisible) {
            refreshList();
        }
    }

    @OnClick({R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_add)) {
                    new InputDialog(mContext).Builder().setDialogClickListener(new InputDialog.DialogClickListener() {
                        @Override
                        public void getImagePosition(String member_phone) {
                            if (presenter != null) {
                                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(mContext, "正在提交...");
                                presenter.addOrder(member_phone);
                            }
                        }
                    }).show();
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 400) {
            if (resultCode == 401) {
                if (data == null) {
                    return;
                }
                OrderStatusBean data1 = (OrderStatusBean) data.getSerializableExtra("data");
                int position = data.getIntExtra("position", 0);
                if (mList != null && mList.size() > position) {
                    RepairListBean.OrderBean orderBean = mList.get(position);
                    orderBean.setOrder_status(data1.getOrder_status());
                    orderBean.setStatus_name(data1.getStatus_name());
                    RepairListBean.OrderBean orderBean1 = mAdapter.getData().get(position);
                    orderBean1.setOrder_status(data1.getOrder_status());
                    orderBean1.setStatus_name(data1.getStatus_name());
                    mAdapter.notifyItemChanged(position);
                }
            }
        }
    }

    /**
     * 获得维修订单列表
     *
     * @param orderBeans fresh
     * @param cur_page   当前页面
     * @param total_page 总页面
     * @param fresh
     */
    @Override
    public void repairListSuccess(List<RepairListBean.OrderBean> orderBeans, int cur_page, int total_page, boolean fresh) {
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
    public void repairListFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
        loadingSuccessOrFailure(2);
    }

    @Override
    public void showRefreshNoDate(String msg, List<RepairListBean.OrderBean> orderBeans) {
        closeDialog();
        loadingSuccessOrFailure(1);
    }

    /**
     * 添加成功
     *
     * @param msg
     */
    @Override
    public void repairAddSuccess(String msg) {
        closeDialog();
        ToastUtils.show(msg);
        refreshList();
    }

    @Override
    public void repairAddFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    /**
     * 删除订单
     *
     * @param position
     * @param msg
     */
    @Override
    public void deleteOrderSuccess(int position, String msg) {
        closeDialog();
        ToastUtils.show(msg);
        mAdapter.removeAt(position);
    }

    @Override
    public void deleteOrderFailure(String msg) {
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
