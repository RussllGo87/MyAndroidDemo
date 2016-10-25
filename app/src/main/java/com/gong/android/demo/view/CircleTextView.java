package com.gong.android.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.gong.android.demo.R;

/**
 * Created by gongguopei@che-mi.net on 2016/10/23.
 */
public class CircleTextView extends View {

    private final int DEFAULT_STOKE_COLOR = Color.BLACK;
    private final int DEFAULT_TITLE_TEXT_SIZE = 22;
    private final int DEFAULT_VALUE_TEXT_SIZE = 36;
    private final int DEFAULT_UNIT_TEXT_SIZE = 16;


    // 矩形
    RectF rectF = new RectF();
//    Paint mRecPaint;

    // 标题
    Paint mTitlePaint;
    private int mTitleColor;
    private float mTitleTextSize;
    private String mTitle;

    // 圆弧
    Paint mArcPaint;
    private float mArcStokeWidth;
    private int mArcStockStartColor;
    private int mArcStockEndColor;
    Shader gradient;

    // 数值
    private int progress;
    Paint mValuePaint;
    Rect bounds = new Rect();
    private int mValueColor;
    private float mValueTextSize;

    Paint mLinePaint;


    // 单位
    Paint mUnitPaint;
    private int mUnitColor;
    private float mUnitTextSize;
    private String mUnit;

    int width;
    int height;


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
            mArcStockStartColor = a.getColor(R.styleable.CircleTextView_arc_stockStartColor, DEFAULT_STOKE_COLOR);
            mArcStockEndColor = a.getColor(R.styleable.CircleTextView_arc_stockEndColor, DEFAULT_STOKE_COLOR);

            mTitle = a.getString(R.styleable.CircleTextView_arc_title);
            mTitleColor = a.getColor(R.styleable.CircleTextView_arc_title_textColor, DEFAULT_STOKE_COLOR);
            mTitleTextSize = a.getDimensionPixelSize(R.styleable.CircleTextView_arc_title_textSize, DEFAULT_TITLE_TEXT_SIZE);

            mValueColor = a.getColor(R.styleable.CircleTextView_arc_value_textColor, DEFAULT_STOKE_COLOR);
            mValueTextSize = a.getDimensionPixelSize(R.styleable.CircleTextView_arc_value_textSize, DEFAULT_VALUE_TEXT_SIZE);

            mUnit = a.getString(R.styleable.CircleTextView_arc_unit);
            mUnitColor = a.getColor(R.styleable.CircleTextView_arc_unit_textColor, DEFAULT_STOKE_COLOR);
            mUnitTextSize = a.getDimensionPixelSize(R.styleable.CircleTextView_arc_unit_textSize, DEFAULT_UNIT_TEXT_SIZE);
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
            mArcStockStartColor = a.getColor(R.styleable.CircleTextView_arc_stockStartColor, DEFAULT_STOKE_COLOR);
            mArcStockEndColor = a.getColor(R.styleable.CircleTextView_arc_stockEndColor, DEFAULT_STOKE_COLOR);

            mTitle = a.getString(R.styleable.CircleTextView_arc_title);
            mTitleColor = a.getColor(R.styleable.CircleTextView_arc_title_textColor, DEFAULT_STOKE_COLOR);
            mTitleTextSize = a.getDimensionPixelSize(R.styleable.CircleTextView_arc_title_textSize, DEFAULT_TITLE_TEXT_SIZE);

            mValueColor = a.getColor(R.styleable.CircleTextView_arc_value_textColor, DEFAULT_STOKE_COLOR);
            mValueTextSize = a.getDimensionPixelSize(R.styleable.CircleTextView_arc_value_textSize, DEFAULT_VALUE_TEXT_SIZE);

