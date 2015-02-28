package net.onefree.aiphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.onefree.aiphone.R;
import net.onefree.aiphone.bean.Application;
import net.onefree.aiphone.utils.BitmapUtils;

import java.util.List;

/**
 * Created by maoah on 14/10/23.
 */
public class AppAdapter extends BaseAdapter {

    LayoutInflater inflater;

    Context mContext;

    private List<Application> applications;

    public AppAdapter(Context mContext, List<Application> applications) {
        this.mContext = mContext;
        this.applications = applications;
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class Holer {
        TextView name;
        ImageView icon;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holer holer = null;
        Application application = applications.get(i);
        if (view == null) {
            holer = new Holer();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_app, null);
            holer.name = (TextView) view.findViewById(R.id.name);
            holer.icon = (ImageView) view.findViewById(R.id.icon);
            view.setTag(holer);
        } else {
            holer = (Holer) view.getTag();
        }
        holer.name.setText(application.getName());
        holer.icon.setImageDrawable(application.getAppIcon());
        System.out.println("---------------------------" + BitmapUtils.getBitmapByte(BitmapUtils.drawableToBitmap(application.getAppIcon())).length / 1024);
        return view;
    }
}
