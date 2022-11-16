package com.example.towerdriver.base_login.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_authentication.ui.activity.FaceLivenessExpActivity;
import com.example.towerdriver.base_login.presenter.LoginPresenter;
import com.example.towerdriver.base_login.view.LoginView;
import com.example.towerdriver.base_main.ui.activity.MainActivity;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.repair.base.activity.RepairMainActivity;
import com.example.towerdriver.staff.base.activity.StaffMainActivity;
import com.example.towerdriver.station.base.activity.StationMainActivity;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.timeutil.CodeTimeUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.weight.VerificationAction;
import com.example.towerdriver.weight.VerificationCodeEditText;
import com.hjq.toast.ToastUtils;

import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * @author 53288
 * @description 验证码登陆页面
 * @date 2021/5/22
 */
public class VerificationActivity extends BaseActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.am_et)
    VerificationCodeEditText vc_et;
    @BindView(R.id.bt_load)
    AppCompatButton bt_load;
    private String mPhone;
    private int mLogin_type;
    private String mCode;
    private int mLastLoginType = 0;         //记录上次登陆的状态
    private CodeTimeUtils codeTimeUtils;

    public static void launch(Activity activity, String phone, int login_type, int mLastLoginType) {
        Intent intent = new Intent(activity, VerificationActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("login_type", login_type);
        intent.putExtra("lastLoginType", mLastLoginType);
        activity.startActivity(intent);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_verification;
    }

    @Override
    protected void initView() {
        mPhone = getIntent().getStringExtra("phone");
        mLogin_type = getIntent().getIntExtra("login_type", 1);
        mLastLoginType = getIntent().getIntExtra("lastLoginType", 0);
        vc_et.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onInputCompleted(CharSequence s) {
                mCode = s.toString();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_back, R.id.bt_load})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_load:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_load)) {
                    commit();
                }
                break;
        }
    }

    /**
     * 用户登陆
     */
    private void commit() {
        if (TextUtils.isEmpty(mCode)) {
            ToastUtils.show("请输入验证码");
            return;
        }
        if (presenter != null) {
            presenter.sendLogin(mPhone, mCode, 2, mLogin_type);
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在登录...");
        }
    }

    /**
     * 登陆成功
     *
     * @param msg
     * @param type
     */
    @Override
    public void LoginSuccess(String msg, int type) {
        ToastUtils.show("登录成功");
        closeDialog();
        if (mLastLoginType != 0) {         //是否是登陆过期引起的登陆,如果不是直接跳转主页，如果是就关闭当前登录页
            LogUtils.d("测试登陆过期引起的");
            if (mLastLoginType != type) {
                ActivityManager.finishAllActivity();
                if (type == 1) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }else if (type == 2) {
                    Intent intent = new Intent(this, StationMainActivity.class);
                    startActivity(intent);
                }else if (type == 3) {
                    Intent intent = new Intent(this, RepairMainActivity.class);
                    startActivity(intent);
                }else if (type == 4) {
                    Intent intent = new Intent(this, StaffMainActivity.class);
                    startActivity(intent);
                }
            }
        } else {
            LogUtils.d("测试正常退出引起的");
            if (type == 1) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (type == 2) {
                Intent intent = new Intent(this, StationMainActivity.class);
                startActivity(intent);
            }else if (type == 3) {
                Intent intent = new Intent(this, RepairMainActivity.class);
                startActivity(intent);
            }else if (type == 4) {
                Intent intent = new Intent(this, StaffMainActivity.class);
                startActivity(intent);
            }
        }
        if (type == 1) {
            Set<String> tags = new HashSet<>();
            tags.add("user");
            JPushInterface.addTags(this, 0, tags);
        }
        ActivityManager.finishActivity(LoginActivity.class);
        finish();
    }

    /**
     * 登陆失败
     *
     * @param msg
     */
    @Override
    public void LoginFailed(String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    /**
     * 获取验证码成功
     * 这个页面不用
     *
     * @param msg
     */
    @Override
    public void onSendCodeSuccess(String phone, String msg) {

    }

    @Override
    public void LoadingClose() {

    }

    @Override
    public void showFailed(int code, String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    /**
     * 获取验证码失败
     * 这个页面不用
     *
     * @param msg
     */
    @Override
    public void onSendCodeFailure(String msg) {

    }

}
