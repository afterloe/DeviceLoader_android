package com.github.afterloe.pifinder.api;

import com.github.afterloe.pifinder.domain.Point;
import com.github.afterloe.pifinder.domain.ResponseObj;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PointService {

    @GET(ApiService.BASE_URL + "/v1/point/{id}")
    Flowable<ResponseObj<List<Point>>> fetchPoints(@Path("id") Integer id);
}
