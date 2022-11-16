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
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.appcompat.widget.AppCompatTextView;


/**
 * @author 53288
 * @description 个人中心弹窗
 * @date 2021/5/22
 */
public class ButtomDialog {
    private Context context;
    private Dialog dialog;
    private int type = 0;                               //显示样式
    public DialogClickListener dialogClickListener;


    public ButtomDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public ButtomDialog(Context context) {
        this.context = context;
    }


    public ButtomDialog setType(int type) {
        this.type = type;
        return this;
    }

    public ButtomDialog Builder(String top, String bottom) {
        View view = View.inflate(context, R.layout.dialog_buttom, null);
        dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);
        dialog.show();
        dialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog不消失
        AppCompatTextView tv_confirm = view.findViewById(R.id.tv_confirm);
        AppCompatTextView tv_cancel = view.findViewById(R.id.tv_cancel);
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
