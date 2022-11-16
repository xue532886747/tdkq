package com.example.towerdriver.base_member_level.ui.activity;


import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.example.base_ui.bubbleseek.BubbleSeekBar;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_driver.ui.activity.DriverReleaseActivity;
import com.example.towerdriver.base_main.bean.AdvertiBean;
import com.example.towerdriver.base_member_level.model.bean.LevelBean;
import com.example.towerdriver.base_member_level.presenter.MemberLevelPresenter;
import com.example.towerdriver.base_member_level.ui.adapter.MemberChartAdapter;
import com.example.towerdriver.base_member_level.view.MemberLevelView;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.weight.CircleImageView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.util.BannerUtils;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 会员等级
 * @date 2021/5/25
 */
public class MemberLevelActivity extends BaseActivity<MemberLevelPresenter> implements MemberLevelView {
    @BindView(R.id.tv_point)
    AppCompatTextView tv_point;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.rv_chart_list)
    RecyclerView rv_chart_list;
    @BindView(R.id.circleImageView)
    CircleImageView mImageHead;
    @BindView(R.id.iv_level)
    AppCompatImageView iv_level;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private SmartRefreshUtils mSmartRefreshUtils;
    private MemberChartAdapter memberChartAdapter;


    @Override
    protected MemberLevelPresenter createPresenter() {
        return new MemberLevelPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_member_level;
    }

    @Override
    protected void initView() {
        rv_chart_list.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        memberChartAdapter = new MemberChartAdapter();
        rv_chart_list.setAdapter(memberChartAdapter);
        rv_chart_list.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), DisPlayUtils.dp2px(10));

            }
        });
        rv_chart_list.setClipToOutline(true);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    @Override
    protected void initData() {
        refreshList();
    }

    /**
     * 下拉刷新
     */
    private void refreshList() {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在加载...");
            presenter.getMemberLevel();
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
     * 获得等级信息成功
     *
     * @param memberInfoBean 用户信息
     * @param list           用户列表
     * @param msg
     */
    @Override
    public void getLevelSuccess(LevelBean.MemberInfoBean memberInfoBean, List<LevelBean.OperateIntegralBean> list, String msg) {
        loadingSuccessOrFailure(1);
        closeDialog();
        if (memberInfoBean != null) {
            Glide.with(this).load(memberInfoBean.getMember_img()).into(mImageHead);
            Glide.with(this).load(memberInfoBean.getIntegral_image()).into(iv_level);
            tv_point.setText(memberInfoBean.getIntegral() + "");
        }
        List<LevelBean.OperateIntegralBean> mList = new ArrayList<>();
        LevelBean.OperateIntegralBean operateIntegralBean = new LevelBean.OperateIntegralBean();
        operateIntegralBean.setType(1);
        mList.add(operateIntegralBean);
        for (LevelBean.OperateIntegralBean integralBean : list) {
            integralBean.setType(2);
        }
        mList.addAll(list);
        memberChartAdapter.setList(mList);
    }

    private void loadingSuccessOrFailure(int loading_type) {
        if (loading_type == 1) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.success();
            }
        } else if (loading_type == 2) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.fail();
            }
        }
    }

    /**
     * 失败
     *
     * @param msg
     */
    @Override
    public void getLevelFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
        loadingSuccessOrFailure(2);
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
}
