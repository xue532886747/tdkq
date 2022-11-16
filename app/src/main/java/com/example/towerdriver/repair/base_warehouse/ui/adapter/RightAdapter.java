package com.example.towerdriver.repair.base_warehouse.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
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
public class RightAdapter extends BaseQuickAdapter<MountBean.MountingsListBean, BaseViewHolder> {

    public RightAdapter() {
        super(R.layout.adp_warehouse_right);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MountBean.MountingsListBean rentListBean) {
        baseViewHolder.setText(R.id.tv_title, "名称: "+rentListBean.getName());
        baseViewHolder.setText(R.id.et_number,rentListBean.getNum()+"");
    }
}
