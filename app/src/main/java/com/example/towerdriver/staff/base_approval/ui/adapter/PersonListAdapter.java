package com.example.towerdriver.staff.base_approval.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base_order_list.model.OrderListBean;
import com.example.towerdriver.staff.base_approval.model.NewDepartmentListBean;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.weight.CircleImageView;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @author 53288
 * @description 责任人
 * @date 2021/7/8
 */
public class PersonListAdapter extends BaseQuickAdapter<NewDepartmentListBean, BaseViewHolder> implements LoadMoreModule {


    public PersonListAdapter() {
        super(R.layout.adp_person_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NewDepartmentListBean newDepartmentListBean) {
        baseViewHolder.setText(R.id.tv_name, newDepartmentListBean.getName());
        LinearLayout ll_item = baseViewHolder.getView(R.id.ll_item);
        ViewGroup.LayoutParams layoutParams = ll_item.getLayoutParams();
        int itemWidth = (int) ((MyApplication.getScreenWidth() - 80 - DisPlayUtils.dp2px(20)) / 4);
        layoutParams.width = itemWidth;
        ll_item.setLayoutParams(layoutParams);
        CircleImageView circleImageView = baseViewHolder.getView(R.id.iv_head_image);
        Glide.with(getContext()).load(newDepartmentListBean.getImage()).
                placeholder(R.mipmap.log_image_bg).error(R.mipmap.log_image_bg).into(circleImageView);


    }
}
