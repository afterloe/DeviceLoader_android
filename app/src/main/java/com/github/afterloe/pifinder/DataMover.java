package com.github.afterloe.pifinder;

import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import timber.log.Timber;

class DataMover implements Serializable {

    private static Long HTTP_CONNECT_TIMEOUT = 5L;
    private static Long HTTP_READ_TIMEOUT = 5L;

    public static OkHttpClient client;
    public static GsonBuilder gsonBuilder;

    static void initOKHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(true);
        client = builder.build();
    }

    static void initGson() {
        gsonBuilder = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping(); //默认是GSON把HTML 转义的
    }

    static void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}