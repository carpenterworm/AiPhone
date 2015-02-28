package net.onefree.aiphone.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.onefree.aiphone.R;
import net.onefree.aiphone.bean.PhoneInfo;

import java.util.List;

/**
 * Created by maoah on 14/10/24.
 */
public class ExpandPanelView extends RelativeLayout implements View.OnClickListener {

    private View titleLayout;

    private TextView titleTextView;
    private ImageView arrow;
    private ViewGroup mContent;

    private boolean mExpand;

    private int contentHeight;
    private int animDuration = 300;

    private LayoutInflater inflater;

    private String title;

    private List<PhoneInfo> phoneInfos;

    private int contentLineMarginLeft = 0;

    public ExpandPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_expand_panle, this, true);
        initView();
        initStyle(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView() {
        titleTextView = (TextView) findViewById(R.id.title);
        titleLayout = findViewById(R.id.title_layout);
        arrow = (ImageView) findViewById(R.id.arrow);
        mContent = (ViewGroup) findViewById(R.id.content);

        measureContentHeight();

        titleLayout.setOnClickListener(this);
        contentLineMarginLeft = getResources().getDimensionPixelOffset(R.dimen.expand_panel_content_margin_left);
    }

    /**
     * 测量内容高度
     */
    private void measureContentHeight() {
        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//        mContent.measure(width, height);

        mContent.measure(MeasureSpec.makeMeasureSpec(getResources().getDisplayMetrics().widthPixels, MeasureSpec.EXACTLY), 0);
        contentHeight = mContent.getMeasuredHeight();
    }

    /**
     * 初始化配置信息
     *
     * @param context
     * @param attrs
     * @return
     */
    private void initStyle(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandPanelView);

        if (a.hasValue(R.styleable.ExpandPanelView_title)) {
            title = a.getString(R.styleable.ExpandPanelView_title);
            titleTextView.setText(title);
        }

//        mExpand = a.getBoolean(R.styleable.ExpandPanleView_showContent, false);

        if (mExpand) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, contentHeight);
            params.addRule(RelativeLayout.BELOW, R.id.title_layout);
            mContent.setLayoutParams(params);
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
            params.addRule(RelativeLayout.BELOW, R.id.title_layout);
            mContent.setLayoutParams(params);
        }
        a.recycle();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_layout:
                showOrHideContent();
                break;
        }
    }

    private void showOrHideContent() {
        ViewWrapper wrapper = new ViewWrapper(mContent);
        if (mExpand) {
            ObjectAnimator.ofFloat(arrow, "rotation", 0).setDuration(animDuration).start();
            ObjectAnimator.ofInt(wrapper, "height", 0).setDuration(animDuration).start();
        } else {
            ObjectAnimator.ofFloat(arrow, "rotation", -180).setDuration(animDuration).start();
            ObjectAnimator.ofInt(wrapper, "height", contentHeight).setDuration(animDuration).start();
        }
        mExpand = !mExpand;
    }


    private class ViewWrapper {
        private View mTarget;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getHeight() {
            return mTarget.getLayoutParams().height == LayoutParams.WRAP_CONTENT ? (mExpand ? contentHeight : 0) : mTarget.getLayoutParams().height;
        }

        public void setHeight(int height) {
            mTarget.getLayoutParams().height = height;
            mTarget.requestLayout();
        }
    }

    public void setPhoneInfos(List<PhoneInfo> phoneInfos) {
        this.phoneInfos = phoneInfos;
        addPhoneInfos();
    }

    private void addPhoneInfos() {
        if (phoneInfos == null)
            return;
        mContent.removeAllViews();

        LinearLayout.LayoutParams topLineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        View topLine = inflater.inflate(R.layout.line, null);
        mContent.addView(topLine, topLineParams);

        for (int i = 0; i < phoneInfos.size(); i++) {
            PhoneInfo phoneInfo = phoneInfos.get(i);
            View view = inflater.inflate(R.layout.item_phone_info, null);
            View line = inflater.inflate(R.layout.line, null);
            LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
            lineParams.setMargins(contentLineMarginLeft, 0, 0, 0);
            setPhoneInfoViewValue(phoneInfo, view);
            mContent.addView(view);
            if (i < phoneInfos.size() - 1)
                mContent.addView(line, lineParams);
        }
        measureContentHeight();
    }

    private void setPhoneInfoViewValue(PhoneInfo phoneInfo, View view) {
        TextView key = (TextView) view.findViewById(R.id.key);
        TextView value = (TextView) view.findViewById(R.id.value);
        key.setText(phoneInfo.getKey());
        value.setText(phoneInfo.getValuse());
    }

    public void show() {
        showOrHideContent();
    }

    public void hide() {
        showOrHideContent();
    }
}
