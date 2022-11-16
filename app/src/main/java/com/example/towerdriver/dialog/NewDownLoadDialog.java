package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.rxhttp.download.DownloadInfo;
import com.example.rxhttp.download.RxDownload;
import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.utils.tools.LogUtils;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;


/**
 * @author 53288
 * @description 下载的弹窗
 * @date 2021/7/11
 */
public class NewDownLoadDialog {
    private Context context;
    private Window window;
    private Dialog dialog;
    ProgressBar progress_bar;
    AppCompatTextView tv_title;
    AppCompatTextView tv_number;
    public DialogClickListener dialogClickListener;
    public Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int obj = (Integer) msg.obj;
            if (progress_bar != null) {
                progress_bar.setProgress(obj);
            }
            if (tv_number != null) {
                tv_number.setText(obj + "%");
            }
        }
    };
    RxDownload rxDownload;
    private String names;

    public NewDownLoadDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public NewDownLoadDialog(Context context) {
        this.context = context;
    }


    public NewDownLoadDialog Builder(String title) {
        if (context != null) {
            dialog = new Dialog(context, R.style.LocCenterDialogStyle);
            dialog.setCancelable(false); // 是否可以按“返回键”消失
            dialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
            window = dialog.getWindow();
            View decorView = window.getDecorView();
            decorView.setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setContentView(R.layout.dialog_download);
            tv_title = decorView.findViewById(R.id.tv_title);
            tv_number = decorView.findViewById(R.id.tv_number);
            progress_bar = decorView.findViewById(R.id.progress_bar);
            progress_bar.setMax(100);
            tv_title.setText(title);
            View view_delete = decorView.findViewById(R.id.view_delete);
            view_delete.bringToFront();
            view_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.downLoadCancel(names);
                    }
                }
            });
        }
        return this;
    }

    public void setNumber(int number) {
        Message message = Message.obtain();
        message.obj = number;
        handler.sendMessage(message);
    }

    public NewDownLoadDialog show() {
        if (dialog != null)
            dialog.show();
        return this;
    }

    public void cancle() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    public void shutDownDialog() {
        if (rxDownload != null) {
            rxDownload.disposes();
        }
        if (dialogClickListener != null) {
            dialogClickListener = null;
        }
        if (context != null) {
            context = null;
        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public interface DialogClickListener {

        void downLoadCancel(String name);

        void downLoadFinish(String name);

        void downLoadError(String name);

        void downLoadStop(String name);
    }


    /**
     * 下载
     *
     * @param url
     */
    public void getDownLoad(String url) {
        String name = url.substring(url.lastIndexOf("/") + 1);    //文件名称
        String loc = Constant.OUTPUTDIR + File.separator + "TOWERDRIVER";
        names = loc + File.separator + name;
        LogUtils.d("names = " + names);
        File file = new File(names);
        if (file.exists()) {
            if (dialogClickListener != null) {
                dialogClickListener.downLoadFinish(names);
            }
            return;
        }
        DownloadInfo downloadInfo = DownloadInfo.create(url, loc, name);
        rxDownload = RxDownload.create(downloadInfo);
        rxDownload.setDownloadListener(new RxDownload.DownloadListener() {
            @Override
            public void onStarting(DownloadInfo info) {
                LogUtils.d("onStarting");
            }

            @Override
            public void onDownloading(DownloadInfo info) {
                LogUtils.d("onDownloading");
            }

            @Override
            public void onStopped(DownloadInfo info) {
                LogUtils.d("onStopped");
                if (dialogClickListener != null) {
                    dialogClickListener.downLoadStop(info.saveDirPath + File.separator + info.saveFileName);
                }
            }

            @Override
            public void onCanceled(DownloadInfo info) {
                LogUtils.d("onCanceled");
                if (dialogClickListener != null) {
                    dialogClickListener.downLoadCancel(info.saveDirPath + File.separator + info.saveFileName);
                }
            }

            @Override
            public void onCompletion(DownloadInfo info) {
                LogUtils.d("onCompletion = " + info.saveDirPath);
                if (dialogClickListener != null) {
                    dialogClickListener.downLoadFinish(info.saveDirPath + File.separator + info.saveFileName);
                }
            }

            @Override
            public void onError(DownloadInfo info, Throwable e) {
                LogUtils.d("onError");
                if (dialogClickListener != null) {
                    dialogClickListener.downLoadError(info.saveDirPath + File.separator + info.saveFileName);
                }
            }
        });
        rxDownload.setProgressListener(new RxDownload.ProgressListener() {
            @Override
            public void onProgress(float progress, long downloadLength, long contentLength) {
                float pressent = (float) downloadLength / contentLength * 100;
                setNumber((int) pressent);
            }
        });
        rxDownload.start();
    }
}
