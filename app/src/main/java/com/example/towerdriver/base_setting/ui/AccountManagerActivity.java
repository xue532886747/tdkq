package com.example.towerdriver.base_setting.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.utils.RepeatClickResolveUtil;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description
 * @date 2021/7/28
 */
public class AccountManagerActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.cl_cancellation)
    ConstraintLayout cl_cancellation;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_account_manager;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.cl_cancellation, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_cancellation:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_cancellation)) {
                    Intent intent = new Intent(this, CancellationActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_back:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_back)) {
                    finish();
                }
                break;
        }
    }

}
