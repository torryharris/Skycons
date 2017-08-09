package com.thbs.skycons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SunView extends SkyconView {

    private static Paint paint;
    private int screenW, screenH;
    private float X, Y;
    private Path path, path1;
    private double count;

    public SunView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public SunView(Context context,boolean isStatic,boolean isAnimated, int strokeColor , int backgroundColor) {
        super(context);

        this.isStatic = isStatic;
        this.isAnimated = isAnimated;
        this.strokeColor = strokeColor;
        this.bgColor = backgroundColor;

        init();
    }

    private void init() {

        // initialize default values
        count = 0;
        paint = new Paint();

        paint.setColor(strokeColor);

        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(0, 0, 0, strokeColor);

        path= new Path();
        isAnimated = true;


    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w; //getting Screen Width
        screenH = h;// getting Screen Height

        // center point  of Screen
        X = screenW/2;
        Y = (screenH/2);

        path.moveTo(X, Y);

    }



    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // set canvas background color
        canvas.drawColor(bgColor);

        // initializing paths
        path = new Path();
        path1 = new Path();
        // set stroke width
        paint.setStrokeWidth((float) (0.02083 * screenW));

        //incrementing counter for rotation
        count = count+0.1;

        //comparison to check 360 degrees rotation
        int retval = Double.compare(count, 360.00);

        if(retval > 0) {

            if(!isAnimated) {
                // mark completion of animation
                isAnimated = true;
                //resetting counter on completion of a rotation
                count = 0;
            }else {
                //resetting counter on completion of a rotation
                count = 0;
            }
        }

        // drawing center circle
        path.addCircle(X,Y, (int)(0.1042 * screenW), Path.Direction.CW);

        // drawing arms of sun
        for(int i=0;i<360;i+=45){
            path1.moveTo(X,Y);
            float x1 = (float)((int)(0.1458 * screenW) * Math.cos(Math.toRadians(i+count))+X); //arm pointX at radius 50 with incrementing angle from center of sun
            float y1 = (float)((int)(0.1458 * screenW) * Math.sin(Math.toRadians(i+count))+Y);//arm pointY at radius 50 with incrementing angle from center of sun
            float X2 = (float)((int)(0.1875 * screenW) * Math.cos(Math.toRadians(i+count))+X);//arm pointX at radius 65 with incrementing angle from center of sun
            float Y2 = (float)((int)(0.1875 * screenW) * Math.sin(Math.toRadians(i+count))+Y);//arm pointY at radius 65 with incrementing angle from center of sun
            path1.moveTo(x1,y1); // draw arms of sun
            path1.lineTo(X2,Y2);

        }

        // drawing paths on canvas
        canvas.drawPath(path, paint);
        canvas.drawPath(path1, paint);

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
