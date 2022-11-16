package com.example.towerdriver.base_register.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_register.presenter.RegisterPresenter;
import com.example.towerdriver.base_register.view.IRegisterView;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.SettingUtils;
import com.example.towerdriver.utils.timeutil.CodeTimeUtils;
import com.example.towerdriver.utils.timeutil.ITimeOver;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.example.towerdriver.utils.tools.StatusBarUtil;
import com.hjq.toast.ToastUtils;

import java.util.Objects;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 注册页面
 * @date 2021/5/24
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IRegisterView, ITimeOver {
    @BindView(R.id.rl_back)
    LinearLayout rl_back;
    @BindView(R.id.et_tel)
    AppCompatEditText et_tel;                //输入的手机号
    @BindView(R.id.et_verification)
    AppCompatEditText et_verification;       //验证码
    @BindView(R.id.et_pass)
    AppCompatEditText et_pass;               //输入的密码
    @BindView(R.id.et_pass_again)
    AppCompatEditText et_pass_again;         //再次输入密码
    @BindView(R.id.bt_verification)
    AppCompatButton bt_verification;         //点击获取验证码
    @BindView(R.id.bt_load)
    AppCompatButton bt_load;                // 注册
    @BindView(R.id.ll_goto_load)
    LinearLayout ll_goto_load;              //去登陆
    private CodeTimeUtils codeTimeUtils;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_register;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
        if (!SettingUtils.getInstance().isDarkTheme()) {
            StatusBarUtil.setLightMode(this);
        }
        codeTimeUtils = new CodeTimeUtils(this, 60);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.rl_back, R.id.bt_verification, R.id.bt_load, R.id.ll_goto_load})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.bt_verification:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_verification)) {
                    sendMessage();
                }
                break;
            case R.id.bt_load:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_load)) {
                    commit();
                }
                break;
            case R.id.ll_goto_load:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_goto_load)) {
                    finish();
                }
                break;
        }
    }


    /**
     * 发送验证码
     */
    private void sendMessage() {
        String tel;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_tel.getText()).toString())) {
            ToastUtils.show("请您填写手机号");
            return;
        }
        tel = et_tel.getText().toString();
        if (presenter != null) {
            presenter.getVerificationCode(tel, "1");
        }
    }

    /**
     * 用户提交注册
     */
    private void commit() {
        KeyboardUtils.hideKeyboard(this);
        String tel, verification, pass, pass_again;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_tel.getText()).toString())) {
            ToastUtils.show("请您填写手机号");
            return;
        }
        tel = et_tel.getText().toString();
        if (TextUtils.isEmpty(Objects.requireNonNull(et_verification.getText()).toString())) {
            ToastUtils.show("请您填写验证码");
            return;
        }
        verification = et_verification.getText().toString();
        if (TextUtils.isEmpty(Objects.requireNonNull(et_pass.getText()).toString())) {
            ToastUtils.show("请您输入密码");
            return;
        }
        pass = et_pass.getText().toString();
        if (TextUtils.isEmpty(Objects.requireNonNull(et_pass_again.getText()).toString())) {
            ToastUtils.show("请您再次输入密码");
            return;
        }
        pass_again = et_pass_again.getText().toString();
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在注册...");
            presenter.register(tel, verification, pass, pass_again);
        }
    }

    /**
     * 倒计时结束
     */
    @Override
    public void onFinish() {
        bt_verification.setEnabled(true);
        bt_verification.setText("获取验证码");
    }

    /**
     * 倒计时开始
     *
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        bt_verification.setEnabled(false);
        bt_verification.setText("剩余时间 " + millisUntilFinished / 1000);
    }

    /**
     * 发送验证码成功
     *
     * @param msg
     */
    @Override
    public void onSendCodeSuccess(String msg) {
        ToastUtils.show(msg);
        if (codeTimeUtils != null) {
            codeTimeUtils.start();
        }
    }

    /**
     * 发送验证码失败
     *
     * @param msg
     */
    @Override
    public void onSendCodeFailure(String msg) {
        ToastUtils.show(msg);
    }

    /**
     * 注册成功
     *
     * @param msg
     */
    @Override
    public void onRegisterSuccess(String msg) {
        closeDialog();
        ToastUtils.show(msg);
        finish();
    }

    /**
     * 注册失败
     *
     * @param msg
     */
    @Override
    public void onRegisterFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void LoadingClose() {

    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    protected void onDestroy() {
        if (codeTimeUtils != null) {
            codeTimeUtils.cancel();
        }
        super.onDestroy();
    }
}
