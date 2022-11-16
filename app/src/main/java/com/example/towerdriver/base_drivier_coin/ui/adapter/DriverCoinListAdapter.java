package com.example.towerdriver.base_drivier_coin.ui.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.towerdriver.R;
import com.example.towerdriver.base_drivier_coin.model.DriverCoinBean;
import com.example.towerdriver.base_invite.model.InviteBean;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.Group;

/**
 * @author 53288
 * @description
 * @date 2021/6/17
 */
public class DriverCoinListAdapter extends BaseQuickAdapter<DriverCoinBean.WithdrawBean, BaseViewHolder> implements LoadMoreModule {


    public DriverCoinListAdapter() {
        super(R.layout.adp_coin_list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DriverCoinBean.WithdrawBean withdrawBean) {
        baseViewHolder.setText(R.id.tv_moneys,  withdrawBean.getPrice()+"元");
        baseViewHolder.setText(R.id.tv_time,  withdrawBean.getCreatetime());
    }
}
