package com.example.towerdriver.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base_driver.ui.adapter.DriverListAdapter;
import com.example.towerdriver.base_setmenu.model.GoodsDetailBean;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.weight.RectImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author 53288
 * @description 个人中心弹窗
 * @date 2021/5/22
 */
public class LocCenterDialog {
    private Context context;
    private Window window;
    private Dialog dialog;
    private int type = 0;                               //显示样式
    public DialogClickListener dialogClickListener;

    public LocCenterDialog setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
        return this;
    }

    public LocCenterDialog(Context context) {
        this.context = context;
    }


    public LocCenterDialog setType(int type) {
        this.type = type;
        return this;
    }

    public LocCenterDialog Builder(double lat, double lng, String name, String address, String phone, String major, List<String> imagelist) {
        if (context != null) {
            dialog = new Dialog(context, R.style.LocCenterDialogStyle);
            window = dialog.getWindow();
            View decorView = window.getDecorView();
            decorView.setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setContentView(R.layout.dialog_loc_center);
            AppCompatTextView tv_loc_name = decorView.findViewById(R.id.tv_loc_name);
            AppCompatTextView tv_loc_address = decorView.findViewById(R.id.tv_loc_address);
            AppCompatTextView tv_loc_phone = decorView.findViewById(R.id.tv_loc_phone);
            RectImageView iv_major = decorView.findViewById(R.id.iv_major);
            RecyclerView rv_image = decorView.findViewById(R.id.rv_image);
            rv_image.setLayoutManager(new GridLayoutManager(context, 3));
            ImageListAdapter imageListAdapter = new ImageListAdapter();
            rv_image.setAdapter(imageListAdapter);
            imageListAdapter.setList(imagelist);
            View view_delete = decorView.findViewById(R.id.view_delete);
            view_delete.bringToFront();
            Glide.with(context).load(major).placeholder(R.mipmap.log_image_bg).
                    error(R.mipmap.log_image_bg).into(iv_major);
            if (!TextUtils.isEmpty(name)) {
                tv_loc_name.setText("姓名 " + name);
            }
            if (!TextUtils.isEmpty(address)) {
                tv_loc_address.setText("地址 " + address);
            }
            if (!TextUtils.isEmpty(phone)) {
                tv_loc_phone.setText("电话 " + phone);
            }
            tv_loc_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.getPhone(phone, address);
                    }
                }
            });

            tv_loc_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.getNavigation(lat,lng,address);
                    }
                }
            });
            view_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancle();
                }
            });
            iv_major.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        List<String> list = new ArrayList<>();
                        list.add(major);
                        if (imagelist != null && imagelist.size() != 0) {
                            list.addAll(imagelist);
                        }
                        dialogClickListener.getImagePosition(0, major, list);
                    }
                }
            });
        }
        return this;
    }

    public LocCenterDialog show() {
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

        void getPhone(String phone, String address);

        void getNavigation(double lat, double lng, String address);

        void getImagePosition(int current_position, String current_url, List<String> all_image);
    }

    class ImageListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ImageListAdapter() {
            super(R.layout.adp_img_item);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, String image) {
            int adapterPosition = baseViewHolder.getAdapterPosition();
            RectImageView imageView = baseViewHolder.getView(R.id.iv_image);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int itemWidth = (int) ((DisPlayUtils.dp2px(270) - DisPlayUtils.dp2px(20)) / 3);
            layoutParams.width = itemWidth;
//            layoutParams.height = itemWidth;
            imageView.setLayoutParams(layoutParams);
            Glide.with(getContext()).load(image).placeholder(R.mipmap.log_image_bg).
                    error(R.mipmap.log_image_bg).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialogClickListener != null) {
                        dialogClickListener.getImagePosition(adapterPosition, image, getData());
                    }
                }
            });
        }
    }
}
