package com.example.towerdriver.staff.base_approval.ui.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.base_ui.bigimageview.ImagePreview;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.dialog.NewDownLoadDialog;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.staff.base_approval.model.ApprovalDetailBean;
import com.example.towerdriver.staff.base_approval.presenter.ApprovalDetailPresenter;
import com.example.towerdriver.staff.base_approval.ui.adapter.ApprovalFileListAdapter;
import com.example.towerdriver.staff.base_approval.ui.adapter.ImageListAdapter;
import com.example.towerdriver.staff.base_approval.view.ApprovalDetailView;
import com.example.towerdriver.utils.SmartRefreshUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.example.base_ui.bigimageview.ImagePreview.LoadStrategy.AlwaysThumb;

/**
 * @author 53288
 * @description 审批详情页
 * @date 2021/7/10
 */
public class ApprovalDetailActivity extends BaseActivity<ApprovalDetailPresenter> implements ApprovalDetailView {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;
    @BindView(R.id.rv_file_list)
    RecyclerView rv_file_list;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_title)
    AppCompatTextView tv_title;             //谁提交的审批
    @BindView(R.id.tv_approval_name)
    AppCompatTextView tv_approval_name;     //审批人
    @BindView(R.id.tv_status_name)
    AppCompatTextView tv_status_name;       //审批状态
    @BindView(R.id.tv_name)
    AppCompatTextView tv_name;              //审批内容
    @BindView(R.id.tv_content)
    AppCompatTextView tv_content;              //审批详情
    @BindView(R.id.iv_status_image)
    AppCompatImageView iv_status_image;         //审批状态
    private SmartRefreshUtils mSmartRefreshUtils;
    private String id;                      //id
    private ImageListAdapter imageListAdapter;
    private ApprovalFileListAdapter approvalFileListAdapter;
    private NewDownLoadDialog newDownLoadDialog;

    public static void launch(Activity activity, String id) {
        Intent intent = new Intent(activity, ApprovalDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    @Override
    protected ApprovalDetailPresenter createPresenter() {
        return new ApprovalDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_approval_detail;
    }

    @Override
    protected void initView() {
        id = getIntent().getStringExtra("id");
        rv_list.setLayoutManager(new GridLayoutManager(this, 3));
        rv_list.setNestedScrollingEnabled(false);
        rv_file_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_file_list.setNestedScrollingEnabled(false);
        mSmartRefreshUtils = SmartRefreshUtils.with(smartRefreshLayout);
        mSmartRefreshUtils.pureScrollMode();
        imageListAdapter = new ImageListAdapter();
        imageListAdapter.addChildClickViewIds(R.id.iv_image);
        imageListAdapter.setAnimationEnable(true);
        rv_list.setAdapter(imageListAdapter);
        approvalFileListAdapter = new ApprovalFileListAdapter();
        approvalFileListAdapter.addChildClickViewIds(R.id.btn_preview);
        approvalFileListAdapter.setAnimationEnable(true);
        rv_file_list.setAdapter(approvalFileListAdapter);
        approvalFileListAdapter.setVisible(true);
    }

    @Override
    protected void initData() {
        if (rv_list.getItemDecorationCount() == 0) {
            rv_list.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.bottom = 20;
                }
            });
        }
        imageListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ArrayList<String> data = (ArrayList<String>) adapter.getData();
                seePhoto(position, data.get(position), data);
            }
        });
        approvalFileListAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ArrayList<String> data = (ArrayList<String>) adapter.getData();
                //试一下下载
