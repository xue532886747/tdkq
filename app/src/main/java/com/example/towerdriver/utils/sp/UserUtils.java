package com.example.towerdriver.utils.sp;

import android.text.TextUtils;

import com.example.towerdriver.base_login.bean.UserBean;
import com.google.gson.Gson;

/**
 * @author 53288
 * @description User
 * @date 2021/5/25
 */
public class UserUtils {

    private static final String KEY_LOGIN_BEAN = "KEY_LOGIN_BEAN";

    private UserBean mUserBean = null;

    private static class Holder {
        private static final UserUtils INSTANCE = new UserUtils();
    }

    public static UserUtils getInstance() {
        return Holder.INSTANCE;
    }

    private UserUtils() {
        getLoginBean();
    }

    public UserBean getLoginBean() {
        if (mUserBean == null) {
            String json = SPUtils.getInstance().get(KEY_LOGIN_BEAN, "");
            if (!TextUtils.isEmpty(json)) {
                try {
                    mUserBean = new Gson().fromJson(json, UserBean.class);
                } catch (Exception ignore) {

                }
            }
        }
        return mUserBean;
    }

    public void login(UserBean userBean) {
        mUserBean = userBean;
        String json = new Gson().toJson(userBean);
        SPUtils.getInstance().save(KEY_LOGIN_BEAN, json);
    }

    public void logout() {
        mUserBean = null;
        SPUtils.getInstance().clear();
    }

    public boolean isLogin() {
        UserBean loginBean = getLoginBean();
        if (loginBean == null) {
            return false;
        }
        if (!TextUtils.isEmpty(loginBean.getToken())) {
            return true;
        }
        return false;
    }

    /**
     * 用户token
     *
     * @return
     */
    public String getUserToken() {
        UserBean loginBean = getLoginBean();
        if (loginBean == null) {
            return null;
        }
        return loginBean.getToken();
    }

    /**
     * 登陆类型
     *
     * @return
     */
    public int getLoginType() {
        if (isLogin()) {
            UserBean loginBean = getLoginBean();
            return loginBean.getLogin_type();
        }
        return 0;
    }
}
