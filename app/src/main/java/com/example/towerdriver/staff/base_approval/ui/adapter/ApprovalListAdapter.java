package com.example.towerdriver.staff.base_approval.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.staff.base_approval.model.SponsorListBean;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @author 53288
 * @description 审批列表
 * @date 2021/7/8
 */
public class ApprovalListAdapter extends BaseQuickAdapter<SponsorListBean.ListBean, BaseViewHolder> implements LoadMoreModule {

    private int ListType = 1;

    public ApprovalListAdapter() {
        super(R.layout.adp_approval_list);
    }

    public int getListType() {
        return ListType;
    }

    public void setListType(int listType) {
        ListType = listType;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SponsorListBean.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_status_name, "审批状态: " + listBean.getStatus_name());
        baseViewHolder.setText(R.id.tv_create_time, listBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_title, "审批内容: " + listBean.getTitle());
        baseViewHolder.setText(R.id.tv_staff_name, "由 " + listBean.getStaff_name() + " 发起");
        baseViewHolder.setText(R.id.tv_audit_name, "由 " + listBean.getAudit_name() + " 审批");
        AppCompatButton bt_right = baseViewHolder.getView(R.id.bt_right);
        AppCompatButton bt_left = baseViewHolder.getView(R.id.bt_left);
        if (ListType == 0) {
            if (listBean.getStatus() == 1) {
                bt_right.setVisibility(View.VISIBLE);
                bt_left.setVisibility(View.GONE);
                bt_right.setText("删除");
            }else {
                bt_right.setVisibility(View.GONE);
                bt_left.setVisibility(View.GONE);
            }
        } else {
            if (listBean.getStatus() == 1) {
                bt_right.setVisibility(View.VISIBLE);
                bt_left.setVisibility(View.VISIBLE);
                bt_left.setText("通过审批");
                bt_right.setText("拒绝审批");
            }
        }
    }
}
