package com.example.base_ui.nice_spinner;

import android.text.Spannable;
import android.text.SpannableString;

/**
 * @author 53288
 * @description
 * @date 2021/1/20
 */
public class SimpleSpinnerTextFormatter<T> implements SpinnerTextFormatter<T> {

    @Override
    public Spannable format(T item) {
        return new SpannableString(item.toString());
    }
}
