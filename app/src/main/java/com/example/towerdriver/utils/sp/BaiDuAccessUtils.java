package com.example.towerdriver.utils.sp;

import android.text.TextUtils;

import com.example.towerdriver.base_authentication.model.bean.BaiduTokenBean;
import com.google.gson.Gson;

/**
 * @author 53288
 * @description 获取百度身份证识别的access_token
 * @date 2021/5/25
 */
public class BaiDuAccessUtils {
    private static final String SP_NAME = "BAIDU_INFO";
    private static final String KEY_BAIDU_ACCESS_BEAN = "KEY_BAIDU_ACCESS_BEAN";

    private BaiduTokenBean baiduTokenBean = null;

    private static class Holder {
        private static final BaiDuAccessUtils INSTANCE = new BaiDuAccessUtils();
    }

    public static BaiDuAccessUtils getInstance() {
        return Holder.INSTANCE;
    }

    private BaiDuAccessUtils() {

        getBaiduTokenBean();
    }

    public BaiduTokenBean getBaiduTokenBean() {
        if (baiduTokenBean == null) {
            String json = SPUtils.getInstance().get(KEY_BAIDU_ACCESS_BEAN, "");
            if (!TextUtils.isEmpty(json)) {
                try {
                    baiduTokenBean = new Gson().fromJson(json, BaiduTokenBean.class);
                } catch (Exception ignore) {
                }
            }
        }
        return baiduTokenBean;
    }

    public void AccessTokenLogin(BaiduTokenBean userBean) {
        baiduTokenBean = userBean;
        String json = new Gson().toJson(userBean);
        SPUtils.getInstance().save(KEY_BAIDU_ACCESS_BEAN, json);
    }

    public void AccessTokenLogout() {
        baiduTokenBean = null;
        SPUtils.getInstance().clear();
    }

    public boolean AccessTokenIsLogin() {
        BaiduTokenBean baiduTokenBean = getBaiduTokenBean();
        if (baiduTokenBean == null) {
            return false;
        } else {
            if ("".equals(baiduTokenBean.getAccess_token())) {
                return false;
            }
            return true;
        }
    }

    public String getAccessToken() {
        BaiduTokenBean baiduTokenBean = getBaiduTokenBean();
        if (baiduTokenBean == null) {
            return "";
        } else {
            Long expires_in = baiduTokenBean.getExpires_in();
            if (System.currentTimeMillis()/ 1000  - expires_in >= 0) { //当前日期大于过期日期的话 就立刻
                AccessTokenLogout();
                return "";
            }
            return baiduTokenBean.getAccess_token();
        }
    }
}
