package com.example.towerdriver.staff.base_approval.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.staff.base_approval.model.ApprovalStatusBean;
import com.example.towerdriver.staff.base_approval.model.SponsorListBean;

import java.util.List;

/**
 * @author 53288
 * @description 审批的列表
 * @date 2021/7/8
 */
public interface IApprovalListView extends BaseView {

    /**
     * 审批列表成功
     *
     * @param articleBean 列表数据
     * @param cur_page    当前页面
     * @param total_page  总页面
     * @param articleBean fresh
     */
    void approvalListSuccess(List<SponsorListBean.ListBean> articleBean, int cur_page, int total_page, boolean fresh);

    /**
     * 审批列表失败
     *
     * @param msg
     */
    void approvalListFailure(String msg);

    /**
     * 发布列表为空
     *
     * @param msg
     * @param listBeans
     */
    void showRefreshNoDate(String msg, List<SponsorListBean.ListBean> listBeans);


    /**
     * 删除审批成功
     *
     * @param msg
     * @param position
     */
    void deleteApprovalSuccess(String msg, int id, int position);

    /**
     * 删除审批失败
     *
     * @param msg
     */
    void deleteApprovalFailure(String msg);

    /**
     * 审批通过或不通过
     *
     * @param msg
     * @param position
     */
    void permissionApprovalSuccess(String msg, int position, ApprovalStatusBean approvalStatusBean);

    /**
     * 审批通过或不通过
     *
     * @param msg
     */
    void permissionApprovalFailure(String msg);

}
