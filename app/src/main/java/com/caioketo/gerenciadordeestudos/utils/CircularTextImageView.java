package com.caioketo.gerenciadordeestudos.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import com.pkmmte.view.CircularImageView;

/**
 * Created by Caio on 24/10/2014.
 */
public class CircularTextImageView extends CircularImageView {
    private Paint mPaint;
    private String mText = "";

    public CircularTextImageView(Context context) {
        this(context, null);
    }

    public CircularTextImageView(Context context, AttributeSet attrs) {
        this(context, attrs, com.pkmmte.view.R.styleable.CustomCircularImageViewTheme_circularImageViewStyle);
    }

    public CircularTextImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initThis(context);
    }

    private void initThis(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(11);
    }

    public void setText(String text) {
        mText = text;
        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int imgWidth = getMeasuredWidth();
        int imgHeight = getMeasuredHeight();
        float txtWidth = mPaint.measureText(mText);

        int x = (int)(imgWidth/2 - txtWidth/2);
        int y = imgHeight/2 - 6; // 6 is half of the text size
        canvas.drawText(mText, x, y, mPaint);
        Log.d("CIRCTEXT", mText);
    }
}