            mUnit = a.getString(R.styleable.CircleTextView_arc_unit);
            mUnitColor = a.getColor(R.styleable.CircleTextView_arc_unit_textColor, DEFAULT_STOKE_COLOR);
            mUnitTextSize = a.getDimensionPixelSize(R.styleable.CircleTextView_arc_unit_textSize, DEFAULT_UNIT_TEXT_SIZE);
        } finally {
            a.recycle();
        }
        init();
    }

    private void init() {

//        mRecPaint = new Paint();
//        mRecPaint.setColor(Color.BLACK);
//        mRecPaint.setAntiAlias(true);
//        mRecPaint.setStrokeWidth(mArcStokeWidth);
//        mRecPaint.setStyle(Paint.Style.STROKE);
//        mRecPaint.setStrokeCap(Paint.Cap.ROUND);

        mArcPaint = new Paint();
//        mArcPaint.setColor(mArcStockColor);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(mArcStokeWidth);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);

        mTitlePaint = new TextPaint();
        mTitlePaint.setColor(mTitleColor);
        mTitlePaint.setTextSize(mTitleTextSize);
        mTitlePaint.setAntiAlias(true);

        mValuePaint = new TextPaint();
        mValuePaint.setColor(mValueColor);
        mValuePaint.setTextSize(mValueTextSize);
        mValuePaint.setAntiAlias(true);

        mUnitPaint = new TextPaint();
        mUnitPaint.setColor(mUnitColor);
        mUnitPaint.setTextSize(mUnitTextSize);
        mUnitPaint.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setColor(mArcStockEndColor);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStrokeWidth(12.0f);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        rectF.set(mArcStokeWidth / 2f, mArcStokeWidth / 2f,
                width - mArcStokeWidth / 2f, height - mArcStokeWidth / 2f);
        if(gradient == null) {
            gradient = new SweepGradient(width / 2.0f + mArcStokeWidth / 2f,
                    height / 2.0f +  mArcStokeWidth / 2f, mArcStockStartColor, mArcStockEndColor);
            float degree = 135.0f;
            Matrix gradientMatrix = new Matrix();
            gradientMatrix.preRotate(degree, width / 2.0f + mArcStokeWidth / 2f, height / 2.0f +  mArcStokeWidth / 2f);
            gradient.setLocalMatrix(gradientMatrix);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画矩形
//        canvas.drawRect(rectF, mRecPaint);

        // 画圆弧
        float percent = progress / 100.0f;
        mArcPaint.setShader(gradient);
        canvas.drawArc(rectF, 135.0f, percent * 270.0f, false, mArcPaint);

        // 画数值
        mValuePaint.getTextBounds(String.valueOf(progress), 0, String.valueOf(progress).length(), bounds);
        int w = bounds.width();
        int h = bounds.height();
        canvas.drawText(String.valueOf(progress),
                width / 2.0f - w / 2.0f,
                height / 2.0f + h / 2.0f,
                mValuePaint);

        // 画标题
        if(!TextUtils.isEmpty(mTitle)) {
            mTitlePaint.getTextBounds(mTitle, 0, mTitle.length(), bounds);
            w = bounds.width();
            h = bounds.height();
            canvas.drawText(mTitle,
                    width / 2.0f - w / 2.0f,
                    height / 4.0f + h / 2.0f,
                    mTitlePaint);
        }

        // 画单位
        if(!TextUtils.isEmpty(mUnit)) {
            mUnitPaint.getTextBounds(mUnit, 0, mUnit.length(), bounds);
            w = bounds.width();
            h = bounds.height();
            canvas.drawText(mUnit,
                    width / 2.0f - w / 2.0f,
                    3 * height / 4.0f + h / 2.0f,
                    mUnitPaint);
        }

        // 画下标图
        mValuePaint.getTextBounds(String.valueOf("1"), 0, String.valueOf("1").length(), bounds);
        w = bounds.width();
        h = bounds.height();
        canvas.drawLine(
                width / 2.0f + mArcStokeWidth / 2f - w * 2.0f,
                5 * height / 8.0f + h / 4.0f ,
                width / 2.0f + mArcStokeWidth / 2f + w * 2.0f,
                5 * height / 8.0f + h / 4.0f,
                mLinePaint);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
