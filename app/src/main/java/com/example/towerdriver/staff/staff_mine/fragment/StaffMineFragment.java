package com.example.towerdriver.staff.staff_mine.fragment;

import android.content.Intent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseFragment;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_code.ui.InviteActivity;
import com.example.towerdriver.base_setting.ui.SettingActivity;
import com.example.towerdriver.event.ChangeUserEvent;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.repair.base_person.ui.activity.RepairCenterActivity;
import com.example.towerdriver.staff.staff_person.ui.activity.StaffCenterActivity;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.weight.CircleImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 维修端个人
 * @date 2021/7/4
 */
public class StaffMineFragment extends BaseFragment  {
    @BindView(R.id.cl_change_phone)
    ConstraintLayout cl_change_phone;
    @BindView(R.id.cl_command)
    ConstraintLayout cl_command;
    @BindView(R.id.cl_setting)
    ConstraintLayout cl_setting;
    @BindView(R.id.iv_member_img)
    CircleImageView mHeadImage;
    @BindView(R.id.tv_name)
    AppCompatTextView tv_name;
    @BindView(R.id.cl_daka)
    ConstraintLayout cl_daka;

    public static StaffMineFragment newInstance() {
        return new StaffMineFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_station_mine;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void initView() {
        updateUser();
    }

    @Override
    protected void initData() {
        cl_daka.setVisibility(View.GONE);
        cl_command.setVisibility(View.GONE);
    }

    /**
     * 登陆
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        LogUtils.d(event.getType() + " , " + event.isLogin());
        if (event.isLogin()) {
            String member_img = UserUtils.getInstance().getLoginBean().getImage();
            Glide.with(this).load(member_img).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(mHeadImage);
            String name = UserUtils.getInstance().getLoginBean().getName();
            tv_name.setText(name + "");
        } else {
            Glide.with(this).load(R.mipmap.log_image_bg).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(mHeadImage);
            tv_name.setText("请登录");
        }
    }

    /**
     * 更改用户信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserChangeEvent(ChangeUserEvent event) {
        String member_img = event.getUserBean().getMember_img();
        Glide.with(this).load(member_img).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(mHeadImage);
        String phone = event.getUserBean().getPhone();
    }


    @OnClick({R.id.cl_change_phone, R.id.cl_command, R.id.cl_setting})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_change_phone:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_change_phone)) {
                    Intent intent = new Intent(getActivity(), StaffCenterActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.cl_command:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_command)) {
                    InviteActivity.launch(getActivity(), 2);
                }
                break;
            case R.id.cl_setting:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_setting)) {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
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

    @Override
    public void onResume() {
        super.onResume();
        updateUser();
    }

    private void updateUser() {
        if (UserUtils.getInstance().isLogin()) {
            String member_img = UserUtils.getInstance().getLoginBean().getImage();
            LogUtils.d("member_img = " + member_img);
            Glide.with(this).load(member_img).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(mHeadImage);
            String name = UserUtils.getInstance().getLoginBean().getName();
            tv_name.setText(name + "");
        } else {
            Glide.with(this).load(R.mipmap.log_image_bg).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(mHeadImage);
            tv_name.setText("请登录");
        }
    }
}
