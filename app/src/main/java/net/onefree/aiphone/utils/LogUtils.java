package net.onefree.aiphone.utils;

import android.util.Log;

import net.onefree.aiphone.common.AppCommon;

/**
 * Created by admin on 2015/1/5.
 */
public class LogUtils {


    public static void d(String tag, String message) {
        if (!AppCommon.IS_DEBUG) {
            Log.d(tag, message);
        }
    }

}
