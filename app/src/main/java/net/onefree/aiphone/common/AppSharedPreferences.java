package net.onefree.aiphone.common;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreferences {
    private static SharedPreferences sharedPreferences = null;
    private static SharedPreferences.Editor sp_editor = null;

    private static SharedPreferences getPrefInstance(final Context c) {
        if (sharedPreferences == null) {
            sharedPreferences = c.getSharedPreferences("aiphone", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    private static SharedPreferences.Editor getEditorInstance(final Context c) {
        if (sp_editor == null) {
            sp_editor = getPrefInstance(c).edit();
        }
        return sp_editor;
    }

    public static Boolean getPreferencesBoolean(final Context c, String constants, boolean defaultValue) {
        return getPrefInstance(c).getBoolean(constants, defaultValue);
    }

    public static void setPreferencesBoolean(final Context c, String constants, final Boolean value) {
        getEditorInstance(c).putBoolean(constants, value).commit();
    }

    public static String getPreferencesString(final Context c, String constants) {
        return getPrefInstance(c).getString(constants, "");
    }

    public static void setPreferencesString(final Context c, String constants, final String value) {
        getEditorInstance(c).putString(constants, value.trim()).commit();
    }

    public static void setPreferencesLong(final Context c, String constants, final long value) {
        getEditorInstance(c).putLong(constants, value).commit();
    }

    public static Long getPreferencesLong(final Context c, String constants) {
        return getPrefInstance(c).getLong(constants, 0);
    }


}
