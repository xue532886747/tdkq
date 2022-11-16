package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.towerdriver.R;
import com.example.towerdriver.utils.tools.LogUtils;
import com.hjq.toast.ToastUtils;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author 53288
 * @description 输入弹窗
 * @date 2021/7/5
 */
public class InputDialog {
    private Context context;
    private Window window;
    private Dialog dialog;
    public DialogClickListener dialogClickListener;

    public InputDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public InputDialog(Context context) {
        this.context = context;
    }


    public InputDialog Builder() {
        if (context != null) {
            dialog = new Dialog(context, R.style.LocCenterDialogStyle);
            window = dialog.getWindow();
            View decorView = window.getDecorView();
            decorView.setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setContentView(R.layout.dialog_input);
            AppCompatEditText et_phone = decorView.findViewById(R.id.et_phone);
            AppCompatButton bt_commit = decorView.findViewById(R.id.bt_commit);
            View view_delete = decorView.findViewById(R.id.view_delete);
            view_delete.bringToFront();
            bt_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndCommit(et_phone.getText().toString());
                    cancle();
                }
            });
            view_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancle();
                }
            });
        }
        return this;
    }

    /**
     * @param phone
     */
    private void checkAndCommit(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show("请您输入电话号码！");
            return;
        }
        if (dialogClickListener != null) {
            dialogClickListener.getImagePosition(phone);
            shutDownDialog();
        }
    }

    public InputDialog show() {
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

        void getImagePosition(String member_phone);
    }

}
