package com.example.towerdriver.station.station_order.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_order_list.model.OrderListBean;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author 53288
 * @description 订单列表
 * @date 2021/6/30
 */
public class StationOrderListAdapter extends BaseQuickAdapter<OrderListBean.OrderBean, BaseViewHolder> implements LoadMoreModule {

    public StationOrderListAdapter() {
        super(R.layout.adp_order_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderListBean.OrderBean noticeListBean) {
        baseViewHolder.setText(R.id.tv_status, "订单状态: " + noticeListBean.getStatus_name());
        baseViewHolder.setText(R.id.tv_title, "套餐名称: " + noticeListBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_time, "下单时间: " + noticeListBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_code, "订单编号: " + noticeListBean.getOrder_sn());
        AppCompatTextView tv_price = baseViewHolder.getView(R.id.tv_price);
        AppCompatButton bt_right = baseViewHolder.getView(R.id.bt_right);
        AppCompatButton bt_left = baseViewHolder.getView(R.id.bt_left);
        bt_right.setVisibility(View.GONE);
        bt_left.setVisibility(View.GONE);
        tv_price.setVisibility(View.GONE);
    }
}
