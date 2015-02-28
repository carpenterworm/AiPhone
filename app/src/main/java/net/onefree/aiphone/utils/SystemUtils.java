package net.onefree.aiphone.utils;

import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by maoah on 14/12/1.
 */
public class SystemUtils {

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }
}
