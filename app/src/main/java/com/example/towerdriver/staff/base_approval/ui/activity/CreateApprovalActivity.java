package com.example.towerdriver.staff.base_approval.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.base_ui.file_picker.FilePicker;
import com.example.base_ui.file_picker.model.EssFile;
import com.example.base_ui.file_picker.util.Const;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_driver.presenter.CreateApprovalPresenter;
import com.example.towerdriver.base_driver.ui.adapter.GridImageAdapter;
import com.example.towerdriver.dialog.ButtomDialog;
import com.example.towerdriver.dialog.CenterDialog;
import com.example.towerdriver.dialog.DownLoadDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.staff.base_approval.ui.adapter.ApprovalFileListAdapter;
import com.example.towerdriver.staff.base_approval.ui.adapter.PersonListAdapter;
import com.example.towerdriver.staff.base_approval.view.CreateApprovalView;
import com.example.towerdriver.staff.base_approval.model.NewDepartmentListBean;
import com.example.towerdriver.utils.MyFileUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.TimeUtil;
import com.example.towerdriver.utils.tools.LogUtils;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description 新建审批
 * @date 2021/7/8
 */
public class CreateApprovalActivity extends BaseActivity<CreateApprovalPresenter> implements CreateApprovalView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.et_name)
    AppCompatEditText et_name;
    @BindView(R.id.et_content)
    AppCompatEditText et_content;
    @BindView(R.id.rv_image_list)
    RecyclerView rv_image_list;
    @BindView(R.id.rv_file_list)
    RecyclerView rv_file_list;
    @BindView(R.id.rv_person_list)
    RecyclerView rv_person_list;
    @BindView(R.id.bt_add)
    AppCompatButton bt_add;
    @BindView(R.id.bt_commit)
    AppCompatButton bt_commit;
    @BindView(R.id.bt_add_files)
    AppCompatButton bt_add_files;
    private GridImageAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private static final int MaxNumber = 6;             //照片最大上传
    private Uri mPhotoUri;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private PersonListAdapter mPersonListAdapter;
    private List<String> mFileList = new ArrayList<>();
    private ApprovalFileListAdapter approvalFileListAdapter;
    private DownLoadDialog downLoadDialog;          //上传

    @Override
    protected CreateApprovalPresenter createPresenter() {
        return new CreateApprovalPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_create_approval;
    }


    @Override
    protected void initView() {
        rv_image_list.setLayoutManager(new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rv_image_list.setNestedScrollingEnabled(false);
        rv_file_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_file_list.setNestedScrollingEnabled(false);
        rv_person_list.setLayoutManager(new GridLayoutManager(this, 4));
        rv_person_list.setNestedScrollingEnabled(false);
        mAdapter = new GridImageAdapter(this);
        mAdapter.setList(mList);
        mAdapter.setSelectMax(MaxNumber);
        rv_image_list.setAdapter(mAdapter);
        mPersonListAdapter = new PersonListAdapter();
        rv_person_list.setAdapter(mPersonListAdapter);
        approvalFileListAdapter = new ApprovalFileListAdapter();
        rv_file_list.setAdapter(approvalFileListAdapter);
        approvalFileListAdapter.addChildClickViewIds(R.id.iv_delete);
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
                requirePermissionIntent(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            @Override
            public void CheckPosition(int position, String image) {     //选中图片

            }
        });
        approvalFileListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                approvalFileListAdapter.removeAt(position);
                mFileList.remove(position);
            }
        });
    }

    @Override
    protected void initData() {
        rv_image_list.addItemDecoration(new RecyclerView.ItemDecoration() {
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

    /**
     * 根据所需要的权限跳到不同的地方
     *
     * @param args 需要的权限
     */
    private void requirePermissionIntents(String... args) {
        if (presenter != null) {
            presenter.addToRxLife(new RxPermissions(this).
                    requestEachCombined(args).
                    subscribe(permission -> {
                        if (permission.granted) {   //如果权限中有
                            FilePicker
                                    .from(this)
                                    .chooseForMimeType()
                                    .setMaxCount(1)
                                    .setFileTypes("pdf", "xls", "doc", "ppt", "zip", "txt")
                                    .requestCode(REQUEST_CODE_CHOOSE)
                                    .start();
                        } else {
                            showPermissionDialog("需要权限开通", "有些权限未开启，是否前往设置?");
                        }
                    }));
        }
    }

    @OnClick({R.id.ll_back, R.id.bt_add, R.id.bt_commit, R.id.bt_add_files})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                onBackPressed();
                break;
            case R.id.bt_add:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_add)) {
                    Intent intent = new Intent(this, AudioListActivity.class);
                    if (mPersonListAdapter != null && mPersonListAdapter.getData() != null && mPersonListAdapter.getData().size() > 0) {
                        intent.putExtra("audit_id", mPersonListAdapter.getData().get(0).getId() + "");
                    }
                    startActivityForResult(intent, 400);
                }
                break;
            case R.id.bt_commit:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_commit)) {
                    commit();
                }
                break;
            case R.id.bt_add_files:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_add_files)) {
                    requirePermissionIntents(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                }
                break;
        }
    }

    private void commit() {
        String audit_id;
        if (mPersonListAdapter != null && mPersonListAdapter.getData() != null && mPersonListAdapter.getData().size() > 0) {
            audit_id = mPersonListAdapter.getData().get(0).getId() + "";
        } else {
            ToastUtils.show("您还未选择审批人!");
            return;
        }
        String title;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_name.getText()).toString())) {
            ToastUtils.show("您还未编辑标题!");
            return;
        }
        title = et_name.getText().toString();
        String content;
        if (TextUtils.isEmpty(Objects.requireNonNull(et_content.getText()).toString())) {
            ToastUtils.show("您还未编辑内容");
            return;
        }
        content = et_content.getText().toString();
        String image = checkImage();
        String file = checkFile();
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在提交...");
            presenter.release(audit_id, title, content, image, file);
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
            case 400:
                if (resultCode == 401) {
                    if (data == null) {
                        return;
                    }
                    NewDepartmentListBean data1 = (NewDepartmentListBean) data.getSerializableExtra("data");
                    List<NewDepartmentListBean> newDepartmentListBeans = new ArrayList<>();
                    newDepartmentListBeans.add(data1);
                    mPersonListAdapter.setList(newDepartmentListBeans);
                }
                break;
            case REQUEST_CODE_CHOOSE:
                if (data != null) {
                    ArrayList<EssFile> essFileList = data.getParcelableArrayListExtra(Const.EXTRA_RESULT_SELECTION);
                    String path = null;
                    for (EssFile essFile : essFileList) {
                        path = essFile.getFile().getPath();
                    }
                    if (presenter != null) {
                        downLoadDialog = new DownLoadDialog(this).Builder("正在上传...");
                        downLoadDialog.show();
                        presenter.getImageToUrl(1, path);
                    }
                }
                break;
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

    /**
     * 检查file
     *
     * @return
     */
    private String checkFile() {
        StringBuilder stringBuilder = new StringBuilder();
        if (mFileList != null && mFileList.size() != 0) {
            for (int i = 0; i < mFileList.size(); i++) {
                String s = mFileList.get(i);
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
     * 上传图片至服务器变成网络图片
     *
     * @param path
     */
    public void sendUriToPath(String path) {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在上传...");
            presenter.getImageToUrl(0, path);
        }
    }

    /**
     * 提交成功
     *
     * @param msg
     */
    @Override
    public void createApprovalSuccess(String msg) {
        closeDialog();
        Intent intentTemp = getIntent();
        setResult(401, intentTemp);
        finish();
    }

    @Override
    public void createApprovalFailure(String msg) {
        closeDialog();
        ToastUtils.show(msg);
    }

    /**
     * 进度条
     *
     * @param contentLength
     * @param mCurrentLength
     */
    @Override
    public void FileToUrlProgress(long contentLength, long mCurrentLength) {
        // LogUtils.d("contentLength = " + contentLength + " , mCurrentLength = " + mCurrentLength);
        float pressent = (float) mCurrentLength / contentLength * 100;
        LogUtils.d("进度条 =" + (int) pressent);
        if (downLoadDialog != null) {
            downLoadDialog.setNumber((int) pressent);
        }
    }

    @Override
    public void FileToUrlSuccess(int type, String url) {
        closeDialog();
        if (downLoadDialog != null) {
            downLoadDialog.cancle();
        }
        if (type == 0) {
            mList.add(url);
            mAdapter.setList(mList);
            mAdapter.notifyDataSetChanged();
        } else {
            mFileList.add(url);
            approvalFileListAdapter.setList(mFileList);
        }
    }

    @Override
    public void FileToUrlFailure(String msg) {
        closeDialog();
        if (downLoadDialog != null) {
            downLoadDialog.cancle();
        }
        ToastUtils.show(msg);
    }

    @Override
    public void LoadingClose() {
        if (downLoadDialog != null) {
            downLoadDialog.cancle();
        }
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        if (downLoadDialog != null) {
            downLoadDialog.cancle();
        }
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
        if (!TextUtils.isEmpty(et_name.getText().toString())) {
            return true;
        }
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
        if (downLoadDialog != null) {
            downLoadDialog.shutDownDialog();
        }
    }
}
