package com.example.towerdriver.base_change_phone.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_change_phone.presenter.CheckNewPhonePresenter;
import com.example.towerdriver.base_change_phone.presenter.CheckOldPhonePresenter;
import com.example.towerdriver.base_change_phone.view.ICheckNewPhoneView;
import com.example.towerdriver.base_change_phone.view.ICheckOldPhoneView;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.ChangeUserEvent;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.timeutil.CodeTimeUtils;
import com.example.towerdriver.utils.timeutil.ITimeOver;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.hjq.toast.ToastUtils;

import java.util.Objects;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 检查老手机
 * @date 2021/6/18
 */
public class CheckNewPhoneActivity extends BaseActivity<CheckNewPhonePresenter> implements ICheckNewPhoneView, ITimeOver {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.bt_send_message)
    AppCompatButton bt_send_message;
    @BindView(R.id.bt_commit)
    AppCompatButton bt_commit;
    @BindView(R.id.et_phone)
    AppCompatEditText et_phone;
    @BindView(R.id.et_code)
    AppCompatEditText et_code;
    private CodeTimeUtils codeTimeUtils;


    @Override
    protected CheckNewPhonePresenter createPresenter() {
        return new CheckNewPhonePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_change_phone;
    }

    @Override
    protected void initView() {
        codeTimeUtils = new CodeTimeUtils(this, 60);
    }

    @Override
    protected void initData() {


    }

    @OnClick({R.id.ll_back, R.id.bt_commit, R.id.bt_send_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_commit:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_commit)) {
                    KeyboardUtils.hideKeyboard(this);
                    commit();
                }
                break;
            case R.id.bt_send_message:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_send_message)) {
                    KeyboardUtils.hideKeyboard(this);
                    sendMessage();
                }
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void sendMessage() {
        String tel;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_phone.getText()).toString())) {
            ToastUtils.show("请您填写手机号");
            return;
        }
        tel = et_phone.getText().toString();

        if (presenter != null) {
            presenter.getVerificationCode(tel, UserUtils.getInstance().getLoginType(),"5");
        }
    }

    /**
     * 提交
     */
    private void commit() {
        String tel, code;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_phone.getText()).toString())) {
            ToastUtils.show("请您填写手机号");
            return;
        }
        tel = et_phone.getText().toString();
        if (TextUtils.isEmpty(Objects.requireNonNull(et_code.getText()).toString())) {
            ToastUtils.show("请您填写验证码");
            return;
        }
        code = et_code.getText().toString();
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
            presenter.checkNewPhone(tel, code, 1);
        }
    }

    /**
     * 检查
     *
     * @param type
     * @param msg
     */
    @Override
    public void checkNewPhoneSuccess(int type, String msg, String phone) {
        closeDialog();
        if (UserUtils.getInstance().isLogin()) {
            UserUtils.getInstance().getLoginBean().setPhone(phone);
        }
        new ChangeUserEvent(UserUtils.getInstance().getLoginBean()).post();
        ActivityManager.finishActivity(this);
        ActivityManager.finishActivity(CheckOldPhoneActivity.class);
    }

    @Override
    public void checkNewPhoneFailure(int type, String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void onSendCodeSuccess(String phone, String msg) {
        ToastUtils.show(msg);
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
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    @Override
    public void onFinish() {
        bt_send_message.setEnabled(true);
        bt_send_message.setText("获取验证码");
    }

    @Override
    public void onTick(long millisUntilFinished) {
        bt_send_message.setEnabled(false);
        bt_send_message.setText("剩余时间 " + millisUntilFinished / 1000);
    }

    @Override
    protected void onDestroy() {
        if (codeTimeUtils != null) {
            codeTimeUtils.cancel();
        }
        super.onDestroy();
    }
}
