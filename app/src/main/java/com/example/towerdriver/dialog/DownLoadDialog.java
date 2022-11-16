package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.towerdriver.R;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;


/**
 * @author 53288
 * @description 上传或下载的弹窗
 * @date 2021/7/11
 */
public class DownLoadDialog {
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
            if (dialogClickListener != null && obj == 100) {
                dialogClickListener.finish();
            }
        }
    };

    public DownLoadDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public DownLoadDialog(Context context) {
        this.context = context;
    }


    public DownLoadDialog Builder(String title) {
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
                        dialogClickListener.bottom();
                    }
                    cancle();
                }
            });
        }
        return this;
    }

    public void setNumber(int number) {
        LogUtils.d(Thread.currentThread().getName());
        Message message = Message.obtain();
        message.obj = number;
        handler.sendMessage(message);
    }

    public DownLoadDialog show() {
        if (dialog != null) dialog.show();
        return this;
    }

    public void cancle() {
        if (dialog != null) dialog.cancel();
    }

    public void shutDownDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public interface DialogClickListener {

        void bottom();

        void finish();
    }

}
