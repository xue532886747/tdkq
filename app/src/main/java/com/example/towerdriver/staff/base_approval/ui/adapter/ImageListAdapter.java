package com.example.towerdriver.staff.base_approval.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.weight.RectImageView;

import org.jetbrains.annotations.NotNull;

/**
 * @author 53288
 * @description 图片
 * @date 2021/7/10
 */
public class ImageListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageListAdapter() {
        super(R.layout.adp_img_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String image) {
        RectImageView imageView = baseViewHolder.getView(R.id.iv_image);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int itemWidth = (int) ((MyApplication.getScreenWidth() - 80 - DisPlayUtils.dp2px(20)) / 3);
        layoutParams.width = itemWidth;
        layoutParams.height = itemWidth;
        imageView.setLayoutParams(layoutParams);
        Glide.with(getContext()).load(image).placeholder(R.mipmap.log_image_bg).
                error(R.mipmap.log_image_bg).into(imageView);

    }
}