//                presenter.getDownLoad(data.get(position));
                createDownDialog(data.get(position));
            }
        });
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
        refreshList();
    }

    /**
     * 生产弹出的dialog
     *
     * @param s
     */
    private void createDownDialog(String s) {
        newDownLoadDialog = new NewDownLoadDialog(this).Builder("正在下载...").
                setDialogClickListener(new NewDownLoadDialog.DialogClickListener() {
                    @Override
                    public void downLoadCancel(String name) {
                        LogUtils.d("downLoadCancel" + " name - " + name);
                        deleteFiles(name);
                    }

                    @Override
                    public void downLoadFinish(String name) {
                        LogUtils.d("downLoadFinish" + " name - " + name);
                        ToastUtils.show("文件可在文件管理中查看");
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(name))));
//                        QbSdk.openFileReader(ApprovalDetailActivity.this, name, null, new ValueCallback<String>() {
//                            @Override
//                            public void onReceiveValue(String s) {
//                                if ("fileReaderClosed".equals(s)) {
//                                    QbSdk.closeFileReader(ApprovalDetailActivity.this);
//                                }
//                            }
//                        });
                        openFiles(name);
                        if (newDownLoadDialog != null) {
                            newDownLoadDialog.shutDownDialog();
                        }


                    }

                    @Override
                    public void downLoadError(String name) {
                        deleteFiles(name);
                        LogUtils.d("downLoadError" + " name - " + name);

                    }

                    @Override
                    public void downLoadStop(String name) {
                        LogUtils.d("downLoadStop" + " name - " + name);
                        deleteFiles(name);

                    }
                }).show();
        newDownLoadDialog.getDownLoad(s);
    }


    boolean openFiles(String path) {
        String THIRD_PACKAGE = "ThirdPackage";
        String OPEN_MODE = "OpenFile";
        String SEND_CLOSE_BROAD = "SendCloseBroad";
        String CLEAR_TRACE = "ClearTrace";
        String packageName = "cn.wps.moffice_eng";
        String className = "cn.wps.moffice.documentmanager.PreStartActivity2";
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(OPEN_MODE, "READ_ONLY");
        //打开模式
        bundle.putBoolean(SEND_CLOSE_BROAD, true);
        //关闭时是否发送广播
        bundle.putString(THIRD_PACKAGE, "com.example.towerdriver");
        //第三方应用的包名，用于对改应用合法性的验证
        bundle.putBoolean(CLEAR_TRACE, true);
        //清除打开记录
        //bundle.putBoolean(CLEAR_FILE, true);
        //关闭后删除打开文件
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setClassName(packageName, className);

        File file = new File(path);
        if (file == null || !file.exists()) {
            return false;
        }
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setData(uri);
        intent.putExtras(bundle);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public void deleteFiles(String name) {
        File file = new File(name);
        if (file.exists()) {
            boolean delete = file.delete();
        }
        if (newDownLoadDialog != null) {
            newDownLoadDialog.shutDownDialog();
        }
    }


    /**
     * 阅读照片
     *
     * @param current_position
     * @param current_url
     * @param all_image
     */
    private void seePhoto(int current_position, String current_url, List<String> all_image) {
        ImagePreview.getInstance().
                setContext(this).
                setIndex(current_position).
                setImageList(all_image).
                setLoadStrategy(AlwaysThumb).
                setEnableClickClose(true).
                setEnableDragClose(true).
                setEnableUpDragClose(true).
                setEnableDragCloseIgnoreScale(true).
                setShowCloseButton(true).
                setShowDownButton(false).
                setShowIndicator(true).
                setErrorPlaceHolder(R.drawable.load_failed).
                start();
    }

    /**
     * 下拉刷新
     */
    private void refreshList() {
        if (presenter != null) {
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(this, "正在加载...");
            presenter.getApprovalDetail(id);
        }
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
        }
    }

    /**
     * 详情
     *
     * @param approvalDetailBean
     */
    @Override
    public void approvalDetailSuccess(ApprovalDetailBean approvalDetailBean) {
        closeDialog();
        loadingSuccessOrFailure(1);
        if (approvalDetailBean != null) {
            tv_title.setText(approvalDetailBean.getStaff_name() + "提交的审批");
            tv_approval_name.setText("审批人: " + approvalDetailBean.getAudit_name());
            tv_status_name.setText(approvalDetailBean.getStatus_name());
            if (approvalDetailBean.getStatus() == 1) {
                Glide.with(this).load(R.mipmap.daishenhes).skipMemoryCache(true).into(iv_status_image);
            } else if (approvalDetailBean.getStatus() == 2) {
                Glide.with(this).load(R.mipmap.yishenhe).skipMemoryCache(true).into(iv_status_image);
            } else if (approvalDetailBean.getStatus() == 3) {
                Glide.with(this).load(R.mipmap.weitongguo).skipMemoryCache(true).into(iv_status_image);
            }
            tv_name.setText(approvalDetailBean.getTitle());
            tv_content.setText(approvalDetailBean.getContent());
            if (approvalDetailBean.getImages() != null) {
                imageListAdapter.setList(approvalDetailBean.getImages());
            }
            if (approvalDetailBean.getFiles() != null) {
                approvalFileListAdapter.setList(approvalDetailBean.getFiles());
            }
        }
    }

    @Override
    public void approvalDetailFailure(String msg) {
        ToastUtils.show(msg);
        closeDialog();
        loadingSuccessOrFailure(2);
    }

    @Override
    public void getDownloadProgress(long downloadLength, long contentLength) {
        LogUtils.d("downloadLength = " + downloadLength + " , contentLength = " + contentLength);
    }

    @Override
    public void getDownLoadSuccess(String name) {
        LogUtils.d("name = " + name);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(name))));
    }

    @Override
    public void getDownLoadFailure(String name) {
        LogUtils.d("name = " + name);
    }

    @Override
    public void LoadingClose() {
        loadingSuccessOrFailure(2);
        goToLogin();
    }

    @Override
    public void showFailed(int code, String msg) {
        closeDialog();
        loadingSuccessOrFailure(2);
        ToastUtils.show(msg);
    }

    /**
     * 根据返回的状态判断显示空还是列表
     *
     * @param loading_type
     */
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
        if (newDownLoadDialog != null) {
            newDownLoadDialog.shutDownDialog();
        }

        super.onDestroy();
    }
}
