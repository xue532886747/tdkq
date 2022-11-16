package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.utils.tools.LogUtils;
import com.example.towerdriver.weight.RectImageView;
import com.hjq.toast.ToastUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 53288
 * @description 救援的弹窗
 * @date 2021/6/19
 */
public class RescueDialog {
    private Context context;
    private Window window;
    private Dialog dialog;
    public DialogClickListener dialogClickListener;

    public RescueDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public RescueDialog(Context context) {
        this.context = context;
    }


    public RescueDialog Builder(double lat, double lng) {
        if (context != null) {
            dialog = new Dialog(context, R.style.LocCenterDialogStyle);
            window = dialog.getWindow();
            View decorView = window.getDecorView();
            decorView.setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setContentView(R.layout.dialog_rescue);
            LinearLayout ll_car = decorView.findViewById(R.id.ll_car);
            LinearLayout ll_battery = decorView.findViewById(R.id.ll_battery);
            AppCompatEditText et_phone = decorView.findViewById(R.id.et_phone);
            AppCompatEditText et_solution = decorView.findViewById(R.id.et_solution);
            AppCompatButton bt_commit = decorView.findViewById(R.id.bt_commit);
            AppCompatImageView iv_battery = decorView.findViewById(R.id.iv_battery);
            AppCompatImageView iv_car = decorView.findViewById(R.id.iv_car);
            View view_delete = decorView.findViewById(R.id.view_delete);
            view_delete.bringToFront();
            iv_car.setSelected(true);
            et_solution.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    /*告诉父组件不要拦截他的触摸事件*/
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    if (MotionEvent.ACTION_UP == event.getAction()) {
                        /*告诉父组件可以拦截他的触摸事件*/
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    return false;
                }
            });
            ll_car.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_car.setSelected(true);
                    iv_battery.setSelected(false);
                }
            });
            ll_battery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_battery.setSelected(true);
                    iv_car.setSelected(false);
                }
            });
            bt_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAndCommit(lat, lng, et_phone.getText().toString(), et_solution.getText().toString(), iv_car.isSelected());
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
     * @param lat
     * @param lng
     * @param phone
     * @param content
     * @param isCheck
     */
    private void checkAndCommit(double lat, double lng, String phone, String content, boolean isCheck) {
        LogUtils.d("lat = " + lat + " , lng = " + lng + " ,phone = " + phone + " ,content = " + content + " isCheck = " + isCheck);
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show("请您输入电话号码！");
            return;
        }
        String car = "车";
        if (isCheck) {
            car = "车";
        } else {
            car = "电";
        }
        if (dialogClickListener != null) {
            dialogClickListener.getImagePosition(lat, lng, phone, car, content);
            shutDownDialog();
        }
    }

    public RescueDialog show() {
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

        void getImagePosition(double lat, double lng, String member_phone, String check, String content);
    }

}
