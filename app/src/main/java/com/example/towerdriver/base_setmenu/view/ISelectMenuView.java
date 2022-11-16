package com.example.towerdriver.base_setmenu.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_setmenu.model.SelectBean;

/**
 * @author 53288
 * @description 套餐选择
 * @date 2021/5/24
 */
public interface ISelectMenuView extends BaseView {

    /**
     * 获取列表成功
     *
     * @param selectBean 数据
     * @param isRefresh  是否刷新
     */
    void onSelectMenuSuccess(SelectBean selectBean, boolean isRefresh);


    void onSelectMenuFailure(String msg);
}
