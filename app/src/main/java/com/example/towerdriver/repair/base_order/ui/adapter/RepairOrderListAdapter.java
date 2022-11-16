package com.example.towerdriver.repair.base_order.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.repair.base_order.model.RepairListBean;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author 53288
 * @description 订单列表
 * @date 2021/6/30
 */
public class RepairOrderListAdapter extends BaseQuickAdapter<RepairListBean.OrderBean, BaseViewHolder> implements LoadMoreModule {

    public RepairOrderListAdapter() {
        super(R.layout.adp_repair_order_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RepairListBean.OrderBean noticeListBean) {
        baseViewHolder.setText(R.id.tv_status, "订单状态: " + noticeListBean.getStatus_name());
        baseViewHolder.setText(R.id.tv_title, "套餐名称: " + noticeListBean.getRent_name());
        baseViewHolder.setText(R.id.tv_time, "下单时间: " + noticeListBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_code, "订单编号: " + noticeListBean.getOrder_sn());
        baseViewHolder.setText(R.id.tv_car_number, "车牌号: " + noticeListBean.getCar_number());
        baseViewHolder.setText(R.id.tv_frame_number, "车架号: " + noticeListBean.getFrame_number());
        AppCompatTextView tv_price = baseViewHolder.getView(R.id.tv_price);
        AppCompatButton bt_right = baseViewHolder.getView(R.id.bt_right);
        AppCompatButton bt_left = baseViewHolder.getView(R.id.bt_left);
        bt_right.setVisibility(View.VISIBLE);
        bt_right.setText("删除");
        bt_left.setVisibility(View.GONE);
        if (noticeListBean.getOrder_status() == 4) {
            bt_left.setVisibility(View.VISIBLE);
            bt_left.setText("检车");
        }
        tv_price.setVisibility(View.GONE);
    }
}
