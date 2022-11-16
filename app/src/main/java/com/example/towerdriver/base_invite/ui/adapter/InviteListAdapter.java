package com.example.towerdriver.base_invite.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_invite.model.InviteBean;
import com.example.towerdriver.base_order_list.model.OrderListBean;

import org.jetbrains.annotations.NotNull;

/**
 * @author 53288
 * @description
 * @date 2021/6/17
 */
public class InviteListAdapter extends BaseQuickAdapter<InviteBean.ListBean, BaseViewHolder> implements LoadMoreModule {


    public InviteListAdapter() {
        super(R.layout.adp_invite_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, InviteBean.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_name, listBean.getName());
        baseViewHolder.setText(R.id.tv_phone, "电话: " + listBean.getPhone());
        baseViewHolder.setText(R.id.tv_time, "时间: " + listBean.getCreatetime());
    }
}
