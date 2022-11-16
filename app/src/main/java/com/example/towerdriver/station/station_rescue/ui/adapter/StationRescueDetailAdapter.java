package com.example.towerdriver.station.station_rescue.ui.adapter;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.appcation.MyApplication;
import com.example.towerdriver.base_driver.ui.adapter.DriverListAdapter;
import com.example.towerdriver.station.station_rescue.model.NewRescueDetailBean;
import com.example.towerdriver.station.station_rescue.model.RescueDetailBean;
import com.example.towerdriver.utils.tools.DisPlayUtils;
import com.example.towerdriver.weight.RectImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * @author 53288
 * @description
 * @date 2021/7/2
 */
public class StationRescueDetailAdapter extends BaseMultiItemQuickAdapter<NewRescueDetailBean, BaseViewHolder> {

    public StationRescueDetailAdapter() {
        addItemType(1, R.layout.adp_rescue_detail_title);
        addItemType(2, R.layout.adp_rescue_detail_content);
        addItemType(3, R.layout.adp_rescue_detail_image);
        addItemType(4, R.layout.adp_rescue_detail_star);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, NewRescueDetailBean rescueDetailBean) {
        switch (baseViewHolder.getItemViewType()) {
            case 1:
                baseViewHolder.setText(R.id.tv_title, rescueDetailBean.getTitle());
                break;
            case 2:
                baseViewHolder.setText(R.id.tv_title, rescueDetailBean.getTitle());
                baseViewHolder.setText(R.id.tv_content, rescueDetailBean.getContent());
                break;
            case 3:
                RecyclerView recyclerView = baseViewHolder.getView(R.id.rv_images);
                GoodImagesAdapter goodImagesAdapter = new GoodImagesAdapter();
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                goodImagesAdapter.setList(rescueDetailBean.getImages());
                recyclerView.setAdapter(goodImagesAdapter);
                break;
            case 4:
                MaterialRatingBar star = baseViewHolder.getView(R.id.star);
                star.setRating(Float.parseFloat(rescueDetailBean.getScore()));
                star.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                baseViewHolder.setText(R.id.tv_evaluate, "留言: " + rescueDetailBean.getEvaluate());
                break;
        }
    }


    class GoodImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public GoodImagesAdapter() {
            super(R.layout.adp_img_item);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, String image) {
            int adapterPosition = baseViewHolder.getAdapterPosition();
            RectImageView imageView = baseViewHolder.getView(R.id.iv_image);
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            int itemWidth = (int) ((MyApplication.getScreenWidth() - 80 - DisPlayUtils.dp2px(20)) / 3);
            layoutParams.width = itemWidth;
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
