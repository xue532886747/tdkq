package com.example.towerdriver.base_invite.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_invite.presenter.ChangeInvitePresenter;
import com.example.towerdriver.base_invite.view.IChangeInviteView;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.hjq.toast.ToastUtils;

import java.util.Objects;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 更换联系人
 * @date 2021/8/2
 */
public class ChangeInviteActivity extends BaseActivity<ChangeInvitePresenter> implements IChangeInviteView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.bt_commit)
    AppCompatButton bt_commit;
    @BindView(R.id.et_phone)
    AppCompatEditText et_phone;

    @Override
    protected ChangeInvitePresenter createPresenter() {
        return new ChangeInvitePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_change_invite;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ll_back, R.id.bt_commit})
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
        }
    }

    /**
     * 提交
     */
    private void commit() {
        String tel;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_phone.getText()).toString())) {
            ToastUtils.show("请您填写手机号");
            return;
        }
        tel = et_phone.getText().toString();
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
            presenter.getInviteList(tel);
        }
    }


    @Override
    public void changeInviteSuccess(String msg) {
        closeDialog();
        ToastUtils.show(msg);
        finish();
    }

    @Override
    public void changeInviteFailure(String msg) {
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
}
