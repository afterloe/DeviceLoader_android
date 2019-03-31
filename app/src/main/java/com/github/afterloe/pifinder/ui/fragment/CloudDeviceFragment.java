package com.github.afterloe.pifinder.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.api.ApiService;
import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.ui.adapter.DeviceAdapter;
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
 * 远程设备列表
 */
public class CloudDeviceFragment extends Fragment implements Serializable {

    private SwipeRefreshLayout srl_refresh;
    private FloatingActionButton fab_top; // 悬浮按钮
    private RecyclerView rec_device;
    private CompositeDisposable subscriptions;
    private DeviceAdapter adapter;
    private int curPage = 1;
    private ArrayList<Device> data;
    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    public static CloudDeviceFragment newInstance() {
        return new CloudDeviceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cloud_devices, container, false);
        srl_refresh = view.findViewById(R.id.srl_refresh);
        rec_device = view.findViewById(R.id.rec_device);
        fab_top = view.findViewById(R.id.fab_top);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rec_device.setLayoutManager(layoutManager);
        srl_refresh.setOnRefreshListener(() -> {
            curPage = 1;
            fetchCloudDevice(true);  // 拉取远程数据
        });
        // 设置 进度条颜色
        srl_refresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        rec_device.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 加载更多
                    if (layoutManager.getItemCount() - recyclerView.getChildCount() <= layoutManager.findFirstVisibleItemPosition()) {
                        ++curPage;
                        fetchCloudDevice(false); // 更新数据
                    }
                }
                if (0 != layoutManager.findFirstVisibleItemPosition()) {
                    // 显示悬浮按钮
                    fabShowAnim();
                } else {
                    // 隐藏悬浮按钮
                    fabHiddenAnim();
                }
            }
        });
        fab_top.setOnClickListener(v -> {
            LinearLayoutManager manager = (LinearLayoutManager) rec_device.getLayoutManager();
            if (10 > manager.findFirstVisibleItemPosition()) {
                rec_device.smoothScrollToPosition(0);
            } else {
                rec_device.scrollToPosition(0);
                fabHiddenAnim();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscriptions = new CompositeDisposable();
        data = new ArrayList<>();
        adapter = new DeviceAdapter(getActivity(), data);
        rec_device.setAdapter(adapter);
        srl_refresh.setRefreshing(true);
        fetchCloudDevice(true);  // 拉取远程数据
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscriptions.clear();
    }

    /* 悬浮按钮显示动画 */
    @SuppressLint("RestrictedApi")
    private void fabShowAnim() {
        if (fab_top.getVisibility() == View.GONE) {
            fab_top.setVisibility(View.VISIBLE);
            ViewCompat.animate(fab_top).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null).start();
        }
    }

    /* 悬浮图标隐藏动画 */
    private void fabHiddenAnim() {
        if (fab_top.getVisibility() == View.VISIBLE) {
            ViewCompat.animate(fab_top).scaleX(0.0F).scaleY(0.0F).alpha(0.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) { }

                @SuppressLint("RestrictedApi")
                @Override
                public void onAnimationEnd(View view) {
                    fab_top.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(View view) { }
            }).start();
        }
    }

    private void fetchCloudDevice(boolean isRefresh) {
        Disposable subscribe = ApiService.getInstance().deviceService.fetchDevices((curPage - 1) * 10, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> srl_refresh.setRefreshing(true))
                .doFinally(() -> srl_refresh.setRefreshing(false))
                .subscribe(data -> {
                    if (data.getCode().equals(200)) {
                        List<Device> results = data.getData();
                        if (isRefresh) {
                            adapter.addAll(results);
                            ToastUtils.shortToast(ResUtils.getString(R.string.refresh_success));
                        } else {
                            adapter.loadMore(results);
                            String msg = String.format(ResUtils.getString(R.string.load_more_num), results.size(), "设备");
                            ToastUtils.shortToast(msg);
                        }
                    }
                }, NetworkUtils::processRequestException);
        subscriptions.add(subscribe);
    }
}
