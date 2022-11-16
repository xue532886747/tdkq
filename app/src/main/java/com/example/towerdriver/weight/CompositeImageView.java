package com.example.towerdriver.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.towerdriver.R;
import com.example.towerdriver.utils.tools.DisPlayUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author 53288
 * @description 讲两个view合成一个, 未完成 要重新封装
 * @date 2021/5/24
 */
public class CompositeImageView extends LinearLayout {

    private CircleImageView iv_circle_head;
    private AppCompatImageView iv_medal;
    private AppCompatTextView tv_medal;
    private LinearLayout ll_user_status;
    private boolean composite_civ_border_have;
    private float composite_civ_rect_width;
    private float composite_civ_rect_height;
    private Drawable composite_civ_rect_bg;
    private float composite_civ_img_width;

    public CompositeImageView(Context context) {
        this(context, null);
    }

    public CompositeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompositeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CompositeImageView, defStyleAttr, 0);
        composite_civ_border_have = typedArray.getBoolean(R.styleable.CompositeImageView_composite_civ_border_have, true);
        composite_civ_rect_width = typedArray.getDimension(R.styleable.CompositeImageView_composite_civ_rect_width, DisPlayUtils.dp2px(10));
        composite_civ_rect_height = typedArray.getDimension(R.styleable.CompositeImageView_composite_civ_rect_height, DisPlayUtils.dp2px(10));
        composite_civ_rect_bg = typedArray.getDrawable(R.styleable.CompositeImageView_composite_civ_rect_bg);
        composite_civ_img_width = typedArray.getDimension(R.styleable.CompositeImageView_composite_civ_img_width, DisPlayUtils.dp2px(42));
        typedArray.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_composite_head, this);
        iv_circle_head = view.findViewById(R.id.iv_circle_head);
        ViewGroup.LayoutParams layoutParams = iv_circle_head.getLayoutParams();
        layoutParams.width = (int) composite_civ_img_width;
        layoutParams.height = (int) composite_civ_img_width;
        iv_medal = view.findViewById(R.id.iv_medal);
        tv_medal = view.findViewById(R.id.tv_medal);
        ll_user_status = view.findViewById(R.id.ll_user_status);
        ViewGroup.LayoutParams layoutParams1 = ll_user_status.getLayoutParams();
        layoutParams1.width = (int) composite_civ_rect_width;
        layoutParams1.height = (int) composite_civ_rect_height;
        ll_user_status.setBackground(composite_civ_rect_bg);
        if (!composite_civ_border_have) {
            iv_circle_head.setBorderWidth(0);
            iv_circle_head.setBorderColor(0);
        }
    }


    public boolean isComposite_civ_border_have() {
        return composite_civ_border_have;
    }

    public void setComposite_civ_border_have(boolean composite_civ_border_have) {
        this.composite_civ_border_have = composite_civ_border_have;
    }

    public void setIv_circle_head(String head) {
        if (iv_circle_head != null) {
            Glide.with(this).load(head).into(iv_circle_head);
        }
    }

    public void setIv_medal(int medal) {
        if (iv_medal != null) {
            Glide.with(this).load(medal).into(iv_circle_head);
        }
    }

    public void setTv_medal(String medal) {
        if (tv_medal != null) {
            tv_medal.setText(medal);
        }
    }

    public void setLl_user_status(boolean isVisible) {
        if (isVisible) {
            ll_user_status.setVisibility(VISIBLE);
        } else {
            ll_user_status.setVisibility(GONE);
        }

    }
}
