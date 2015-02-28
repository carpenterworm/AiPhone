package net.onefree.aiphone.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import net.onefree.aiphone.R;

/**
 * Created by maoah on 14-10-19.
 */
public class CircleChart extends View implements android.os.Handler.Callback {

    private Paint paint;
    private RectF viewRectF;

    private Handler handler = new Handler(this);
    private static final int ANIMATION_MESSAGE = 1000;
    private static final int ANIMATION_DURATION = 1000 / 60;

    /**
     * 开始弧度*
     */
    private int startRadian = 130;
    /**
     * 终止弧度数*
     */
    private int endRadian = 280;

    private int height;
    private int width;
    /**
     * 组件实际高度
     */
    private int realityHeight;
    /**
     * 弧度线宽度
     */
    private int strokeWidth = 1;
    /**
     * 内间距
     */
    private int insideMargin = 10;

    /**
     * 图表背景
     */
    private int rateNormalBg;
    /**
     * 图表已使用背景
     */
    private int rateSelectBg;

    /**
     * 图表名字
     */
    private String name = "--";

    /**
     * 已用*
     */
    private float used = 25;
    /**
     * 全部*
     */
    private float total = 100;

    /**
     * 总使用弧度
     */
    private float usedRate = 0;
    /**
     * 当前比率-数字
     */
    private float usedRateCurrentDigit = 0;
    private String usedTotal = "";
    /**
     * 当前已画弧度数
     */
    private int drawCircleRate = 0;

    /**
     * 弧度步数
     */
    private float radianStep = 2;

    /**
     * name 大小
     */
    private float nameSize;

    /**
     * 百分比数字大小
     */
    private float rateSize;

    /**
     * 以用，总共字休大小
     */
    private float rateSecondarySize;

    public CircleChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        TypedArray a = initStyle(context, attrs);
        a.recycle();

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!handler.hasMessages(ANIMATION_MESSAGE))
                    handler.sendEmptyMessageDelayed(ANIMATION_MESSAGE, 400);
            }

        });
    }

    /**
     * 初始化配置信息
     *
     * @param context
     * @param attrs
     * @return
     */
    private TypedArray initStyle(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleChart);
        if (a.hasValue(R.styleable.CircleChart_name)) {
            name = a.getString(R.styleable.CircleChart_name);
        }

//        if (a.hasValue(R.styleable.CircleChart_usedRate)) {
//            usedRate = a.getInt(R.styleable.CircleChart_usedRate, 0);
//
//        }

        if (a.hasValue(R.styleable.CircleChart_usedTotal)) {
            usedTotal = a.getString(R.styleable.CircleChart_usedTotal);
        }


        rateNormalBg = a.getColor(R.styleable.CircleChart_rateNormalBg, getResources().getColor(R.color.circle_chart_bg));
        rateSelectBg = a.getColor(R.styleable.CircleChart_rateSelectBg, getResources().getColor(R.color.app_main_color));
        return a;
    }


    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        paint = new Paint();
        paint.setAntiAlias(true);
        insideMargin = this.getResources().getDimensionPixelSize(R.dimen.circle_chart_rate_total_margin);
        strokeWidth = this.getResources().getDimensionPixelSize(R.dimen.circle_chart_rate_width);
        nameSize = context.getResources().getDimensionPixelSize(R.dimen.circle_chart_name_size);
        rateSize = context.getResources().getDimensionPixelSize(R.dimen.circle_chart_rate_size);
        rateSecondarySize = context.getResources().getDimensionPixelSize(R.dimen.circle_chart_rate__secondary_size);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        realityHeight = height / 2 + (int) (height / 2 * Math.sin(40));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景透明
        canvas.drawColor(Color.TRANSPARENT);
        drawCircle(canvas);
        drawName(canvas);
        drawRate(canvas);
    }


    /**
     * 画圆
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        //裁剪画布
        paint.setColor(getResources().getColor(R.color.app_main_color));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        //加上描边
        paint.setStrokeWidth(strokeWidth);
        //画园
        //canvas.drawCircle(width/2, height/2, width/2-10, paint);
        viewRectF = new RectF(0 + strokeWidth, 0 + strokeWidth, width - strokeWidth, height - strokeWidth);

        paint.setColor(rateNormalBg);
        canvas.drawArc(viewRectF, startRadian, endRadian, false, paint);

        paint.setColor(rateSelectBg);
        canvas.drawArc(viewRectF, startRadian, drawCircleRate, false, paint);

    }

    /**
     * 画字
     *
     * @param canvas
     */
    private void drawName(Canvas canvas) {
        Rect rect = new Rect();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.text_color_b));
        paint.setTextSize(nameSize);
        paint.getTextBounds(name, 0, name.length(), rect);
        int textWidth = rect.width();
        int textHeight = rect.height();
        canvas.drawText(name, width / 2 - textWidth / 2, realityHeight + textHeight / 2, paint);

//        canvas.drawLine(width / 2, 0, width / 2, realityHeight, paint);
//        canvas.drawLine(0, height / 2, width, height / 2, paint);
    }

    /**
     * 画使用比率
     *
     * @param canvas
     */
    private void drawRate(Canvas canvas) {
        Rect rateRect = new Rect();
        Rect totalRect = new Rect();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(getResources().getColor(R.color.app_main_color));

        String usedRateCurrentDigitTemp = String.valueOf((int) (usedRateCurrentDigit));
        paint.setTextSize(rateSize);
        paint.getTextBounds(String.valueOf(usedRateCurrentDigitTemp), 0, String.valueOf(usedRateCurrentDigitTemp).length(), rateRect);
        float rateTextHeight = rateRect.height();
        canvas.drawText(String.valueOf(usedRateCurrentDigitTemp) + "%", width / 2, realityHeight / 2 + rateTextHeight / 2, paint);

        paint.setTextSize(rateSecondarySize);
        paint.getTextBounds(usedTotal, 0, usedTotal.length(), totalRect);
        float totalTextHeight = totalRect.height();
        canvas.drawText(usedTotal, width / 2, realityHeight / 2 + rateTextHeight / 2 + totalTextHeight + insideMargin, paint);

    }


    private void doAnimation() {
//        usedTotal = used + "/" + total;
        long now = SystemClock.uptimeMillis();
        if (drawCircleRate < usedRate) {
            drawCircleRate += radianStep;
            usedRateCurrentDigit += ((used / total) * 100) / (usedRate / radianStep);
            handler.sendEmptyMessageAtTime(ANIMATION_MESSAGE, now + ANIMATION_DURATION);
        }
        invalidate();
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case ANIMATION_MESSAGE:
                doAnimation();
                break;
        }
        return false;
    }

    public void setUsedTotal(float used, float total) {
        this.used = used;
        this.total = total;

        usedRate = endRadian * (used / total);

    }

    public void setUsedTotalText(String str){
        this.usedTotal = str;
    }
}
