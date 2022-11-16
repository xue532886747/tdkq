package com.example.towerdriver.base_forget.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_forget.presenter.ForgetPassPresenter;
import com.example.towerdriver.base_forget.view.IForgetPassView;
import com.example.towerdriver.base_login.ui.activity.VerificationActivity;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.timeutil.CodeTimeUtils;
import com.example.towerdriver.utils.timeutil.ITimeOver;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.hjq.toast.ToastUtils;

import java.util.Objects;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 忘记密码
 * @date 2021/5/24
 */
public class ForgetPassActivity extends BaseActivity<ForgetPassPresenter> implements IForgetPassView, ITimeOver {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.et_phone)
    AppCompatEditText et_phone;
    @BindView(R.id.et_code)
    AppCompatEditText et_code;
    @BindView(R.id.et_pass)
    AppCompatEditText et_pass;
    @BindView(R.id.et_re_pass)
    AppCompatEditText et_re_pass;
    @BindView(R.id.bt_verification)
    AppCompatButton bt_verification;
    @BindView(R.id.bt_load)
    AppCompatButton bt_load;
    private int login_type;     //登陆类型
    private CodeTimeUtils codeTimeUtils;

    /**
     * @param activity
     * @param login_type 登陆类型
     */
    public static void launch(Activity activity, int login_type) {
        Intent intent = new Intent(activity, ForgetPassActivity.class);
        intent.putExtra("login_type", login_type);
        activity.startActivity(intent);
    }

    @Override
    protected ForgetPassPresenter createPresenter() {
        return new ForgetPassPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_forget_pass;
    }

    @Override
    protected void initView() {
        login_type = getIntent().getIntExtra("login_type", 0);
        codeTimeUtils = new CodeTimeUtils(this, 60);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_back, R.id.bt_load, R.id.bt_verification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_verification:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_verification)) {
                    KeyboardUtils.hideKeyboard(this);
                    getCode();
                }
                break;
            case R.id.bt_load:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_load)) {
                    KeyboardUtils.hideKeyboard(this);
                    commit();
                }
                break;
        }
    }

    /**
     * 点击获取验证码
     */
    private void getCode() {
        String tel;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_phone.getText()).toString())) {
            ToastUtils.show("请您填写手机号");
            return;
        }
        tel = et_phone.getText().toString();
        if (presenter != null) {
            presenter.getVerificationCode(tel, login_type,"3");
        }
    }

    private void commit() {
        KeyboardUtils.hideKeyboard(this);
        String tel, verification, pass, pass_again;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_phone.getText()).toString())) {
            ToastUtils.show("请您填写手机号");
            return;
        }
        tel = et_phone.getText().toString();
        if (TextUtils.isEmpty(Objects.requireNonNull(et_code.getText()).toString())) {
            ToastUtils.show("请您填写验证码");
            return;
        }
        verification = et_code.getText().toString();
        if (TextUtils.isEmpty(Objects.requireNonNull(et_pass.getText()).toString())) {
            ToastUtils.show("请您输入密码");
            return;
        }
        pass = et_pass.getText().toString();
        if (TextUtils.isEmpty(Objects.requireNonNull(et_re_pass.getText()).toString())) {
            ToastUtils.show("请您再次输入密码");
            return;
        }
        pass_again = et_re_pass.getText().toString();
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
        if (presenter != null) {
            presenter.forgetPass(tel, verification, pass, pass_again, login_type);
        }
    }

    @Override
    public void onFinish() {
        bt_verification.setEnabled(true);
        bt_verification.setText("获取验证码");
    }

    @Override
    public void onTick(long millisUntilFinished) {
        bt_verification.setEnabled(false);
        bt_verification.setText("剩余时间 " + millisUntilFinished / 1000);
    }

    @Override
    public void onForgetPassSuccess(int type, String msg) {
        ToastUtils.show(msg);
        closeDialog();
        finish();
    }

    @Override
    public void onForgetPassFailure(int type, String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    @Override
    public void onSendCodeSuccess(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        if (codeTimeUtils != null) {
            codeTimeUtils.start();
        }
    }

    @Override
    public void onSendCodeFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    @Override
    public void LoadingClose() {

    }

    @Override
    public void showFailed(int code, String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    @Override
    protected void onDestroy() {
        if (codeTimeUtils != null) {
            codeTimeUtils.cancel();
        }
        super.onDestroy();
    }

}
