package com.example.towerdriver.staff.base_approval.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseFragment;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.ApprovalStatusEvent;
import com.example.towerdriver.event.ChangeUserEvent;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.repair.base_order.ui.activity.CheckCarDetailActivity;
import com.example.towerdriver.staff.base.activity.StaffMainActivity;
import com.example.towerdriver.staff.base_approval.model.ApprovalStatusBean;
import com.example.towerdriver.staff.base_approval.model.SponsorListBean;
import com.example.towerdriver.staff.base_approval.presenter.ApprovalListPresenter;
import com.example.towerdriver.staff.base_approval.ui.activity.ApprovalDetailActivity;
import com.example.towerdriver.staff.base_approval.ui.activity.CreateApprovalActivity;
import com.example.towerdriver.staff.base_approval.ui.adapter.ApprovalListAdapter;
import com.example.towerdriver.staff.base_approval.view.IApprovalListView;
import com.example.towerdriver.repair.base_order.model.RepairListBean;
import com.example.towerdriver.staff.interfaces.IClickFreshListener;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 我发起的
 * @date 2021/7/8
 */
public class MyLaunchFragment extends BaseFragment<ApprovalListPresenter> implements IApprovalListView, IClickFreshListener {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_title)
    AppCompatTextView tv_title;
    @BindView(R.id.ll_noting)
    LinearLayout ll_noting;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private SmartRefreshUtils mSmartRefreshUtils;
    private static final int PAGE_START = 1;        // 第一页
    private static final int PAGE_SIZE = 6;         // 每页显示的数量
    private int currPage = 1;                       // 当前页面
    private int all_page = 1;                       //总页数
    private List<SponsorListBean.ListBean> mList = new ArrayList<>();
    private ApprovalListAdapter mAdapter;
    private int ListType = 0;
    private boolean isCanFresh = false;

    public static MyLaunchFragment newInstance() {
        return new MyLaunchFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_launch_list;
    }

    @Override
    protected ApprovalListPresenter createPresenter() {
        return new ApprovalListPresenter(this);
    }

//    @Override
//    protected boolean isRegisterEventBus() {
//        return true;
//    }

    @Override
    protected void initView() {

        rv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ApprovalListAdapter();
        mAdapter.setListType(ListType);
        rv_list.setAdapter(mAdapter);
        mAdapter.getLoadMoreModule().setEnableLoadMore(true);
        mAdapter.setAnimationEnable(true);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        mAdapter.addChildClickViewIds(R.id.bt_right, R.id.bt_left);
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
                if (o instanceof SponsorListBean.ListBean) {
                    ApprovalDetailActivity.launch(getActivity(), ((SponsorListBean.ListBean) o).getId() + "");
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Object o = adapter.getData().get(position);
                if (view.getId() == R.id.bt_right) {
                    if (o instanceof SponsorListBean.ListBean) {
                        if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_right)) {
                            getDialog(position, ((SponsorListBean.ListBean) o).getId() + "");
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_add)) {
                    Intent intent = new Intent(MyLaunchFragment.this.getContext(), CreateApprovalActivity.class);
                    startActivityForResult(intent, 400);
                }
                break;
        }
    }

    /**
     * 弹窗
     *
     * @param position
     * @param order_id
     */
    private void getDialog(int position, String order_id) {
        String title = "是否删除审批?";
        CenterDialog centerDialog = new CenterDialog(mContext).Builder(title, "确认", "取消");
        centerDialog.setDialogClickListener(new CenterDialog.DialogClickListener() {
            @Override
            public void top(int type) {
                if (presenter != null) {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(mContext, "正在删除...");
                    presenter.getDeleteApproval(order_id, position);
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
            presenter.getList(ListType, currPage, fresh);
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
            isCanFresh = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 400) {
            if (resultCode == 401) {
                refreshList();
            }
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onApprovalStatusEvent(ApprovalStatusEvent event) {
//        LogUtils.d(event.toString());
//    }

    /**
     * 我发起的
     *
     * @param articleBean fresh
     * @param cur_page    当前页面
     * @param total_page  总页面
     * @param fresh
     */
    @Override
    public void approvalListSuccess(List<SponsorListBean.ListBean> articleBean, int cur_page, int total_page, boolean fresh) {
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
        int size = articleBean.size();
        mList.addAll(articleBean);
        if (fresh) {
            mAdapter.setList(mList);
            rv_list.scrollToPosition(0);
        } else {
            mAdapter.addData(articleBean);
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
    public void approvalListFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
        loadingSuccessOrFailure(2);
    }

    @Override
    public void showRefreshNoDate(String msg, List<SponsorListBean.ListBean> listBeans) {
        loadingSuccessOrFailure(1);
        closeDialog();
    }

    /**
     * 删除审批
     *
     * @param msg
     * @param id
     * @param position
     */
    @Override
    public void deleteApprovalSuccess(String msg, int id, int position) {
        ToastUtils.show(msg);
        closeDialog();
        checkDeleteList(position, id);
    }

    @Override
    public void deleteApprovalFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }


    @Override
    public void permissionApprovalSuccess(String msg, int position, ApprovalStatusBean approvalStatusBean) {

    }

    @Override
    public void permissionApprovalFailure(String msg) {

    }

    /**
     * 删除item
     *
     * @param position
     * @param id
     */
    private void checkDeleteList(int position, int id) {
        if (mList != null && mList.size() > 0) {
            Iterator<SponsorListBean.ListBean> iterator = mList.iterator();
            while (iterator.hasNext()) {
                SponsorListBean.ListBean next = iterator.next();
                if (next.getId() == id) {
                    iterator.remove();
                    mAdapter.removeAt(position);
//                    new ApprovalStatusEvent(1, id).post();
                }
            }
        }
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

    @Override
    public void clickRefresh(int position) {
        if (position == 0 && isCanFresh) {
            refreshList();
        }
    }
}
