package com.github.afterloe.pifinder.api;

import com.github.afterloe.pifinder.DataMover;
import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.domain.ResponseObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public final class ApiService implements Serializable {

    protected static final String BASE_URL = "http://192.168.2.59:8080";

    private static ApiService instance;
    public DeviceService deviceService;

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
    }
}

