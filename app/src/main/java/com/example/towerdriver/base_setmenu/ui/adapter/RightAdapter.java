package com.example.towerdriver.base_setmenu.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.utils.tools.DisPlayUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @author 53288
 * @description 套餐右边的列表
 * @date 2021/6/10
 */
public class RightAdapter extends BaseQuickAdapter<SelectBean.RentListBean, BaseViewHolder> {

    public RightAdapter() {
        super(R.layout.adp_menu_right);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SelectBean.RentListBean rentListBean) {
        baseViewHolder.setText(R.id.tv_title, rentListBean.getName());
        ImageView imageView = baseViewHolder.getView(R.id.iv_major_image);
        Glide.with(getContext()).load(rentListBean.getImage()).placeholder(R.mipmap.log_image_bg)
                .error(R.mipmap.log_image_bg).into(imageView);

    }
}
