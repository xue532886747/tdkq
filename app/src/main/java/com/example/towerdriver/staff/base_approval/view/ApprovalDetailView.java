package com.example.towerdriver.staff.base_approval.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.staff.base_approval.model.ApprovalDetailBean;

/**
 * @author 53288
 * @description 审批详情
 * @date 2021/7/10
 */
public interface ApprovalDetailView extends BaseView {
    /**
     * 审批详情
     */
    void approvalDetailSuccess(ApprovalDetailBean approvalDetailBean);

    /**
     * 审批详情失败
     *
     * @param msg
     */
    void approvalDetailFailure(String msg);

    /**
     * 下载进度
     *
     * @param downloadLength
     * @param contentLength
     */
    @Deprecated
    void getDownloadProgress(long downloadLength, long contentLength);


    /**
     * 下载成功
     *
     * @param name
     */
    @Deprecated
    void getDownLoadSuccess(String name);

    /**
     * 下载失败
     *
     * @param name
     */
    @Deprecated
    void getDownLoadFailure(String name);
}
