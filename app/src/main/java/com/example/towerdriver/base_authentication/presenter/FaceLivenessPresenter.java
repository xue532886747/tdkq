package com.example.towerdriver.base_authentication.presenter;

import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.view.IFacePrepareView;
import com.example.towerdriver.base_authentication.view.IFacelivenessView;

/**
 * @author 53288
 * @description 人脸识别开始
 * @date 2021/5/26
 */
public class FaceLivenessPresenter extends BasePresenter<IFacelivenessView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public FaceLivenessPresenter(IFacelivenessView baseView) {
        super(baseView);
    }


}
