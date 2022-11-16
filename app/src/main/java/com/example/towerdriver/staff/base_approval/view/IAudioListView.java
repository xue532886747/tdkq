package com.example.towerdriver.staff.base_approval.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.staff.base_approval.model.AudioListBean;
import com.example.towerdriver.staff.base_approval.model.SponsorListBean;

import java.util.List;

/**
 * @author 53288
 * @description 审批人的列表
 * @date 2021/7/8
 */
public interface IAudioListView extends BaseView {

    /**
     * 审批人列表成功
     *
     * @param departmentListBeans 列表数据
     */
    void audioListSuccess(List<AudioListBean.DepartmentListBean> departmentListBeans);

    /**
     * 审批人列表失败
     *
     * @param msg
     */
    void audioListFailure(String msg);

    /**
     * 发布列表为空
     *
     * @param msg
     * @param departmentListBeans
     */
    void showRefreshNoDate(String msg, List<AudioListBean.DepartmentListBean> departmentListBeans);
}
