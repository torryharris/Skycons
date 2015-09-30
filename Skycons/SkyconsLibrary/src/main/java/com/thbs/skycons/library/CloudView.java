package com.thbs.skycons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CloudView extends SkyconView {

    private Paint paint;
    private int screenW, screenH;
    private float X, Y;
    private double count;
    Cloud c;

    public CloudView(Context context) {
        super(context);
        init();
    }

    public CloudView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w; //getting Screen Width
        screenH = h; // getting Screen Height

        // center point  of Screen
        X = screenW/2;
        Y = (screenH/2);

        c =  new Cloud();

    }

    private void init() {

        // initialize default values
        count = 0;
        isAnimated = true;

        paint = new Paint();
        paint.setColor(strokeColor);
        paint.setStrokeWidth((screenW/25));
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // set canvas background color
        canvas.drawColor(bgColor);

        // set stroke width
        paint.setStrokeWidth((float)(0.02083*screenW));

        //incrementing counter for rotation

        count = count+0.5;

        //comparison to check 360 degrees rotation
        int retval = Double.compare(count, 360.00);

        if(retval == 0) {

            if(!isAnimated) {
                // mark completion of animation
                isAnimated = true;
                //resetting counter on completion of a rotation
                count = 0;
            } else {
                //resetting counter on completion of a rotation
                count = 0;
            }
        }

        // draw cloud
        canvas.drawPath(c.getCloud(X,Y,screenW,count), paint);

        if(!isStatic || !isAnimated) {
            // invalidate if not static or not animating
            invalidate();


        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // nothing to do
                return true;
            case MotionEvent.ACTION_MOVE:
                // nothing to do
                break ;
            case MotionEvent.ACTION_UP:
                // start animation if it is not animating
                if(isStatic && isAnimated) {
                    isAnimated = false;
                }

                break;
            default:
                return false;
        }

        // Schedules a repaint.
        if(!isAnimated) {
            invalidate();
        }
        return true;
    }

}
