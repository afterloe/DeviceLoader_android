package com.github.afterloe.pifinder.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.ui.activity.DeviceDetailActivity;

import java.io.Serializable;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceItem> implements Serializable  {

    private Context context;
    private List<Device> devices;

    public DeviceAdapter(Context context, List<Device> devices) {
        this.context = context;
        this.devices = devices;
    }

    public void addAll(List<Device> data) {
        devices.clear();
        devices.addAll(data);
        notifyDataSetChanged();
    }

    public void loadMore(List<Device> data) {
        devices.addAll(data);
    }

    @NonNull
    @Override
    public DeviceItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DeviceItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_devices, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceItem holder, int position) {
        holder.bind(devices.get(position));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    class DeviceItem extends RecyclerView.ViewHolder {

        ImageView pic;
        TextView deviceName;
        TextView devicePosition;
        TextView deviceDistance;
        View view;

        DeviceItem(@NonNull View view) {
            super(view);
            pic = view.findViewById(R.id.device_status);
            deviceName = view.findViewById(R.id.device_name);
            devicePosition = view.findViewById(R.id.device_position);
            deviceDistance = view.findViewById(R.id.device_distance);
            this.view = view;
        }

        void bind(Device device) {
            pic.setBackgroundResource(R.drawable.minium_device_hd_72px);
            devicePosition.setText(device.getPosition());
            deviceName.setText(device.getName());
            Double distance = device.getDistance();
            if (null != distance) {
                deviceDistance.setText("可连接");
            }
            // bind 监听事件
            view.setOnClickListener(v -> {
                Intent intent = new Intent(context, DeviceDetailActivity.class);
                intent.putExtra("id", device.getId());
                context.startActivity(intent);
            });
        }
    }
}
