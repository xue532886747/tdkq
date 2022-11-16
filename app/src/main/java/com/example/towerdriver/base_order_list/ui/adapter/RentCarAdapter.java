package com.example.towerdriver.base_order_list.ui.adapter;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_order_list.model.RentCarBean;

import org.jetbrains.annotations.NotNull;

/**
 * @author 53288
 * @description 用户提车的列表
 * @date 2021/6/25
 */
public class RentCarAdapter extends BaseQuickAdapter<RentCarBean, BaseViewHolder> {


    public RentCarAdapter() {
        super(R.layout.adp_rentcar);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RentCarBean rentCarBean) {
        baseViewHolder.setText(R.id.tv_name, rentCarBean.getName());
        RadioGroup radioGroup = baseViewHolder.getView(R.id.rg_content);
        RadioButton rb_left = baseViewHolder.getView(R.id.rb_left);
        RadioButton rb_right = baseViewHolder.getView(R.id.rb_right);
        rb_left.setText("完好");
        rb_right.setText("损坏");
        if ("1".equals(rentCarBean.getType())) {
            rb_left.setChecked(true);
        } else if ("2".equals(rentCarBean.getType())) {
            rb_right.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_left:
                        rentCarBean.setType("1");
                        break;
                    case R.id.rb_right:
                        rentCarBean.setType("2");
                        break;
                }
            }
        });
    }
}
