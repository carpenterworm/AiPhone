package net.onefree.aiphone.utils;

import android.content.Context;

import net.onefree.aiphone.bean.PhoneInfo;

/**
 * Created by maoah on 14/11/1.
 */
public class PhoneInfoUtils {

    private static PhoneInfoUtils phoneInfoUtils;


    public static PhoneInfoUtils getInstance(Context context) {
        if (phoneInfoUtils == null) {
            return new PhoneInfoUtils();
        }
        return phoneInfoUtils;
    }


}
