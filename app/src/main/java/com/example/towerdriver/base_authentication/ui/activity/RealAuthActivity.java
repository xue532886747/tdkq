package com.example.towerdriver.base_authentication.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.UriUtils;
import com.example.base_ui.bigimageview.tool.ui.ToastUtil;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_authentication.model.bean.IdCardBean;
import com.example.towerdriver.base_authentication.presenter.RealAuthPresenter;
import com.example.towerdriver.base_authentication.view.IRealAuthView;
import com.example.towerdriver.dialog.ButtomDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.ChangeUserEvent;
import com.example.towerdriver.event.UserInformationEvent;
import com.example.towerdriver.member_model.MemberInfoBean;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.TimeUtil;
import com.example.towerdriver.utils.sp.BaiDuAccessUtils;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.weight.RectImageView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 身份证实名认证
 * @date 2021/5/25
 */
public class RealAuthActivity extends BaseActivity<RealAuthPresenter> implements IRealAuthView, BaseActivity.PermissionListener {
    @BindView(R.id.iv_id_card_front)
    RectImageView iv_id_card_front;        //正面
    @BindView(R.id.iv_id_card_back)
    RectImageView iv_id_card_back;         //背面
    @BindView(R.id.bt_commit)
    AppCompatButton bt_commit;             //身份证提交
    @BindView(R.id.tv_name)
    TextView tv_name;                      //姓名
    @BindView(R.id.tv_address)
    TextView tv_address;                   //地址
    @BindView(R.id.tv_id_code)
    TextView tv_id_code;                   //身份证
    @BindView(R.id.tv_effect_date)
    TextView tv_effect_date;               //有效期
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.cl_user_info)
    ConstraintLayout cl_user_info;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private SmartRefreshUtils mSmartRefreshUtils;
    private static final String FRONT = "front";
    private static final String BACK = "back";
    private String idCardType = FRONT;           //判断是身份证的正面还是背面
    private int mCameraType = 0;                 //判断是选择相机还是相册
    private ButtomDialog buttomDialog;
    private Uri mPhotoUri;                       //临时的
    private String mFrontUrl, mBackUrl;          //正面的地址，背面的地址


    @Override
    protected RealAuthPresenter createPresenter() {
        return new RealAuthPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_real_auth;
    }

    @Override
    protected void initView() {
        setPermissionListener(this);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                if (presenter != null) {
                    presenter.getMemberInfo();
                }
            }
        });
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void initData() {
        if (!BaiDuAccessUtils.getInstance().AccessTokenIsLogin() && presenter != null) {
            presenter.getBaiduAuth();
        }
        if (presenter != null) {
            presenter.getMemberInfo();
        }
    }

    /**
     * 更改用户信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInfo(UserInformationEvent event) {
        boolean success = event.isSuccess();
        if (success) {
            if (presenter != null) {
                presenter.getMemberInfo();
            }
        }
    }

    @OnClick({R.id.bt_commit, R.id.iv_id_card_front, R.id.iv_id_card_back, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_id_card_front:
                idCardType = FRONT;
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.iv_id_card_front)) {
                    checkPermission();
                }
                break;
            case R.id.iv_id_card_back:
                idCardType = BACK;
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.iv_id_card_back)) {
                    checkPermission();
                }
                break;
            case R.id.bt_commit:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_commit)) {
                    commit();
                }
                break;
            case R.id.ll_back:
                finish();
                break;
        }
    }

    /**
     * 检查权限，然后根据权限返回dialog
     */
    private void checkPermission() {
        checkLocationPermission(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void commit() {
        if (TextUtils.isEmpty(mFrontUrl)) {
            ToastUtils.show("您还未上传身份证正面,请上传！");
            return;
        }
        if (TextUtils.isEmpty(mBackUrl)) {
            ToastUtils.show("您还未上传身份证背面,请上传！");
            return;
        }
        if (TextUtils.isEmpty(tv_name.getText())) {
            ToastUtils.show("未检测到您的姓名,请填写！");
            return;
        }
        if (TextUtils.isEmpty(tv_address.getText())) {
            ToastUtils.show("未检测到您的身份证住址,请填写！");
            return;
        }
        if (TextUtils.isEmpty(tv_id_code.getText())) {
            ToastUtils.show("未检测到您的身份证号,请填写！");
            return;
        }
        if (TextUtils.isEmpty(tv_effect_date.getText())) {
            ToastUtils.show("未检测到您的身份证有效日期,请填写！");
            return;
        }
        FacePrepareActivity.launch(this, tv_name.getText().toString(), tv_id_code.getText().toString(), mFrontUrl, mBackUrl);
    }

    /**
     * 调起Image转url
     *
     * @param uri
     */
    public void FileToUrl(Uri uri) {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在上传图片...");
            presenter.getImageToUrl(idCardType, uri);
        }
    }

    /**
     * image转url成功
     *
     * @param type 身份证正反面
     * @param url
     */
    @Override
    public void ImageToUrlSuccess(String type, String url) {
        closeDialog();
        idCardType = type;
        if (FRONT.equals(type)) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在识别正面...");
            mFrontUrl = url;
            if (presenter != null) {
                presenter.getIdCard(mFrontUrl, idCardType);
            }
            Glide.with(this).load(url).placeholder(R.mipmap.log_image_bg)
                    .error(R.mipmap.log_image_bg).into(iv_id_card_front);
        } else if (BACK.equals(type)) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在识别背面...");
            mBackUrl = url;
            if (presenter != null) {
                presenter.getIdCard(mBackUrl, idCardType);
            }
            Glide.with(this).load(url).placeholder(R.mipmap.log_image_bg)
                    .error(R.mipmap.log_image_bg).into(iv_id_card_back);
        }
    }

    @Override
    public void ImageToUrlFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void onAccessTokenSuccess() {

    }

    @Override
    public void onAccessTokenFailure() {
        ToastUtils.show("模块实例化失败，请联系当前上家手动认证！");
    }

    /**
     * 身份证验证
     *
     * @param type       正面或反面
     * @param idCardBean
     */
    @Override
    public void onIdCardAuthSuccess(String type, IdCardBean idCardBean) {
        closeDialog();
        idCardType = type;
        String risk_type = idCardBean.getRisk_type();
        if (!TextUtils.isEmpty(risk_type)) {
            if ("normal".equals(risk_type)) {
                IdCardBean.WordsResultBean words_result = idCardBean.getWords_result();
                if (words_result != null) {
                    if (FRONT.equals(type)) {
                        tv_name.setText(words_result.getNameBean().getWords());
                        tv_address.setText(words_result.getAddressBean().getWords());
                        tv_id_code.setText(words_result.getCitizenshipNumberBean().getWords());
                    }
                    if (BACK.equals(type)) {
                        tv_effect_date.setText(words_result.getSignDateBean().getWords() + " - " + words_result.getExpirationdateBean().getWords());
                    }
                }
            } else if ("reversed_side".equals(risk_type)) {
                ToastUtils.show("身份证正反面颠倒，请重新上传!");
            } else if ("non_idcard".equals(risk_type)) {
                ToastUtils.show("上传的图片中不包含身份证，请重新上传!");
            } else if ("blurred".equals(risk_type)) {
                ToastUtils.show("身份证模糊，请重新上传!");
            } else if ("other_type_card".equals(risk_type)) {
                ToastUtils.show("其他类型证照，请重新上传!");
            } else if ("over_exposure".equals(risk_type)) {
                ToastUtils.show("身份证关键字段反光或过曝，请重新上传!");
            } else if ("over_dark".equals(risk_type)) {
                ToastUtils.show("身份证欠曝（亮度过低），请重新上传!");
            } else if ("unknown".equals(risk_type)) {
                ToastUtils.show("未知状态，请重新上传!");
            }
        }
    }

    @Override
    public void onIdCardAuthFailure(String msg) {
        closeDialog();
        ToastUtils.show("身份证认证失败！请重新上传");
    }

    /**
     * 获得用户信息成功
     *
     * @param memberInfoBean
     */
    @Override
    public void getMemberInfoSuccess(MemberInfoBean memberInfoBean) {
        loadingSuccessOrFailure(1);
        if (memberInfoBean != null) {
            Integer if_intact = memberInfoBean.getIf_intact();
            if(UserUtils.getInstance().isLogin()){
                UserUtils.getInstance().getLoginBean().setIf_intact(if_intact);
            }
            if (if_intact == 0) {       //没有认证
                iv_id_card_front.setClickable(true);
                iv_id_card_back.setClickable(true);
                cl_user_info.setVisibility(View.VISIBLE);
            } else {        //认证
                iv_id_card_front.setClickable(false);
                iv_id_card_back.setClickable(false);
                cl_user_info.setVisibility(View.GONE);
                Glide.with(this).load(memberInfoBean.getCard_front())
                        .placeholder(R.mipmap.log_image_bg).error(R.mipmap.log_image_bg).into(iv_id_card_front);
                Glide.with(this).load(memberInfoBean.getCard_reverse())
                        .placeholder(R.mipmap.log_image_bg).error(R.mipmap.log_image_bg).into(iv_id_card_back);
            }
        }
    }

    @Override
    public void getMemberInfoFailure(String msg) {
        loadingSuccessOrFailure(2);
    }

    @Override
    public void LoadingClose() {
        loadingSuccessOrFailure(2);
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        loadingSuccessOrFailure(2);
        ToastUtils.show(msg);
        closeDialog();
    }


    /**
     * 请求权限成功
     */
    @Override
    public void requestPermissionSuccess() {
        dialogCreate();
    }

    /**
     * 请求权限失败
     */
    @Override
    public void requestPermissionFailure() {

    }

    /**
     * 弹窗选择相机或相册
     */
    private void dialogCreate() {
        buttomDialog = new ButtomDialog(this);
        buttomDialog.Builder("相机拍照", "相册选择").setDialogClickListener(new ButtomDialog.DialogClickListener() {
            @Override
            public void top(int type) {
                mCameraType = 0;
                goToCameraOrAlbum();
            }

            @Override
            public void bottom(int type) {
                mCameraType = 1;
                goToCameraOrAlbum();
            }
        }).show();
    }

    /**
     * 跳转到相机或者相册
     */
    private void goToCameraOrAlbum() {
        if (mCameraType == 0) {
            startCamera();
        } else if (mCameraType == 1) {
            openAlbum();
        }
    }

    /**
     * 启动相机
     */
    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "tdkq_" + TimeUtil.getTimeStamp() + ".png");
        mPhotoUri = FileProvider.getUriForFile(getApplicationContext(), getResources().getString(R.string.file_provider_authorities), file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);// 更改系统默认存储路径
        startActivityForResult(intent, Constant.TAKE_PHOTO);//打开相机
    }

    /**
     * 跳转相册
     */
    private void openAlbum() {
        ImageSelector.builder()
                .useCamera(false) // 设置是否使用拍照
                .setSingle(true)  //设置是否单选
                .canPreview(true) //是否可以预览图片，默认为true
                .start(this, Constant.CHOOSE_PHOTO); // 打开相册
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    startCrop(mPhotoUri);
                }
                break;
            case Constant.CHOOSE_PHOTO:
                if (data == null) {//如果没有拍照或没有选取照片，则直接返回
                    return;
                }
                if (resultCode == RESULT_OK) {
                    ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
                    if (stringArrayListExtra != null && stringArrayListExtra.size() > 0) {
                        mPhotoUri = UriUtils.getImageContentUri(getApplicationContext(), stringArrayListExtra.get(0));
                        startCrop(mPhotoUri);
                    }
                }
                break;
        }
        //裁剪都走这里
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            if (data == null) {//如果没有拍照或没有选取照片，则直接返回
                return;
            }
            handleCropResult(data);
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            Toast.makeText(this, "裁剪失败,请检查图片！", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 裁剪成功
     *
     * @param data
     */
    private void handleCropResult(Intent data) {
        final Uri resultUri = UCrop.getOutput(data);
        LogUtils.d("resultUri = " + resultUri);
        if (resultUri != null) {
            FileToUrl(resultUri);
        }
    }

    /**
     * 裁剪
     *
     * @param uri
     */
    private void startCrop(Uri uri) {
        UCrop.Options options = new UCrop.Options();
        Uri destinationUri = Uri.fromFile(new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "tdkq_" + TimeUtil.getTimeStamp() + ".png"));
        UCrop uCrop = UCrop.of(uri, destinationUri);//第一个参数是裁剪前的uri,第二个参数是裁剪后的uri
        options.useSourceImageAspectRatio();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setAllowedGestures(UCropActivity.ALL, UCropActivity.ALL, UCropActivity.ALL);
        options.setToolbarTitle("身份证裁剪");//设置标题栏文字
        options.setCropGridStrokeWidth(2);//设置裁剪网格线的宽度(我这网格设置不显示，所以没效果)
        options.setCropFrameStrokeWidth(10);//设置裁剪框的宽度
        options.setMaxScaleMultiplier(4);//设置最大缩放比例
        options.setShowCropGrid(true);  //设置是否显示裁剪网格
        options.setCircleDimmedLayer(false);
        // options.withAspectRatio(3, 2);
        options.setShowCropFrame(true); //设置是否显示裁剪边框(true为方形边框)
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));//标题字的颜色以及按钮颜色
        options.setDimmedLayerColor(Color.parseColor("#AA000000"));//设置裁剪外颜色
        options.setToolbarColor(Color.parseColor("#000000")); // 设置标题栏颜色
        options.setStatusBarColor(Color.parseColor("#000000"));//设置状态栏颜色
        options.setCropGridColor(Color.parseColor("#ffffff"));//设置裁剪网格的颜色
        options.setCropFrameColor(Color.parseColor("#ffffff"));//设置裁剪框的颜色
        uCrop.withOptions(options);
        uCrop.start(this);
    }

    private void loadingSuccessOrFailure(int loading_type) {
        if (loading_type == 1) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.success();
            }
        } else if (loading_type == 2) {
            if (mSmartRefreshUtils != null) {
                mSmartRefreshUtils.fail();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (buttomDialog != null) {
            buttomDialog.shutDownDialog();
        }
    }
}
