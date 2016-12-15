package com.technopark.dreamteam.moneybox;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sp41mer on 14.12.16.
 */

public class MeshokView extends View {

    private final Paint paint = new Paint();

    Activity host = (Activity) getContext();

    Drawable d = ContextCompat.getDrawable(host, R.drawable.ic_money_bag1);



    public MeshokView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MeshokView(Context context) {
        super(context);
    }

    public MeshokView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (wMode == MeasureSpec.EXACTLY) {
            if (hMode == MeasureSpec.EXACTLY) {
                setMeasuredDimension(width, height);
            } else if (hMode == MeasureSpec.AT_MOST) {
                setMeasuredDimension(width, Math.min(width, height));
            } else {
                setMeasuredDimension(width, width);
            }
        } else if (wMode == MeasureSpec.AT_MOST) {
            if (hMode == MeasureSpec.EXACTLY) {
                setMeasuredDimension(Math.min(width, height), height);
            } else if (hMode == MeasureSpec.AT_MOST) {
                int minSize = Math.min(width, height);
                setMeasuredDimension(minSize, minSize);
            } else {
                setMeasuredDimension(width, width);
            }
        } else {
            if (hMode == MeasureSpec.UNSPECIFIED) {
                setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
            } else {
                setMeasuredDimension(height, height);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int width = getWidth();
        final int height = getHeight();

        float sum = (float) DBHelper.calculateSum(host);
        long goal = DBHelper.readGoal(host);

        int newHeight = (int)(height * sum / goal);

        paint.setARGB(255, 255, 235, 59);
        canvas.drawRect(0, height-newHeight, width, height, paint);

        d.setBounds(0, 0, width, height);

        d.draw(canvas);
    }
}
