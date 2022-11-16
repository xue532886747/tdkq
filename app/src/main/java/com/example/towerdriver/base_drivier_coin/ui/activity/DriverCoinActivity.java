package com.example.towerdriver.base_drivier_coin.ui.activity;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_drivier_coin.model.DriverCoinBean;
import com.example.towerdriver.base_drivier_coin.present.IDriverCoinPresenter;
import com.example.towerdriver.base_drivier_coin.ui.adapter.DriverCoinListAdapter;
import com.example.towerdriver.base_drivier_coin.view.IDriverCoinView;
import com.example.towerdriver.base_invite.model.InviteBean;
import com.example.towerdriver.base_invite.ui.adapter.InviteListAdapter;
import com.example.towerdriver.dialog.TiXianDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.ChangeUserEvent;
import com.example.towerdriver.event.WxEntryEvent;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 我的骑行币
 * @date 2021/6/19
 */
public class DriverCoinActivity extends BaseActivity<IDriverCoinPresenter> implements IDriverCoinView {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.ll_tixian)
    AppCompatButton ll_tixian;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.ll_noting)
    LinearLayout ll_noting;
    @BindView(R.id.tv_number)
    AppCompatTextView tv_number;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private SmartRefreshUtils mSmartRefreshUtils;
    private static final int PAGE_START = 1;        // 第一页
    private static final int PAGE_SIZE = 6;         // 每页显示的数量
    private int currPage = 1;                       // 当前页面
    private int all_page = 1;                       //总页数
    private List<DriverCoinBean.WithdrawBean> mList = new ArrayList<>();
    private DriverCoinListAdapter mAdapter;


    @Override
    protected IDriverCoinPresenter createPresenter() {
        return new IDriverCoinPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_drivier_coin;
    }

    @Override
    protected void initView() {
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new DriverCoinListAdapter();
        rv_list.setAdapter(mAdapter);
        mAdapter.getLoadMoreModule().setEnableLoadMore(true);
        mAdapter.setAnimationEnable(true);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void initData() {
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
        refreshList();
    }

    @OnClick({R.id.ll_back, R.id.ll_tixian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_tixian:
                goToWx();
                break;
        }
    }

    private void goToWx() {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(this, Constant.WxAppId);
        // 判断是否安装了微信客户端
        if (!wxapi.isWXAppInstalled()) {
            ToastUtils.show("您还未安装微信客户端！");
            return;
        }
        // 发送授权登录信息，来获取code
        SendAuth.Req req = new SendAuth.Req();
        // 应用的作用域，获取个人信息
        req.scope = "snsapi_userinfo";
        /**
         * 用于保持请求和回调的状态，授权请求后原样带回给第三方
         * 为了防止csrf攻击（跨站请求伪造攻击），后期改为随机数加session来校验
         */
        req.state = "app_wechat";
        wxapi.sendReq(req);
    }


    /**
     * 更改用户信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxEntryEvent(WxEntryEvent event) {
        String code = event.getCode();
        LogUtils.d("code = " + code);
        if (presenter != null) {
            new TiXianDialog(DriverCoinActivity.this).Builder().setDialogClickListener(new TiXianDialog.DialogClickListener() {
                @Override
                public void getTiXianMoney(String content) {
                    if (!TextUtils.isEmpty(content)) {
                        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(DriverCoinActivity.this, "正在加载...");
                        presenter.tixian(content, code, 1);
                    } else {
                        ToastUtils.show("您还未输入金额");
                    }

                }
            }).show();

        }
    }

    /**
     * 网络请求
     *
     * @param fresh
     */
    private void sendRequest(boolean fresh) {
        if (presenter != null) {
            presenter.getDriverCoinList(currPage, fresh);
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

    @Override
    public void IDriverCoinSuccess(List<DriverCoinBean.WithdrawBean> withdrawBeans, int cur_page, int total_page, boolean fresh, String count) {
        closeDialog();
        if (mSmartRefreshUtils != null) {
            mSmartRefreshUtils.success();
        }
        tv_number.setText(count);
        all_page = total_page;
        ll_noting.setVisibility(View.GONE);
        rv_list.setVisibility(View.VISIBLE);
        currPage = cur_page + 1;
        if (fresh) {
            mList = new ArrayList<>();
        }
        int size = withdrawBeans.size();
        mList.addAll(withdrawBeans);
        if (fresh) {
            mAdapter.setList(mList);
        } else {
            mAdapter.addData(withdrawBeans);
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
    public void IDriverCoinFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        loadingSuccessOrFailure(2);
    }

    @Override
    public void showRefreshNoDate(String msg, List<DriverCoinBean.WithdrawBean> withdrawBeans,String banalce) {
        tv_number.setText(banalce+"");
        closeDialog();
        loadingSuccessOrFailure(1);
    }

    @Override
    public void tixianSuccess(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        refreshList();
    }

    @Override
    public void tixianFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    @Override
    public void LoadingClose() {
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
