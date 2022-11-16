package com.example.towerdriver.base_setmenu.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_driver.ui.activity.DriverReleaseActivity;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.base_setmenu.presenter.SelectMenuPresenter;
import com.example.towerdriver.base_setmenu.ui.adapter.LeftAdapter;
import com.example.towerdriver.base_setmenu.ui.adapter.RightAdapter;
import com.example.towerdriver.base_setmenu.view.ISelectMenuView;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
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
 * @description 套餐选择
 * @date 2021/5/24
 */
public class SelectMenuActivity extends BaseActivity<SelectMenuPresenter> implements ISelectMenuView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.rv_left)
    RecyclerView rv_left;
    @BindView(R.id.rv_right)
    RecyclerView rv_right;
    @BindView(R.id.ll_noting)
    LinearLayout ll_noting;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            sendList(0, true);
        }
    };

    @Override
    protected SelectMenuPresenter createPresenter() {
        return new SelectMenuPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_select_menu;
    }

    @Override
    protected void initView() {
        rv_right.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_left.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        leftAdapter = new LeftAdapter();
        rv_left.setAdapter(leftAdapter);
        rightAdapter = new RightAdapter();
        rv_right.setAdapter(rightAdapter);

    }

    @Override
    protected void initData() {
        leftAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                leftAdapter.setSelect_num(position);
                Object o = adapter.getData().get(position);
                if (o instanceof SelectBean.ClassifyBean) {
                    sendList(((SelectBean.ClassifyBean) o).getId(), false);
                }
            }
        });
        rightAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Object o = adapter.getData().get(position);
                if (o instanceof SelectBean.RentListBean) {
                    if (!RepeatClickResolveUtil.isFastDoubleClick(position)) {
                        GoodsDetailActivity.launch(SelectMenuActivity.this, String.valueOf(((SelectBean.RentListBean) o).getId()), true, null, adapter.getViewByPosition(position, R.id.iv_major_image));
                    }
                }
            }
        });
        Message message = Message.obtain();
        message.what = 1;
        mHandler.sendMessageDelayed(message, 500);
    }

    /**
     * 请求数据
     *
     * @param id
     * @param isFresh
     */
    public void sendList(int id, boolean isFresh) {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(SelectMenuActivity.this, "正在加载..");
            presenter.getMenuList(id, isFresh);
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

    /**
     * 套餐列表
     *
     * @param selectBean 数据
     * @param isRefresh  是否刷新
     */
    @Override
    public void onSelectMenuSuccess(SelectBean selectBean, boolean isRefresh) {
        closeDialog();
        List<SelectBean.ClassifyBean> classify = selectBean.getClassify();
        leftAdapter.setList(classify);
        List<SelectBean.RentListBean> rent_list = selectBean.getRent_list();
        rightAdapter.setList(rent_list);
        if (isRefresh) {
            leftAdapter.setSelect_num(0);
        }
        if (rent_list != null) {
            if (rent_list.size() == 0) {
                checkVisible(1);
            } else {
                checkVisible(2);
            }
        } else {
            checkVisible(1);
        }
    }

    /**
     * 检查是否显示
     *
     * @param type
     */
    private void checkVisible(int type) {
        if (type == 1) {
            ll_noting.setVisibility(View.VISIBLE);
            rv_right.setVisibility(View.GONE);
        } else {
            ll_noting.setVisibility(View.GONE);
            rv_right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSelectMenuFailure(String msg) {
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
        ToastUtils.show(msg);
    }
}
