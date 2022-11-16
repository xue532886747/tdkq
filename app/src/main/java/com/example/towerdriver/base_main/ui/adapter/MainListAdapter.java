package com.example.towerdriver.base_main.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.base_ui.badgeview.QBadgeView;
import com.example.towerdriver.R;
import com.example.towerdriver.base_main.bean.ListBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author 53288
 * @description ListBean
 * @date 2021/5/20
 */
public class MainListAdapter extends RecyclerView.Adapter {
    private static final String TAG = "BoyItemAdapter1";
    private Context mContext;
    private List<ListBean> mList = new ArrayList<>();


    public MainListAdapter(Context context) {
        this.mContext = context;

    }

    public void setData(List<ListBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adp_main_list_item, parent, false);
        holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ListBean listBean = mList.get(position);
            ((ItemViewHolder) holder).tv_title.setText(listBean.getTitle());
            Glide.with(mContext).load(listBean.getImage()).into(((ItemViewHolder) holder).iv_major_image);
            ((ItemViewHolder) holder).cl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(position);
                    }
                }
            });
            if (position == 1) {
                setDotNoticeNumber(((ItemViewHolder) holder).badgeView, listBean.getNumber());
            } else {
                ((ItemViewHolder) holder).badgeView.hide(true);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tv_title;
        AppCompatImageView iv_major_image;
        ConstraintLayout cl_item;
        QBadgeView badgeView;
        RelativeLayout rl_number;

        ItemViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_main_title);
            iv_major_image = itemView.findViewById(R.id.iv_major_image);
            cl_item = itemView.findViewById(R.id.cl_item);
            rl_number = itemView.findViewById(R.id.rl_number);
            badgeView = new QBadgeView(mContext);
            badgeView.setBadgeBackgroundColor(0xFFDA1A17);
            badgeView.setExactMode(false);
            badgeView.bindTarget(rl_number).setBadgeTextColor(0xFFFFFFFF);
        }
    }

    public interface OnItemClickListener {

        void setOnItemClickListener(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void OnItemClickListener(OnItemClickListener onInviteClickListener) {
        this.onItemClickListener = onInviteClickListener;
    }

    public void setDotNoticeNumber(QBadgeView badgeView, int number) {
        badgeView.setBadgeNumber(number);
        if (number == 0) {
            badgeView.hide(true);
        }
    }
}
