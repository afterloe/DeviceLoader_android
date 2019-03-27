package com.github.afterloe.pifinder.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public abstract class Api implements Serializable {

    protected static final String BASE_URL = "http://192.168.2.59:8080";
    private static OkHttpClient client;
    private static GsonBuilder gsonBuilder;

    /**
     * 初始化api连接池
     */
    private void initApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(3 * 1000, TimeUnit.MILLISECONDS)
                .followRedirects(true);
        client = builder.build();
    }

    /**
     * 初始化JSON 序列
     */
    private void initGSON() {
        gsonBuilder = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping(); //默认是GSON把HTML 转义的
    }

    protected OkHttpClient getClient() {
        if (null == client) {
            initApi();
        }
        return client;
    }

    protected Gson getGson() {
        if (null == gsonBuilder) {
            initGSON();
        }
        return gsonBuilder.create();
    }
}

