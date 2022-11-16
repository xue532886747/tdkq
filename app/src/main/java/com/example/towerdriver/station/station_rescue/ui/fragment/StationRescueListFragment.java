package com.example.towerdriver.station.station_rescue.ui.fragment;

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
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_order_list.ui.activity.OrderListActivity;
import com.example.towerdriver.base_rescue.model.RescueListBean;
import com.example.towerdriver.base_rescue.ui.activity.RescueCommentActivity;
import com.example.towerdriver.base_rescue.ui.activity.RescueListActivity;
import com.example.towerdriver.base_rescue.ui.adapter.RescueListAdapter;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.station.base.activity.StationMainActivity;
import com.example.towerdriver.station.station_rescue.model.RescueBean;
import com.example.towerdriver.station.station_rescue.model.StationRescueBean;
import com.example.towerdriver.station.station_rescue.presenter.StationRescueListPresenter;
import com.example.towerdriver.station.station_rescue.ui.activity.FinishRescueActivity;
import com.example.towerdriver.station.station_rescue.ui.activity.StationRescueDetailActivity;
import com.example.towerdriver.station.station_rescue.ui.adapter.StationRescueListAdapter;
import com.example.towerdriver.station.station_rescue.view.IStationRescueListView;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author 53288
 * @description 救援列表
 * @date 2021/6/30
 */
public class StationRescueListFragment extends BaseFragment<StationRescueListPresenter> implements IStationRescueListView {
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
    private List<StationRescueBean.OrderBean> mList = new ArrayList<>();
    private StationRescueListAdapter mAdapter;


    public static StationRescueListFragment newInstance() {
        return new StationRescueListFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_station_rescue_list;
    }

    @Override
    protected StationRescueListPresenter createPresenter() {
        return new StationRescueListPresenter(this);
    }

    @Override
    protected void initView() {
        rv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new StationRescueListAdapter();
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
        mAdapter.addChildClickViewIds(R.id.bt_evaluate);
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
                Object o = adapter.getData().get(position);
                if (!RepeatClickResolveUtil.isFastDoubleClick(position)) {
                    if (o instanceof StationRescueBean.OrderBean) {
                        StationRescueDetailActivity.launch(getActivity(), ((StationRescueBean.OrderBean) o).getId() + "");
                    }
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Object o = adapter.getData().get(position);
                if (o instanceof StationRescueBean.OrderBean) {
                    Integer order_status = ((StationRescueBean.OrderBean) o).getStatus();
                    if (order_status == 2) {   //接单按钮
                        getDialog(2, ((StationRescueBean.OrderBean) o).getId() + "", position, null);
                    } else if (order_status == 3) { //完成按钮
                        Intent intent = new Intent(StationRescueListFragment.this.getContext(), FinishRescueActivity.class);
                        intent.putExtra("rescue_id", ((StationRescueBean.OrderBean) o).getId() + "");
                        intent.putExtra("position", position);
                        startActivityForResult(intent,400);
                    }
                }
            }
        });
    }

    /**
     * 网络请求
     *
     * @param fresh
     */
    private void sendRequest(boolean fresh) {
        if (presenter != null) {
            presenter.getRescueList(currPage, fresh);
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


    /**
     * 弹窗
     *
     * @param type_status
     * @param id
     * @param position
     * @param rescue_id
     */
    private void getDialog(int type_status, String id, int position, String rescue_id) {
        String title = "";
        if (type_status == 2) {
            title = "是否接单?";
        } else if (type_status == 3) {
            title = "是否完成订单?";
        }
        CenterDialog centerDialog = new CenterDialog(mContext).Builder(title, "确认", "取消");
        centerDialog.setDialogClickListener(new CenterDialog.DialogClickListener() {
            @Override
            public void top(int type) {
                if (type_status == 2) {
                    if (presenter != null) {
                        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(mContext, "正在接单...");
                        presenter.getReceiverOrder(id, position);
                    }
                } else if (type_status == 3) {

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
     * 请求列表成功
     *
     * @param rescueOrderBeans fresh
     * @param cur_page         当前页面
     * @param total_page       总页面
     * @param fresh
     */
    @Override
    public void rescueListSuccess(List<StationRescueBean.OrderBean> rescueOrderBeans, int cur_page, int total_page, boolean fresh) {
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
        int size = rescueOrderBeans.size();
        mList.addAll(rescueOrderBeans);
        if (fresh) {
            mAdapter.setList(mList);
        } else {
            mAdapter.addData(rescueOrderBeans);
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
    public void rescueListFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        loadingSuccessOrFailure(2);
    }

    @Override
    public void showRefreshNoDate(String msg, List<StationRescueBean.OrderBean> orderBeans) {
        closeDialog();
        loadingSuccessOrFailure(1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 400) {
            if (resultCode == 401) {
                if (data == null) {
                    return;
                }
                RescueBean data1 = (RescueBean) data.getSerializableExtra("data");
                int position = data.getIntExtra("position", 0);
                if (mList != null && mList.size() > position) {
                    StationRescueBean.OrderBean orderBean = mList.get(position);
                    orderBean.setStatus(data1.getStatus());
                    orderBean.setStatus_name(data1.getStatus_name());
                    StationRescueBean.OrderBean orderBean1 = mAdapter.getData().get(position);
                    orderBean1.setStatus(data1.getStatus());
                    orderBean1.setStatus_name(data1.getStatus_name());
                    mAdapter.notifyItemChanged(position);
                }
            }
        }
    }

    /**
     * 救援接单成功
     *
     * @param msg
     * @param position
     * @param rescueBean
     */
    @Override
    public void receiverOrderSuccess(String msg, int position, RescueBean rescueBean) {
        closeDialog();
        ToastUtils.show(msg);
        if (mList != null && mList.size() > position) {
            StationRescueBean.OrderBean orderBean = mList.get(position);
            orderBean.setStatus(rescueBean.getStatus());
            orderBean.setStatus_name(rescueBean.getStatus_name());
            StationRescueBean.OrderBean orderBean1 = mAdapter.getData().get(position);
            orderBean1.setStatus(rescueBean.getStatus());
            orderBean1.setStatus_name(rescueBean.getStatus_name());
            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void receiverOrderFailure(String msg) {
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
