package net.onefree.aiphone.ui.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import net.onefree.aiphone.R;
import net.onefree.aiphone.bean.Devices;
import net.onefree.aiphone.common.AppCommon;
import net.onefree.aiphone.common.AppSharedPreferences;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by maoah on 2014/12/2.
 */
public class App extends Application {

    private static Devices DEVICES;
    private static Context mContext;

    public static DisplayImageOptions options;

    // 待开发--activity 传递数据
    public static Object objectIntent;

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
    }

    private void initApp() {
        mContext = this;
        initLeanCloud();
        initUser();
        initDevices();
        initImageLoder();
    }


    /**
     * 初始化云存储
     */
    private void initLeanCloud() {

    }

    /**
     * 初始用户信息
     */
    public static void initUser() {
    }

    private void initDevices() {
        String devices = AppSharedPreferences.getPreferencesString(this.getApplicationContext(), AppCommon.REGISTER_DEVICES);
        if (TextUtils.isEmpty(devices))
            return;
    }

    /**
     * 初始图片加载器
     */
    private void initImageLoder() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);

        options = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisk(true).showImageOnLoading(R.drawable.none).build();
    }


    /**
     * 当前设备注册信息
     *
     * @return
     */
    public static Devices getDEVICES() {
        return DEVICES;
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 图片动画显示器
     *
     * @author admin
     */
    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    /**
     * 登录
     *
     * @param context
     */
    public static void goToLogin(Activity context) {

    }
}
