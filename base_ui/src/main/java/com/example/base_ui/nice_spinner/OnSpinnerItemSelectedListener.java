package com.example.base_ui.nice_spinner;

import android.view.View;

/**
 * @author 53288
 * @description
 * @date 2021/6/26
 */
public interface OnSpinnerItemSelectedListener {
    void onItemSelected(NiceSpinner parent, View view, int position, long id);
}
