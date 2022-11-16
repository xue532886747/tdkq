package com.example.towerdriver.base_setting.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_setting.presenter.AccountCancellationPresenter;
import com.example.towerdriver.base_setting.view.AccountCancellation;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.utils.RepeatClickResolveUtil;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 准备注销
 * @date 2021/7/28
 */
public class CancellationActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.tv_content)
    AppCompatTextView tv_content;
    @BindView(R.id.bt_quit)
    AppCompatButton bt_quit;

    @Override
    protected AccountCancellationPresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_cancellation;
    }

    @Override
    protected void initView() {
        String text = "注销账号是不可恢复的操作，请确认该账号相关信息和数据均以妥善备份，账号相关的所有订单、服务均已进行妥善处理。注销账号后，你将无法继续使用该账号或找回该账号添加或绑定的任何内容或信息(即使您使用相同的手机号码)，包括但不限于:(1)你将无法继续使用该账号;\n" +
                "(2)您账号的个人资料、历史信息(包括头像、其他记录、订单、服务、优惠券及积分信息等)都将无法获取或找回:\n" + "(3)在账号注销期间，如果您的账号被他人投诉、被国家机关调查或者整处于诉讼、仲裁程序中，塔电快骑有权自行终止你薄荷账号的注销而无需另行征求您的同意。\n" +
                "\n" +
                "轻按下方「申请注销，按钮，即表示你已阅读并同意以上重要提醒";
        tv_content.setText(text);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.bt_quit, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_quit:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_quit)) {
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
     * 提交申请
     */
    private void commit() {
        new CenterDialog(this).Builder("确定要注销账号?", "确定", "取消").
                setDialogClickListener(new CenterDialog.DialogClickListener() {
                    @Override
                    public void top(int type) {
                        Intent intent = new Intent(CancellationActivity.this,ReallyCancelActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void bottom(int type) {

                    }
                }).show();
    }

}
