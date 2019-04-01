package com.github.afterloe.pifinder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.api.ApiService;
import com.github.afterloe.pifinder.domain.Task;
import com.github.afterloe.pifinder.ui.adapter.TaskAdapter;
import com.github.afterloe.pifinder.utils.NetworkUtils;
import com.github.afterloe.pifinder.utils.ResUtils;
import com.github.afterloe.pifinder.utils.ToastUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 附近设备
 */
public class NearbyDeviceFragment extends Fragment implements Serializable {

    private static final Integer TASK_ID = 1;
    private MapView mapView;
    private CompositeDisposable subscriptions;
    private RecyclerView rec_tasks;
    private List<Task> tasks;
    private TaskAdapter adapter;

    public static NearbyDeviceFragment newInstance() {
        return new NearbyDeviceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby_device, container, false);
        mapView = view.findViewById(R.id.gis_map);
        rec_tasks = view.findViewById(R.id.tasks);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        rec_tasks.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        subscriptions = new CompositeDisposable();
        adapter = new TaskAdapter(getActivity(), tasks);
        ArcGISMap map = new ArcGISMap(Basemap.Type.STREETS_WITH_RELIEF_VECTOR, 26.09049903663615, 119.30582982722777, 19);
        mapView.setMap(map);
        tasks = new ArrayList<>();
        rec_tasks.setAdapter(adapter);
        fetchMyTask(TASK_ID);
    }

    @Override
    public void onPause() {
        mapView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void fetchMyTask(Integer taskId) {
        Disposable subscribe = ApiService.getInstance().taskService.fetchTasks(taskId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (data.getCode().equals(200)) {
                        List<Task> results = data.getData();
                        adapter.addAll(results);
                        if (0 == results.size()) {
                            ToastUtils.longToast(ResUtils.getString(R.string.task_default));
                        }
                    }
                }, NetworkUtils::processRequestException);
        subscriptions.add(subscribe);
    }
}
