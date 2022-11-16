package com.example.towerdriver.staff.base_approval.view;

import com.example.towerdriver.base.BaseView;

/**
 * @author 53288
 * @description 新建审批
 * @date 2021/7/8
 */
public interface CreateApprovalView extends BaseView {
    /**
     * 审批发布成功
     *
     * @param msg
     */
    void createApprovalSuccess(String msg);

    /**
     * 审批发布失败
     *
     * @param msg
     */
    void createApprovalFailure(String msg);

    /**
     * 总进度，当前进度
     * @param contentLength
     * @param mCurrentLength
     */
    void FileToUrlProgress(long contentLength, long mCurrentLength);

    /**
     * 图片转为url成功
     */
    void FileToUrlSuccess(int type, String url);

    /**
     * 图片转为url失败
     */
    void FileToUrlFailure(String msg);
}
