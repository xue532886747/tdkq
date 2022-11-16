package com.example.towerdriver.base_order_list.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_order_list.model.OrderDean;
import com.example.towerdriver.base_order_list.model.OrderListBean;

import org.jetbrains.annotations.NotNull;

/**
 * @author 53288
 * @description 订单详情
 * @date 2021/6/21
 */
public class OrderDetailAdapter extends BaseQuickAdapter<OrderDean, BaseViewHolder> implements LoadMoreModule {

    public OrderDetailAdapter() {
        super(R.layout.adp_order_detail);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderDean orderDean) {
        baseViewHolder.setText(R.id.tv_title, orderDean.getTitle());
        baseViewHolder.setText(R.id.tv_name, orderDean.getName());
    }
}
