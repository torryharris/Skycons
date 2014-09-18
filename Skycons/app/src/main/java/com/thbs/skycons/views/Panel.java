package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.thbs.skycons.R;

/**
 * Created by administrator on 17/09/14.
 */
public class Panel extends View {

    private Bitmap  mBitmap;
    private Canvas  mCanvas;
    private Path mPath;
    private Paint   mPaint;

    Bitmap bitmap;
    Canvas pcanvas;

    int x = 0;
    int y =0;
    int r =0;


    public Panel(Context context) {
        super(context);

        init();
    }

    public Panel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Panel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }


    public void init() {

        setFocusable(true);
        setBackgroundColor(Color.TRANSPARENT);

        // setting paint
        mPaint = new Paint();
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mPaint.setAntiAlias(true);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        // converting image bitmap into mutable bitmap
        bitmap =  bm.createBitmap(295, 260, Config.ARGB_8888);
        pcanvas = new Canvas();
        pcanvas.setBitmap(bitmap);    // drawXY will result on that Bitmap
        pcanvas.drawBitmap(bm, 0, 0, null);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        pcanvas.drawLine(x, y, x+50, y, mPaint);

        canvas.drawBitmap(bitmap, 0, 0,null);

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // set parameter to draw circle on touch event
        x = (int) event.getX();
        y = (int) event.getY();

        r =20;
        // At last invalidate canvas
        invalidate();
        return true;
    }
}
