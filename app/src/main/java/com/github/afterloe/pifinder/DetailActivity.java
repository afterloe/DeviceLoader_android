package com.github.afterloe.pifinder;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.github.afterloe.pifinder.domain.Device;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity implements Serializable {

    private SwipeRefreshLayout swipeRefreshLayout;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false); // 设置不刷新
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detail);
        swipeRefreshLayout = findViewById(R.id.layout_detail);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(DetailActivity.this, "重新加载... ...", Toast.LENGTH_LONG).show();
                handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        Intent intent = getIntent();
        Device device = (Device) intent.getSerializableExtra("object");
        if (null == device) {
            device = new Device("device is not found");
            device.setSsid("ssid not found");
        }
        TextView ssid = findViewById(R.id.textView2);
        TextView time = findViewById(R.id.textView4);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time.setText(simpleDateFormat.format(new Date()));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 返回键 监听
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }
}
