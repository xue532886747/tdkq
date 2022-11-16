package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.towerdriver.R;
import com.hjq.toast.ToastUtils;

import java.util.Objects;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;


/**
 * @author 53288
 * @description 数量选择弹窗
 * @date 2021/6/18
 */
public class BuyDialog {
    private Context context;
    private Window window;
    private Dialog dialog;
    public DialogClickListener dialogClickListener;
    private String number;

    public BuyDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public BuyDialog(Context context) {
        this.context = context;
    }


    public BuyDialog Builder(String number, String title) {
        this.number = number;
        dialog = new Dialog(context, R.style.CenterDialogStyle);
        window = dialog.getWindow();
        View decorView = window.getDecorView();
        decorView.setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setContentView(R.layout.dialog_buy);
        AppCompatTextView tv_title = decorView.findViewById(R.id.tv_title);
        tv_title.setText(title);
        AppCompatButton bt_cancel = decorView.findViewById(R.id.bt_cancel);
        AppCompatButton bt_commit = decorView.findViewById(R.id.bt_commit);
        AppCompatImageView iv_good_add = decorView.findViewById(R.id.iv_good_add);
        AppCompatImageView iv_sub = decorView.findViewById(R.id.iv_sub);
        AppCompatEditText et_number = decorView.findViewById(R.id.et_number);
        et_number.setText(number);
        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                String s1 = et_number.getText().toString();
                if (!TextUtils.isEmpty(s)) {
//                    et_number.setSelection(s.length());
//                    et_number.setText(s);
                }
//                if (s1.equals("")) {
//                    String num = "1";
//                    et_number.setText(num);
//                    return;
//                }
//                if (Integer.parseInt(s1) > 1000) {
//                    String num = "1000";
//                    et_number.setText(num);
//                }
//                if (Integer.parseInt(s1) < 1) {
//                    String num = "1";
//                    et_number.setText(num);
//                }
            }
        });
        iv_good_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = et_number.getText().toString();
                if (!TextUtils.isEmpty(number) && !"".equals(number)) {
                    int number_value = Integer.parseInt(number);
                    if (number_value >= 1000) {
                        return;
                    }
                    number_value += 1;
                    et_number.setText(String.valueOf(number_value));
                } else {
                    et_number.setText("1");
                }
            }
        });
        iv_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = et_number.getText().toString();
                if (!TextUtils.isEmpty(number) && !"".equals(number)) {
                    int number_value = Integer.parseInt(number);
                    if (number_value <= 1) {
                        return;
                    }
                    number_value -= 1;
                    et_number.setText(String.valueOf(number_value));
                } else {
                    et_number.setText("1");
                }
            }
        });
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClickListener != null) {
                    String number = et_number.getText().toString();
                    if (!TextUtils.isEmpty(number) && !"".equals(number)) {
                        if (Integer.parseInt(number) > 1000) {
                            ToastUtils.show("您输入超过库存，请重新输入");
                        } else {
                            dialogClickListener.confirm(number);
                        }
                    } else {
                        ToastUtils.show("请输入金额");
                    }
                    shutDownDialog();
                }
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogClickListener != null) {
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
        void cancle();

        void confirm(String number);
    }


}
