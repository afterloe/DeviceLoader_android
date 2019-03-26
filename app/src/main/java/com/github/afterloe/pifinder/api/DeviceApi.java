package com.github.afterloe.pifinder.api;

import android.util.Log;

import com.github.afterloe.pifinder.domain.Device;
import com.github.afterloe.pifinder.domain.ResponseObj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.util.stream.Collectors.toList;


public class DeviceApi implements Serializable {

    private static final String BASE_URL = "http://192.168.2.59:8080";
    private static final String DEVICE_LIST = BASE_URL + "/v1/list";

    public List<Device> getDeviceList(int begin, int end) {
        String fetchUrl = DEVICE_LIST + "?bg=" + begin + "&ed=" + end;
        ArrayList<Device> devices = new ArrayList<>();
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(fetchUrl).get().build();
        Call call = okHttpClient.newCall(request);
        call.timeout().timeout(5 * 1000, TimeUnit.MILLISECONDS);
        try {
           Response response = call.execute();
           Gson gson = new GsonBuilder()
                   .setLenient()// json宽松
                   .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                   .serializeNulls() //智能null
                   .setPrettyPrinting()// 调教格式
                   .disableHtmlEscaping() //默认是GSON把HTML 转义的
                   .create();
           ResponseObj res = gson.fromJson(response.body().string(), ResponseObj.class);
           if (res.getCode().equals(200)) {
               ArrayList objs = (ArrayList) res.getData();
               devices = (ArrayList<Device>) objs.stream().map(obj -> {
                   Map detail = (Map) obj;
                   Device device = new Device();
                   device.setId(Double.valueOf(detail.get("id").toString()).intValue());
                   device.setName(detail.get("name").toString());
                   device.setRemark(detail.get("remark").toString());
                   return device;
               }).collect(toList());
//               for (Map obj : objs) {
//                   Log.i("api", obj.toString());
//                   Device device = new Device();
//                   device.setId(Integer.valueOf(obj.get("id").toString()));
//                   device.setName(obj.get("name").toString());
//                   device.setRemark(obj.get("remark").toString());
//                   devices.add(device);
//               }
           } else {
               throw new Exception(res.getMsg());
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return devices;
    }
}
