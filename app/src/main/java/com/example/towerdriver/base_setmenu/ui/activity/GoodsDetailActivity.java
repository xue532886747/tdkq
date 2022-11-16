package com.example.towerdriver.base_setmenu.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_authentication.ui.activity.FaceLivenessExpActivity;
import com.example.towerdriver.base_driver.ui.activity.DriverReleaseActivity;
import com.example.towerdriver.base_pay.ui.PayStyleActivity;
import com.example.towerdriver.base_setmenu.model.GoodsDetailBean;
import com.example.towerdriver.base_setmenu.presenter.GoodsDetailPresenter;
import com.example.towerdriver.base_setmenu.view.IGoodsDetailView;
import com.example.towerdriver.dialog.BuyDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.sp.SettingUtils;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.utils.tools.StatusBarUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 产品详情
 * @date 2021/6/10
 */
public class GoodsDetailActivity extends BaseActivity<GoodsDetailPresenter> implements IGoodsDetailView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String rent_id;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.iv_head_image)
    AppCompatImageView iv_head_image;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    private SmartRefreshUtils mSmartRefreshUtils;
    @BindView(R.id.tv_fee)
    AppCompatTextView tv_fee;
    @BindView(R.id.tv_day)
    AppCompatTextView tv_day;
    @BindView(R.id.tv_month)
    AppCompatTextView tv_month;
    @BindView(R.id.tv_name)
    AppCompatTextView tv_name;
    @BindView(R.id.tv_content)
    AppCompatTextView tv_content;
    @BindView(R.id.tv_yajin)
    AppCompatTextView tv_yajin;             //押金
    @BindView(R.id.tv_money)
    AppCompatTextView tv_money;             //付款金额
    @BindView(R.id.et_number)
    AppCompatTextView tb_number;            //数量
    @BindView(R.id.iv_good_add)
    AppCompatImageView iv_add;              //增加
    @BindView(R.id.iv_sub)
    AppCompatImageView iv_sub;              //减少
    @BindView(R.id.bt_commit)
    AppCompatButton bt_commit;              //生成订单
    @BindView(R.id.textView21)
    AppCompatTextView tv_jieday;
    private String old_number = "1";
    private boolean isPay = true;
    private String order_id;


    public static void launch(Activity activity, String rent_id, boolean isPay, String order_id, View view) {
        Intent intent = new Intent(activity, GoodsDetailActivity.class);
        intent.putExtra("rent_id", rent_id);
        intent.putExtra("isPay", isPay);
        intent.putExtra("order_id", order_id);
        if (view != null) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity, view, "major_image").toBundle());
        } else {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }

    }

    @Override
    protected GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_good_detail;
    }

    @Override
    protected void initView() {
        rent_id = getIntent().getStringExtra("rent_id");
        isPay = getIntent().getBooleanExtra("isPay", true);
        order_id = getIntent().getStringExtra("order_id");
        toolbar.bringToFront();
        tb_number.setText(old_number);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        if (isPay) {
            bt_commit.setText("立即购买");
            tv_jieday.setText("购买天数");
        } else {
            tv_jieday.setText("续费天数");
            bt_commit.setText("立即续费");
        }

    }

    /**
     * 下拉刷新
     */
    private void refreshList() {
        if (presenter != null) {
            presenter.getGoodsDetail(rent_id);
        }
        getPrice(Integer.parseInt(Objects.requireNonNull(tb_number.getText()).toString()));
    }

    @Override
    protected void initData() {

        refreshList();
    }

    @OnClick({R.id.ll_back, R.id.iv_good_add, R.id.iv_sub, R.id.bt_commit, R.id.et_number})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_good_add:   //增加数量
                KeyboardUtils.hideKeyboard(this);
                addNumber();
                break;
            case R.id.iv_sub:   //增加数量
                KeyboardUtils.hideKeyboard(this);
                subNumber();
                break;
            case R.id.bt_commit:   //增加数量
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_commit)) {
                    KeyboardUtils.hideKeyboard(this);
                    createOrder();
                }
                break;
            case R.id.et_number:   //选择数量的弹窗
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.et_number)) {
                    createDialog();
                }
                break;
        }
    }

    /**
     * 弹窗
     */
    private void createDialog() {
        BuyDialog dialog = new BuyDialog(this).Builder(old_number, "修改购买天数").setDialogClickListener(new BuyDialog.DialogClickListener() {
            @Override
            public void cancle() {

            }

            @Override
            public void confirm(String number) {
                if (!TextUtils.isEmpty(number)) {
                    getPrice(Integer.parseInt(number));
                }
            }
        });
        dialog.show();
    }

    /**
     * 增加数量
     */
    private void addNumber() {
        int number_value = Integer.parseInt(Objects.requireNonNull(tb_number.getText()).toString());
        if (number_value >= 1000) {
            ToastUtils.show("不能在加了");
            return;
        }
        number_value += 1;
        getPrice(number_value);
    }

    /**
     * 减少数量
     */
    private void subNumber() {
        int number_value = Integer.parseInt(Objects.requireNonNull(tb_number.getText()).toString());
        if (number_value <= 1) {
            ToastUtils.show("不能在减了");
            return;
        }
        number_value -= 1;
        getPrice(number_value);
    }

    /**
     * 计算价格
     */
    private void getPrice(int number) {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在计算...");
            if (isPay) {
                presenter.getGoodsPrice(rent_id, String.valueOf(number));
            } else {
                presenter.getGoodsComputePrice(order_id, String.valueOf(number));
            }
        }
    }

    /**
     * 生成订单或立即续费
     */
    private void createOrder() {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在生成订单...");
            if (isPay) {
                presenter.getCreateOrder(rent_id, Objects.requireNonNull(tb_number.getText()).toString());
            } else {
                presenter.getComputeOrder(order_id, Objects.requireNonNull(tb_number.getText()).toString());
            }
        }
    }

    /**
     * 商品详情页成功
     *
     * @param goodsDetailBean
     */
    @Override
    public void onGoodsDetailSuccess(GoodsDetailBean goodsDetailBean) {
        closeDialog();
        loadingSuccessOrFailure(1);
        String image = goodsDetailBean.getImage();
        Glide.with(this).load(image).placeholder(R.mipmap.log_image_bg)
                .error(R.mipmap.log_image_bg).into(iv_head_image);
        tv_name.setText(goodsDetailBean.getName() + "");
        tv_content.setText(goodsDetailBean.getDescribe() + "");
        tv_month.setText(goodsDetailBean.getMonth_price() + "");
        tv_day.setText(goodsDetailBean.getRent_price() + "");
        tv_fee.setText(goodsDetailBean.getOverdue_price() + "");
        tv_yajin.setText(goodsDetailBean.getCash_price() + "");
    }

    @Override
    public void onGoodsDetailFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        loadingSuccessOrFailure(2);
    }

    /**
     * 得到价格计算结果
     *
     * @param price
     */
    @Override
    public void onGoodsPriceSuccess(String price, String number) {
        closeDialog();
        tv_money.setText(price + " 元");
        old_number = number;
        tb_number.setText(number);
    }

    @Override
    public void onGoodsPriceFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
        tb_number.setText(old_number);
    }

    /**
     * 生成订单
     *
     * @param order_sn
     * @param price
     */
    @Override
    public void onCreateOrderSuccess(String order_sn, String price) {
        closeDialog();
        if (isPay) {
            PayStyleActivity.launch(this, rent_id, price, order_sn, 0);
        } else {
            PayStyleActivity.launch(this, rent_id, price, order_sn, 2);
        }
    }

    @Override
    public void onCreateOrderFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    @Override
    public void LoadingClose() {
        loadingSuccessOrFailure(2);
        goToLogin();
        tb_number.setText(old_number);
    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        loadingSuccessOrFailure(2);
        ToastUtils.show(msg);
        tb_number.setText(old_number);
    }


    /**
     * 根据返回的状态判断显示空还是列表
     *
     * @param loading_type
     */
    private void loadingSuccessOrFailure(int loading_type) {
        closeDialog();
        if (loading_type == 1) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.success();
            }

        } else if (loading_type == 2) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.fail();
            }
        }
    }
}
