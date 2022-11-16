package com.example.base_ui.file_picker.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.base_ui.R;
import com.example.base_ui.file_picker.model.BreadModel;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * BreadAdapter
 * Created by 李波 on 2018/2/5.
 */

public class BreadAdapter extends BaseQuickAdapter<BreadModel, BaseViewHolder> {

    public BreadAdapter(@Nullable List<BreadModel> data) {
        super(R.layout.bread_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BreadModel item) {
        helper.setText(R.id.btn_bread, item.getCurName());
    }
}
