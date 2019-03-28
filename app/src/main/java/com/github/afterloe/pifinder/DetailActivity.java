package com.github.afterloe.pifinder;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.github.afterloe.pifinder.api.DeviceApi;
import com.github.afterloe.pifinder.domain.Device;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements Serializable {

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<ScanResult> results;
    private TextView deviceName;
    private TextView deviceModifyTime;
    private TextView deviceRemakrs;
    private SimpleDateFormat simpleDateFormat;

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

    private static final double A_Value = 50; /**A - 发射端和接收端相隔1米时的信号强度*/
    private static final double n_Value = 2.77; /** n - 环境衰减因子*/

    /**
     * 估算wifi距离
     *
     * @param rssi
     * @return
     */
    public static double getDistance(int rssi){
        int iRssi = Math.abs(rssi);
        double power = (iRssi - A_Value) / ( 10 * n_Value);
        return Math.pow(10, power);
    }

    private void initView(Context context) {
        deviceName = findViewById(R.id.device_name);
        deviceModifyTime = findViewById(R.id.device_modifyTime);
        deviceRemakrs = findViewById(R.id.device_remarks);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 设置下拉加载
        swipeRefreshLayout = findViewById(R.id.layout_detail);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(context, "重新加载... ...", Toast.LENGTH_LONG).show();
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
    }

    class DetailLoadTask extends AsyncTask<Void, Void, Void> {

        private int id;
        private Device fetchDevice;

        public DetailLoadTask(int id) {
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fetchDevice = new DeviceApi().getDevice(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            deviceName.setText(fetchDevice.getName());
            Long modifyTime = fetchDevice.getModifyTime();
            Log.i("detail", modifyTime + "");
            if (null != modifyTime && 0 != modifyTime) {
                deviceModifyTime.setText(simpleDateFormat.format(new Date(modifyTime * 1000)));
            } else {
                deviceModifyTime.setText("暂未同步");
            }
            deviceRemakrs.setText(fetchDevice.getRemark());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_detail);
        final Context context = DetailActivity.this;
        initView(context);

        // 获取数据
        Intent intent = getIntent();
        Device device = (Device) intent.getSerializableExtra("object");
        new DetailLoadTask(device.getId()).execute();

        final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Log.i("rssi leve", 4 * ( wifi.getConnectionInfo().getRssi() + 100) / 45 + "");
        Log.i("rssi distance", getDistance(wifi.getConnectionInfo().getRssi())+ "");
        Log.i("detail", wifi.getScanResults().size() + "");
        for (ScanResult config : wifi.getScanResults()) {
            Log.i("detail -> ", config.SSID + " - " + config.level + " | " + config.capabilities + " | " + config.BSSID + " | " + getDistance(config.level));
        }

        // 设置webView
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
        webView.loadUrl("https://baidu.com");
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
