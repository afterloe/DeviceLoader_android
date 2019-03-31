package com.github.afterloe.pifinder.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.github.afterloe.pifinder.DataMoverApp;

/**
 * 应用包相关的工具类
 */

public class PackageUtils {

    public static int packageCode() {
        PackageManager manager = DataMoverApp.getContext().getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(DataMoverApp.getContext().getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String packageName() {
        PackageManager manager = DataMoverApp.getContext().getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(DataMoverApp.getContext().getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
}
