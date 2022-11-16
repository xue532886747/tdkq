package com.example.towerdriver.base_person.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.UriUtils;
import com.example.base_ui.bigimageview.tool.ui.ToastUtil;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;

import com.example.towerdriver.base_authentication.ui.activity.RealAuthActivity;
import com.example.towerdriver.base_change_pass.ui.activity.ChangePassActivity;
import com.example.towerdriver.base_change_phone.ui.activity.CheckNewPhoneActivity;
import com.example.towerdriver.base_change_phone.ui.activity.CheckOldPhoneActivity;
import com.example.towerdriver.base_drivier_coin.ui.activity.DriverCoinActivity;
import com.example.towerdriver.base_invite.ui.activity.ChangeInviteActivity;
import com.example.towerdriver.base_login.bean.UserBean;
import com.example.towerdriver.base_person.presenter.PersonCenterPresenter;
import com.example.towerdriver.base_person.view.PersonCenterView;
import com.example.towerdriver.dialog.ButtomDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.ChangeUserEvent;
import com.example.towerdriver.event.LoginEvent;
import com.example.towerdriver.member_model.MemberInfoBean;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.TimeUtil;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.weight.CircleImageView;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 个人中心设置
 * @date 2021/5/22
 */
public class PersonCenterActivity extends BaseActivity<PersonCenterPresenter> implements PersonCenterView, BaseActivity.PermissionListener {
    @BindView(R.id.cl_change_image)
    ConstraintLayout cl_change_image;
    @BindView(R.id.cl_complete_detail)
    ConstraintLayout cl_complete_detail;
    @BindView(R.id.iv_member_img)
    CircleImageView iv_member_img;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.cl_change_pass)
    ConstraintLayout cl_change_pass;
    @BindView(R.id.cl_change_phone)
    ConstraintLayout cl_change_phone;
    @BindView(R.id.cl_coin)
    ConstraintLayout cl_coin;
    @BindView(R.id.tv_name)
    AppCompatTextView tv_name;
    @BindView(R.id.tv_phone)
    AppCompatTextView tv_phone;
    @BindView(R.id.cl_change_friend)
    ConstraintLayout cl_change_friend;

    private Uri mPhotoUri;
    private int mCameraType = 0;
    private ButtomDialog buttomDialog;


    @Override
    protected PersonCenterPresenter createPresenter() {
        return new PersonCenterPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_person;
    }

    @Override
    protected void initView() {
        setPermissionListener(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.cl_change_image, R.id.cl_complete_detail, R.id.ll_back, R.id.cl_change_pass,
            R.id.cl_change_phone, R.id.cl_coin, R.id.cl_change_friend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_change_image:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_change_image)) {
                    buttomDialog = new ButtomDialog(this);
                    buttomDialog.Builder("相机拍照", "相册选择").setDialogClickListener(new ButtomDialog.DialogClickListener() {
                        @Override
                        public void top(int type) {
                            mCameraType = 0;
                            checkLocationPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);

                        }

                        @Override
                        public void bottom(int type) {
                            mCameraType = 1;
                            checkLocationPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                        }
                    }).show();
                }
                break;
            case R.id.cl_complete_detail:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_complete_detail)) {
                    mCameraType = 2; //INTERACT_ACROSS_USERS_FULL
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        checkLocationPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.INTERACT_ACROSS_PROFILES);
                    } else {
                        checkLocationPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
                    }

                }
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.cl_change_pass:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_change_pass)) {
                    Intent intent = new Intent(this, ChangePassActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.cl_change_phone:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_change_phone)) {
                    Intent intent = new Intent(this, CheckOldPhoneActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.cl_coin:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_coin)) {
                    Intent intent = new Intent(this, DriverCoinActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.cl_change_friend:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.cl_change_friend)) {
                    Intent intent = new Intent(this, ChangeInviteActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 权限请求成功
     */
    @Override
    public void requestPermissionSuccess() {
        if (mCameraType == 0) {
            startCamera();
        } else if (mCameraType == 1) {
            openAlbum();
        } else if (mCameraType == 2) {
            startActivity(new Intent(PersonCenterActivity.this, RealAuthActivity.class));
        }
    }

    /**
     * 权限请求失败，跳转至请求权限的地方
     */
    @Override
    public void requestPermissionFailure() {

    }

    /**
     * 用户刷新数据
     */
    private void refreshUser() {
        if (UserUtils.getInstance().isLogin()) {
            String member_img = UserUtils.getInstance().getLoginBean().getMember_img();
            Glide.with(this).load(member_img).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(iv_member_img);
            tv_name.setText(UserUtils.getInstance().getLoginBean().getName() + "");
            tv_phone.setText(UserUtils.getInstance().getLoginBean().getPhone() + "");
        }
    }

    /**
     * 启动相机
     */
    public void startCamera() {
        if (buttomDialog != null) {
            buttomDialog.cancle();
        }
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
        if (buttomDialog != null) {
            buttomDialog.cancle();
        }
        ImageSelector.builder()
                .useCamera(false) // 设置是否使用拍照
                .setSingle(true)  //设置是否单选
                .canPreview(true) //是否可以预览图片，默认为true
                .start(this, Constant.CHOOSE_PHOTO); // 打开相册
//        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        openAlbumIntent.setType("image/*");
//        startActivityForResult(openAlbumIntent, Constant.CHOOSE_PHOTO);//打开相册
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
        if (resultUri != null) {
            File file = null;   //图片地址
            try {
                file = new File(new URI(resultUri.toString()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            if (presenter != null) {
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在上传...");
                presenter.changeHeadImage(file);
            }
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
        options.setCompressionQuality(50);
        options.setAllowedGestures(UCropActivity.ALL, UCropActivity.ALL, UCropActivity.ALL);
        options.setToolbarTitle("头像裁剪");//设置标题栏文字
        options.setCropGridStrokeWidth(0);//设置裁剪网格线的宽度(我这网格设置不显示，所以没效果)
        options.setCropFrameStrokeWidth(10);//设置裁剪框的宽度
        options.setMaxScaleMultiplier(4);//设置最大缩放比例
        options.setShowCropGrid(false);  //设置是否显示裁剪网格
        options.setCircleDimmedLayer(true);
        options.setShowCropFrame(false); //设置是否显示裁剪边框(true为方形边框)
        options.setToolbarWidgetColor(Color.parseColor("#ffffff"));//标题字的颜色以及按钮颜色
        options.setDimmedLayerColor(Color.parseColor("#AA000000"));//设置裁剪外颜色
        options.setToolbarColor(Color.parseColor("#000000")); // 设置标题栏颜色
        options.setStatusBarColor(Color.parseColor("#000000"));//设置状态栏颜色
        options.setCropGridColor(Color.parseColor("#ffffff"));//设置裁剪网格的颜色
        options.setCropFrameColor(Color.parseColor("#ffffff"));//设置裁剪框的颜色
        uCrop.withOptions(options);
        uCrop.start(this);
    }

    /**
     * 头像上传成功
     *
     * @param type 登陆类型
     * @param img  头像
     * @param msg
     */
    @Override
    public void changeImgSuccess(int type, String img, String msg) {
        closeDialog();
        ToastUtils.show(msg);
        UserUtils.getInstance().getLoginBean().setMember_img(img);
        Glide.with(this).load(img).placeholder(R.mipmap.log_image_bg).error(R.mipmap.image_head).into(iv_member_img);
        new ChangeUserEvent(UserUtils.getInstance().getLoginBean()).post();
    }

    /**
     * 头像上传失败
     *
     * @param type 登陆类型
     * @param msg
     */
    @Override
    public void changeImgFailure(int type, String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void getMemberInfoSuccess(MemberInfoBean memberInfoBean) {
        if (UserUtils.getInstance().isLogin()) {
            UserBean userBean = UserUtils.getInstance().getLoginBean();
            userBean.setName(memberInfoBean.getName());
            userBean.setIf_intact(memberInfoBean.getIf_intact());
            userBean.setMember_img(memberInfoBean.getMember_img());
            userBean.setIntegral_image(memberInfoBean.getIntegral_image());
            userBean.setPhone(memberInfoBean.getPhone());
            userBean.setQr_image(memberInfoBean.getQr_image());
            userBean.setLogin_type(memberInfoBean.getType());
        }
        refreshUser();
    }

    @Override
    public void getMemberInfoFailure(String msg) {

    }

    @Override
    public void LoadingClose() {
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    protected void onDestroy() {
        if (buttomDialog != null) {
            buttomDialog.shutDownDialog();
        }
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.getMemberInfo();
        }
        refreshUser();
    }
}
