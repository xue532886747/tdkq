package com.example.towerdriver.base_person.presenter;

import com.example.rxhttp.core.RxHttp;
import com.example.rxhttp.request.RxRequest;
import com.example.rxhttp.request.exception.ExceptionHandle;
import com.example.towerdriver.api.FreeApi;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_authentication.model.bean.ImageToUrlBean;
import com.example.towerdriver.base_person.bean.MemberImageBean;
import com.example.towerdriver.base_person.view.PersonCenterView;
import com.example.towerdriver.member_model.IMemberModel;
import com.example.towerdriver.member_model.MemberInfoBean;
import com.example.towerdriver.member_model.MemberModel;
import com.example.towerdriver.utils.RxPartMapUtils;
import com.example.towerdriver.utils.sp.UserUtils;

import java.io.File;

import okhttp3.MultipartBody;

/**
 * @author 53288
 * @description
 * @date 2021/5/22
 */
public class PersonCenterPresenter extends BasePresenter<PersonCenterView> implements IMemberModel {
    private MemberModel memberModel;

    /**
     * 绑定view层与p层
     *
     * @param baseView
     */
    public PersonCenterPresenter(PersonCenterView baseView) {
        super(baseView);
    }

    /**
     * 获得用户信息
     */
    public void getMemberInfo() {
        memberModel = new MemberModel(this);
        memberModel.getMemberInfo();
    }

    /**
     * 更换头像
     *
     * @param file
     */
    public void changeHeadImage(File file) {
        if (UserUtils.getInstance().isLogin()) {
            int loginType = UserUtils.getInstance().getLoginType();
            if (loginType == 1) {
                changeUserHead(loginType, file);
            }else {
                if (isAttach()) {
                    getMvpView().LoadingClose();
                }
            }
        }
    }

    /**
     * 客户端修改头像
     *
     * @param type
     * @param file
     */
    private void changeUserHead(int type, File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("image", file.getName(), RxPartMapUtils.toRequestBodyOfImage(file));
        builder.addFormDataPart("token", UserUtils.getInstance().getUserToken());
        addToRxLife(RxHttp.request(FreeApi.api().ChangeMemberImg(builder.build())).listener(new RxRequest.RequestListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onError(ExceptionHandle handle) {
                if (isAttach()) {
                    getMvpView().showFailed(handle.getCode(), handle.getMsg());
                }
            }

            @Override
            public void onFinish() {

            }
        }).request(new RxRequest.ResultCallback<MemberImageBean>() {
            @Override
            public void onSuccess(int code, String msg, MemberImageBean data) {
                if (data != null) {
                    if (isAttach()) {
                        getMvpView().changeImgSuccess(type, data.getMember_img(), msg);
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    if (code == FreeApi.Code.TOKENEXPIRED) {
                        getMvpView().LoadingClose();
                    } else {
                        getMvpView().changeImgFailure(type, msg);
                    }
                }
            }
        }));
    }

    @Override
    public void getMemberSuccess(MemberInfoBean memberInfoBean, String msg) {
        if (isAttach()) {
            getMvpView().getMemberInfoSuccess(memberInfoBean);
        }
    }

    @Override
    public void getMemberFailure(String msg) {

    }

    @Override
    public void getTokenFailure() {
        if (isAttach()) {
            getMvpView().LoadingClose();
        }
    }

    @Override
    public void getCodeElseFailure(int code, String msg) {

    }

    @Override
    public void detachView() {
        super.detachView();
        if (memberModel != null) {
            memberModel.unMemberModel();
        }
    }
}
