package com.example.towerdriver.base_member_level.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_member_level.model.bean.LevelBean;

import org.jetbrains.annotations.NotNull;

/**
 * @author 53288
 * @description
 * @date 2021/6/8
 */
public class MemberChartAdapter extends BaseMultiItemQuickAdapter<LevelBean.OperateIntegralBean, BaseViewHolder> {

    public MemberChartAdapter() {
        addItemType(1, R.layout.adp_item_chart_head);
        addItemType(2, R.layout.adp_item_chart_content);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LevelBean.OperateIntegralBean operateIntegralBean) {
        int size = this.getData().size();
        switch (baseViewHolder.getItemViewType()) {
            case 1:

                break;
            case 2:
                View view = baseViewHolder.getView(R.id.view_line);
                if (baseViewHolder.getAdapterPosition() == size - 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
                baseViewHolder.setText(R.id.tv_left, operateIntegralBean.getScope());
                baseViewHolder.setText(R.id.tv_middle, operateIntegralBean.getName());
                baseViewHolder.setText(R.id.tv_right, operateIntegralBean.getExplain());
                break;
        }
    }
}
