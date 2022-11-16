package com.example.towerdriver.base_rescue.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.example.base_ui.bigimageview.tool.ui.ToastUtil;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_order_list.ui.activity.OrderDetailActivity;
import com.example.towerdriver.base_order_list.ui.activity.OrderListActivity;
import com.example.towerdriver.base_rescue.presenter.RescueCommentPresenter;
import com.example.towerdriver.base_rescue.view.IRescueCommentView;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;

import java.util.Objects;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * @author 53288
 * @description 救援评价
 * @date 2021/6/21
 */
public class RescueCommentActivity extends BaseActivity<RescueCommentPresenter> implements IRescueCommentView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.bt_add)
    AppCompatButton bt_add;
    @BindView(R.id.star)
    MaterialRatingBar mStar;
    @BindView(R.id.tv_point)
    AppCompatTextView tv_point;
    @BindView(R.id.et_content)
    AppCompatEditText et_content;
    private String rescue_id;
    private int position;

    public static void launch(Activity activity, String rescue_id, int position) {
        Intent intent = new Intent(activity, RescueCommentActivity.class);
        intent.putExtra("rescue_id", rescue_id);
        intent.putExtra("position", position);
        activity.startActivityForResult(intent, 400);
    }

    @Override
    protected RescueCommentPresenter createPresenter() {
        return new RescueCommentPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_rescue_comment;
    }

    @Override
    protected void initView() {
        rescue_id = getIntent().getStringExtra("rescue_id");
        position = getIntent().getIntExtra("position", 0);
        mStar.setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                LogUtils.d("rating = " + rating);
                checkStar();
            }
        });
        mStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                LogUtils.d("rating = " + rating + ",fromUser = " + fromUser);
                if (fromUser && rating < 1.0) {
                    mStar.setRating(1.0f);
                }
            }
        });
        checkStar();
    }

    @Override
    protected void initData() {

    }

    /**
     * 检查评价
     */
    private void checkStar() {
        switch ((int) mStar.getRating()) {
            case 0:
                tv_point.setText(null);
                break;
            case 1:
                tv_point.setText("非常差");
                break;
            case 2:
                tv_point.setText("差");
                break;
            case 3:
                tv_point.setText("一般");
                break;
            case 4:
                tv_point.setText("好");
                break;
            case 5:
                tv_point.setText("非常好");
                break;
        }
    }

    @OnClick({R.id.ll_back, R.id.bt_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.bt_add:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_add)) {
                    KeyboardUtils.hideKeyboard(this);
                    commit();
                }
                break;
        }
    }

    /**
     * 提交评价
     */
    private void commit() {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
            presenter.getRescueComment(rescue_id, String.valueOf((int) mStar.getRating()), Objects.requireNonNull(et_content.getText()).toString());
        }
    }

    @Override
    public void rescueCommentSuccess(String msg) {
        ToastUtils.show("评价成功!");
        closeDialog();
        Intent intent = new Intent(this, OrderListActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("status", "5");
        intent.putExtra("status_name", "已评价");
        setResult(401, intent);
        finish();
    }

    @Override
    public void rescueCommentFailure(String msg) {
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
