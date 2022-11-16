package com.example.base_ui.nice_spinner;

import android.text.Spannable;

/**
 * @author 53288
 * @description
 * @date 2021/6/26
 */
public interface SpinnerTextFormatter<T> {
    Spannable format(T item);
}
