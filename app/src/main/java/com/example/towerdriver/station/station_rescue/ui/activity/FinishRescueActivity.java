package com.example.towerdriver.station.station_rescue.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_driver.ui.adapter.GridImageAdapter;
import com.example.towerdriver.base_order_list.ui.activity.OrderDetailActivity;
import com.example.towerdriver.base_order_list.ui.activity.OrderListActivity;
import com.example.towerdriver.dialog.ButtomDialog;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.station.base.activity.StationMainActivity;
import com.example.towerdriver.station.station_rescue.model.RescueBean;
import com.example.towerdriver.station.station_rescue.presenter.FinishRescuePresenter;
import com.example.towerdriver.station.station_rescue.ui.fragment.StationRescueListFragment;
import com.example.towerdriver.station.station_rescue.view.IFinishRescueView;
import com.example.towerdriver.utils.MyFileUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.TimeUtil;
import com.example.towerdriver.utils.tools.KeyboardUtils;
import com.example.towerdriver.weight.FullyGridLayoutManager;
import com.hjq.toast.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 完成救援
 * @date 2021/7/2
 */
public class FinishRescueActivity extends BaseActivity<FinishRescuePresenter> implements IFinishRescueView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.et_content)
    AppCompatEditText et_content;   //内容
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.bt_release)
    AppCompatButton bt_release;     //发布
    private GridImageAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private static final int MaxNumber = 3;             //照片最大上传
    private Uri mPhotoUri;
    private boolean isReleaseSuccess = false;           //发布成功
    private String rescue_id;
    private int position = 0;

    @Override
    protected FinishRescuePresenter createPresenter() {
        return new FinishRescuePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_finish_rescue;
    }

    @Override
    protected void initView() {
        rescue_id = getIntent().getStringExtra("rescue_id");
        position = getIntent().getIntExtra("position", 0);
        rv_list.setLayoutManager(new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        mAdapter = new GridImageAdapter(this);
        mAdapter.setList(mList);
        mAdapter.setSelectMax(MaxNumber);
        rv_list.setAdapter(mAdapter);
        et_content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*告诉父组件不要拦截他的触摸事件*/
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    /*告诉父组件可以拦截他的触摸事件*/
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        mAdapter.setIAddPhotosClickListener(new GridImageAdapter.IAddPhotosClickListener() {
            @Override
            public void add() {                                         //增加图片
                requirePermissionIntent(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            @Override
            public void CheckPosition(int position, String image) {     //选中图片

            }
        });
        rv_list.addItemDecoration(new RecyclerView.ItemDecoration() {
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

    }

    /**
     * 根据所需要的权限跳到不同的地方
     *
     * @param args 需要的权限
     */
    private void requirePermissionIntent(String... args) {
        if (presenter != null) {
            presenter.addToRxLife(new RxPermissions(this).
                    requestEachCombined(args).
                    subscribe(permission -> {
                        if (permission.granted) {   //如果权限中有
                            ButtomDialog buttomDialog = new ButtomDialog(this);
                            buttomDialog.Builder("相机拍照", "相册选择").setDialogClickListener(new ButtomDialog.DialogClickListener() {
                                @Override
                                public void top(int type) {
                                    startCamera();
                                }

                                @Override
                                public void bottom(int type) {
                                    openAlbum();
                                }
                            }).show();
                        } else {
                            showPermissionDialog("需要权限开通", "有些权限未开启，是否前往设置?");
                        }
                    }));
        }
    }

    @OnClick({R.id.ll_back, R.id.bt_release})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.bt_release:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_release)) {
                    KeyboardUtils.hideKeyboard(this);
                    commit();
                }
                break;
        }
    }

    /**
     * 发布
     */
    private void commit() {
        String content = et_content.getText().toString();
        String image = checkImage();
        if (TextUtils.isEmpty(image)) {
            ToastUtils.show("请上传图片！");
            return;
        }
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在上传...");
            presenter.release(rescue_id, content, image);
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
                    String path = MyFileUtils.getFileProviderUriToPath(mPhotoUri, this);
                    sendUriToPath(path);
                }
                break;
            case Constant.CHOOSE_PHOTO:
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
        if (mList != null && mList.size() != 0) {
            for (int i = 0; i < mList.size(); i++) {
                String s = mList.get(i);
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

    @Override
    public void finishRescueSuccess(String msg, RescueBean rescueBean) {
        isReleaseSuccess = true;
        closeDialog();
        ToastUtils.show(msg);
        Intent intent = getIntent();
        intent.putExtra("data", rescueBean);
        intent.putExtra("position", position);
        setResult(401, intent);
        finish();
    }

    @Override
    public void finishRescueFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    @Override
    public void imageToUrlSuccess(String url) {
        closeDialog();
        mList.add(url);
        mAdapter.setList(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void imageToUrlFailure(String msg) {
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
    public void onBackPressed() {
        if (checkHaveContent()) {
            CenterDialog centerDialog = new CenterDialog(this);
            centerDialog.Builder("是否放弃编辑?", "确认", "取消").setDialogClickListener(new CenterDialog.DialogClickListener() {
                @Override
                public void top(int type) {
                    centerDialog.cancle();
                    finish();
                }

                @Override
                public void bottom(int type) {
                    centerDialog.cancle();
                }
            });
            centerDialog.show();
        } else {
            finish();
        }
    }

    /**
     * 检查是否有内容
     */
    private boolean checkHaveContent() {
        if (!TextUtils.isEmpty(et_content.getText().toString())) {
            return true;
        }
        if (mList != null && mList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
