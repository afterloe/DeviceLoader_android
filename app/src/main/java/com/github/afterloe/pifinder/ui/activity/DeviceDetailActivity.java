package com.github.afterloe.pifinder.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.ui.fragment.DeviceDetailFragment;
import com.github.afterloe.pifinder.utils.ResUtils;
import com.github.afterloe.pifinder.utils.ToastUtils;
import com.r0adkll.slidr.Slidr;

import java.io.Serializable;

public class DeviceDetailActivity extends AppCompatActivity implements Serializable {

    private Integer deviceId;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        Slidr.attach(this);
        initData();
        initView();
    }

    private void initData() {
        deviceId = getIntent().getIntExtra("id", -1);
        if (-1 == deviceId) {
            ToastUtils.shortToast(ResUtils.getString(R.string.network_json_syntax_exception));
            return ;
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(ResUtils.getString(R.string.title_device_detail));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        getSupportFragmentManager().beginTransaction().replace(R.id.cly_root, DeviceDetailFragment.newInstance()).commit();
    }
}
