package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class SunView extends View {

    private static Paint paint;
    private int screenW, screenH;
    private float X, Y;
    private Path path, path1;
    private double count;

    public SunView(Context context) {
        super(context);
        init();
    }

    public SunView(Context context, AttributeSet attrs) {
        super(context, attrs);

        String num[] = attrs.getAttributeValue(0).split(".dip");

        X = screenW/2;
        Y = (screenH/2);

        init();
    }

    public SunView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        X = screenW/2;
        Y = (screenH/2);

        init();
    }
    private void init() {

        count = 0;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);

        path= new Path();


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

        // initializing paths
        path = new Path();
        path1 = new Path();

        //incrementing counter for rotation
        count = count+0.1;

        //comparison to check 360 degrees rotation
        int retval = Double.compare(count, 360.00);

        if(retval > 0) {
            //resetting counter on completion of a rotation
            count = 0;
        }

        // drawing center circle
        path.addCircle(X,Y,30, Path.Direction.CW);

        for(int i=0;i<360;i+=45){
            path1.moveTo(X,Y);
            float X1 = (float)(50 * Math.cos(Math.toRadians(i+count)) + X);//arm pointX at radius 50 with incrementing angle from center of sun
            float Y1 = (float)(50 * Math.sin(Math.toRadians(i+count))+Y);//arm pointY at radius 50 with incrementing angle from center of sun
            float X2 = (float)(65 * Math.cos(Math.toRadians(i+count))+X);//arm pointX at radius 65 with incrementing angle from center of sun
            float Y2 = (float)(65 * Math.sin(Math.toRadians(i+count))+Y);//arm pointY at radius 65 with incrementing angle from center of sun
            path1.moveTo(X1,Y1);// drawing arms of sun
            path1.lineTo(X2,Y2);

        }

        // drawing paths on canvas
        canvas.drawPath(path, paint);
        canvas.drawPath(path1, paint);
        invalidate();


    }
}
