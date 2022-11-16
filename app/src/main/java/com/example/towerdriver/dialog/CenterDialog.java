package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.towerdriver.R;

import androidx.appcompat.widget.AppCompatTextView;


/**
 * @author 53288
 * @description 个人中心弹窗
 * @date 2021/5/22
 */
public class CenterDialog {
    private Context context;
    private Window window;
    private Dialog dialog;
    private int type = 0;                               //显示样式
    public DialogClickListener dialogClickListener;


    public CenterDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public CenterDialog(Context context) {
        this.context = context;
    }


    public CenterDialog setType(int type) {
        this.type = type;
        return this;
    }

    public CenterDialog Builder(String title, String top, String bottom) {
        dialog = new Dialog(context, R.style.CenterDialogStyle);
        window = dialog.getWindow();
        View decorView = window.getDecorView();
        decorView.setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_center);
        AppCompatTextView tv_title = decorView.findViewById(R.id.tv_title);
        AppCompatTextView tv_confirm = decorView.findViewById(R.id.tv_confirm);
        AppCompatTextView tv_cancel = decorView.findViewById(R.id.tv_cancel);
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(top)) {
            tv_confirm.setText(top);
        }
        if (!TextUtils.isEmpty(bottom)) {
            tv_cancel.setText(bottom);
        }
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClickListener != null) {
                    dialogClickListener.top(type);
                    shutDownDialog();
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClickListener != null) {
                    dialogClickListener.bottom(type);
                    shutDownDialog();
                }
            }
        });
        return this;
    }

    public void show() {
        if (dialog != null) dialog.show();
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
        void top(int type);

        void bottom(int type);
    }


}
