package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.towerdriver.Constant;
import com.example.towerdriver.R;
import com.example.towerdriver.base_welcome.ui.WelComeActivity;
import com.example.towerdriver.webview.MyWebViewActivity;
import com.hjq.toast.ToastUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author 53288
 * @description
 * @date 2021/7/19
 */
public class UserDialog {
    private Context context;
    private Window window;
    private Dialog dialog;
    public DialogClickListener dialogClickListener;

    public UserDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public UserDialog(Context context) {
        this.context = context;
    }


    public UserDialog Builder(String content) {
        if (context != null) {
            dialog = new Dialog(context, R.style.LocCenterDialogStyle);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            window = dialog.getWindow();
            View decorView = window.getDecorView();
            decorView.setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setContentView(R.layout.dialog_user);
            AppCompatTextView tv_content = decorView.findViewById(R.id.tv_content);
//            tv_content.setText(content);
            final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(content);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    if (dialogClickListener != null) {
                        dialogClickListener.checkUserAgreement();
                    }
                }
            };
            spannableStringBuilder.setSpan(clickableSpan, 43, 49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    if (dialogClickListener != null) {
                        dialogClickListener.checkZuLinAgreement();
                    }
                }
            };
            spannableStringBuilder.setSpan(clickableSpan1, 81, 87,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            ClickableSpan clickableSpan2 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    if (dialogClickListener != null) {
                        dialogClickListener.checkServiceAgreement();
                    }
                }
            };
            spannableStringBuilder.setSpan(clickableSpan2, 88, 94,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_content.setMovementMethod(LinkMovementMethod.getInstance());
            tv_content.setText(spannableStringBuilder);


            AppCompatButton bt_commit = decorView.findViewById(R.id.bt_commit);
            AppCompatTextView tv_getout = decorView.findViewById(R.id.tv_getout);
            bt_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.getAgreement();
                    }
                    cancle();
                }
            });
            tv_getout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.getDisAgree();
                    }
                    cancle();
                }
            });
        }
        return this;
    }

    public UserDialog show() {
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

        void getAgreement();

        void getDisAgree();

        void checkUserAgreement();

        void checkServiceAgreement();

        void checkZuLinAgreement();
    }

}
