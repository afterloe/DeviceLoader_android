package com.github.afterloe.pifinder.component;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.domain.Device;

import java.io.Serializable;
import java.util.List;

public class DeviceAdapter extends ArrayAdapter implements Serializable {

    private final int resourceId;

    public DeviceAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Device device = (Device) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView pic = view.findViewById(R.id.device_status);
        TextView deviceName = view.findViewById(R.id.device_name);
        TextView devicePosition = view.findViewById(R.id.device_position);
        pic.setBackgroundResource(R.drawable.ic_inline);
        devicePosition.setText(device.getPosition());
        deviceName.setText(device.getName());
        return view;
    }

}
