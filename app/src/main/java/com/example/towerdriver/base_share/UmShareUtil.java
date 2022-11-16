package com.example.towerdriver.base_share;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.map.CustomMapStyleCallBack;
import com.example.towerdriver.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author 53288
 * @description 友盟分享
 * @date 2021/6/23
 */
public class UmShareUtil implements UMShareListener {
    private static final String TAG = "UmShareUtil1";
    private ShareAction mShareAction;
    private WeakReference<Activity> activityWeakReference;

    public UmShareUtil(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
        mShareAction = new ShareAction(activityWeakReference.get());
    }

    public UmShareUtil Builder(String url, String title, String description) {
        mShareAction.setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                UMWeb umWeb = new UMWeb(url);
                umWeb.setTitle(title);
                if (!TextUtils.isEmpty(description)) {
                    umWeb.setDescription(description);
                } else {
                    umWeb.setDescription("塔电能源,不一样的生活!");
                }
                umWeb.setThumb(new UMImage(activityWeakReference.get(), R.mipmap.log_rent));
                new ShareAction(activityWeakReference.get()).withMedia(umWeb)
                        .setPlatform(share_media)
                        .setCallback(UmShareUtil.this)
                        .share();
            }
        });
        return this;
    }

    public UmShareUtil show() {
        if (mShareAction != null) {
            mShareAction.open();
        }
        return this;
    }

    public void close() {
        if (mShareAction != null) {
            mShareAction.close();
        }
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        Log.d(TAG, "onStart");
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        Log.d(TAG, "onResult");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
        Log.d(TAG, "onError");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        Log.d(TAG, "onCancel");
    }
}
