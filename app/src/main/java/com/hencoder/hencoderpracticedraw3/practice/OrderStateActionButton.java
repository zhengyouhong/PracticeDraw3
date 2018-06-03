package com.hencoder.hencoderpracticedraw3.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw3.R;

public class OrderStateActionButton extends View {
    private String word = "拼";
    private Bitmap bgBm;
    private Paint paint;

    private int extra;
    private int radius;
    private int maxPaddings;
    private Matrix matrix;

    public OrderStateActionButton(Context context) {
        this(context, null);
    }

    public OrderStateActionButton(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public OrderStateActionButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        bgBm = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher_round);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(dp2px(60));

        matrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        int paddings = extra % maxPaddings;
        scaleBitmap(paddings);

        float ratio = getScaleSize(paddings);
        float[] translateXY = getTranslateValue(paddings);
        canvas.translate(-translateXY[0], -translateXY[1]);

        paint.setAlpha((int) ratio);

        canvas.drawBitmap(bgBm, matrix, paint);
        canvas.restore();

//        if (extra >= maxPaddings / 2) {
////            canvas.save();
//            int paddings2 = paddings > maxPaddings / 2 ? (paddings - maxPaddings / 2 ) : (paddings + maxPaddings / 2);
//            scaleBitmap(paddings2);
//            ratio = getScaleSize(paddings2);
////            float[] translateValue = getTranslateValue(paddings);
////            canvas.translate(-translateValue[0], -translateValue[1]);
//            paint.setAlpha((int) ratio);
//            canvas.drawBitmap(bgBm, matrix, paint);
////            canvas.restore();
//        }


//        paint.setAlpha(255);
//        paint.setColor(Color.RED);
//        canvas.drawCircle(getWidth() /2 , getHeight() / 2, radius, paint);

        paint.setColor(Color.RED);
        Rect rectWord = new Rect();
        paint.getTextBounds(word, 0, word.length(), rectWord);
        int x = getWidth() / 2 - rectWord.width() / 2;
        int y = getHeight() / 2 + rectWord.height() / 2;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseLineY = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(word, 0, word.length(), x, baseLineY, paint);

        extra += 1;
        postInvalidateDelayed(0);


    }

    private float getScaleSize(int paddings) {
        return (1 - paddings * 1.0f / (getWidth() / 2 - radius)) * 255;
    }

    private void scaleBitmap(int paddings) {
        matrix.reset();
        int newWidth = radius + paddings;
        int newHeight = radius + paddings;

        matrix.postScale(newWidth * 1.0f / (bgBm.getWidth() * 0.5f), newHeight * 1.0f / (bgBm.getHeight() * 0.5f));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        radius = (int) (getWidth() * 0.5f * 0.5f);
        maxPaddings = (int) (getWidth() * 0.5f - radius);
    }

    private float[] getTranslateValue(float paddings){
        float dX =  getWidth() / 2 - (paddings + radius) ;
        return new float[]{dX, dX};
    }


    private int dp2px(float dpValue) {
        return (int) (getResources().getDisplayMetrics().density * dpValue + 0.5f);
    }
}
