package net.onefree.aiphone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by maoah on 14/10/22.
 */
public class RateProgressView extends View {

    private int height;
    private int width;

    public RateProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        // 新建一个矩形
        RectF outerRect = new RectF(0, 0, width, height);

        canvas.drawRoundRect(outerRect, 10, 10, paint);

        outerRect = new RectF(0, 0, width/2, height);
        paint.setColor(Color.BLUE);
        canvas.drawRoundRect(outerRect, 10, 10, paint);

        outerRect = new RectF(0, 0, width/4, height);
        paint.setColor(Color.GREEN);
        canvas.drawRoundRect(outerRect, 10, 10, paint);

        outerRect = new RectF(0, 0, width/8, height);
        paint.setColor(Color.YELLOW);
        canvas.drawRoundRect(outerRect, 10, 10, paint);

    }
}
