package com.example.towerdriver.staff.base_approval.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.staff.base_approval.model.SponsorListBean;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author 53288
 * @description 文件列表
 * @date 2021/7/8
 */
public class ApprovalFileListAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    private boolean isVisible;

    public ApprovalFileListAdapter() {
        super(R.layout.adp_file_list);
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String listBean) {
        baseViewHolder.setText(R.id.tv_name, listBean);
        AppCompatImageView iv_delete = baseViewHolder.getView(R.id.iv_delete);
        AppCompatButton btn_preview = baseViewHolder.getView(R.id.btn_preview);
        if(isVisible){
            iv_delete.setVisibility(View.GONE);
            btn_preview.setVisibility(View.VISIBLE);
        }else {
            iv_delete.setVisibility(View.VISIBLE);
            btn_preview.setVisibility(View.GONE);
        }
    }
}
