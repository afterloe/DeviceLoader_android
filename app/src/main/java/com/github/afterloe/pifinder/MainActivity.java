package com.github.afterloe.pifinder;

import android.Manifest;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.afterloe.pifinder.api.DeviceApi;
import com.github.afterloe.pifinder.component.DeviceAdapter;
import com.github.afterloe.pifinder.component.DeviceClick;
import com.github.afterloe.pifinder.domain.Device;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener, Serializable {

    private List<Device> deviceList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter adapter;
    private Context context;
    private ListView lv;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    if (swipeRefreshLayout.isRefreshing()) {
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false); // 设置不刷新
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
        needPermissions(); // 申请数据
        initDevice(); // 初始化设备数据
        context = MainActivity.this;
        swipeRefreshLayout = findViewById(R.id.list_device);
        lv = findViewById(R.id.lv);
        lv.setOnScrollListener(this);
        adapter = new DeviceAdapter(context, R.layout.device_item, deviceList);
        lv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(context, "正在搜索附近设备... ...", Toast.LENGTH_SHORT).show();
                new LoadDataThread().start();
            }
        });
        lv.setOnItemClickListener(new DeviceClick(context));
    }

    private void initDevice() {
        deviceList = new ArrayList<>();
        new DeviceLoadTask(deviceList, 10, 0).execute();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}

    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends Thread{
        @Override
        public void run() {
            initData();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }

        private void initData() {
            // TODO
        }
    }
}

class DeviceLoadTask extends AsyncTask<Void, Void, List<Device>> {

    private int begin;
    private int end;
    private List<Device> devices;

    public DeviceLoadTask(List<Device> devices, int count, int page) {
        this.begin = count * page;
        this.end = count;
        this.devices = devices;
    }

    @Override
    protected List<Device> doInBackground(Void... voids) {
        devices = new DeviceApi().getDeviceList(this.begin, this.end);
        return devices;
    }
}
