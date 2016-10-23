package com.gong.android.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.gong.android.demo.R;

/**
 * Created by gongguopei@che-mi.net on 2016/10/23.
 */
public class CircleTextView extends View {

    private final int DEFAULT_STOKE_COLOR = Color.BLACK;

    RectF rectF = new RectF();

    Paint mArcPaint;
    private float mArcStokeWidth;
    private int mArcStockColor;

    private int progress;


    public CircleTextView(Context context) {
        this(context, null);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleTextView,
                defStyleAttr, 0);

        try {
            mArcStokeWidth = a.getDimension(R.styleable.CircleTextView_arc_stokeWidth, 0.0F);
            mArcStockColor = a.getColor(R.styleable.CircleTextView_arc_stockColor, DEFAULT_STOKE_COLOR);
        } finally {
            a.recycle();
        }
        init();
    }

    @TargetApi(21)
    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleTextView,
                defStyleAttr, defStyleRes);

        try {
            mArcStokeWidth = a.getDimension(R.styleable.CircleTextView_arc_stokeWidth, 0.0F);
            mArcStockColor = a.getColor(R.styleable.CircleTextView_arc_stockColor, DEFAULT_STOKE_COLOR);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setColor(mArcStockColor);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(mArcStokeWidth);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        rectF.set(mArcStokeWidth / 2f, mArcStokeWidth / 2f,
                width - mArcStokeWidth / 2f, MeasureSpec.getSize(heightMeasureSpec) - mArcStokeWidth / 2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float percent = progress / 100.0f;
        canvas.drawArc(rectF, 150.0f, percent * 240.0f, false, mArcPaint);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
