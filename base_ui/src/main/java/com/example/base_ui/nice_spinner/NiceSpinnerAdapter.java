package com.example.base_ui.nice_spinner;

import android.content.Context;

import java.util.List;


/**
 * @author 53288
 * @description
 * @date 2021/1/20
 */
public class NiceSpinnerAdapter<T> extends NiceSpinnerBaseAdapter {

    private final List<T> items;

    NiceSpinnerAdapter(
            Context context,
            List<T> items,
            int textColor,
            int backgroundSelector,
            SpinnerTextFormatter<T> spinnerTextFormatter,
            PopUpTextAlignment horizontalAlignment
    ) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter, horizontalAlignment);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size() - 1;
    }

    @Override
    public T getItem(int position) {
        if (position >= selectedIndex) {
            return items.get(position + 1);
        } else {
            return items.get(position);
        }
    }

    @Override
    public T getItemInDataset(int position) {
        return items.get(position);
    }
}
