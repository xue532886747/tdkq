package com.example.towerdriver.base_order_list.ui.adapter;

import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_notice.model.NoticeBean;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.weight.CircleImageView;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @author 53288
 * @description
 * @date 2021/6/17
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderListBean.OrderBean, BaseViewHolder> implements LoadMoreModule {


    public OrderListAdapter() {
        super(R.layout.adp_order_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrderListBean.OrderBean noticeListBean) {
        baseViewHolder.setText(R.id.tv_status, "订单状态: " + noticeListBean.getStatus_name());
        baseViewHolder.setText(R.id.tv_title, "套餐名称: " + noticeListBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_time, "下单时间: " + noticeListBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_code, "订单编号: " + noticeListBean.getOrder_sn());
        baseViewHolder.setText(R.id.tv_price, "¥" + noticeListBean.getPay_price());
        AppCompatButton bt_right = baseViewHolder.getView(R.id.bt_right);
        AppCompatButton bt_left = baseViewHolder.getView(R.id.bt_left);
        if (noticeListBean.getOrder_status() == 0 || noticeListBean.getOrder_status() == 7) {
            bt_right.setVisibility(View.GONE);
            bt_left.setVisibility(View.GONE);
            bt_right.setText("订单删除");
        } else if (noticeListBean.getOrder_status() == 1) {
            bt_right.setVisibility(View.VISIBLE);
            bt_left.setVisibility(View.VISIBLE);
            bt_right.setText("订单取消");
            bt_left.setText("订单支付");
        } else if (noticeListBean.getOrder_status() == 2) {
            bt_right.setVisibility(View.VISIBLE);
            bt_left.setVisibility(View.VISIBLE);
            bt_right.setText("退款");
            bt_left.setText("提车");
        } else if (noticeListBean.getOrder_status() == 4) {
            bt_right.setVisibility(View.VISIBLE);
            bt_left.setVisibility(View.GONE);
            bt_right.setText("订单续租");
        } else {
            bt_right.setVisibility(View.GONE);
            bt_left.setVisibility(View.GONE);
        }
    }
}
