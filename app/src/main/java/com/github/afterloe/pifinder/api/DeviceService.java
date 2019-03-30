package com.github.afterloe.pifinder.api;

import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.domain.ResponseObj;

import java.io.Serializable;
import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeviceService extends Serializable {

    String BASE_URL = "http://192.168.2.59:8080";

    @GET(BASE_URL + "/v2/list")
    Flowable<ResponseObj<List<Device>>> fetchDevices(@Query("bg") Integer bg, @Query("ed") Integer ed);

    @GET(BASE_URL + "/v1/device/{id}")
    Flowable<ResponseObj<Device>> fetchDevicde(@Path("id") Integer id);
}


//class DeviceApi {
//
//    private static final String DEVICE_LIST = BASE_URL + "/v2/list";
//    private static final String DEVICE_DETAIL = BASE_URL + "/v1/device";
//
//    private static DeviceApi instance;
//
//    public static DeviceApi getInstance() {
//        if (instance == null) {
//            instance = new DeviceApi();
//        }
//        return instance;
//    }
//
//    private DeviceApi() {
//        Retrofit storeRestAPI = new Retrofit.Builder().baseUrl(BASE_URL)
//                .client(DataMover.client)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        apis = storeRestAPI.create(APIs.class);
//    }
//
//    public Device getDevice(int id) {
//        String fetchUrl = DEVICE_DETAIL + "/" + id;
//        Device fetchDevice = null;
//        final Request request = new Request.Builder().url(fetchUrl).get().build();
//        try {
//            Response response = getClient().newCall(request).execute();
//            ResponseObj<Device> res = getGson().fromJson(response.body().string(),
//                    new TypeToken<ResponseObj<Device>>(){}.getType());
//            if (res.getCode().equals(200)) {
//                fetchDevice = res.getData();
//            } else {
//                throw new Exception(res.getMsg());
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//        return fetchDevice;
//    }
//
//    public List<Device> getDeviceList(int begin, int end) {
//        String fetchUrl = DEVICE_LIST + "?bg=" + begin + "&ed=" + end;
//        List<Device> devices = new ArrayList<>();
//        final Request request = new Request.Builder().url(fetchUrl).get().build();
//        try {
//           Response response = getClient().newCall(request).execute();
//           ResponseObj<List<Device>> res = getGson().fromJson(response.body().string(),
//                   new TypeToken<ResponseObj<List<Device>>>(){}.getType());
//           if (res.getCode().equals(200)) {
//               devices = res.getData();
//           } else {
//               throw new Exception(res.getMsg());
//           }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return devices;
//    }
//}
