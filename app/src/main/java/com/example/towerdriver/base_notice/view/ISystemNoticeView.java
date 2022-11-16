package com.example.towerdriver.base_notice.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_driver.model.bean.ReleaseListBean;
import com.example.towerdriver.base_notice.model.NoticeBean;

import java.util.List;

/**
 * @author 53288
 * @description 系统消息
 * @date 2021/5/31
 */
public interface ISystemNoticeView extends BaseView {

    /**
     * 消息列表成功
     *
     * @param articleBean 列表数据
     * @param cur_page    当前页面
     * @param total_page  总页面
     * @param articleBean fresh
     * @param select_type 1=系统消息，2=救援消息，3=订单消息
     */
    void noticeListSuccess(List<NoticeBean.NoticeListBean> articleBean, int cur_page, int total_page, boolean fresh, String select_type);

    /**
     * 消息列表失败
     *
     * @param msg
     */
    void noticeListFailure(String msg);

    /**
     * 发布列表为空
     *
     * @param msg
     * @param articleBean
     */
    void showRefreshNoDate(String msg, List<NoticeBean.NoticeListBean> articleBean);


    /**
     * 消息标记为已读
     */
    void noticeDotSuccess(int position, String id);

    /**
     * 消息标记为已读失败
     *
     * @param msg
     */
    void noticeDotFailure(String msg);

}
