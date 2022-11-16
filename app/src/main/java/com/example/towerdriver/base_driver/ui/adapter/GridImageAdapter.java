package com.example.towerdriver.base_driver.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 53288
 * @description 点击加入图片
 * @date 2021/6/3
 */
public class GridImageAdapter extends RecyclerView.Adapter<GridImageAdapter.ViewHolder> {

    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<String> list = new ArrayList<>();
    private int selectMax = 3;
    private Context mContext;


    public GridImageAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public List<String> getList() {
        return list;
    }


    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        LinearLayout ll_del;
        RelativeLayout rel_item;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.fiv);
            ll_del = view.findViewById(R.id.ll_del);
            rel_item = view.findViewById(R.id.rel_item);
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.gv_filter_image, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        ViewGroup.LayoutParams layoutParams = viewHolder.rel_item.getLayoutParams();
        float itemWidth = MyApplication.getScreenWidth() / 3f;
        layoutParams.width = (int) itemWidth;
        viewHolder.rel_item.setLayoutParams(layoutParams);
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(R.mipmap.log_creame);
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iAddPhotosClickListener != null) {
                        iAddPhotosClickListener.add();
                    }

                }
            });
            viewHolder.ll_del.setVisibility(View.INVISIBLE);
        } else {
            String image = list.get(position);
            viewHolder.ll_del.setVisibility(View.VISIBLE);
            viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = viewHolder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        list.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, list.size());
                    }
                }
            });
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iAddPhotosClickListener != null) {
                        iAddPhotosClickListener.CheckPosition(position, image);
                    }

                }
            });
            Glide.with(mContext).load(image).placeholder(R.mipmap.log_image_bg).
                    error(R.mipmap.log_image_bg).into(viewHolder.mImg);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = list == null ? 0 : list.size();
        return position == size;
    }


    public interface IAddPhotosClickListener {
        void add();

        void CheckPosition(int position, String image);
    }

    private IAddPhotosClickListener iAddPhotosClickListener;

    public void setIAddPhotosClickListener(IAddPhotosClickListener iAddPhotosClickListener) {
        this.iAddPhotosClickListener = iAddPhotosClickListener;
    }

}
