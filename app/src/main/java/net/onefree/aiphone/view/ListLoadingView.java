package net.onefree.aiphone.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.onefree.aiphone.R;

/**
 * Created by admin on 2015/1/13.
 */
public class ListLoadingView {

    private Context mContext;

    private View contentView;

    private LayoutInflater layoutInflater;

    private TextView hint;
    private ProgressBar progressBar;

    public ListLoadingView(Context mContext) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
        contentView = layoutInflater.inflate(R.layout.listview_loading, null);
        progressBar = (ProgressBar) contentView.findViewById(R.id.progressBar);
        hint = (TextView) contentView.findViewById(R.id.hint);
    }

    public View getContentView() {
        return contentView;
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        this.hint.setVisibility(View.GONE);
    }

    public void showHint(String hint) {
        progressBar.setVisibility(View.GONE);
        this.hint.setVisibility(View.VISIBLE);
        this.hint.setText(hint);
    }

    public void hide(){
        progressBar.setVisibility(View.GONE);
        this.hint.setVisibility(View.GONE);
    }

}
