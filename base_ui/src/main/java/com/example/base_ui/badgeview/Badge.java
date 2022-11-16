package com.example.base_ui.badgeview;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @author 53288
 * @description
 * @date 2021/5/31
 */
public interface Badge {

    /**
     * 设置Badge数字
     *
     * @param badgeNum
     * @return
     */
    Badge setBadgeNumber(int badgeNum);

    int getBadgeNumber();

    /**
     * 设置Badge文本
     *
     * @param badgeText
     * @return
     */
    Badge setBadgeText(String badgeText);

    String getBadgeText();

    /**
     * 设置是否显示精确模式数值
     *
     * @param isExact
     * @return
     */
    Badge setExactMode(boolean isExact);

    boolean isExactMode();

    /**
     * 设置是否显示阴影
     *
     * @param showShadow
     * @return
     */
    Badge setShowShadow(boolean showShadow);

    boolean isShowShadow();

    /**
     * 设置背景色
     *
     * @param color
     * @return
     */
    Badge setBadgeBackgroundColor(int color);

    /**
     * 描边
     *
     * @param color
     * @param width
     * @param isDpValue
     * @return
     */
    Badge stroke(int color, float width, boolean isDpValue);

    int getBadgeBackgroundColor();

    /**
     * 设置背景图片
     *
     * @param drawable
     * @return
     */
    Badge setBadgeBackground(Drawable drawable);

    Badge setBadgeBackground(Drawable drawable, boolean clip);

    Drawable getBadgeBackground();

    /**
     * 设置文本颜色
     *
     * @param color
     * @return
     */
    Badge setBadgeTextColor(int color);

    int getBadgeTextColor();

    /**
     * 设置文本字体大小
     *
     * @param size
     * @param isSpValue
     * @return
     */
    Badge setBadgeTextSize(float size, boolean isSpValue);

    float getBadgeTextSize(boolean isSpValue);

    /**
     * 设置内边距
     *
     * @param padding
     * @param isDpValue
     * @return
     */
    Badge setBadgePadding(float padding, boolean isDpValue);

    float getBadgePadding(boolean isDpValue);

    boolean isDraggable();

    /**
     * 设置Badge相对于TargetView的位置
     *
     * @param gravity
     * @return
     */
    Badge setBadgeGravity(int gravity);

    int getBadgeGravity();

    /**
     * 设置外边距
     *
     * @param offset
     * @param isDpValue
     * @return
     */
    Badge setGravityOffset(float offset, boolean isDpValue);

    Badge setGravityOffset(float offsetX, float offsetY, boolean isDpValue);

    float getGravityOffsetX(boolean isDpValue);

    float getGravityOffsetY(boolean isDpValue);

    /**
     * 打开拖拽消除模式并设置监听
     *
     * @param l
     * @return
     */
    Badge setOnDragStateChangedListener(OnDragStateChangedListener l);

    PointF getDragCenter();

    Badge bindTarget(View view);

    View getTargetView();

    /**
     * 	隐藏Badge
     * @param animate
     */
    void hide(boolean animate);

    interface OnDragStateChangedListener {
        int STATE_START = 1;
        int STATE_DRAGGING = 2;
        int STATE_DRAGGING_OUT_OF_RANGE = 3;
        int STATE_CANCELED = 4;
        int STATE_SUCCEED = 5;

        void onDragStateChanged(int dragState, Badge badge, View targetView);
    }
}
