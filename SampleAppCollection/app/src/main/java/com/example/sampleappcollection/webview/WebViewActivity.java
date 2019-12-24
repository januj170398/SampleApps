package com.example.sampleappcollection.webview;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleappcollection.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：WebView与Native交互
 *
 * @author zhangqin
 */
public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.webview_btn)
    Button mWebviewBtn;

    private Context mContext;
    private static final String TAG = "WebViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        mContext = this;

        initWeb();

        // 调用js中的setName方法
        mWebviewBtn.setOnClickListener(v -> mWebView.loadUrl("javascript:setName('测试与js交互')"));

    }

    private void initWeb() {
        WebSettings webSettings = mWebView.getSettings();

        // 启用js
        webSettings.setJavaScriptEnabled(true);
        // 禁止缩放
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(false);
        //不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);

        // WebView 背景透明效果
        // webview.setBackgroundColor(Color.TRANSPARENT);
        mWebView.addJavascriptInterface(new JsInteration(), "app");
        mWebView.loadUrl("file:///android_asset/html/webtest.html");
    }

    public class JsInteration {
        @JavascriptInterface
        public void showName(String name) {
            Log.e(TAG, "showName: " + name);
            Toast.makeText(mContext, "name=" + name, Toast.LENGTH_LONG).show();
        }
    }
}
