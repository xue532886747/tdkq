package com.example.towerdriver.base_authentication.presenter;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxOriginRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.model.bean.BaiduTokenBean;
import com.example.towerdriver.base_authentication.model.bean.IdCardBean;
import com.example.towerdriver.base_authentication.view.IFacePrepareView;
import com.example.towerdriver.base_authentication.view.IRealAuthView;
import com.example.towerdriver.utils.sp.BaiDuAccessUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import java.util.HashMap;

import static com.example.towerdriver.Constant.BAIDU_ASE_URL;
import static com.example.towerdriver.Constant.BAIDU_IDCARD_URL;
import static com.example.towerdriver.Constant.CLIENT_ID;
import static com.example.towerdriver.Constant.CLIENT_SECRET;

/**
 * @author 53288
 * @description 人脸识别准备
 * @date 2021/5/26
 */
public class FacePreparePresenter extends BasePresenter<IFacePrepareView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public FacePreparePresenter(IFacePrepareView baseView) {
        super(baseView);
    }


}
