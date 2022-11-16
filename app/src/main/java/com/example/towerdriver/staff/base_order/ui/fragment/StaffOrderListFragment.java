package com.example.towerdriver.staff.base_order.ui.fragment;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseFragment;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_notice.ui.adapter.FragmentViewPagerAdapter;
import com.example.towerdriver.staff.base_approval.ui.fragment.MyApprovalFragment;
import com.example.towerdriver.staff.base_approval.ui.fragment.MyLaunchFragment;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.tools.DisPlayUtils;

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

/**
 * @author 53288
 * @description 审批列表
 * @date 2021/7/8
 */
public class StaffOrderListFragment extends BaseFragment {
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    private static final String[] CHANNELS = new String[]{"我发起的", "我审批的"};
    private List<String> mDataList = new ArrayList<>(Arrays.asList(CHANNELS));
    private CommonNavigator mCommonNavigator;
    private FragmentViewPagerAdapter fragmentViewPagerAdapter;
    private MyLaunchFragment myLaunchFragment;                          //我发起的
    private MyApprovalFragment myApprovalFragment;                      //我审批的

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public static StaffOrderListFragment newInstance() {
        return new StaffOrderListFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_staff_list;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void initView() {
        myLaunchFragment = MyLaunchFragment.newInstance();
        myApprovalFragment = MyApprovalFragment.newInstance();
        mFragments.add(myLaunchFragment);
        mFragments.add(myApprovalFragment);
        fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getChildFragmentManager(), mFragments, CHANNELS);
        mViewPager.setAdapter(fragmentViewPagerAdapter);
        mCommonNavigator = new CommonNavigator(mContext);
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
                simplePagerTitleView.setTextSize(14);
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
                linePagerIndicator.setColors(getResources().getColor(R.color.main_light_color));
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

    @OnClick({R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_add:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_add)) {

//                    startActivityForResult(intent, 400);
                }
                break;
        }
    }

    @Override
    public void LoadingClose() {

    }

    @Override
    public void showFailed(int code, String msg) {

    }
}
