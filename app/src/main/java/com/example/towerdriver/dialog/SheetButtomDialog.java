package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.example.towerdriver.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.appcompat.widget.AppCompatTextView;


/**
 * @author 53288
 * @description 拨打电话和导航
 * @date 2021/5/22
 */
public class SheetButtomDialog {
    private Context context;
    private int type = 0;                               //显示样式
    public DialogClickListener dialogClickListener;


    public SheetButtomDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public SheetButtomDialog(Context context) {
        this.context = context;
    }

    public SheetButtomDialog setType(int type) {
        this.type = type;
        return this;
    }

    public SheetButtomDialog Builder(String top, String bottom) {
        if (context != null) {
            View view = View.inflate(context, R.layout.dialog_sheetbuttom, null);
            BottomSheetDialog dialog = new BottomSheetDialog(context,R.style.MyDialogStyle);
            dialog.setDismissWithAnimation(true);
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
                        dialog.dismiss();
                    }
                }
            });
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.bottom(type);
                        dialog.dismiss();
                    }
                }
            });
        }
        return this;
    }

    public interface DialogClickListener {
        void top(int type);

        void bottom(int type);
    }


}
