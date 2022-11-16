package com.example.towerdriver.station.station_order.ui.fragment;

import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BaseFragment;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_order_list.presenter.OrderListPresenter;
import com.example.towerdriver.base_order_list.ui.activity.OrderDetailActivity;
import com.example.towerdriver.base_order_list.ui.activity.OrderListActivity;
import com.example.towerdriver.base_order_list.ui.activity.RentCarDetailActivity;
import com.example.towerdriver.base_order_list.ui.adapter.OrderListAdapter;
import com.example.towerdriver.base_order_list.view.IOrderListView;
import com.example.towerdriver.base_pay.ui.PayStyleActivity;
import com.example.towerdriver.base_setmenu.ui.activity.GoodsDetailActivity;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.station.station_order.presenter.StationOrderListPresenter;
import com.example.towerdriver.station.station_order.ui.activity.StationOrderDetailActivity;
import com.example.towerdriver.station.station_order.ui.adapter.StationOrderListAdapter;
import com.example.towerdriver.station.station_order.view.IStationOrderListView;
import com.example.towerdriver.station.station_rescue.ui.fragment.StationRescueListFragment;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author 53288
 * @description 站长订单列表
 * @date 2021/6/30
 */
public class StationOrderListFragment extends BaseFragment<StationOrderListPresenter> implements IStationOrderListView {
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
    private StationOrderListAdapter mAdapter;
    private String address;

    public static StationOrderListFragment newInstance() {
        return new StationOrderListFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_station_order_list;
    }

    @Override
    protected StationOrderListPresenter createPresenter() {
        return new StationOrderListPresenter(this);
    }

    @Override
    protected void initView() {
        rv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new StationOrderListAdapter();
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
                StationOrderDetailActivity.launch(getActivity(), mAdapter.getData().get(position).getId() + "");
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
