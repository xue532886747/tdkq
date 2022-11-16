package com.example.towerdriver.base_driver.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.base_ui.bigimageview.ImagePreview;
import com.example.base_ui.bigimageview.view.listener.OnOriginProgressListener;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_driver.model.bean.ReleaseListBean;
import com.example.towerdriver.base_driver.presenter.DriverListPresenter;
import com.example.towerdriver.base_driver.ui.adapter.DriverListAdapter;
import com.example.towerdriver.base_driver.view.IDriveListView;
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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.base_ui.bigimageview.ImagePreview.LoadStrategy.AlwaysThumb;

/**
 * @author 53288
 * @description 小哥发布列表页
 * @date 2021/6/3
 */
public class DriverListActivity extends BaseActivity<DriverListPresenter> implements IDriveListView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.ll_noting)
    LinearLayout ll_noting;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_switch)
    AppCompatTextView tv_switch;
    @BindView(R.id.cl_top)
    ConstraintLayout cl_top;
    private DriverListAdapter mAdapter;
    private SmartRefreshUtils mSmartRefreshUtils;
    private boolean type = false;                   // false==看所有列表，true==只看我
    private static final int PAGE_START = 1;        // 第一页
    private static final int PAGE_SIZE = 6;         // 每页显示的数量
    private int currPage = 1;                       // 当前页面
    private int all_page = 1;                       //总页数
    private List<ReleaseListBean.ArticleBean> mList = new ArrayList<>();
    private static final int RequestCode = 100;
    private CenterDialog centerDialog;

    @Override
    protected DriverListPresenter createPresenter() {
        return new DriverListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_diriver_list;
    }

    @Override
    protected void initView() {
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DriverListAdapter(type);
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
        mAdapter.setItemClickListener(new DriverListAdapter.ItemClickListener() {
            @Override
            public void getImagePosition(int current_position, String current_url, List<String> all_image) {
                seePhoto(current_position, current_url, all_image);
            }
        });
        mAdapter.addChildClickViewIds(R.id.bt_delete);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.bt_delete:
                        if (presenter != null) {
                            Object o = adapter.getData().get(position);
                            if (o instanceof ReleaseListBean.ArticleBean) {
                                Integer id = ((ReleaseListBean.ArticleBean) o).getId();
                                prepareDelete(String.valueOf(id), position);
                            }
                        }
                        break;
                }
            }
        });
        refreshList();
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
     * 网络请求
     *
     * @param fresh
     */
    private void sendRequest(boolean fresh) {
        if (presenter != null) {
            presenter.getDriverListPresenter(type, currPage, fresh);
        }
    }

    /**
     * 准备删除item的弹窗
     *
     * @param id
     * @param position
     */
    private void prepareDelete(String id, int position) {
        centerDialog = new CenterDialog(this);
        centerDialog.Builder("是否删除？", "确认", "取消");
        centerDialog.setDialogClickListener(new CenterDialog.DialogClickListener() {
            @Override
            public void top(int type) {
                deleteItem(String.valueOf(id), position);
                centerDialog.cancle();
            }

            @Override
            public void bottom(int type) {
                centerDialog.cancle();
            }
        });
        centerDialog.show();
    }


    /**
     * 删除item
     *
     * @param id
     * @param position
     */
    private void deleteItem(String id, int position) {
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在删除...");
        if (presenter != null) {
            presenter.deleteItem(String.valueOf(id), position);
        }
    }


    @OnClick({R.id.ll_back, R.id.tv_switch, R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_switch:
                closeDialog();
                this.type = !type;
                refreshList();
                break;
            case R.id.ll_add:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_add)) {
                    Intent intent = new Intent(this, DriverReleaseActivity.class);
                    startActivityForResult(intent, RequestCode);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode) {
            if (resultCode == 1) {
                if (data != null) {
                    boolean isReleaseSuccess = data.getBooleanExtra("isReleaseSuccess", false);
                    if (isReleaseSuccess) {
                        refreshList();
                    }
                }
            }
        }
    }

    /**
     * 获取列表成功
     *
     * @param articleBean fresh
     * @param cur_page    当前页面
     * @param total_page  总页面
     * @param fresh       是否是刷新
     * @param select_type 是否是刷新
     */
    @Override
    public void contentListSuccess(List<ReleaseListBean.ArticleBean> articleBean, int cur_page, int total_page, boolean fresh, boolean select_type) {
        closeDialog();
        if (mSmartRefreshUtils != null) {
            mSmartRefreshUtils.success();
        }
        all_page = total_page;
        checkSelect(select_type);
        mAdapter.setSelectType(type);
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

    /**
     * 获取列表失败
     *
     * @param msg
     */
    @Override
    public void contentListFailure(String msg) {
        closeDialog();
        loadingSuccessOrFailure(2);
    }

    /**
     * 获取列表成功，但列表无数据
     *
     * @param msg
     * @param articleBean
     */
    @Override
    public void showRefreshNoDate(boolean select_type,String msg, List<ReleaseListBean.ArticleBean> articleBean) {
        closeDialog();
        checkSelect(select_type);
        loadingSuccessOrFailure(1);
    }

    /**
     * 检查选择是自己的列表还是全部的列表
     *
     * @param select_type
     */
    public void checkSelect(boolean select_type) {
        type = select_type;
        if (type) {
            tv_switch.setText(R.string.driver_mylist);
        } else {
            tv_switch.setText(R.string.driver_alllist);
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

    /**
     * 删除成功
     *
     * @param position 位置
     * @param id       id
     * @param msg
     */
    @Override
    public void DeleteContentSuccess(int position, int id, String msg) {
        closeDialog();
        ToastUtils.show(msg);
        checkDeleteList(position, id);
    }

    /**
     * 删除item
     *
     * @param position
     * @param id
     */
    private void checkDeleteList(int position, int id) {
        if (mList != null && mList.size() > 0) {
            Iterator<ReleaseListBean.ArticleBean> iterator = mList.iterator();
            while (iterator.hasNext()) {
                ReleaseListBean.ArticleBean next = iterator.next();
                if (next.getId() == id) {
                    iterator.remove();
                    mAdapter.removeAt(position);
                }
            }
        }
        checkList();
    }

    /**
     * 检查list
     */
    private void checkList() {
        if (mList != null && mList.size() > 0) {
            ll_noting.setVisibility(View.GONE);
            rv_list.setVisibility(View.VISIBLE);
        } else {
            ll_noting.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
        }
    }

    /**
     * 删除失败
     *
     * @param position
     * @param msg
     */
    @Override
    public void DeleteContentFailure(int position, String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void LoadingClose() {
        ToastUtils.show(Constant.TOKEN_EXPIRED);
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        ToastUtils.show(msg);
        loadingSuccessOrFailure(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (centerDialog != null) {
            centerDialog.shutDownDialog();
        }
    }
}
