package com.example.towerdriver.staff.base_approval.ui.fragment;

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
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.ApprovalStatusEvent;
import com.example.towerdriver.staff.base.activity.StaffMainActivity;
import com.example.towerdriver.staff.base_approval.model.ApprovalStatusBean;
import com.example.towerdriver.staff.base_approval.model.SponsorListBean;
import com.example.towerdriver.staff.base_approval.presenter.ApprovalListPresenter;
import com.example.towerdriver.staff.base_approval.ui.activity.ApprovalDetailActivity;
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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author 53288
 * @description 我审批的
 * @date 2021/7/8
 */
public class MyApprovalFragment extends BaseFragment<ApprovalListPresenter> implements IApprovalListView, IClickFreshListener {
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
    private int ListType = 1;
    private boolean isCanFresh = false;


    public static MyApprovalFragment newInstance() {
        return new MyApprovalFragment();
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

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onApprovalStatusEvent(ApprovalStatusEvent event) {
//        LogUtils.d(event.toString());
//    }

    @Override
    protected void initView() {
        tv_title.setText("处理审批");
        ll_add.setVisibility(View.GONE);
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
                if (view.getId() == R.id.bt_left) {
                    if (o instanceof SponsorListBean.ListBean) {
                        if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_left)) {
                            getDialog(1, position, ((SponsorListBean.ListBean) o).getId() + "");
                        }
                    }
                }
                if (view.getId() == R.id.bt_right) {
                    if (o instanceof SponsorListBean.ListBean) {
                        if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_right)) {
                            getDialog(2, position, ((SponsorListBean.ListBean) o).getId() + "");
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
     * @param id
     */
    private void getDialog(int types, int position, String id) {
        String title = "";
        if (types == 1) {
            title = "是否通过当前审批?";
        } else if (types == 2) {
            title = "是否拒绝当前审批?";
        }
        CenterDialog centerDialog = new CenterDialog(mContext).Builder(title, "确认", "取消");
        centerDialog.setDialogClickListener(new CenterDialog.DialogClickListener() {
            @Override
            public void top(int type) {
                if (presenter != null) {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(mContext, "正在提交...");
                    presenter.permissionApprovalOrder(types, id, position);
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

    /**
     * 我审批的列表
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

    @Override
    public void deleteApprovalSuccess(String msg, int id, int position) {

    }

    @Override
    public void deleteApprovalFailure(String msg) {

    }

    /**
     * 审批通过或者没通过
     *
     * @param msg
     * @param position
     * @param approvalStatusBean
     */
    @Override
    public void permissionApprovalSuccess(String msg, int position, ApprovalStatusBean approvalStatusBean) {
        closeDialog();
        ToastUtils.show(msg);
        if (mList != null && mList.size() > position) {
            SponsorListBean.ListBean listBean = mList.get(position);
            listBean.setStatus(approvalStatusBean.getStatus());
            listBean.setStatus_name(approvalStatusBean.getStatus_name());
            SponsorListBean.ListBean orderBean1 = mAdapter.getData().get(position);
            orderBean1.setStatus(approvalStatusBean.getStatus());
            orderBean1.setStatus_name(approvalStatusBean.getStatus_name());
            mAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void permissionApprovalFailure(String msg) {
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


    @Override
    public void clickRefresh(int position) {
        if (position == 1 && isCanFresh) {
            refreshList();
        }
    }
}
