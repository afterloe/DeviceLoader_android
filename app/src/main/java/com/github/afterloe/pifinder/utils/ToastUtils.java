package com.github.afterloe.pifinder.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.github.afterloe.pifinder.DataMoverApp;

/**
 * 描述：Toast 工具类
 *
 * @author CoderPig on 2018/02/14 11:06.
 */

public class ToastUtils {
    public static void shortToast(String msg) {
        Toast toast = Toast.makeText(DataMoverApp.getContext(),msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 40);
        toast.show();
    }

    public static void longToast(String msg) {
        Toast toast = Toast.makeText(DataMoverApp.getContext(),msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 40);
        toast.show();
    }
}
