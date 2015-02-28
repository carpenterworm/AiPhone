package net.onefree.aiphone.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by admin on 2015/1/12.
 */
public class Application extends BaseApplication implements Serializable {

    private Drawable appIcon;

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
