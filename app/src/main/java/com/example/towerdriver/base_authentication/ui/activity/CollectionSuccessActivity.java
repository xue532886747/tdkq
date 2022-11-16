package com.example.towerdriver.base_authentication.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.ui.utils.IntentUtils;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.baidu.idl.face.platform.utils.DensityUtils;
import com.bumptech.glide.Glide;
import com.example.base_ui.bigimageview.tool.ui.ToastUtil;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_authentication.model.bean.PersonVerifyBean;
import com.example.towerdriver.base_authentication.presenter.CollectionPresenter;
import com.example.towerdriver.base_authentication.view.ICollectionView;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.event.ChangeUserEvent;
import com.example.towerdriver.event.UserInformationEvent;
import com.example.towerdriver.utils.ActivityManager;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.weight.CircleImageView;
import com.hjq.toast.ToastUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 人脸采集成功
 * @date 2021/5/27
 */
public class CollectionSuccessActivity extends BaseActivity<CollectionPresenter> implements ICollectionView {
    private String mName, mCode, mFront_url, mBack_url, mBitmap;
    @BindView(R.id.iv_member_img)
    CircleImageView iv_member_img;
    @BindView(R.id.bt_commit)
    AppCompatButton bt_commit;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (presenter != null) {
                        presenter.getPersonVerify(mBitmap, mCode, mName);
                    }
                    break;
            }
        }
    };

    public static void launch(Activity activity, String name, String code, String front_url, String back_url) {
        Intent intent = new Intent(activity, CollectionSuccessActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("code", code);
        intent.putExtra("front_url", front_url);
        intent.putExtra("back_url", back_url);
        activity.startActivity(intent);
    }

    @Override
    protected CollectionPresenter createPresenter() {
        return new CollectionPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_collect_success;
    }

    @Override
    protected void initView() {
        mName = getIntent().getStringExtra("name");
        mCode = getIntent().getStringExtra("code");
        mFront_url = getIntent().getStringExtra("front_url");
        mBack_url = getIntent().getStringExtra("back_url");
        mBitmap = IntentUtils.getInstance().getBitmap();
    }

    @Override
    protected void initData() {
        if (mBitmap == null) {
            finishTowActivity();
            return;
        }
        Bitmap bmp = base64ToBitmap(mBitmap);
        bmp = FaceSDKManager.getInstance().scaleImage(bmp,
                DensityUtils.dip2px(getApplicationContext(), 97),
                DensityUtils.dip2px(getApplicationContext(), 97));
        Glide.with(this).load(bmp).into(iv_member_img);
        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在验证...");
        Message message = Message.obtain();
        message.what = 1;
        mHandler.sendMessageDelayed(message, 500);
    }

    private Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64Utils.decode(base64Data, Base64Utils.NO_WRAP);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    @OnClick({R.id.bt_commit, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_commit:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_commit)) {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在验证...");
                    if (presenter != null) {
                        presenter.getPersonVerify(mBitmap, mCode, mName);
                    }
                }
                break;
            case R.id.ll_back:
                finishTowActivity();
                break;
        }
    }

    /**
     * 人脸检测成功
     *
     * @param personVerifyBean >=80分，即可认证为成功
     */
    @Override
    public void onVerifySuccess(PersonVerifyBean personVerifyBean) {
        closeDialog();
        Double score = personVerifyBean.getResult().getScore();
        if (score >= 80.0) {
            ToastUtils.show("人脸检测成功!");
            if (presenter != null) {
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在上传信息...");
                presenter.commitApprove(mName, mCode, mFront_url, mBack_url);
            }
        } else {
            ToastUtils.show("人脸检测失败,请重新录制视频!");
        }
    }

    @Override
    public void onVerifyFailure(String msg, int code) {
        ToastUtils.show(msg + ",code = " + code);
        closeDialog();
    }

    /**
     * 上传身份成功
     *
     * @param msg
     */
    @Override
    public void onApproveSuccess(String msg) {
        if (UserUtils.getInstance().isLogin()) {
            UserUtils.getInstance().getLoginBean().setIf_intact(1);
            UserUtils.getInstance().getLoginBean().setName(mName);
        }
        closeDialog();
        ToastUtils.show(msg);
        new UserInformationEvent(true).post();
        new ChangeUserEvent(UserUtils.getInstance().getLoginBean()).post();
        finishTowActivity();
    }

    @Override
    public void onApproveFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void LoadingClose() {
        goToLogin();

    }

    @Override
    public void showFailed(int code, String msg) {
        ToastUtils.show(msg);
        closeDialog();
    }

    /**
     * 关闭两个页面
     */
    private void finishTowActivity() {
        ActivityManager.finishActivity(FacePrepareActivity.class);
        ActivityManager.finishActivity(FaceLivenessExpActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishTowActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IntentUtils.getInstance().release();
        if (mHandler != null) {
            mHandler = null;
        }
    }
}
