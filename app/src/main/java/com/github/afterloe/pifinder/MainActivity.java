package com.github.afterloe.pifinder;

import android.Manifest;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.afterloe.pifinder.api.DeviceApi;
import com.github.afterloe.pifinder.component.DeviceAdapter;
import com.github.afterloe.pifinder.component.DeviceClick;
import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.utils.NetworkUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener, Serializable {

    private List<Device> deviceList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter adapter;
    private ListView lv;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    // 设置不刷新
                    if (swipeRefreshLayout.isRefreshing()) {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    break;
            }
        }
    };

    /**
     *  申请 wifi读取权限
     */
    private void needPermissions() {
        String [] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION};
        if (permissions.length != 0) {
            requestPermissions(permissions, 1);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = MainActivity.this;
        needPermissions(); // 申请权限
        initView(context); // 初始化视图
    }

    private void initView(Context context) {
        deviceList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.list_device);
        lv = findViewById(R.id.lv);
        lv.setOnScrollListener(this);
        adapter = new DeviceAdapter(context, R.layout.device_item, deviceList);
        lv.setAdapter(adapter);
        // 设置下拉动作
        swipeRefreshLayout.setOnRefreshListener(() -> {
            Toast.makeText(context, "正在搜索附近设备... ...", Toast.LENGTH_SHORT).show();
            new DeviceLoadTask(10, 0).execute();
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        });
        // 设置下拉颜色
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        lv.setOnItemClickListener(new DeviceClick(context));
        new DeviceLoadTask(10, 0).execute(); // 加载数据
    }

    class DeviceLoadTask extends AsyncTask<Void, Void, List<Device>> {

        private int begin;
        private int end;

        public DeviceLoadTask(int count, int page) {
            this.begin = count * page;
            this.end = count;
        }

        @Override
        protected List<Device> doInBackground(Void... voids) {
            deviceList = new DeviceApi().getDeviceList(this.begin, this.end);
            return deviceList;
        }

        @Override
        protected void onPostExecute(List<Device> devices) {
            super.onPostExecute(devices);
            // 可连接 / 不可连接
//            final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//            List<WifiConfiguration> results = wifi.getConfiguredNetworks();
//            results.stream().forEach(r -> devices.stream().forEach(d -> {
//                Log.i("main", r.SSID + " | " + d.getSsid());
//                if (r.SSID.equals("\"" + d.getSsid() + "\"")) {
//                    Log.i("main", r.status + " -- >");
//                    if (r.status == WifiConfiguration.Status.DISABLED) {
//                        d.setDistance(0.1);
//                    }
//                }
//            }));
            adapter.clear();
            adapter.addAll(deviceList);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}
}