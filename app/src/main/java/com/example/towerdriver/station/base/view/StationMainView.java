package com.example.towerdriver.station.base.view;

import com.example.towerdriver.base.BaseView;
import com.example.towerdriver.bean.VersionBean;

/**
 * @author 53288
 * @description
 * @date 2021/8/2
 */
public interface StationMainView extends BaseView {

    void getVersionSuccess(VersionBean version);
}
