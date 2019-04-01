package com.github.afterloe.pifinder.api;

import com.github.afterloe.pifinder.domain.ResponseObj;
import com.github.afterloe.pifinder.domain.Task;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TaskService {

    @GET(ApiService.BASE_URL + "/v1/task/list")
    Flowable<ResponseObj<List<Task>>> fetchTasks(@Query("key") Integer id);
}
