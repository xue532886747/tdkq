package com.example.towerdriver.base_setting.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.base_ui.bigimageview.tool.ui.ToastUtil;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_login.ui.activity.LoginActivity;
import com.example.towerdriver.base_setting.presenter.AccountCancellationPresenter;
import com.example.towerdriver.base_setting.view.AccountCancellation;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.hjq.toast.ToastUtils;

import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * @author 53288
 * @description 真正注销
 * @date 2021/7/28
 */
public class ReallyCancelActivity extends BaseActivity<AccountCancellationPresenter> implements AccountCancellation {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.et_pass)
    AppCompatEditText et_pass;
    @BindView(R.id.bt_quit)
    AppCompatButton bt_quit;

    @Override
    protected AccountCancellationPresenter createPresenter() {
        return new AccountCancellationPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_really_cancel;
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.bt_quit, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_quit:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_quit)) {
                    KeyboardUtils.hideKeyboard(this);
                    commit();
                }
                break;
            case R.id.ll_back:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_back)) {
                    finish();
                }
                break;
        }
    }

    /**
     * 发送验证码
     */
    private void commit() {
        if (TextUtils.isEmpty(et_pass.getText().toString())) {
            ToastUtils.show("请输入账户密码");
            return;
        }
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
            presenter.userAccountCancel(et_pass.getText().toString());
        }
    }

    @Override
    public void cancellationSuccess(String msg) {
        closeDialog();
        ToastUtils.show("提交成功");
        Set<String> tags = new HashSet<>();
        tags.add("user");
        JPushInterface.deleteTags(this, 0, tags);
        UserUtils.getInstance().logout();
        ActivityManager.finishAllActivity();
        LoginActivity.launch(this, 0);
    }

    @Override
    public void cancellationFailure(String msg) {
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
    }
}
