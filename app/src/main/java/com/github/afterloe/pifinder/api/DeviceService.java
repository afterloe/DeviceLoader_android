package com.github.afterloe.pifinder.api;

import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.domain.ResponseObj;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceService {


    @GET(ApiService.BASE_URL + "/v2/list")
    Flowable<ResponseObj<List<Device>>> fetchDevices(@Query("bg") Integer bg, @Query("ed") Integer ed);

    @GET(ApiService.BASE_URL + "/v1/device/{id}")
    Flowable<ResponseObj<Device>> fetchDevicde(@Path("id") Integer id);
}
