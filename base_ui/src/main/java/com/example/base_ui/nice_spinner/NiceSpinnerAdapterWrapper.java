package com.example.base_ui.nice_spinner;

import android.content.Context;
import android.widget.ListAdapter;

/**
 * @author 53288
 * @description
 * @date 2021/1/20
 */
public class NiceSpinnerAdapterWrapper<T> extends NiceSpinnerBaseAdapter<T> {

    private final ListAdapter baseAdapter;

    NiceSpinnerAdapterWrapper(
            Context context,
            ListAdapter toWrap,
            int textColor,
            int backgroundSelector,
            SpinnerTextFormatter<T> spinnerTextFormatter,
            PopUpTextAlignment horizontalAlignment
    ) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter, horizontalAlignment);
        baseAdapter = toWrap;
    }

    @Override
    public int getCount() {
        return baseAdapter.getCount() - 1;
    }

    @Override
    public T getItem(int position) {
        return (T) baseAdapter.getItem(position >= selectedIndex ? position + 1 : position);
    }

    @Override
    public T getItemInDataset(int position) {
        return (T) baseAdapter.getItem(position);
    }
}
