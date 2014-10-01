package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * This view draws cloud with rain.
 */
public class CloudRainView extends View {

    private static Paint paintCloud, paintRain;
    private int screenW, screenH;
    private float X, Y;
    private Path pathCloud, pathRain;
    int x1=0, y1=0, x2=0, y2=0, x3=0, y3=0;
    float m = 0;
    boolean drop1 = true, drop2 = false, drop3 = false;
    private double count;

    public CloudRainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        count = 0;

        paintCloud = new Paint();
        paintRain = new Paint();

        //Paint for drawing cloud
        paintCloud.setColor(Color.BLACK);
        paintCloud.setStrokeWidth(10);
        paintCloud.setAntiAlias(true);
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setShadowLayer(0, 0, 0, Color.BLACK);

        //Paint for drawing rain drops
        paintRain.setColor(Color.BLACK);
        paintRain.setAntiAlias(true);
        paintRain.setStrokeCap(Paint.Cap.ROUND);
        paintRain.setStyle(Paint.Style.FILL);

        pathCloud = new Path();
    }

    // Initial declaration of the coordinates.
    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;

        X = screenW/2;
        Y = (screenH/2);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paintCloud.setStrokeWidth((float)(0.02083*screenW));
        paintRain.setStrokeWidth((float)(0.015*screenW));

        pathCloud = new Path(); // pathCloud for cloud
        pathRain = new Path(); // pathCloud for drop

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

        pathCloud.moveTo(X1,Y1);

        PointF P1c1 = calculateTriangle(X1, Y1, P1X, P1Y, true, count);
        PointF P1c2 = calculateTriangle(X1, Y1, P1X, P1Y, false, count);
        PointF P2c1 = calculateTriangle(P1X, P1Y, P2X, P2Y, true, count);
        PointF P2c2 = calculateTriangle(P1X, P1Y, P2X, P2Y, false, count);
        PointF P3c1 = calculateTriangle(P2X, P2Y, P3X, P3Y, true, count);
        PointF P3c2 = calculateTriangle(P2X, P2Y, P3X, P3Y, false, count);
        PointF P4c1 = calculateTriangle(P3X, P3Y, P4X, P4Y, true, count);
        PointF P4c2 = calculateTriangle(P3X, P3Y, P4X, P4Y, false, count);
        PointF P5c1 = calculateTriangle(P4X, P4Y, X1, Y1, true, count);
        PointF P5c2 = calculateTriangle(P4X, P4Y, X1,Y1, false, count);


        if(drop1) { // Drop 1 of the rain

            pathRain = new Path();

            if(x1==0) {
                x1 = (int) P1c2.x;
            }
            if(y1==0) {
                float value = (int) P1c2.y-((P1c1.y+P1Y)/2);
                y1 = (int) (P1c2.y-value/2);
            }

            // Shape for rain drop
            pathRain.moveTo(x1, y1);
            pathRain.addArc(new RectF(x1 - 5, (y1 - 5) + m, x1 + 5, y1 + 5 + m), 180, -180);
            pathRain.lineTo(x1, (y1 - 10) + m);
            pathRain.close();

            // First fill the shape with paint
            paintRain.setStyle(Paint.Style.FILL);
            canvas.drawPath(pathRain, paintRain);

            // Then, draw the same pathCloud with paint stroke
            paintRain.setStyle(Paint.Style.STROKE);
            canvas.drawPath(pathRain, paintRain);

            m = m+2.5f;

            if(m==100) {
                m=0;
                pathRain.reset();
                pathRain.moveTo(0, 0);
                drop2 = true;
                drop1 = false;
                drop3 = false;
            }
        }

        if(drop2) { // Drop 2 of the rain
            pathRain = new Path();

            if(x2==0) {
                x2 = (int) P2c2.x;
            }
            if(y2==0) {
                float value = (int) P2c2.y-((P2c1.y+P2Y)/2);
                y2 = (int) (P2c2.y-value/2);
            }

            pathRain.moveTo(x2, y2);
            pathRain.addArc(new RectF(x2 - 5, (y2 - 5) + m, x2 + 5, y2 + 5 + m), 180, -180);
            pathRain.lineTo(x2, (y2 - 10) + m);
            pathRain.close();

            // First fill the shape with paint
            paintRain.setStyle(Paint.Style.FILL);
            canvas.drawPath(pathRain, paintRain);

            // Then, draw the same pathCloud with paint stroke
            paintRain.setStyle(Paint.Style.STROKE);
            canvas.drawPath(pathRain, paintRain);

            m = m+2.5f;

            if(m==100) {
                m=0;
                pathRain.reset();
                pathRain.moveTo(0, 0);
                drop1 = false;
                drop2 = false;
                drop3 = true;
            }
        }

        if(drop3) { // Drop 3 of the rain
            pathRain = new Path();

            if(x3==0) {
                x3 = (x1+x2)/2;
            }
            if(y3==0) {
                y3 = (y1+y2)/2;
            }

            pathRain.moveTo(x3, y3);
            pathRain.addArc(new RectF(x3 - 5, (y3 - 5) + m, x3 + 5, y3 + 5 + m), 180, -180);
            pathRain.lineTo(x3, (y3 - 10) + m);
            pathRain.close();

            // First fill the shape with paint
            paintRain.setStyle(Paint.Style.FILL);
            canvas.drawPath(pathRain, paintRain);

            // Then, draw the same pathCloud with paint stroke
            paintRain.setStyle(Paint.Style.STROKE);
            canvas.drawPath(pathRain, paintRain);

            m = m+2.5f;

            if(m==100) {
                m=0;
                pathRain.reset();
                pathRain.moveTo(0, 0);
                drop1 = true;
                drop2 = false;
                drop3 = false;
            }
        }




        pathCloud.moveTo(X1,Y1);
        pathCloud.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, P1X, P1Y);
        pathCloud.cubicTo(P2c1.x, P2c1.y, P2c2.x, P2c2.y, P2X, P2Y);
        pathCloud.cubicTo(P3c1.x, P3c1.y, P3c2.x, P3c2.y, P3X, P3Y);
        pathCloud.cubicTo(P4c1.x, P4c1.y, P4c2.x, P4c2.y, P4X, P4Y);
        pathCloud.cubicTo(P5c1.x, P5c1.y, P5c2.x, P5c2.y, X1, Y1);

        //fill cloud with white color
        paintCloud.setColor(Color.WHITE);
        paintCloud.setStyle(Paint.Style.FILL);
        canvas.drawPath(pathCloud, paintCloud);

        //draw stroke with back color
        paintCloud.setColor(Color.BLACK);
        paintCloud.setStyle(Paint.Style.STROKE);
        canvas.drawPath(pathCloud, paintCloud);

        invalidate();


    }

    private PointF calculateTriangle(float x1, float y1, float x2,
                                     float y2, boolean left, double count) {
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
