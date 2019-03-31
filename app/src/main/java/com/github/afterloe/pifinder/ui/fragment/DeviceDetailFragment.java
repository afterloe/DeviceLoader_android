package com.github.afterloe.pifinder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.api.ApiService;
import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.domain.Point;
import com.github.afterloe.pifinder.ui.adapter.PointAdapter;
import com.github.afterloe.pifinder.utils.NetworkUtils;
import com.github.afterloe.pifinder.utils.ResUtils;
import com.github.afterloe.pifinder.utils.ToastUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceDetailFragment extends Fragment implements Serializable {

    private TextView deviceName;
    private TextView deviceModifyTime;
    private TextView deviceRemarks;
    private SimpleDateFormat simpleDateFormat;
    private RecyclerView rec_points;
    private CompositeDisposable subscriptions;
    private List<Point> data;
    private PointAdapter adapter;

    public static DeviceDetailFragment newInstance() {
        return new DeviceDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_detail, container, false);
        deviceName = view.findViewById(R.id.device_name);
        deviceModifyTime = view.findViewById(R.id.device_modifyTime);
        deviceRemarks = view.findViewById(R.id.device_remarks);
        rec_points = view.findViewById(R.id.rec_points);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rec_points.setLayoutManager(layoutManager);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscriptions = new CompositeDisposable();
        data = new ArrayList<>();
        adapter = new PointAdapter(getActivity(), data);
        rec_points.setAdapter(adapter);
        Bundle bundle = getArguments();
        fetchDeviceDetail(bundle.getInt("id"));
        fetchPoints(bundle.getInt("id"));
    }

    private void fetchDeviceDetail(Integer id) {
        Disposable subscribe = ApiService.getInstance().deviceService.fetchDevicde(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (data.getCode().equals(200)) {
                        Device device = data.getData();
                        deviceName.setText(device.getName());
                        Long modifyTime = device.getModifyTime();
                        if (null != modifyTime && 0 != modifyTime) {
                            deviceModifyTime.setText(simpleDateFormat.format(new Date(modifyTime * 1000)));
                        }
                        deviceRemarks.setText(device.getRemark());
                    } else {
                        ToastUtils.shortToast(ResUtils.getString(R.string.network_connected_exception));
                    }
                }, NetworkUtils::processRequestException);
        subscriptions.add(subscribe);
    }

    private void fetchPoints(Integer id) {
        Disposable subscribe = ApiService.getInstance().pointService.fetchPoints(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (data.getCode().equals(200)) {
                        List<Point> results = data.getData();
                        adapter.addAll(results);
                        if (0 == results.size()) {
                            ToastUtils.longToast(ResUtils.getString(R.string.point_default));
                        }
                    }
                }, NetworkUtils::processRequestException);
        subscriptions.add(subscribe);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscriptions.clear();
    }
}
