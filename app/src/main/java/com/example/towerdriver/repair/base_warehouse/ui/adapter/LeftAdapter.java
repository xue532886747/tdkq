package com.example.towerdriver.repair.base_warehouse.ui.adapter;

import android.content.res.Resources;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.repair.base_warehouse.model.MountBean;

import org.jetbrains.annotations.NotNull;

/**
 * @author 53288
 * @description
 * @date 2021/7/5
 */
public class LeftAdapter extends BaseQuickAdapter<MountBean.VarietyListBean, BaseViewHolder> {
    private int select_num = -1;

    public LeftAdapter() {
        super(R.layout.adp_repair_left);
    }

    public void setSelect_num(int select_num) {
        this.select_num = select_num;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MountBean.VarietyListBean selectBean) {
        TextView title = baseViewHolder.getView(R.id.tv_title);
        if (select_num == baseViewHolder.getAdapterPosition()) {
            title.setBackgroundResource(R.drawable.select_menu_left_item_select_no);
            title.setTextColor(getContext().getResources().getColor(R.color.white));
        } else {
            title.setBackgroundResource(R.drawable.select_menu_left_item_select);
            title.setTextColor(getContext().getResources().getColor(R.color.text_black_color));
        }
        baseViewHolder.setText(R.id.tv_title, selectBean.getName());
    }
}
