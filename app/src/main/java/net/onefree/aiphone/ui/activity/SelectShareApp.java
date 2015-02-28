package net.onefree.aiphone.ui.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import net.onefree.aiphone.R;
import net.onefree.aiphone.adapter.AppAdapter;
import net.onefree.aiphone.bean.Application;
import net.onefree.aiphone.common.StatusCode;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 2015/1/12.
 */
public class SelectShareApp extends BaseActivity {


    @InjectView(R.id.listview)
    public ListView listView;

    AppAdapter appAdapter;
    private List<Application> applications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_share_app);
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        showBack("分享App");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.putExtra("application", applications.get(position));
                App.objectIntent = applications.get(position);
                setResult(StatusCode.SELECT_APP);
                finish();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadApps();
    }

    private void loadApps() {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                //非系统应用
            } else {
                continue;
            }
            Application application = new Application();
            application.setName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            application.setPackageName(packageInfo.packageName);
            application.setVersionCode(packageInfo.versionCode);
            application.setVersionName(packageInfo.versionName);
            application.setAppIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));

            applications.add(application);
        }

        appAdapter = new AppAdapter(this, applications);
        listView.setAdapter(appAdapter);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
