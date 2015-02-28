package net.onefree.aiphone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.onefree.aiphone.R;
import net.onefree.aiphone.bean.Tool;

import java.util.List;

/**
 * Created by maoah on 14/10/23.
 */
public class ToolAdapter extends BaseAdapter {

    LayoutInflater inflater;

    Context mContext;

    private List<Tool> tools;

    public ToolAdapter(Context mContext, List<Tool> tools) {
        this.mContext = mContext;
        this.tools = tools;
    }

    @Override
    public int getCount() {
        return tools.size();
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
        Tool tool = tools.get(i);
        if (view == null) {
            holer = new Holer();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_tool, null);
            holer.name = (TextView) view.findViewById(R.id.name);
            holer.icon = (ImageView) view.findViewById(R.id.icon);
            view.setTag(holer);
        }
        holer = (Holer) view.getTag();
        holer.name.setText(tool.getName());
        if (tool.getIcon() != 0) {
            holer.icon.setImageResource(tool.getIcon());
        } else {
            holer.icon.setImageResource(R.drawable.ic_launcher);
        }
        return view;
    }
}
