package com.example.towerdriver.base_code.presenter;

import android.net.Uri;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxOriginRequest;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.model.bean.ImageToUrlBean;
import com.example.towerdriver.base_authentication.model.bean.PersonVerifyBean;
import com.example.towerdriver.base_authentication.view.ICollectionView;
import com.example.towerdriver.base_code.view.InviteView;
import com.example.towerdriver.utils.RxPartMapUtils;
import com.example.towerdriver.utils.tools.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import okhttp3.MultipartBody;

import static com.example.towerdriver.Constant.BAUDU_PERSON_VERIFY;


/**
 * @author 53288
 * @description 邀请好友二维码
 * @date 2021/5/31
 */
public class InvitePresenter extends BasePresenter<InviteView> {

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public InvitePresenter(InviteView baseView) {
        super(baseView);
    }
}
