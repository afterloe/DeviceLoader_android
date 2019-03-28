package com.github.afterloe.pifinder.api;

import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.domain.ResponseObj;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class DeviceApi extends Api {

    private static final String DEVICE_LIST = BASE_URL + "/v2/list";
    private static final String DEVICE_DETAIL = BASE_URL + "/v1/device";

    public Device getDevice(int id) {
        String fetchUrl = DEVICE_DETAIL + "/" + id;
        Device fetchDevice = null;
        final Request request = new Request.Builder().url(fetchUrl).get().build();
        try {
            Response response = getClient().newCall(request).execute();
            ResponseObj<Device> res = getGson().fromJson(response.body().string(),
                    new TypeToken<ResponseObj<Device>>(){}.getType());
            if (res.getCode().equals(200)) {
                fetchDevice = res.getData();
            } else {
                throw new Exception(res.getMsg());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return fetchDevice;
    }

    public List<Device> getDeviceList(int begin, int end) {
        String fetchUrl = DEVICE_LIST + "?bg=" + begin + "&ed=" + end;
        List<Device> devices = new ArrayList<>();
        final Request request = new Request.Builder().url(fetchUrl).get().build();
        try {
           Response response = getClient().newCall(request).execute();
           ResponseObj<List<Device>> res = getGson().fromJson(response.body().string(),
                   new TypeToken<ResponseObj<List<Device>>>(){}.getType());
           if (res.getCode().equals(200)) {
               devices = res.getData();
           } else {
               throw new Exception(res.getMsg());
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }
}
