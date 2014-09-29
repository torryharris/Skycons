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
        System.out.println(num[0]);

        // screenW = Integer.valueOf(attrs.getAttributeValue(0).split("dp""));
        //screenH = Integer.valueOf(attrs.getAttributeValue(1));

        X = screenW/2;
        Y = (screenH/2);

//        path.moveTo(X, Y);

        init();
    }

    public SunView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //screenW = Integer.valueOf(attrs.getAttributeValue(0));
        //screenH = Integer.valueOf(attrs.getAttributeValue(1));

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

        screenW = w;
        screenH = h;
        X = screenW/2;
        Y = (screenH/2);

        path.moveTo(X, Y);

    }



    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path = new Path();
        path1 = new Path();
        count = count+0.1;
        int retval = Double.compare(count, 360.00);

        if(retval > 0) {

            count = 0;
        }

        path.addCircle(X,Y,30, Path.Direction.CW);

        for(int i=0;i<360;i+=45){
            path1.moveTo(X,Y);
            float X1 = (float)(50 * Math.cos(Math.toRadians(i+count)) + X);
            float Y1 = (float)(50 * Math.sin(Math.toRadians(i+count))+Y);
            float X2 = (float)(65 * Math.cos(Math.toRadians(i+count))+X);
            float Y2 = (float)(65 * Math.sin(Math.toRadians(i+count))+Y);
            path1.moveTo(X1,Y1);
            path1.lineTo(X2,Y2);

        }

        canvas.drawPath(path, paint);
        canvas.drawPath(path1, paint);
        invalidate();


    }
}
