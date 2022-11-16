package com.example.towerdriver.repair.base_order.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.Marker;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.base_ui.nice_spinner.NiceSpinner;
import com.example.base_ui.nice_spinner.OnSpinnerItemSelectedListener;
import com.example.base_ui.nice_spinner.SpinnerTextFormatter;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_driver.ui.adapter.GridImageAdapter;
import com.example.towerdriver.base_order_list.model.EntrepotBean;
import com.example.towerdriver.base_order_list.model.OrderStatusBean;
import com.example.towerdriver.base_order_list.model.RentCarBean;
import com.example.towerdriver.base_order_list.model.ScanBean;
import com.example.towerdriver.base_order_list.presenter.RentCarDetailPresenter;
import com.example.towerdriver.base_order_list.ui.activity.OrderListActivity;
import com.example.towerdriver.base_order_list.ui.activity.RentCarDetailActivity;
import com.example.towerdriver.base_order_list.ui.adapter.RentCarAdapter;
import com.example.towerdriver.base_order_list.view.IRentCarDetailView;
import com.example.towerdriver.dialog.ButtomDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.map.NewLocationManager;
import com.example.towerdriver.repair.base_order.presenter.CheckDetailPresenter;
import com.example.towerdriver.repair.base_order.view.ICheckDetailView;
import com.example.towerdriver.utils.MyFileUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.TimeUtil;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.weight.FullyGridLayoutManager;
import com.example.zxing.android.CaptureActivity;
import com.example.zxing.bean.ZxingConfig;
import com.example.zxing.common.Constant;
import com.google.gson.Gson;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.towerdriver.Constant.REQUEST_CODE_SCAN;

/**
 * @author 53288
 * @description 检车
 * @date 2021/7/5
 */
