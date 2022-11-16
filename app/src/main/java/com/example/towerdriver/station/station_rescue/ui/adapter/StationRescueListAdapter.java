package com.example.towerdriver.station.station_rescue.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_rescue.model.RescueListBean;
import com.example.towerdriver.station.station_rescue.model.StationRescueBean;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @author 53288
 * @description 站点端救援列表
 * @date 2021/6/30
 */
public class StationRescueListAdapter extends BaseQuickAdapter<StationRescueBean.OrderBean, BaseViewHolder> implements LoadMoreModule {

    public StationRescueListAdapter() {
        super(R.layout.adp_rescue_list);
    }

    /**
     * 救援状态：1=待接单,2=已派单,3=救援中,4=已完成,5=已评价
     * 2 接单按钮 3 完成按钮
     *
     * @param baseViewHolder
     * @param rescueOrderBean
     */
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, StationRescueBean.OrderBean rescueOrderBean) {
        baseViewHolder.setText(R.id.tv_status, "订单状态: " + rescueOrderBean.getStatus_name());
        baseViewHolder.setText(R.id.tv_title, "救援类型: " + rescueOrderBean.getReason());
        baseViewHolder.setText(R.id.tv_time, "下单时间: " + rescueOrderBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_name, "姓名: " + rescueOrderBean.getMember_name());
        baseViewHolder.setText(R.id.tv_phone, "电话: " + rescueOrderBean.getMember_phone());
        AppCompatButton bt_evaluate = baseViewHolder.getView(R.id.bt_evaluate);
        if (rescueOrderBean.getStatus() == 2) {
            bt_evaluate.setVisibility(View.VISIBLE);
            bt_evaluate.setText("接单");
        } else if (rescueOrderBean.getStatus() == 3) {
            bt_evaluate.setVisibility(View.VISIBLE);
            bt_evaluate.setText("完成");
        } else {
            bt_evaluate.setVisibility(View.GONE);
        }
    }
}
