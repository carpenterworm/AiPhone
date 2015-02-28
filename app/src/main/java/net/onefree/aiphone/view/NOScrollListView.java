package net.onefree.aiphone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by maoah on 14/10/25.
 */
public class NOScrollListView extends ListView {

    public NOScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NOScrollListView(Context context) {
        super(context);
    }

    public NOScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
