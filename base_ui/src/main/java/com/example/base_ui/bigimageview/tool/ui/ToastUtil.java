package com.example.base_ui.bigimageview.tool.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author 工藤
 * @email qinglingou@gmail.com
 */
public class ToastUtil {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public static ToastUtil getInstance() {
        return InnerClass.instance;
    }

    public void _short(final Context context, final String text) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void _long(final Context context, final String text) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private static class InnerClass {
        private static final ToastUtil instance = new ToastUtil();
    }
}