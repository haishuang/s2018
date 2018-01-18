package example.hais.s2018.utils;

import android.util.Log;

import example.hais.s2018.base.BaseApplication;

/**
 * Created by Huanghs on 2017/8/21.
 */

public class LogUtils {
    private final static String TAG = "youledong";

    private LogUtils() {
    }

    public static void e(String info) {
        if (BaseApplication.isDebug) {
            Log.e(TAG, "=======" + info);
        }
    }

    public static void e(String tag, String info) {
        if (BaseApplication.isDebug) {
            Log.e(tag, "=======" + info);
        }
    }
}
