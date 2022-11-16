package com.example.towerdriver.base_welcome.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.base_ui.bigimageview.ImagePreview;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_login.ui.activity.LoginActivity;
import com.example.towerdriver.base_main.ui.activity.MainActivity;
import com.example.towerdriver.base_register.view.IRegisterView;
import com.example.towerdriver.dialog.UserDialog;
import com.example.towerdriver.repair.base.activity.RepairMainActivity;
import com.example.towerdriver.staff.base.activity.StaffMainActivity;
import com.example.towerdriver.station.base.activity.StationMainActivity;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.SettingUtils;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.timeutil.CodeTimeUtils;
import com.example.towerdriver.utils.timeutil.ITimeOver;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.utils.tools.StatusBarUtil;
import com.example.towerdriver.webview.MyWebViewActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 欢迎页面
 * @date 2021/6/2
 */
public class WelComeActivity extends BaseActivity implements ITimeOver {
    @BindView(R.id.bt_skip)
    AppCompatButton bt_skip;
    private CodeTimeUtils codeTimeUtils;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
//        if (!SettingUtils.getInstance().isDarkTheme()) {
//            StatusBarUtil.setLightMode(this);
//        }
        StatusBarUtil.setLightMode(this);
        return R.layout.act_welcome;
    }

    @Override
    protected void initView() {
        String text = "感谢您使用塔电快骑!\n" +
                "我们非常重视您的个人信息和隐私保护。依据最新法律要求，我们更新了《隐私政策》。\n" +
                "为向您提供更好的旅行服务,在使用我们的产品前,请您阅读完整版《租赁协议》和《用户协议》的所有条款,包括:\n" +
                "1、为向您提供包括账户注册、交易支付在内的基本功能,我们可能会基于具体业务场景收集您的个人信息;\n" +
                "2、我们会基于您的授权来为您提供更好的服务,这些授权包括定位(为您精确推荐附近的优质资源)、设备信息(为保障账户、交易安全及补偿GPS信号不佳时的定位数据,获取包括IMEL,IMS在内的设备标识符)、存储空间(减少重复加载节省您的流量)您有权拒绝或取消」IMEL,IMS在内的设备标识符)、存储空间(减少重复加载,节省您的流量),您有权拒绝或取消这些授权;3、我们会基于先进的技术和管理措施保护您个人信息的安全;4、未经您的同意,我们不会将您的个人信息共享给第三方;5、为向您提供更好的塔电快骑会员服务,您同意提供及时、详尽及准确的个人资料;6、您在享用塔电快骑会员服务的同时,授权并同意接受塔电向您的电子邮件、手机、通信地址等发送商业信息,包括不限于最新的塔电产品信息、促销信息等。若您选择不接受塔电提供的各类信息服务,您可以按照塔电提供的相应设置拒绝该类信息服务。";
        if (SettingUtils.getInstance().isUserAgree()) {
            codeTimeUtils = new CodeTimeUtils(this, 1);
            codeTimeUtils.start();
        } else {
            LogUtils.d(SettingUtils.getInstance().isUserAgree() + "");
            UserDialog userDialog = new UserDialog(this).Builder(text).setDialogClickListener(new UserDialog.DialogClickListener() {
                @Override
                public void getAgreement() {
                    SettingUtils.getInstance().setUserAgree(true);
                    goToActivity();
                }

                @Override
                public void getDisAgree() {
                    SettingUtils.getInstance().setUserAgree(false);
                    finish();
                }

                @Override
                public void checkUserAgreement() {
                    MyWebViewActivity.launch(WelComeActivity.this, false, "隐私协议", Constant.USER_PRIVACY);
                }

                @Override
                public void checkServiceAgreement() {
                    MyWebViewActivity.launch(WelComeActivity.this, false, "用户协议", Constant.PRIVACY_AGREEMENT);
                }

                @Override
                public void checkZuLinAgreement() {
                    MyWebViewActivity.launch(WelComeActivity.this, false, "租赁协议", Constant.LEASE_AGREEMENT);
                }
            }).show();
        }
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.bt_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_skip:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_skip)) {
                    goToActivity();
                }
                break;
        }
    }

    /**
     * 跳转页面
     */
    private void goToActivity() {
        if (UserUtils.getInstance().isLogin()) {    //如果登陆了
            int loginType = UserUtils.getInstance().getLoginType();
            if (loginType == 1) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (loginType == 2) {
                Intent intent = new Intent(this, StationMainActivity.class);
                startActivity(intent);
            } else if (loginType == 3) {
                Intent intent = new Intent(this, RepairMainActivity.class);
                startActivity(intent);
            } else if (loginType == 4) {
                Intent intent = new Intent(this, StaffMainActivity.class);
                startActivity(intent);
            }
        } else {
            LoginActivity.launch(this, 0);
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            this.overridePendingTransition(com.example.base_ui.R.anim.fade_in, com.example.base_ui.R.anim.fade_out);
        }
        finish();
    }


    @Override
    public void onFinish() {
        goToActivity();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        bt_skip.setText("跳过 " + (millisUntilFinished / 1000 + 1));
    }

    @Override
    protected void onDestroy() {
        if (codeTimeUtils != null) {
            codeTimeUtils.cancel();
        }
        super.onDestroy();
    }
}
