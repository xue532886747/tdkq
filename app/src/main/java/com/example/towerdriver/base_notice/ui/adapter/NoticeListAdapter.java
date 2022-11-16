package com.example.towerdriver.base_notice.ui.adapter;

import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_notice.model.NoticeBean;
import com.example.towerdriver.weight.CircleImageView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author 53288
 * @description
 * @date 2021/6/17
 */
public class NoticeListAdapter extends BaseQuickAdapter<NoticeBean.NoticeListBean, BaseViewHolder> implements LoadMoreModule {
    private int type;

    public NoticeListAdapter(int type) {
        super(R.layout.adp_notice_list);
        this.type = type;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NoticeBean.NoticeListBean noticeListBean) {
        View dot = baseViewHolder.getView(R.id.view_dot);
        if (noticeListBean.getStatus() == 1) {
            dot.setVisibility(View.GONE);
        } else {
            dot.setVisibility(View.VISIBLE);
        }
        CircleImageView circleImageView = baseViewHolder.getView(R.id.iv_head_image);
        if (type == 1) {
            Glide.with(getContext()).load(R.mipmap.log_system).
                    placeholder(R.mipmap.log_image_bg).error(R.mipmap.log_image_bg).into(circleImageView);
        } else if (type == 2) {
            Glide.with(getContext()).load(R.mipmap.log_rescue).
                    placeholder(R.mipmap.log_image_bg).error(R.mipmap.log_image_bg).into(circleImageView);
        } else if (type == 3) {
            Glide.with(getContext()).load(R.mipmap.logo).
                    placeholder(R.mipmap.log_image_bg).error(R.mipmap.log_image_bg).into(circleImageView);
        }
        baseViewHolder.setText(R.id.tv_title, noticeListBean.getTitle());
        baseViewHolder.setText(R.id.tv_time, noticeListBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_describe, noticeListBean.getDescribe());

    }
}
