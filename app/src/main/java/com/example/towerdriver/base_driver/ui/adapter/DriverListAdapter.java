package com.example.towerdriver.base_driver.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base_driver.model.bean.ReleaseBean;
import com.example.towerdriver.base_driver.model.bean.ReleaseListBean;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.weight.CircleImageView;
import com.example.towerdriver.weight.RectImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 53288
 * @description 小哥发布列表
 * @date 2021/6/4
 */
public class DriverListAdapter extends BaseQuickAdapter<ReleaseListBean.ArticleBean, BaseViewHolder> implements LoadMoreModule {
    private boolean type;

    public DriverListAdapter(boolean type) {
        super(R.layout.adp_driver_list);
        this.type = type;
    }

    public void setSelectType(boolean type) {
        this.type = type;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ReleaseListBean.ArticleBean articleBean) {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_item);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ImageListAdapter imageListAdapter = new ImageListAdapter();
        imageListAdapter.setList(articleBean.getImage());
        recyclerView.setAdapter(imageListAdapter);
        baseViewHolder.setText(R.id.tv_name, articleBean.getName());
        baseViewHolder.setText(R.id.tv_date, articleBean.getCreatetime());
        baseViewHolder.setText(R.id.tv_phone, articleBean.getPhone());
        baseViewHolder.setText(R.id.tv_title, articleBean.getTitle());
        baseViewHolder.setText(R.id.tv_content, articleBean.getContent());
        baseViewHolder.setText(R.id.tv_status, articleBean.getStatus());
        CircleImageView circleImageView = baseViewHolder.getView(R.id.circleImageView);
        Glide.with(getContext()).load(articleBean.getMember_image()).
                placeholder(R.mipmap.log_image_bg) .error(R.mipmap.log_image_bg).into(circleImageView);
        AppCompatTextView textView = baseViewHolder.getView(R.id.tv_status);
        AppCompatButton button = baseViewHolder.getView(R.id.bt_delete);
        if (type) {         //只看我
            textView.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
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
            int itemWidth = (int) ((MyApplication.getScreenWidth() - 80 - DisPlayUtils.dp2px(20)) / 3);
            layoutParams.width = itemWidth;
            layoutParams.height = itemWidth;
            imageView.setLayoutParams(layoutParams);
            Glide.with(getContext()).load(image).placeholder(R.mipmap.log_image_bg).
                    error(R.mipmap.log_image_bg).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.getImagePosition(adapterPosition, image, getData());
                }
            });
        }
    }

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        /**
         * 获得当前的图片
         *
         * @param current_position 当前的图片
         * @param current_url      当前的url
         * @param all_image        所有的图片
         */
        void getImagePosition(int current_position, String current_url, List<String> all_image);
    }
}
