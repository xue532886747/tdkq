package com.example.towerdriver.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lazyfragment.LazyFragment;
import com.example.towerdriver.base_login.ui.activity.LoginActivity;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.utils.sp.UserUtils;
import com.hjq.toast.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.towerdriver.Constant.TOKEN_EXPIRED;

/**
 * @author 53288
 * @description fragment的基类，是用懒加载
 * @date 2021/5/31
 */
public abstract class BaseFragment<P extends BasePresenter> extends LazyFragment implements BaseView {

    private Unbinder unbinder;
    protected P presenter;
    protected Context mContext;
    protected Dialog mWeiboDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getRootView() != null) {
            unbinder = ButterKnife.bind(this, getRootView());
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
        presenter = createPresenter();
        initView();
        initData();
    }

    /**
     * 是否注册事件分发，默认不绑定
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    /**
     * 获取布局资源文件
     *
     * @return int
     */
    @Override
    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract P createPresenter();

    protected abstract void initView();

    protected abstract void initData();


    /**
     * 登陆过期，前往登录页
     */
    public void goToLogin() {
        closeDialog();
        ToastUtils.show(TOKEN_EXPIRED);
        if (UserUtils.getInstance().getLoginBean() != null) {
            int login_type = UserUtils.getInstance().getLoginBean().getLogin_type();
            UserUtils.getInstance().logout();
            new LoginEvent(false, login_type).post();
            LoginActivity.launch(getActivity(), login_type);
        } else {
            new LoginEvent(false, 0).post();
            LoginActivity.launch(getActivity(), 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        //销毁时，解除绑定
        if (presenter != null) {
            presenter.detachView();
        }
        mWeiboDialog = null;
        closeDialog();
    }


    /**
     * 关闭弹窗
     */
    protected void closeDialog() {
        if (mWeiboDialog != null && mWeiboDialog.isShowing()) {
            WeiboDialogUtils.closeDialog(mWeiboDialog);
        }
    }
}
