package com.github.afterloe.pifinder;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.github.afterloe.pifinder.domain.Device;

import java.io.Serializable;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detail);
        Intent intent = getIntent();
        Device device = (Device) intent.getSerializableExtra("object");
        if (null == device) {
            device = new Device("device is not found");
            device.setSsid("ssid not found");
        }
        TextView ssid = findViewById(R.id.textView2);
        TextView time = findViewById(R.id.textView4);
        time.setText(new Date().toString());
        ssid.setText(device.getSsid());
        final WebView webView = findViewById(R.id.webView);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webView.getSettings()
                            .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }
            }
        });
        webView.loadUrl("https://github.com/afterloe");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 返回键 监听
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
