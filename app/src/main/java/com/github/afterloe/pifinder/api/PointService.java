package com.github.afterloe.pifinder.api;

import com.github.afterloe.pifinder.domain.Point;
import com.github.afterloe.pifinder.domain.ResponseObj;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PointService {

   String BASE_URL = "http://192.168.3.3:8080";

    @GET(BASE_URL + "/v1/point/{id}")
    Flowable<ResponseObj<List<Point>>> fetchPoints(@Query("id") Integer id);
}
