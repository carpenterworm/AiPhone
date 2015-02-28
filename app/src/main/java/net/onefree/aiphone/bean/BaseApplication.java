package net.onefree.aiphone.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by admin on 2015/1/12.
 */
public class BaseApplication implements Serializable {

    public static int APP_TYPE_SYSTEM = 0;
    public static int APP_TYPE_USER = 1;
    private String name;
    private int versionCode;
    private String versionName;
    private String packageName;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
