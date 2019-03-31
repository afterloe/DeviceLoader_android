package com.github.afterloe.pifinder.utils;

import com.github.afterloe.pifinder.DataMoverApp;

/**
 * 获取文件资源工具类
 */

public class ResUtils {
    /* 获取文件资源 */
    public static String getString(int strId) {
        return DataMoverApp.getContext().getResources().getString(strId);
    }
}
