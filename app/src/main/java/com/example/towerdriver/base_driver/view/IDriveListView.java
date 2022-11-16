package com.example.towerdriver.base_driver.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_driver.model.bean.ReleaseListBean;

import java.util.List;

/**
 * @author 53288
 * @description 小哥发布列表
 * @date 2021/6/4
 */
public interface IDriveListView extends BaseView {
    /**
     * 小哥列表
     *
     * @param articleBean 列表数据
     * @param cur_page    当前页面
     * @param total_page  总页面
     * @param articleBean fresh
     * @param select_type 列表：1=总列表，2=自己的列表
     */
    void contentListSuccess(List<ReleaseListBean.ArticleBean> articleBean, int cur_page, int total_page, boolean fresh,boolean select_type);

    /**
     * 小哥列表失败
     *
     * @param msg
     */
    void contentListFailure(String msg);

    /**
     * 发布列表为空
     *
     * @param msg
     * @param articleBean
     */
    void showRefreshNoDate(boolean select_type,String msg, List<ReleaseListBean.ArticleBean> articleBean);


    /**
     * 列表删除成功
     */
    void DeleteContentSuccess(int position,int id, String msg);

    /**
     * 列表删除失败
     */
    void DeleteContentFailure(int position, String msg);

}
