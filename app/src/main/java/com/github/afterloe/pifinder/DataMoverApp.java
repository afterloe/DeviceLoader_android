package com.github.afterloe.pifinder;

import android.app.Application;

import java.io.Serializable;

public class DataMoverApp extends Application implements Serializable {

    private static DataMoverApp context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        DataMover.initTimber();
        DataMover.initOKHttp();
    }

    public static DataMoverApp getContext() {
        return context;
    }
}
