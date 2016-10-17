package com.gong.android.demo.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by gongguopei@che-mi.net on 2016/9/23.
 */

public class HiveDrawable extends Drawable {

    Rect mRect = new Rect(); // 用于存储边界信息的Rect
    Paint mPaint;
    Path mPath ;
    BitmapShader mShader;
    Bitmap mBitmap ;

    public HiveDrawable() {
        this(null);
    }

    public HiveDrawable(Bitmap bitmap) {
        init();
        setBitmap(bitmap);
    }

    private void init() {
        initPaint();
        initPath();
    }

    /**
     * 初始化画笔工具Paint
     */
    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint() ;
        }

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
    }

    /**
     * 初始化路线Path
     */
    private void initPath() {
        if(mPath == null) {
            mPath = new Path();
        }

        float l = (float) (mRect.width() / 2);
        float h = (float) (Math.sqrt(3)*l);
        float top = (mRect.height() - h) / 2  ;
        mPath.reset();
        mPath.moveTo(l/2,top);
        mPath.lineTo(0,h/2+top);
        mPath.lineTo(l/2,h+top);
        mPath.lineTo((float) (l*1.5),h+top);
        mPath.lineTo(2*l,h/2+top);
        mPath.lineTo((float) (l*1.5),top);
        mPath.lineTo(l/2,top);
        mPath.close();
    }

    private void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        if(bitmap == null) {
            mShader = null;
        } else {
            mShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(mShader);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        if(mPaint != null) {
            mPaint.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if(mPaint != null) {
            mPaint.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        mRect.set(left, top, right, bottom);

        initPath();
    }
}
