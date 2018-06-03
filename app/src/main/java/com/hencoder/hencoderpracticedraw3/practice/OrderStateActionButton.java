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
    private Bitmap topBm;
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;

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
        topBm = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(dp2px(60));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddings = extra % maxPaddings;
        mBitmap1 = scaleBitmap(paddings);

        int x = getWidth() / 2 - mBitmap1.getWidth() / 2;
        int y = getHeight() / 2 - mBitmap1.getHeight() / 2;

        float ratio = getScaleSize(paddings);

        paint.setAlpha((int) ratio);
        canvas.drawBitmap(mBitmap1, x, y, paint);

        if (extra >= maxPaddings / 2) {
            int paddings2 = paddings > maxPaddings / 2 ? (paddings - maxPaddings / 2 ) : (paddings + maxPaddings / 2);
            mBitmap2 = scaleBitmap(paddings2);
            ratio = getScaleSize(paddings2);
            paint.setAlpha((int) ratio);
            x = getWidth() / 2 - mBitmap2.getWidth() / 2;
            y = getHeight() / 2 - mBitmap2.getHeight() / 2;
            canvas.drawBitmap(mBitmap2, x, y, paint);
        }


        paint.setAlpha(255);
        paint.setColor(Color.RED);
        canvas.drawCircle(getWidth() /2 , getHeight() / 2, radius, paint);

        paint.setColor(Color.WHITE);
        Rect rectWord = new Rect();
        paint.getTextBounds(word, 0, word.length(), rectWord);
        x = getWidth() / 2 - rectWord.width() / 2;
        y = getHeight() / 2 + rectWord.height() / 2;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseLineY = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(word, 0, word.length(), x, baseLineY, paint);

        extra += 1;
        postInvalidateDelayed(0);


    }

    private float getScaleSize(int paddings) {
        return (1 - paddings * 1.0f / (getWidth() / 2 - radius)) * 255;
    }

    private Bitmap scaleBitmap(int paddings) {
        int newWidth = radius + paddings;
        int newHeight = radius + paddings;
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth * 1.0f / (bgBm.getWidth() * 0.5f), newHeight * 1.0f / (bgBm.getHeight() * 0.5f));
        return Bitmap.createBitmap(bgBm, 0, 0, bgBm.getWidth(), bgBm.getHeight(), matrix, true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        radius = (int) (getWidth() * 0.5f * 0.5f);
        maxPaddings = (int) (getWidth() * 0.5f - radius);
    }

    private int dp2px(float dpValue) {
        return (int) (getResources().getDisplayMetrics().density * dpValue + 0.5f);
    }
}
