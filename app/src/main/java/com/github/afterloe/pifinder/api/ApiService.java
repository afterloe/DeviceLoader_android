package com.github.afterloe.pifinder.api;

import com.github.afterloe.pifinder.DataMover;

import java.io.Serializable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请求API
 */
public final class ApiService implements Serializable {

    public static final String BASE_URL = "http://192.168.2.58:8080";

    private static ApiService instance;
    public DeviceService deviceService;
    public PointService pointService;
    public TaskService taskService;

    public static ApiService getInstance() {
        if (instance == null) {
            instance = new ApiService();
        }
        return instance;
    }

    private ApiService() {
        Retrofit storeRestAPI = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(DataMover.client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        deviceService = storeRestAPI.create(DeviceService.class);
        pointService = storeRestAPI.create(PointService.class);
        taskService = storeRestAPI.create(TaskService.class);
    }
}

