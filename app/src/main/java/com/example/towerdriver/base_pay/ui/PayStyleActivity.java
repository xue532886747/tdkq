package com.example.towerdriver.base_pay.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_order_list.ui.activity.OrderListActivity;
import com.example.towerdriver.base_pay.model.PayBean;
import com.example.towerdriver.base_pay.model.PayResult;
import com.example.towerdriver.base_pay.model.ZfbBean;
import com.example.towerdriver.base_pay.presenter.PayStylePresenter;
import com.example.towerdriver.base_pay.view.IPayStyleView;
import com.example.towerdriver.base_setmenu.ui.activity.GoodsDetailActivity;
import com.example.towerdriver.base_setmenu.ui.activity.SelectMenuActivity;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.PayEvent;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.ThreadPoolUtil;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 支付方式
 * @date 2021/6/17
 */
public class PayStyleActivity extends BaseActivity<PayStylePresenter> implements IPayStyleView {
    private String rent_id, money, order_sn;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.tv_price)
    AppCompatTextView tv_price;
    @BindView(R.id.ll_pay)
    LinearLayout ll_pay;
    @BindView(R.id.cl_zhifubao)
    ConstraintLayout cl_zhifubao;
    @BindView(R.id.cl_weixin)
    ConstraintLayout cl_weixin;
    @BindView(R.id.iv_zhifubao)
    View iv_zhifubao;
    @BindView(R.id.iv_weixin)
    View iv_weixin;
    private int type = 0;   //0 == 正常, 1 == 支付订单 ,2 == 续租
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                ToastUtils.show("支付成功");
                goToOrderActivity();
            } else if (TextUtils.equals(resultStatus, "8000")) {
                ToastUtils.show("支付结果确认中");
            } else if ("4000".equals(resultStatus)) {
                ToastUtils.show("支付失败，检查支付金额等关键数据");
            } else if ("6001".equals(resultStatus)) {
                ToastUtils.show("支付取消");
            } else if ("6002".equals(resultStatus)) {
                ToastUtils.show("网络异常");
            } else if ("6004".equals(resultStatus)) {
                ToastUtils.show("请联系客服确认支付结果");
            } else {
                ToastUtils.show("支付错误");
            }
        }
    };

    public static void launch(Activity activity, String rent_id, String money, String order_sn, int type) {
        Intent intent = new Intent(activity, PayStyleActivity.class);
        intent.putExtra("rent_id", rent_id);
        intent.putExtra("money", money);
        intent.putExtra("order_sn", order_sn);
        intent.putExtra("type", type);
        if (type == 1) {
            activity.startActivityForResult(intent, 300);
        } else if (type == 2) {
            activity.startActivityForResult(intent, 300);
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected PayStylePresenter createPresenter() {
        return new PayStylePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_pay_style;
    }

    @Override
    protected void initView() {
        rent_id = getIntent().getStringExtra("rent_id");
        money = getIntent().getStringExtra("money");
        order_sn = getIntent().getStringExtra("order_sn");
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    protected void initData() {
        tv_price.setText(money + "元");
        tabSelected(iv_weixin);
    }

    @OnClick({R.id.ll_back, R.id.ll_pay, R.id.cl_zhifubao, R.id.cl_weixin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_pay:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_pay)) {
                    commit();
                }
                break;
            case R.id.cl_zhifubao:
                tabSelected(iv_zhifubao);
                break;
            case R.id.cl_weixin:
                tabSelected(iv_weixin);
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayEvent(PayEvent event) {
        ToastUtils.show(event.getMsg());
        if (event.getCode() == 0) {
            goToOrderActivity();
        }
    }

    /**
     * 跳转至订单的activity
     */
    private void goToOrderActivity() {
        if (type == 0) {
            ActivityManager.finishActivity(SelectMenuActivity.class);
            ActivityManager.finishActivity(GoodsDetailActivity.class);
            finish();
            OrderListActivity.launch(this);
            LogUtils.d("type = " + type);
        } else if (type == 1) {
            LogUtils.d("type = " + type);
            finish();
        } else if (type == 2) {
            LogUtils.d("type = " + type);
            ActivityManager.finishActivity(GoodsDetailActivity.class);
            finish();
        }
    }

    /**
     * 提交
     */
    private void commit() {
        if (iv_weixin.isSelected()) {
            if (presenter != null) {
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
                if (type == 0 || type == 1) {
                    presenter.getWxPay(order_sn);
                } else {
                    presenter.getWxNewPay(order_sn);
                }
            }
        } else {
            if (presenter != null) {
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
                if (type == 0 || type == 1) {
                    presenter.getZfbPay(order_sn);
                } else {
                    presenter.getZfbNewPay(order_sn);
                }
            }
        }
    }

    /**
     * 选中变色
     *
     * @param view
     */
    private void tabSelected(View view) {
        iv_zhifubao.setSelected(false);
        iv_weixin.setSelected(false);
        view.setSelected(true);
    }

    @Override
    public void onPayWxSuccess(PayBean payBean, String msg) {
        closeDialog();
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constant.WxAppId, false);   //填写自己的APPIDapi.registerApp("wxAPPID");
        String appId = payBean.getAppid();
        String partnerId = payBean.getPartnerid();
        String prepayId = payBean.getPrepayid();
        String packageValue = payBean.getPackageX();
        String nonceStr = payBean.getNoncestr();
        String timeStamp = String.valueOf(payBean.getTimestamp());
        String sign = payBean.getSign();
        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.packageValue = packageValue;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.sign = sign;
        boolean result = api.sendReq(req);
    }


    /**
     * @param orderInfo 接口返回的订单信息
     */
    protected void pay(final String orderInfo) {
        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayStyleActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Set<Map.Entry<String, String>> entries = result.entrySet();
                Iterator<Map.Entry<String, String>> iterator = entries.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> next = iterator.next();
                    String key = next.getKey();
                    String value = next.getValue();
                    LogUtils.d("key = " + key + " ,value = " + value);
                }
                Message msg = Message.obtain();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        });
    }


    @Override
    public void onPayWxFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void onPayZfbSuccess(ZfbBean zfbBean, String msg) {
        closeDialog();
        String alipay = zfbBean.getAlipay();
        if (!TextUtils.isEmpty(msg)) {
            pay(alipay);
        }
    }

    @Override
    public void onPayZfbFailure(String msg) {
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
