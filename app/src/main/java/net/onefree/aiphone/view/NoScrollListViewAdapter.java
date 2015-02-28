package net.onefree.aiphone.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.onefree.aiphone.R;

/**
 * Created by maoah on 14/10/25.
 */
public class NoScrollListViewAdapter extends BaseAdapter {
    private Context mContext;

    public NoScrollListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return LayoutInflater.from(mContext).inflate(R.layout.item_phone_info, null);
    }
}
