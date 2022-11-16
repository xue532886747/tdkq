package com.example.base_ui.file_picker.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.base_ui.R;
import com.example.base_ui.file_picker.SelectOptions;
import com.example.base_ui.file_picker.model.EssFile;
import com.example.base_ui.file_picker.util.UiUtils;


import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;


/**
 * EssMediaAdapter
 * Created by 李波 on 2018/3/2.
 */

public class EssMediaAdapter extends BaseQuickAdapter<EssFile, BaseViewHolder> {

    private int mImageResize;

    public EssMediaAdapter(@Nullable List<EssFile> data) {
        super(R.layout.ess_media_item, data);
    }

    public void setImageResize(int imageSize) {
        this.mImageResize = imageSize;
    }

    @Override
    protected void convert(BaseViewHolder helper, EssFile item) {
        if (item.getItemType() == EssFile.CAPTURE) {
            helper.getView(R.id.media).setVisibility(View.GONE);
            helper.getView(R.id.capture).setVisibility(View.VISIBLE);
            helper.itemView.setLayoutParams(new ViewGroup.LayoutParams(mImageResize - UiUtils.dpToPx(4), mImageResize));
        } else {
            helper.getView(R.id.capture).setVisibility(View.GONE);
            helper.getView(R.id.media).setVisibility(View.VISIBLE);
            helper.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, mImageResize));
            ImageView imageView = helper.getView(R.id.media_thumbnail);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .override(mImageResize, mImageResize)
                    .placeholder(SelectOptions.getInstance().placeHolder == null ? getContext().getResources().getDrawable(R.mipmap.png_holder) : SelectOptions.getInstance().placeHolder);
            Glide
                    .with(getContext())
                    .load(item.getUri())
                    .apply(options)
                    .into(imageView);
            if (SelectOptions.getInstance().isSingle || SelectOptions.getInstance().maxCount == 1) {
                helper.setVisible(R.id.check_view, false);
            } else {
                AppCompatCheckBox checkBox = helper.getView(R.id.check_view);
                helper.setVisible(R.id.check_view, true);
                checkBox.setChecked(item.isChecked());
            }
        }

    }


}
