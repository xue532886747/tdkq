package com.example.towerdriver.utils.sp;

/**
 * @author 53288
 * @description
 * @date 2021/5/20
 */
public class SettingUtils {
    private static final String SP_NAME = "setting";
    private static final String KEY_DARK_THEME = "KEY_DARK_THEME";
    private static final String KEY_USER_AGREE = "KEY_USER_AGREE";

    private final SPUtils mSPUtils = SPUtils.newInstance(SP_NAME);
    private boolean mSystemTheme = true;
    private boolean mDarkTheme = false;
    private boolean mUserAgree = false;

    private static class Holder {
        private static final SettingUtils INSTANCE = new SettingUtils();
    }

    public static SettingUtils getInstance() {
        return Holder.INSTANCE;
    }

    private SettingUtils() {
        mDarkTheme = mSPUtils.get(KEY_DARK_THEME, mDarkTheme);
        mUserAgree = mSPUtils.get(KEY_USER_AGREE, mUserAgree);
    }

    public void setDarkTheme(boolean darkTheme) {
        mDarkTheme = darkTheme;
        mSPUtils.save(KEY_DARK_THEME, darkTheme);
    }

    public void setUserAgree(boolean mUserAgree) {
        this.mUserAgree = mUserAgree;
        mSPUtils.save(KEY_USER_AGREE, mUserAgree);
    }

    public boolean isUserAgree() {
        return mUserAgree;
    }

    public boolean isDarkTheme() {
        return mDarkTheme;
    }
}
