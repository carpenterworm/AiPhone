package net.onefree.aiphone.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2014/12/28.
 */
public class AlertUtils {

    /**
     * toast 短提示
     *
     * @param context
     * @param content
     */
    public static void alert(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
