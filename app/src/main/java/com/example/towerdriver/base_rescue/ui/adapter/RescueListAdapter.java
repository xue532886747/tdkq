package com.example.towerdriver.base_rescue.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.base_rescue.model.RescueListBean;
import com.example.towerdriver.utils.tools.LogUtils;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @author 53288
 * @description 救援列表
 * @date 2021/6/21
 */
public class RescueListAdapter extends BaseQuickAdapter<RescueListBean.RescueOrderBean, BaseViewHolder> implements LoadMoreModule {

    public RescueListAdapter() {
        super(R.layout.adp_rescue_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RescueListBean.RescueOrderBean rescueOrderBean) {
        baseViewHolder.setText(R.id.tv_status, "订单状态: " + rescueOrderBean.getStatus_name());
        baseViewHolder.setText(R.id.tv_title, "救援类型: " + rescueOrderBean.getReason());
        baseViewHolder.setText(R.id.tv_time, "下单时间: " + rescueOrderBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_name, "姓名: " + rescueOrderBean.getMember_name());
        baseViewHolder.setText(R.id.tv_phone, "电话: " + rescueOrderBean.getMember_phone());
        AppCompatButton bt_evaluate = baseViewHolder.getView(R.id.bt_evaluate);
        if (rescueOrderBean.getStatus() == 4) {
            bt_evaluate.setVisibility(View.VISIBLE);
        } else {
            bt_evaluate.setVisibility(View.GONE);
        }
    }
}
