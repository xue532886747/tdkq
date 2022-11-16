package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.towerdriver.R;
import com.example.towerdriver.utils.tools.LogUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;


/**
 * @author 53288
 * @description 上传或下载的弹窗
 * @date 2021/7/11
 */
public class UpdateDialog {
    private Context context;
    private Window window;
    private Dialog dialog;
    public DialogClickListener dialogClickListener;

    public UpdateDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public UpdateDialog(Context context) {
        this.context = context;
    }


    public UpdateDialog Builder(String title, String cur_version, String update_version, String content) {
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
            window.setContentView(R.layout.dialog_update);
            AppCompatTextView tv_title = decorView.findViewById(R.id.tv_title);
            AppCompatTextView tv_cur_version = decorView.findViewById(R.id.tv_cur_version);
            AppCompatTextView tv_update_version = decorView.findViewById(R.id.tv_update_version);
            AppCompatTextView tv_content = decorView.findViewById(R.id.tv_content);
            AppCompatButton bt_commit = decorView.findViewById(R.id.bt_commit);
            View view_delete = decorView.findViewById(R.id.view_delete);

            if (!TextUtils.isEmpty(title)) {
                tv_title.setText(title);
            }
            if (!TextUtils.isEmpty(cur_version)) {
                tv_cur_version.setText("当前版本: " + cur_version);
            }
            if (!TextUtils.isEmpty(update_version)) {
                tv_update_version.setText("最新版本: " + update_version);
            }
            if (!TextUtils.isEmpty(content)) {
                tv_content.setText(content);
            }
            view_delete.bringToFront();
            view_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.finish();
                    }
                    cancle();
                }
            });
            bt_commit.setOnClickListener(new View.OnClickListener() {
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


    public UpdateDialog show() {
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
