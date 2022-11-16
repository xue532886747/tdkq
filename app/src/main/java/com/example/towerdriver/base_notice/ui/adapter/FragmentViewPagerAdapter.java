package com.example.towerdriver.base_notice.ui.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * @author 53288
 * @description fragment的adapter
 * @date 2021/5/31
 */
public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments;
    private String[] CHANNELS;

    public FragmentViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> mFragments, String[] channels) {
        super(fm);
        this.mFragments = mFragments;
        this.CHANNELS = channels;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    //   super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return CHANNELS[position];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
