package com.example.towerdriver.base_code.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base_code.presenter.InvitePresenter;
import com.example.towerdriver.base_code.view.InviteView;
import com.example.towerdriver.base_driver.ui.activity.DriverReleaseActivity;
import com.example.towerdriver.base_invite.ui.activity.InviteListActivity;
import com.example.towerdriver.base_order_list.ui.activity.OrderDetailActivity;
import com.example.towerdriver.base_share.UmShareUtil;
import com.example.towerdriver.dialog.WeiboDialogUtils;
import com.example.towerdriver.utils.MyFileUtils;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.example.towerdriver.utils.sp.UserUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;
import com.umeng.socialize.UMShareAPI;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 53288
 * @description 邀请好友分享码
 * @date 2021/5/31
 */
public class InviteActivity extends BaseActivity<InvitePresenter> implements InviteView {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.ll_share)
    LinearLayout ll_share;
    @BindView(R.id.iv_my_code)
    AppCompatImageView iv_my_code;
    @BindView(R.id.bt_invite_list)
    AppCompatButton bt_invite_list;
    @BindView(R.id.bt_down_code)
    AppCompatButton bt_down_code;
    private Disposable disposable;
    UmShareUtil umShareUtil;
    private int type = 1;

    public static void launch(Activity activity, int type) {
        Intent intent = new Intent(activity, InviteActivity.class);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected InvitePresenter createPresenter() {
        return new InvitePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_invite_code;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 1);
        if (type == 1) {
            bt_invite_list.setVisibility(View.VISIBLE);
        } else {
            bt_invite_list.setVisibility(View.GONE);
        }
        if (UserUtils.getInstance().isLogin()) {
            String qr_image = UserUtils.getInstance().getLoginBean().getQr_image();
            Glide.with(this).load(qr_image).into(iv_my_code);
        }
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.ll_back, R.id.ll_share, R.id.bt_invite_list, R.id.bt_down_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_share:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_share)) {
                    if (UserUtils.getInstance().isLogin()) {
                        String qr_image = UserUtils.getInstance().getLoginBean().getQr_image();
                        umShareUtil = new UmShareUtil(this).Builder(qr_image, "我的二维码", null).show();
                    }
                }
                break;
            case R.id.bt_invite_list:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_invite_list)) {
                    Intent intent = new Intent(this, InviteListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.bt_down_code:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.bt_down_code)) {
                    mWeiboDialog = WeiboDialogUtils.createLoadingDialog(InviteActivity.this, "正在下载...");
                    if (UserUtils.getInstance().isLogin()) {
                        String qr_image = UserUtils.getInstance().getLoginBean().getQr_image();
                        try {
                            Observable.just(qr_image)
                                    .observeOn(Schedulers.io())
                                    .map(new Function<String, Bitmap>() {
                                        @Override
                                        public Bitmap apply(@NotNull String url) throws Exception {
                                            return Glide.with(InviteActivity.this)
                                                    .asBitmap()
                                                    .load(url)
                                                    .fitCenter()
                                                    .submit().get();
                                        }
                                    }).map(new Function<Bitmap, File>() {
                                @Override
                                public File apply(@NotNull Bitmap bitmap) throws Exception {
                                    return MyFileUtils.getFile(bitmap);
                                }
                            }).subscribe(new Observer<File>() {
                                @Override
                                public void onSubscribe(@NotNull Disposable d) {
                                    disposable = d;
                                }

                                @Override
                                public void onNext(@NotNull File file) {
                                    sendToAlbum(file);
                                }

                                @Override
                                public void onError(@NotNull Throwable e) {
                                    closeDialog();
                                    ToastUtils.show("保存失败，请重试！");
                                }

                                @Override
                                public void onComplete() {
                                    closeDialog();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                break;
        }
    }

    @Override
    public void getCodeSuccess(String type, String url) {

    }

    @Override
    public void getCodeFailure(String msg) {

    }

    @Override
    public void LoadingClose() {

    }

    @Override
    public void showFailed(int code, String msg) {

    }

    /**
     * 通知相册
     *
     * @param file
     */
    private void sendToAlbum(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                closeDialog();
                Toast.makeText(InviteActivity.this, "已保存到相册！", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (umShareUtil != null) {
            umShareUtil.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
