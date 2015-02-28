package net.onefree.aiphone.bean;

import java.io.Serializable;

/**
 * Created by maoah on 2014/11/12.
 */
public class Tool implements Serializable {
    public static final int FLASH = 1;
    private String name;
    private Class activity;
    private int icon;
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
