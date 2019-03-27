package com.github.afterloe.pifinder.api;

import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.domain.ResponseObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static java.util.stream.Collectors.toList;

public class DeviceApi extends Api {

    private static final String DEVICE_LIST = BASE_URL + "/v1/list";
    private static final String DEVICE_DETAIL = BASE_URL + "/v1/device";

    public Device getDevice(int id) {
        String fetchUrl = DEVICE_DETAIL + "/" + id;
        final Request request = new Request.Builder().url(fetchUrl).get().build();
        try {
            Response response = getClient().newCall(request).execute();

        } catch (Exception exception) {

        }
        return null;
    }

    public List<Device> getDeviceList(int begin, int end) {
        String fetchUrl = DEVICE_LIST + "?bg=" + begin + "&ed=" + end;
        ArrayList<Device> devices = new ArrayList<>();
        final Request request = new Request.Builder().url(fetchUrl).get().build();
        try {
           Response response = getClient().newCall(request).execute();
           ResponseObj res = getGson().fromJson(response.body().string(), ResponseObj.class);
           if (res.getCode().equals(200)) {
               devices = getGson().fromJson(res.getData(),
                       new TypeToken<ArrayList<Device>>(){}.getType());
           } else {
               throw new Exception(res.getMsg());
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }
}
