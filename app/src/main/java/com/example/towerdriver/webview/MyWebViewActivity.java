package com.example.towerdriver.webview;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.liantian.ac.U;
import com.example.towerdriver.R;
import com.example.towerdriver.base.BaseActivity;
import com.example.towerdriver.base.BasePresenter;
import com.example.towerdriver.base_setmenu.ui.activity.GoodsDetailActivity;
import com.example.towerdriver.base_share.UmShareUtil;
import com.example.towerdriver.utils.RepeatClickResolveUtil;
import com.umeng.socialize.UMShareAPI;

import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 53288
 * @description webview
 * @date 2021/6/21
 */
public class MyWebViewActivity extends BaseActivity {
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    AppCompatTextView tv_title;
    @BindView(R.id.ll_share)
    LinearLayout ll_share;
    private String url;
    private String title;
    private boolean isNeedShare = false;
    UmShareUtil umShareUtil;
    private String html;

    public static void launch(Activity activity, boolean isNeedShare, String title, String url) {
        Intent intent = new Intent(activity, MyWebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("isNeedShare", isNeedShare);
        activity.startActivity(intent);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_webview;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        html = getIntent().getStringExtra("html");
        isNeedShare = getIntent().getBooleanExtra("isNeedShare", false);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setUseWideViewPort(true);
        if (!TextUtils.isEmpty(html)) {
            webSettings.setTextZoom(250);
        }
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);        //不适用缓存
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        // 是否允许通过file url加载的Javascript读取本地文件，默认值 false
        webSettings.setAllowFileAccessFromFileURLs(false);
        // 是否允许通过file url加载的Javascript读取全部资源(包括文件,http,https)，默认值 false
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                    }
                } else {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                        progressBar.setProgress(newProgress);//设置进度值
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
        if (!TextUtils.isEmpty(url)) {
            webview.loadUrl(url);
        }
        if (!TextUtils.isEmpty(html)) {
            webview.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        }
    }

    @OnClick({R.id.ll_back, R.id.ll_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.ll_share:
                if (!RepeatClickResolveUtil.isFastDoubleClick(R.id.ll_share)) {
                    umShareUtil = new UmShareUtil(this).Builder(url, title, null).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (umShareUtil != null) {
            umShareUtil.close();
        }
    }
}
