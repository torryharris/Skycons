package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

public class CloudView extends View {

    private Paint paint;
    private int screenW, screenH;
    private float X, Y;
    private Path path;
    private double count;

    public CloudView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CloudView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    // Initial declaration of the coordinates.
    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w; //getting Screen Width
        screenH = h; // getting Screen Height

        // center point  of Screen
        X = screenW/2;
        Y = (screenH/2);

    }

    private void init() {

        count = 0;

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth((screenW/25));
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);

        path = new Path();

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStrokeWidth((float)(0.02083*screenW));
        path = new Path();

        //incrementing counter for rotation
        count = count+0.5;

        //comparison to check 360 degrees rotation
        int retval = Double.compare(count, 360.00);

        if(retval == 0) {
            //resetting counter on completion of a rotation
            count = 0;
        }

        //different radius values for the cloud coordinates
        int r1 = (int)(0.1875 * screenW);
        int r2 = (int)(0.1041667 * screenW);
        double offset = 0.00023125 * screenW;

        // cloud coordinates from the center of the screen
        float X1 = (float)(r1 * Math.cos(Math.toRadians(0+(0.222*count))) + X); //x value of coordinate 1 at radius r1 from center of Screen and angle incremented with counter
        float Y1 = ((float)(r2 * Math.sin(Math.toRadians(0+(0.222*count))) + Y));//y value of coordinate 1 at radius r2 from center of Screen and angle incremented with counter
        float P1X = (float)(r1 * Math.cos(Math.toRadians(80+(0.111*count))) + X);//x value of coordinate 2 at radius r1 from center of Screen and angle incremented with counter
        float P1Y = ((float)(r2 * Math.sin(Math.toRadians(80+(0.111*count))) + Y));//y value of coordinate 2 at radius r2 from center of Screen and angle incremented with counter
        float P2X = (float)(r1 * Math.cos(Math.toRadians(120+(0.222*count))) + X);//x value of coordinate 3 at radius r1 from center of Screen and angle incremented with counter
        float P2Y = ((float)((r2+(offset*count)) * Math.sin(Math.toRadians(120+(0.222*count))) + Y));//y value of coordinate 3 at varying radius from center of Screen and angle incremented with counter
        float P3X = (float)(r1 * Math.cos(Math.toRadians(200+(0.222*count))) + X);//x value of coordinate 4 at radius r1 from center of Screen and angle incremented with counter
        float P3Y = ((float)(r1 * Math.sin(Math.toRadians(200+(0.222*count))) + Y));//y value of coordinate 4 at radius r1 from center of Screen and angle incremented with counter
        float P4X =(float)(r1 * Math.cos(Math.toRadians(280+(0.222*count))) + X);//x value of coordinate 5 at radius r1 from center of Screen and angle incremented with counter
        float P4Y = ((float)(r1 * Math.sin(Math.toRadians(280+(0.222*count))) + Y));//y value of coordinate 5 at radius r1 from center of Screen and angle incremented with counter


        path.moveTo(X1,Y1);

        // getting points in between coordinates for drawing arc between them
        PointF P1c1 = calculateTriangle(X1, Y1, P1X, P1Y, true);
        PointF P1c2 = calculateTriangle(X1, Y1, P1X, P1Y, false);
        PointF P2c1 = calculateTriangle(P1X, P1Y, P2X, P2Y, true);
        PointF P2c2 = calculateTriangle(P1X, P1Y, P2X, P2Y, false);
        PointF P3c1 = calculateTriangle(P2X, P2Y, P3X, P3Y, true);
        PointF P3c2 = calculateTriangle(P2X, P2Y, P3X, P3Y, false);
        PointF P4c1 = calculateTriangle(P3X, P3Y, P4X, P4Y, true);
        PointF P4c2 = calculateTriangle(P3X, P3Y, P4X, P4Y, false);
        PointF P5c1 = calculateTriangle(P4X, P4Y, X1, Y1, true);
        PointF P5c2 = calculateTriangle(P4X, P4Y, X1,Y1, false);

        // drawing arcs between coordinates
        path.moveTo(X1,Y1);
        path.cubicTo(P1c1.x,P1c1.y,P1c2.x,P1c2.y,P1X,P1Y);
        path.cubicTo(P2c1.x,P2c1.y,P2c2.x,P2c2.y,P2X,P2Y);
        path.cubicTo(P3c1.x,P3c1.y,P3c2.x,P3c2.y,P3X,P3Y);
        path.cubicTo(P4c1.x,P4c1.y,P4c2.x,P4c2.y,P4X,P4Y);
        path.cubicTo(P5c1.x,P5c1.y,P5c2.x,P5c2.y,X1,Y1);

        canvas.drawPath(path, paint);

        invalidate();

    }

    private PointF calculateTriangle(float x1, float y1, float x2,
                                     float y2, boolean left) {

        PointF result = new PointF(0,0);
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle;
        float sideDist = (float)0.5 * (float) Math.sqrt(dx * dx + dy * dy);

        if (left) {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI /3f));
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);

        } else {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI /1.5f));
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }

        return result;

    }

}
