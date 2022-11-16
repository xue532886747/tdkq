package com.example.base_ui.file_picker.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.base_ui.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * SelectSdcardAdapter
 * Created by 李波 on 2018/2/8.
 */

public class SelectSdcardAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public SelectSdcardAdapter(@Nullable List<String> data) {
        super(R.layout.item_select_sdcard,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_select_sdcard,item);
    }
}
