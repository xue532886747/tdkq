package com.example.towerdriver.staff.base_approval.ui.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.staff.base_approval.model.NewDepartmentListBean;
import com.example.towerdriver.weight.CircleImageView;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author 53288
 * @description 负责人列表
 * @date 2021/7/8
 */
public class AudioListAdapter extends BaseMultiItemQuickAdapter<NewDepartmentListBean, BaseViewHolder> {

    public AudioListAdapter() {
        addItemType(1, R.layout.adp_audio_title);
        addItemType(2, R.layout.adp_audio_content);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NewDepartmentListBean newDepartmentListBean) {
        switch (baseViewHolder.getItemViewType()) {
            case 1:
                baseViewHolder.setText(R.id.tv_title, newDepartmentListBean.getDepart_name());
                break;
            case 2:
                baseViewHolder.setText(R.id.tv_name, newDepartmentListBean.getName());
                CircleImageView circleImageView = baseViewHolder.getView(R.id.iv_head_image);
                Glide.with(getContext()).load(newDepartmentListBean.getImage()).
                        placeholder(R.mipmap.log_image_bg).error(R.mipmap.log_image_bg).into(circleImageView);
                AppCompatImageView iv_select = baseViewHolder.getView(R.id.iv_select);
                if (newDepartmentListBean.isSelect()) {
                    Glide.with(getContext()).load(R.mipmap.log_select).into(iv_select);
                } else {
                    Glide.with(getContext()).load(R.mipmap.log_select_no).into(iv_select);
                }
                break;
        }
    }
}
