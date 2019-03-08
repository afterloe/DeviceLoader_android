package com.github.afterloe.pifinder;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Device device = new Device("大厅2排2座数据终端");
        device.setSsid("zero");
        device.setSecret("awdrgy,.23");
        device.setDataURL("http://cw.cityworks.cn/aw-repository");
        deviceList.add(device);
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
            deviceList.add(new Device("device-one-1"));
            deviceList.add(new Device("device-one-2"));
            deviceList.add(new Device("device-one-3"));
            deviceList.add(new Device("device-one-4"));
            deviceList.add(new Device("device-one-5"));
            deviceList.add(new Device("device-one-6"));
            deviceList.add(new Device("device-one-7"));
            deviceList.add(new Device("device-one-8"));
            deviceList.add(new Device("device-one-9"));
            deviceList.add(new Device("device-one-10"));
        }
    }
}
