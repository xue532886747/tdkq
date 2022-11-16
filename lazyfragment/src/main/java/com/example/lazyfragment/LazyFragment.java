package com.example.lazyfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author 53288
 * @description 懒加载的基类
 * @date 2021/5/18
 */
public abstract class LazyFragment extends Fragment {
    private static final String TAG = "LazyFragment1";
    private boolean mIsFirstVisible = true;
    private boolean mUserVisible = false;
    protected View mRootView = null;
    protected boolean mViewCreated = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        if (mRootView == null) {
            final int layoutId = getLayoutRes();
            if (layoutId > 0) {
                mRootView = inflater.inflate(getLayoutRes(), container, false);
            }
        }
        mViewCreated = true;
        return mRootView;
    }


    protected abstract int getLayoutRes();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint = " + isVisibleToUser);
        if (mViewCreated) {
            if (isVisibleToUser && !mUserVisible) {
                dispatchUserVisibleHint(true);
            } else if (!isVisibleToUser && mUserVisible) {
                dispatchUserVisibleHint(false);
            }
        }
    }


    @Nullable
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isHidden() && getUserVisibleHint()) {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            dispatchUserVisibleHint(false);
        } else {
            dispatchUserVisibleHint(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsFirstVisible) {
            if (!isHidden() && !mUserVisible && getUserVisibleHint()) {
                dispatchUserVisibleHint(true);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mUserVisible && getUserVisibleHint()) {
            dispatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
        mViewCreated = false;
        mIsFirstVisible = true;
    }


    private void dispatchUserVisibleHint(boolean visible) {
        if (visible && !isParentVisible()) {
            return;
        }
        if (mUserVisible == visible) {
            return;
        }
        mUserVisible = visible;
        if (visible) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                onVisible(true);
            } else {
                onVisible(false);
            }
            dispatchChildVisibleState(true);
        } else {
            dispatchChildVisibleState(false);
            onInvisible();
        }
    }

    private boolean isParentVisible() {
        Fragment fragment = getParentFragment();
        if (fragment == null) {
            return true;
        }
        if (fragment instanceof LazyFragment) {
            LazyFragment lazyFragment = (LazyFragment) fragment;
            return lazyFragment.isSupportUserVisible();
        }
        return fragment.isVisible();
    }

    private boolean isSupportUserVisible() {
        return mUserVisible;
    }

    private void dispatchChildVisibleState(boolean visible) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        List<Fragment> fragments = childFragmentManager.getFragments();
        if (!fragments.isEmpty()) {
            for (Fragment child : fragments) {
                if (child instanceof LazyFragment && !child.isHidden() && child.getUserVisibleHint()) {
                    ((LazyFragment) child).dispatchUserVisibleHint(visible);
                }
            }
        }
    }

    protected void onVisible(boolean isFirstVisible) {

    }

    protected void onInvisible() {

    }
}
