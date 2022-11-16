package com.example.towerdriver.base_notice.ui.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_notice.presenter.NoticePresenter;
import com.example.towerdriver.base_notice.ui.adapter.FragmentViewPagerAdapter;
import com.example.towerdriver.base_notice.ui.fragment.OrderNoticeFragment;
import com.example.towerdriver.base_notice.ui.fragment.RescueNoticeFragment;
import com.example.towerdriver.base_notice.ui.fragment.SystemNoticeFragment;
import com.example.towerdriver.base_notice.view.INoticeView;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.hjq.toast.ToastUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeAnchor;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

/**
 * @author 53288
 * @description 消息通知
 * @date 2021/5/31
 */
public class NoticeActivity extends BaseActivity<NoticePresenter> implements INoticeView {
    private static final String[] CHANNELS = new String[]{"系统消息", "救援消息", "订单消息"};
    private List<String> mDataList = new ArrayList<>(Arrays.asList(CHANNELS));
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private CommonNavigator mCommonNavigator;
    private SystemNoticeFragment mSystemNoticeFragment;                 //系统消息
    private RescueNoticeFragment mRescueNoticeFragment;                 //救援消息
    private OrderNoticeFragment mOrderNoticeFragment;                   //订单消息
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected NoticePresenter createPresenter() {
        return new NoticePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_notice;
    }

    @Override
    protected void initView() {
        mSystemNoticeFragment = SystemNoticeFragment.newInstance();
        mRescueNoticeFragment = RescueNoticeFragment.newInstance();
        mOrderNoticeFragment = OrderNoticeFragment.newInstance();
        mFragments.add(mSystemNoticeFragment);
        mFragments.add(mRescueNoticeFragment);
        mFragments.add(mOrderNoticeFragment);
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(),
                mFragments, CHANNELS);
        mViewPager.setAdapter(fragmentViewPagerAdapter);
        mCommonNavigator = new CommonNavigator(this);
        mCommonNavigator.setScrollPivotX(0.65f);
        mCommonNavigator.setAdjustMode(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                final BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.text_black_color));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.main_light_color));
                simplePagerTitleView.setTextSize(15);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                badgePagerTitleView.setInnerPagerTitleView(simplePagerTitleView);
                badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(context, 2)));
                badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, 0));
                badgePagerTitleView.setAutoCancelBadge(false);
                return badgePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setRoundRadius(DisPlayUtils.dp2px(20));
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.main_top_tv_color_no));
                return linePagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(mCommonNavigator);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mMagicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mMagicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mMagicIndicator.onPageScrollStateChanged(state);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    @Override
    public void LoadingClose() {
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        ToastUtils.show(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setBadgeNumber(0);
        setBadgeNum(MyApplication.getBadgeNumber(), this);
    }

    /**
     * 华为手机添加角标
     *
     * @param num
     * @param context
     */
    public void setBadgeNum(int num, Context context) {
        try {
            Bundle bunlde = new Bundle();
            bunlde.putString("package", "com.example.towerdriver");
            bunlde.putInt("badgenumber", num);  //MainActivity WelcomeActivity
            bunlde.putString("class", "com.example.towerdriver.base_welcome.ui.WelComeActivity");
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
