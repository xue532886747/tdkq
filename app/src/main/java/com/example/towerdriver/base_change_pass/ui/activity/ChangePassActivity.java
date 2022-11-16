package com.example.towerdriver.base_change_pass.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.base_ui.bigimageview.tool.ui.ToastUtil;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_change_pass.presenter.ChangePassPresenter;
import com.example.towerdriver.base_change_pass.view.IChangePassView;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.hjq.toast.ToastUtils;

import java.util.Objects;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 修改密码
 * @date 2021/6/3
 */
public class ChangePassActivity extends BaseActivity<ChangePassPresenter> implements IChangePassView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.et_tel)
    AppCompatEditText et_tel;
    @BindView(R.id.et_pass)
    AppCompatEditText et_pass;
    @BindView(R.id.et_pass_again)
    AppCompatEditText et_pass_again;
    @BindView(R.id.bt_load)
    AppCompatButton bt_load;

    @Override
    protected ChangePassPresenter createPresenter() {
        return new ChangePassPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_change_pass;
    }

    @Override
    protected void initView() {

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
                    KeyboardUtils.hideKeyboard(this);
                    commit();
                }
                break;
        }
    }

    /**
     * 提交
     */
    private void commit() {
        String old_pass;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_tel.getText()).toString())) {
            ToastUtils.show("请您输入旧密码");
            return;
        }
        old_pass = et_tel.getText().toString();

        String new_pass;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_pass.getText()).toString())) {
            ToastUtils.show("请您输入新密码");
            return;
        }
        new_pass = et_pass.getText().toString();

        String re_pass;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_pass_again.getText()).toString())) {
            ToastUtils.show("请您再次输入新密码");
            return;
        }
        re_pass = et_pass_again.getText().toString();
        int type = 1;
        if (UserUtils.getInstance().isLogin()) {
            type = UserUtils.getInstance().getLoginType();
        }
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
            presenter.changePassNum(type, old_pass, new_pass, re_pass);
        }
    }

    /**
     * 修改密码成功
     *
     * @param type
     * @param msg
     */
    @Override
    public void changePassWordSuccess(int type, String msg) {
        closeDialog();
        ToastUtils.show(msg);
        finish();
    }

    /**
     * 修改密码失败
     *
     * @param type
     * @param msg
     */
    @Override
    public void changePassFailure(int type, String msg) {
        closeDialog();
        ToastUtils.show(msg);
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
