package com.github.afterloe.pifinder.component;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.ui.activity.DetailActivity;

import java.io.Serializable;

public class DeviceClick implements Serializable, AdapterView.OnItemClickListener {

    private Context context;

    public DeviceClick(Context context) {
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Device device = (Device) parent.getItemAtPosition(position);
            Toast.makeText(context, "准备连接 " + device.getName(), Toast.LENGTH_LONG).show();
            Intent detail = new Intent(context, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object", device);
            detail.putExtras(bundle);
            context.startActivity(detail);
        } catch (NullPointerException exception) {

        }
    }
}
