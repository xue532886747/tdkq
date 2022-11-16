package com.example.towerdriver.repair.base_warehouse.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.example.towerdriver.repair.base_warehouse.model.MountBean;

/**
 * @author 53288
 * @description 配件列表
 * @date 2021/7/5
 */
public interface IRepairWareHouseView extends BaseView {

    /**
     * 获取列表成功
     *
     * @param mountBean 数据
     * @param isRefresh  是否刷新
     */
    void onRepairWareHouseSuccess(MountBean mountBean, boolean isRefresh);


    void onRepairWareHouseFailure(String msg);

    /**
     * 仓库列表item数量
     *
     * @param num
     */
    void onRepairNumSuccess(String num,int position);


    void onRepairNumFailure(String msg);


}