public class CheckCarDetailActivity extends BaseActivity<CheckDetailPresenter> implements ICheckDetailView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @BindView(R.id.iv_qr_code)
    AppCompatImageView iv_qr_code;
    @BindView(R.id.iv_qr_code1)
    AppCompatImageView iv_qr_code1;
    @BindView(R.id.et_name)
    AppCompatEditText et_name;
    @BindView(R.id.et_battery)
    AppCompatEditText et_battery;
    @BindView(R.id.et_content)
    AppCompatEditText et_content;
    @BindView(R.id.bt_commit)
    AppCompatButton bt_commit;
    private String order_id;
    private List<RentCarBean> mList = new ArrayList<>();
    private RentCarAdapter mAdapter;
    private int position;
    @BindView(R.id.rv_images)
    RecyclerView rv_images;
    private GridImageAdapter imagesAdapter;
    private List<String> mImagesList = new ArrayList<>();
    private static final int MaxNumber = 12;             //照片最大上传
    private Uri mPhotoUri;

    @Override
    protected CheckDetailPresenter createPresenter() {
        return new CheckDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_check_detail;
    }

    @Override
    protected void initView() {
        order_id = getIntent().getStringExtra("order_id");
        position = getIntent().getIntExtra("position", 0);
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new RentCarAdapter();
        rv_list.setAdapter(mAdapter);
//        requirePermissionIntent(2, Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.READ_EXTERNAL_STORAGE);
//
        rv_images.setLayoutManager(new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        imagesAdapter = new GridImageAdapter(this);
        imagesAdapter.setList(mImagesList);
        imagesAdapter.setSelectMax(MaxNumber);
        rv_images.setAdapter(imagesAdapter);
        imagesAdapter.setIAddPhotosClickListener(new GridImageAdapter.IAddPhotosClickListener() {
            @Override
            public void add() {                                         //增加图片
                requirePermissionIntent(2, Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            @Override
            public void CheckPosition(int position, String image) {     //选中图片

            }
        });
        rv_images.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                if (parent.getChildLayoutPosition(view) % 3 == 0) {
                    outRect.left = 0;
                    outRect.right = 20;
                } else if (parent.getChildLayoutPosition(view) % 3 == 1) {
                    if (Objects.requireNonNull(parent.getAdapter()).getItemCount() == 1) {
                        outRect.left = 0;
                        outRect.right = 0;
                    }
                    outRect.left = 10;
                    outRect.right = 10;
                } else if (parent.getChildLayoutPosition(view) % 3 == 2) {
                    outRect.left = 20;
                    outRect.right = 0;
                }
            }
        });
    }

    @Override
    protected void initData() {
        mList.add(new RentCarBean("钥匙", "1"));
        mList.add(new RentCarBean("遥控器", "1"));
        mList.add(new RentCarBean("镜子", "1"));
        mList.add(new RentCarBean("餐箱架", "1"));
        mList.add(new RentCarBean("脚垫", "1"));
        mList.add(new RentCarBean("后视镜", "1"));
        mList.add(new RentCarBean("整车外观", "1"));
        mList.add(new RentCarBean("车辆灯", "1"));
        mList.add(new RentCarBean("刹车、刹车手把", "1"));
        mList.add(new RentCarBean("刹车解除P档", "1"));
        mList.add(new RentCarBean("喇叭", "1"));
        mList.add(new RentCarBean("显示屏", "1"));
        mList.add(new RentCarBean("电机启动、加速手把", "1"));
        mList.add(new RentCarBean("车轮轴承", "1"));
        mList.add(new RentCarBean("车架、偏撑、方向柱", "1"));
        mList.add(new RentCarBean("轮胎胎压", "1"));
        mAdapter.setAnimationEnable(true);
        mAdapter.setList(mList);
    }

    @OnClick({R.id.ll_back, R.id.iv_qr_code, R.id.iv_qr_code1, R.id.bt_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_qr_code:
                KeyboardUtils.hideKeyboard(this);
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.iv_qr_code)) {
                    requirePermissionIntent(1, Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                }
                break;
            case R.id.iv_qr_code1:
                KeyboardUtils.hideKeyboard(this);
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.iv_qr_code1)) {
                    requirePermissionIntent(1, Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                }
                break;
            case R.id.bt_commit:
                KeyboardUtils.hideKeyboard(this);
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_commit)) {
                    commit();
                }
                break;
        }
    }

    /**
     * 用户提交
     */
    private void commit() {
        List<RentCarBean> data = mAdapter.getData();
        for (RentCarBean datum : data) {
            LogUtils.d("datum = " + datum.toString());
        }
//        if (TextUtils.isEmpty(et_name.getText().toString())) {
//            ToastUtils.show("请您填写车架号!");
//            return;
//        }
//        if (TextUtils.isEmpty(et_battery.getText().toString())) {
//            ToastUtils.show("请您填写电池编号!");
//            return;
//        }
        String image = checkImage();
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(CheckCarDetailActivity.this, "正在提交...");
            presenter.getCheckDetail(order_id, et_name.getText().toString(),
                    et_battery.getText().toString(), mList, Objects.requireNonNull(et_content.getText()).toString(),image);
        }
    }

    private void setRefreshList() {
        if (presenter != null) {

        }
    }

    /**
     * 根据所需要的权限跳到不同的地方
     *
     * @param args 需要的权限
     */
    private void requirePermissionIntent(int type, String... args) {
        if (presenter != null) {
            presenter.addToRxLife(new RxPermissions(this).
                    requestEachCombined(args).
                    subscribe(permission -> {
                        if (permission.granted) {   //如果权限中有
                            if (type == 1) {
                                Intent intent = new Intent(CheckCarDetailActivity.this, CaptureActivity.class);
                                ZxingConfig config = new ZxingConfig();
                                config.setShowAlbum(false);
                                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                startActivityForResult(intent, REQUEST_CODE_SCAN);
                            } else if (type == 2) {
                                new ButtomDialog(this).Builder("相机拍照", "相册选择").setDialogClickListener(new ButtomDialog.DialogClickListener() {
                                    @Override
                                    public void top(int type) {
                                        startCamera();
                                    }

                                    @Override
                                    public void bottom(int type) {
                                        openAlbum();
                                    }
                                }).show();
                            }
                        } else {            //权限不通过就获取权限
                            showPermissionDialog("需要权限开通", "有些权限未开启，是否前往设置?");
                            // ToastUtils.show("权限未开通,请前往设置开通权限！");
                        }
                    }));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                try {
                    if (content.length() == 17) {
                        et_name.setText(content + "");
                    } else {
                        Gson gson = new Gson();
                        ScanBean scanBean = gson.fromJson(content, ScanBean.class);
                        et_battery.setText(scanBean.getCode() + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show("无法识别二维码，请手动输入");
                }
            }
        }
        switch (requestCode) {
            case com.example.towerdriver.Constant.TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    String path = MyFileUtils.getFileProviderUriToPath(mPhotoUri, this);
                    sendUriToPath(path);
                }
                break;
            case com.example.towerdriver.Constant.CHOOSE_PHOTO:
                if (data == null) {//如果没有拍照或没有选取照片，则直接返回
                    return;
                }
                if (resultCode == RESULT_OK) {
                    ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
                    if (stringArrayListExtra != null && stringArrayListExtra.size() > 0) {
                        String path = stringArrayListExtra.get(0);
                        sendUriToPath(path);
                    }
                }
                break;
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
        startActivityForResult(intent, com.example.towerdriver.Constant.TAKE_PHOTO);//打开相机
    }

    /**
     * 跳转相册
     */
    private void openAlbum() {
        ImageSelector.builder()
                .useCamera(false) // 设置是否使用拍照
                .setSingle(true)  //设置是否单选
                .canPreview(true) //是否可以预览图片，默认为true
                .start(this, com.example.towerdriver.Constant.CHOOSE_PHOTO); // 打开相册
    }

    /**
     * 上传图片至服务器变成网络图片
     *
     * @param path
     */
    public void sendUriToPath(String path) {
        if (presenter != null) {
            presenter.getImageToUrl(path);
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在上传...");
        }
    }

    /**
     * 检查图片，删掉最后一个,
     *
     * @return
     */
    private String checkImage() {
        StringBuilder stringBuilder = new StringBuilder();
        if (mImagesList != null && mImagesList.size() != 0) {
            for (int i = 0; i < mImagesList.size(); i++) {
                String s = mImagesList.get(i);
                stringBuilder.append(s);
                stringBuilder.append(",");
            }
        }
        String a = stringBuilder.toString();
        if (!TextUtils.isEmpty(a)) {
            a = a.substring(0, a.length() - 1);
        }
        return a;
    }

    /**
     * 用户提车
     *
     * @param msg
     */
    @Override
    public void onRentCarDetailSuccess(String msg, OrderStatusBean data) {
        closeDialog();
        ToastUtils.show(msg);
        Intent intent = new Intent(this, OrderListActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("position", position);
        setResult(401, intent);
        finish();
    }

    @Override
    public void onRentCarDetailFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void ImageToUrlSuccess(String url) {
        closeDialog();
        mImagesList.add(url);
        imagesAdapter.setList(mImagesList);
        imagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void ImageToUrlFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
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
        super.onDestroy();

    }

}
