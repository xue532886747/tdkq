package com.example.base_ui.nice_spinner;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.base_ui.DisPlayUtils;
import com.example.base_ui.R;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author 53288
 * @description
 * @date 2021/6/26
 */
public abstract class NiceSpinnerBaseAdapter<T> extends BaseAdapter {

    private final PopUpTextAlignment horizontalAlignment;
    private final SpinnerTextFormatter spinnerTextFormatter;

    private int textColor;
    private int backgroundSelector;

    int selectedIndex;

    NiceSpinnerBaseAdapter(
            Context context,
            int textColor,
            int backgroundSelector,
            SpinnerTextFormatter spinnerTextFormatter,
            PopUpTextAlignment horizontalAlignment
    ) {
        this.spinnerTextFormatter = spinnerTextFormatter;
        this.backgroundSelector = backgroundSelector;
        this.textColor = textColor;
        this.horizontalAlignment = horizontalAlignment;
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        TextView textView;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.spinner_list_item, null);
            textView = convertView.findViewById(R.id.text_view_spinner);

            textView.setBackground(ContextCompat.getDrawable(context, backgroundSelector));
            convertView.setTag(new ViewHolder(textView));

        } else {
            textView = ((ViewHolder) convertView.getTag()).textView;
        }

        textView.setText(spinnerTextFormatter.format(getItem(position)));
        textView.setTextColor(textColor);
        setTextHorizontalAlignment(textView);

        return convertView;
    }

    private void setTextHorizontalAlignment(TextView textView) {
        switch (horizontalAlignment) {
            case START:
                textView.setGravity(Gravity.START);
                break;
            case END:
                textView.setGravity(Gravity.END);
                break;
            case CENTER:
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                break;
        }
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    void setSelectedIndex(int index) {
        selectedIndex = index;
    }

    public abstract T getItemInDataset(int position);

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract T getItem(int position);

    @Override
    public abstract int getCount();

    static class ViewHolder {
        TextView textView;

        ViewHolder(TextView textView) {
            this.textView = textView;
        }
    }
}
