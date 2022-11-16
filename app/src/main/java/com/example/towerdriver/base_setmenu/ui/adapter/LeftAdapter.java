package com.example.towerdriver.base_setmenu.ui.adapter;

import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_main.bean.ListBean;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.utils.tools.DisPlayUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @author 53288
 * @description 套餐左边的列表
 * @date 2021/5/24
 */
public class LeftAdapter extends BaseQuickAdapter<SelectBean.ClassifyBean, BaseViewHolder> {
    private int select_num = -1;

    public LeftAdapter() {
        super(R.layout.adp_menu_left);
    }

    public void setSelect_num(int select_num) {
        this.select_num = select_num;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SelectBean.ClassifyBean selectBean) {
        TextView title = baseViewHolder.getView(R.id.tv_title);
        if (select_num == baseViewHolder.getAdapterPosition()) {
            title.setBackgroundResource(R.mipmap.select_menu_select);
        } else {
            title.setBackgroundResource(R.drawable.select_menu_left_item_select_no);
        }
        baseViewHolder.setText(R.id.tv_title, selectBean.getName());
    }
}
