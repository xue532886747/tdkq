package com.example.towerdriver.base_login.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_forget.ui.activity.ForgetPassActivity;
import com.example.towerdriver.base_login.presenter.LoginPresenter;
import com.example.towerdriver.base_login.view.LoginView;
import com.example.towerdriver.base_main.ui.activity.MainActivity;
import com.example.towerdriver.base_register.ui.activity.RegisterActivity;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.repair.base.activity.RepairMainActivity;
import com.example.towerdriver.staff.base.activity.StaffMainActivity;
import com.example.towerdriver.station.base.activity.StationMainActivity;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.MyTool;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.SettingUtils;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.utils.tools.StatusBarUtil;
import com.example.towerdriver.webview.MyWebViewActivity;
import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * @author 53288
 * @description 登录
 * @date 2021/5/19
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView, TextWatcher {

    @BindView(R.id.bt_load)
    AppCompatButton bt_load;                 //登陆
    @BindView(R.id.textView3)
    AppCompatTextView textView3;
    @BindView(R.id.tv_title)
    AppCompatTextView tv_title;              //标题
    @BindView(R.id.bt_switch)
    AppCompatButton bt_switch;               //切换
    @BindView(R.id.tv_register)
    AppCompatTextView tv_register;
    @BindView(R.id.tv_forget_pass)
    AppCompatTextView tv_forget_pass;
    @BindView(R.id.et_account)
    AppCompatEditText et_account;            //账号
    @BindView(R.id.et_password)
    AppCompatEditText et_password;           //密码
    @BindView(R.id.et_tel)
    AppCompatEditText et_tel;                //手机验证码
    @BindView(R.id.iv_account_delete)
    AppCompatImageView iv_account_delete;    //删除账号
    @BindView(R.id.iv_password_delete)
    AppCompatImageView iv_password_delete;   //删除密码
    @BindView(R.id.iv_tel_delete)
    AppCompatImageView iv_tel_delete;        //删除电话
    @BindView(R.id.cl_pass_login)
    ConstraintLayout cl_pass_login;
    @BindView(R.id.cl_tel)
    ConstraintLayout cl_tel;                 //输入电话分组
    @BindView(R.id.cl_parent)
    ConstraintLayout cl_parent;
    @BindView(R.id.rg_item)
    RadioGroup rg_item;                     //登陆的端
    @BindView(R.id.tv_user)
    AppCompatTextView tv_user;
    @BindView(R.id.tv_privacy)
    AppCompatTextView tv_privacy;
    @BindView(R.id.tv_else)
    AppCompatTextView tv_else;
    @BindView(R.id.check_box)
    AppCompatCheckBox check_box;
    private boolean isTel;                   //是否是验证码登陆
    private int loginType = 1;
    private int LoginItem = 1;
    private int mLastLoginType = 0;                  //记录上次登陆的状态
    private long clickTime = 0; //记录第一次点击的时间


    public static void launch(Activity activity, int lastLoginType) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("lastLoginType", lastLoginType);
        activity.startActivity(intent);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_login;
    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    protected void initView() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.background), 0);
        if (!SettingUtils.getInstance().isDarkTheme()) {
            StatusBarUtil.setLightMode(this);
        }
        mLastLoginType = getIntent().getIntExtra("lastLoginType", 0);
        et_account.requestFocus();
        et_account.addTextChangedListener(this);
        et_password.addTextChangedListener(this);
        et_tel.addTextChangedListener(this);
        rg_item.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_client:
                        LoginItem = 1;
                        break;
                    case R.id.rb_staff:
                        LoginItem = 2;
                        break;
                    case R.id.rb_stationmaster:
                        LoginItem = 3;
                        break;
                    case R.id.rb_location:
                        LoginItem = 4;
                        break;
                }
            }
        });
        check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

    }

    @Override
    protected void initData() {
        tv_user.setText("用户协议");
        tv_privacy.setText("隐私协议");
        tv_else.setText("租赁协议");
        checkLocationPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA);
    }

    @OnClick({R.id.bt_load, R.id.bt_switch, R.id.tv_register, R.id.tv_forget_pass,
            R.id.iv_account_delete, R.id.iv_password_delete, R.id.iv_tel_delete,
            R.id.tv_user, R.id.tv_privacy, R.id.tv_else})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_switch:
                isTel = !isTel;
                switchMode();
                break;
            case R.id.tv_register:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.tv_register)) {
                    Intent intent = new Intent(this, RegisterActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.bt_load:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_load)) {
                    if (loginType == 1)
                        UserLogin();
                    if (loginType == 2)
                        getVerificationCode();
                }
                KeyboardUtils.hideKeyboard(this);
                break;
            case R.id.tv_forget_pass:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.tv_forget_pass)) {
                    ForgetPassActivity.launch(this, LoginItem);
                }
                KeyboardUtils.hideKeyboard(this);
                break;
            case R.id.iv_account_delete:
                if (et_account != null) {
                    clearEditText(et_account);
                }
                break;
            case R.id.iv_password_delete:
                if (et_password != null) {
                    clearEditText(et_password);
                }
                break;
            case R.id.iv_tel_delete:
                if (et_tel != null) {
                    clearEditText(et_tel);
                }
                break;

            case R.id.tv_user:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.tv_user)) {
                    MyWebViewActivity.launch(this, false, "用户协议", Constant.PRIVACY_AGREEMENT);
                }
                break;

            case R.id.tv_privacy:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.tv_privacy)) {
                    MyWebViewActivity.launch(this, false, "隐私协议", Constant.USER_PRIVACY);
                }
                break;
            case R.id.tv_else:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.tv_else)) {
                    MyWebViewActivity.launch(this, false, "租赁协议", Constant.LEASE_AGREEMENT);
                }
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getVerificationCode() {
        String tel;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_tel.getText()).toString())) {
            ToastUtils.show("请您填写手机号");
            return;
        }
        tel = et_tel.getText().toString();
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在发送...");
            presenter.getVerificationCode(tel, LoginItem, "2");
        }
    }

    /**
     * 用户登陆
     */
    private void UserLogin() {
        String account, pass;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_account.getText()).toString())) {
            ToastUtils.show("请输入您的账号");
            return;
        }
        account = et_account.getText().toString();
        if (TextUtils.isEmpty(Objects.requireNonNull(et_password.getText()).toString())) {
            ToastUtils.show("请输入您的密码");
            return;
        }
        pass = et_password.getText().toString();
        if (!check_box.isChecked()) {
            ToastUtils.show("请同意协议");
            check_box.startAnimation(MyTool.shakeAnimation(2));
            Vibrator vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(500);
            return;
        }
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在登录...");
            presenter.sendLogin(account, pass, loginType, LoginItem);
        }
    }

    /**
     * 切换登录模式
     */
    private void switchMode() {
        if (isTel) {
            cl_tel.setVisibility(View.VISIBLE);
            cl_pass_login.setVisibility(View.GONE);
            textView3.setVisibility(View.VISIBLE);
            bt_switch.setText("账号登录");
            tv_title.setText("验证码登录");
            bt_load.setText("获取短信验证码");
            loginType = 2;
        } else {
            cl_tel.setVisibility(View.GONE);
            cl_pass_login.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.GONE);
            bt_switch.setText("验证码登录");
            tv_title.setText("账号登录");
            bt_load.setText("登录");
            loginType = 1;
        }
        KeyboardUtils.hideKeyboard(this);
    }

    /**
     * 清除editText的值
     *
     * @param editText
     */
    public void clearEditText(AppCompatEditText editText) {
        Objects.requireNonNull(editText.getText()).clear();
    }

    /**
     * 检查editText是否为null
     *
     * @param view
     */
    public void checkEditText(View view) {
        switch (view.getId()) {
            case R.id.et_account:
                if (TextUtils.isEmpty(Objects.requireNonNull(et_account.getText()).toString())) {
                    changeImageView(iv_account_delete, false, 1);
                } else {
                    changeImageView(iv_account_delete, true, 1);
                }
                break;
            case R.id.et_password:
                if (TextUtils.isEmpty(Objects.requireNonNull(et_password.getText()).toString())) {
                    changeImageView(iv_password_delete, false, 1);
                } else {
                    changeImageView(iv_password_delete, true, 1);
                }
                break;
            case R.id.et_tel:
                if (TextUtils.isEmpty(Objects.requireNonNull(et_tel.getText()).toString())) {
                    changeImageView(iv_tel_delete, false, 2);
                } else {
                    changeImageView(iv_tel_delete, true, 2);
                }
                break;
        }
    }

    /**
     * 删除按钮的变化
     *
     * @param imageView
     */
    public void changeImageView(AppCompatImageView imageView, boolean isVisible, int type) {
        if (isVisible && type == loginType) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkEditText(et_account);
        checkEditText(et_password);
        checkEditText(et_tel);
    }


    /**
     * 登陆成功
     *
     * @param msg
     * @param type 返回走哪个端
     */
    @Override
    public void LoginSuccess(String msg, int type) {
        MMKV mmkv2 = MMKV.mmkvWithID("id");
        if (mmkv2!=null) {
            mmkv2.encode("Type", type);
        }
        closeDialog();
        ToastUtils.show("登录成功");
        if (mLastLoginType != 0) {         //是否是登陆过期引起的登陆,如果不是直接跳转主页，如果是就关闭当前登录页
            if (mLastLoginType != type) {
                ActivityManager.finishAllActivity();
                if (type == 1) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else if (type == 2) {
                    Intent intent = new Intent(this, StationMainActivity.class);
                    startActivity(intent);
                } else if (type == 3) {
                    Intent intent = new Intent(this, RepairMainActivity.class);
                    startActivity(intent);
                } else if (type == 4) {
                    Intent intent = new Intent(this, StaffMainActivity.class);
                    startActivity(intent);
                }

            }
        } else {
            LogUtils.d("测试正常退出引起的");
            if (type == 1) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else if (type == 2) {
                Intent intent = new Intent(this, StationMainActivity.class);
                startActivity(intent);
            } else if (type == 3) {
                Intent intent = new Intent(this, RepairMainActivity.class);
                startActivity(intent);
            } else if (type == 4) {
                Intent intent = new Intent(this, StaffMainActivity.class);
                startActivity(intent);
            }
        }
        if (type == 1) {
            Set<String> tags = new HashSet<>();
            tags.add("user");
            JPushInterface.addTags(this, 0, tags);
            int l = (int) System.currentTimeMillis();
            JPushInterface.setAlias(this, l, UserUtils.getInstance().getLoginBean().getMember_id() + "");
        }
        finish();

    }


    /**
     * 登陆失败
     *
     * @param msg
     */
    @Override
    public void LoginFailed(String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    /**
     * 获取验证码成功
     *
     * @param msg
     */
    @Override
    public void onSendCodeSuccess(String phone, String msg) {
        closeDialog();
        if (mLastLoginType != 0) {          //是否是登陆过期
            VerificationActivity.launch(this, phone, LoginItem, mLastLoginType);
        } else {                //如果不是登陆过期
            VerificationActivity.launch(this, phone, LoginItem, 0);
        }
    }

    /**
     * 获取验证码失败
     *
     * @param msg
     */
    @Override
    public void onSendCodeFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void LoadingClose() {

    }

    @Override
    public void showFailed(int code, String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    /**
     * 退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - clickTime) > 2000) {
                ToastUtils.show("再按一次退出塔电快骑!");
                clickTime = System.currentTimeMillis();
            } else {
                ActivityManager.finishAllActivity();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
