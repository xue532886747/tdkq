package com.example.towerdriver.base_authentication.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceEnvironment;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;
import com.baidu.idl.face.platform.listener.IInitCallback;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_authentication.presenter.FacePreparePresenter;
import com.example.towerdriver.base_authentication.view.IFacePrepareView;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.face.manager.QualityConfigManager;
import com.example.towerdriver.utils.face.model.Const;
import com.example.towerdriver.utils.face.model.QualityConfig;
import com.example.towerdriver.utils.sp.SettingUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.utils.tools.StatusBarUtil;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.towerdriver.Constant.BAIDU_FACE_FILE_NAME;
import static com.example.towerdriver.Constant.BAIDU_FACE_LICENSEID;

/**
 * @author 53288
 * @description 人脸识别准备页面
 * @date 2021/5/26
 */
public class FacePrepareActivity extends BaseActivity<FacePreparePresenter> implements IFacePrepareView {
    @BindView(R.id.bt_commit)
    AppCompatButton bt_commit;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    private String mName, mCode, mFront_url, mBack_url;

    @Override
    protected FacePreparePresenter createPresenter() {
        return new FacePreparePresenter(this);
    }

    public static void launch(Activity activity, String name, String code, String front_url, String back_url) {
        Intent intent = new Intent(activity, FacePrepareActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("code", code);
        intent.putExtra("front_url", front_url);
        intent.putExtra("back_url", back_url);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_face_prepare;
    }

    @Override
    protected void initView() {
        mName = getIntent().getStringExtra("name");
        mCode = getIntent().getStringExtra("code");
        mFront_url = getIntent().getStringExtra("front_url");
        mBack_url = getIntent().getStringExtra("back_url");
        boolean success = setFaceConfig();
        if (!success) {
            ToastUtils.show("初始化失败,请联系平台!");
            return;
        }
        FaceSDKManager.getInstance().initialize(this, BAIDU_FACE_LICENSEID, BAIDU_FACE_FILE_NAME, new IInitCallback() {
            @Override
            public void initSuccess() {
                LogUtils.d("初始化成功！");

            }

            @Override
            public void initFailure(int i, String s) {
                LogUtils.d("初始化失败！" + " i = " + i + " , s = " + s);
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化面部识别
     *
     * @return
     */
    private boolean setFaceConfig() {
        FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();
        // 设置模糊度阈值
        QualityConfigManager manager = QualityConfigManager.getInstance();
        manager.readQualityFile(getApplicationContext(), Const.QUALITY_NORMAL);
        QualityConfig qualityConfig = manager.getConfig();
        if (qualityConfig == null) {
            return false;
        }
        // 设置模糊度阈值
        config.setBlurnessValue(qualityConfig.getBlur());
        // 设置最小光照阈值（范围0-255）
        config.setBrightnessValue(qualityConfig.getMinIllum());
        // 设置最大光照阈值（范围0-255）
        config.setBrightnessMaxValue(qualityConfig.getMaxIllum());
        // 设置左眼遮挡阈值
        config.setOcclusionLeftEyeValue(qualityConfig.getLeftEyeOcclusion());
        // 设置右眼遮挡阈值
        config.setOcclusionRightEyeValue(qualityConfig.getRightEyeOcclusion());
        // 设置鼻子遮挡阈值
        config.setOcclusionNoseValue(qualityConfig.getNoseOcclusion());
        // 设置嘴巴遮挡阈值
        config.setOcclusionMouthValue(qualityConfig.getMouseOcclusion());
        // 设置左脸颊遮挡阈值
        config.setOcclusionLeftContourValue(qualityConfig.getLeftContourOcclusion());
        // 设置右脸颊遮挡阈值
        config.setOcclusionRightContourValue(qualityConfig.getRightContourOcclusion());
        // 设置下巴遮挡阈值
        config.setOcclusionChinValue(qualityConfig.getChinOcclusion());
        // 设置人脸姿态角阈值
        config.setHeadPitchValue(qualityConfig.getPitch());
        config.setHeadYawValue(qualityConfig.getYaw());
        config.setHeadRollValue(qualityConfig.getRoll());
        // 设置可检测的最小人脸阈值
        config.setMinFaceSize(FaceEnvironment.VALUE_MIN_FACE_SIZE);
        // 设置可检测到人脸的阈值
        config.setNotFaceValue(FaceEnvironment.VALUE_NOT_FACE_THRESHOLD);
        // 设置闭眼阈值
        config.setEyeClosedValue(FaceEnvironment.VALUE_CLOSE_EYES);
        // 设置图片缓存数量
        config.setCacheImageNum(FaceEnvironment.VALUE_CACHE_IMAGE_NUM);
        // 设置活体动作，通过设置list，LivenessTypeEunm.Eye, LivenessTypeEunm.Mouth,
        // LivenessTypeEunm.HeadUp, LivenessTypeEunm.HeadDown, LivenessTypeEunm.HeadLeft,
        // LivenessTypeEunm.HeadRight
        List<LivenessTypeEnum> livenessList = new ArrayList<>();
        livenessList.add(LivenessTypeEnum.Eye);
        livenessList.add(LivenessTypeEnum.Mouth);
        livenessList.add(LivenessTypeEnum.HeadRight);
        config.setLivenessTypeList(livenessList);
        // 设置动作活体是否随机
        config.setLivenessRandom(true);
        // 设置开启提示音
        config.setSound(true);
        // 原图缩放系数
        config.setScale(FaceEnvironment.VALUE_SCALE);
        // 抠图宽高的设定，为了保证好的抠图效果，建议高宽比是4：3
        config.setCropHeight(FaceEnvironment.VALUE_CROP_HEIGHT);
        config.setCropWidth(FaceEnvironment.VALUE_CROP_WIDTH);
        // 抠图人脸框与背景比例
        config.setEnlargeRatio(FaceEnvironment.VALUE_CROP_ENLARGERATIO);
        // 加密类型，0：Base64加密，上传时image_sec传false；1：百度加密文件加密，上传时image_sec传true
        config.setSecType(FaceEnvironment.VALUE_SEC_TYPE);
        // 检测超时设置
        config.setTimeDetectModule(FaceEnvironment.TIME_DETECT_MODULE);
        // 检测框远近比率
        config.setFaceFarRatio(FaceEnvironment.VALUE_FAR_RATIO);
        config.setFaceClosedRatio(FaceEnvironment.VALUE_CLOSED_RATIO);
        FaceSDKManager.getInstance().setFaceConfig(config);
        return true;
    }

    @OnClick({R.id.bt_commit, R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
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

    private void commit() {
        FaceLivenessExpActivity.launch(this, mName, mCode, mFront_url, mBack_url);
    }


    @Override
    public void LoadingClose() {

    }

    @Override
    public void showFailed(int code, String msg) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FaceSDKManager.getInstance().release();
    }
}
