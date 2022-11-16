package com.example.towerdriver.base_main.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.towerdriver.R;
import com.example.towerdriver.base_main.bean.AdvertiBean;
import com.example.towerdriver.base_setmenu.model.SelectBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，图片
 */
public class ImageAdapter extends BannerAdapter<AdvertiBean.AdvBean, ImageHolder> {


    public ImageAdapter(List<AdvertiBean.AdvBean> datas) {
        super(datas);
    }

    //更新数据
    public void updateData(List<AdvertiBean.AdvBean> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindView(ImageHolder holder, AdvertiBean.AdvBean data, int position, int size) {
//        holder.imageView.setImageResource(data.getImage());
//        Glide.with(holder.itemView)
//                .load(data.getImage())
////                .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
////                .skipMemoryCache(true)
////                .diskCacheStrategy(DiskCacheStrategy.NONE)
////                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
//                .into(holder.imageView);
        Glide.with(holder.itemView)
                .load(data.getImage())
                .thumbnail(Glide.with(holder.itemView).load(R.drawable.loading))
                //.apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holder.imageView);
    }

}
