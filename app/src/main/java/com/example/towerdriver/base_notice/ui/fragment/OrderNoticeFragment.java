package com.example.towerdriver.base_notice.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseFragment;
import com.example.towerdriver.base_notice.model.NoticeBean;
import com.example.towerdriver.base_notice.presenter.SystemNoticePresenter;
import com.example.towerdriver.base_notice.ui.adapter.NoticeListAdapter;
import com.example.towerdriver.base_notice.view.ISystemNoticeView;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.webview.MyWebViewActivity;
import com.example.towerdriver.webview.NoticeWebViewActivity;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author 53288
 * @description 订单消息
 * @date 2021/5/31
 */
public class OrderNoticeFragment extends BaseFragment<SystemNoticePresenter> implements ISystemNoticeView {
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.ll_noting)
    LinearLayout ll_noting;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private SmartRefreshUtils mSmartRefreshUtils;
    private static final String type = "3";
    private static final int PAGE_START = 1;        // 第一页
    private static final int PAGE_SIZE = 6;         // 每页显示的数量
    private int currPage = 1;                       // 当前页面
    private int all_page = 1;                       //总页数
    private List<NoticeBean.NoticeListBean> mList = new ArrayList<>();
    private NoticeListAdapter mAdapter;

    public static OrderNoticeFragment newInstance() {
        return new OrderNoticeFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.act_system_notice;
    }

    @Override
    protected SystemNoticePresenter createPresenter() {
        return new SystemNoticePresenter(this);
    }

    @Override
    protected void initView() {
        rv_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new NoticeListAdapter(Integer.parseInt(type));
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

                NoticeBean.NoticeListBean noticeListBean = mAdapter.getData().get(position);
                Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra("title", noticeListBean.getTitle());
                intent.putExtra("html", noticeListBean.getContent());
                intent.putExtra("isNeedShare", false);
                startActivity(intent);
                if (presenter != null) {
                    presenter.getChangeNotice(String.valueOf(noticeListBean.getId()), "3", position);
                }
            }
        });
    }

    @Override
    protected void onVisible(boolean isFirstVisible) {
        LogUtils.d("onVisible= " + isFirstVisible);
        if (isFirstVisible) {
            refreshList();
        }
    }

    /**
     * 网络请求
     *
     * @param fresh
     */
    private void sendRequest(boolean fresh) {
        if (presenter != null) {
            presenter.getNoticeList(type, currPage, fresh);
        }
    }

    /**
     * 下拉刷新
     */
    private void refreshList() {
        currPage = PAGE_START;
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        sendRequest(true);
    }

    /**
     * 获得消息成功
     *
     * @param articleBean fresh
     * @param cur_page    当前页面
     * @param total_page  总页面
     * @param fresh
     * @param select_type 1=系统消息，2=救援消息，3=订单消息
     */
    @Override
    public void noticeListSuccess(List<NoticeBean.NoticeListBean> articleBean, int cur_page, int total_page, boolean fresh, String select_type) {
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
    public void noticeListFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        loadingSuccessOrFailure(2);
    }

    @Override
    public void showRefreshNoDate(String msg, List<NoticeBean.NoticeListBean> articleBean) {
        closeDialog();
        loadingSuccessOrFailure(1);
    }

    /**
     * 消息标记为已读
     *
     * @param position
     * @param id
     */
    @Override
    public void noticeDotSuccess(int position, String id) {
        mList.get(position).setStatus(1);
        mAdapter.setData(position, mList.get(position));
    }

    @Override
    public void noticeDotFailure(String msg) {

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
