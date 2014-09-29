package com.thbs.skycons.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

public class CloudHvRainView extends View {

    private static Paint paint, paint1;
    private int screenW, screenH;
    private float X, Y;
    private Path path, path1, path2, path3;
    int m1=0, m2=0, m3=0, x1=0, y1=0, x2=0, y2=0, x3=0, y3=0;
    int count1 = 0, count2 = 0, count3 = 0, i=0;
    private double count;
    boolean pointsStored = false;
    double radius1, radius2;

    public CloudHvRainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        String num1[] = attrs.getAttributeValue(0).split("\\.");
        String num2[] = attrs.getAttributeValue(1).split("\\.");

        screenW = Integer.valueOf(num1[0]);
        screenH = Integer.valueOf(num2[0]);

        X = screenW/2;
        Y = (screenH/2);

        radius1 = 90;
        radius2 = 50;

        init();
    }




    private void init() {

        count = 0;

        paint = new Paint();
        paint1 = new Paint();

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);

        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(8);
        paint1.setAntiAlias(true);
        paint1.setStrokeCap(Paint.Cap.ROUND);
        paint1.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path = new Path();
        path1 = new Path();
        path2 = new Path();
        path3 = new Path();

        count = count+0.5;

        int retval = Double.compare(count, 360.00);

        if(retval > 0) {

        }
        else if(retval < 0) {

        }
        else {
            count = 0;
        }

        float X1 = (float)(radius1 * Math.cos(Math.toRadians(0+(0.222*count))) + X);
        float Y1 = ((float)(radius2 * Math.sin(Math.toRadians(0+(0.222*count))) + Y));
        float P1X = (float)(radius1 * Math.cos(Math.toRadians(80+(0.111*count))) + X);
        float P1Y = ((float)(radius2 * Math.sin(Math.toRadians(80+(0.111*count))) + Y));
        float P2X = (float)(radius1 * Math.cos(Math.toRadians(120+(0.222*count))) + X);
        float P2Y = ((float)((radius2+(0.111*count)) * Math.sin(Math.toRadians(120+(0.222*count))) + Y));
        float P3X = (float)(radius1 * Math.cos(Math.toRadians(200+(0.222*count))) + X);
        float P3Y = ((float)(radius1 * Math.sin(Math.toRadians(200+(0.222*count))) + Y));
        float P4X =(float)(radius1 * Math.cos(Math.toRadians(280+(0.222*count))) + X);
        float P4Y = ((float)(radius1 * Math.sin(Math.toRadians(280+(0.222*count))) + Y));

        path.moveTo(X1,Y1);
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

        path.moveTo(X1,Y1);
        path.cubicTo(P1c1.x,P1c1.y,P1c2.x,P1c2.y,P1X,P1Y);
        path.cubicTo(P2c1.x,P2c1.y,P2c2.x,P2c2.y,P2X,P2Y);
        path.cubicTo(P3c1.x,P3c1.y,P3c2.x,P3c2.y,P3X,P3Y);
        path.cubicTo(P4c1.x,P4c1.y,P4c2.x,P4c2.y,P4X,P4Y);
        path.cubicTo(P5c1.x,P5c1.y,P5c2.x,P5c2.y,X1,Y1);


        if(!pointsStored) {
            x1 = (int) P1c2.x;
            x2 = (int) P2c2.x;
            x3 = (x1+x2)/2;

            float value = (int) P1c2.y-((P1c1.y+P1Y)/2);
            y1 = y2 = y3 = (int) (P1c2.y-value/2) - 20;

            pointsStored = true;

        }

        if(i<=49) {

            if(i<25) {

                //drop11 logic
                if (m1 < 24) {
                    path1.moveTo(x1, y1);

                } else {
                    count1 = count1 + 4;
                    path1.moveTo(x1, y1 + count1);
                }

                path1.lineTo(x1, y1 + m1 + 25);
                canvas.drawPath(path1, paint1);

                m1 = m1 + 4;

                if (m1 == 100) {
                    m1 = 0;
                    count1 = 0;
                }

                //drop21 logic
                if(i>10) {

                    if(m2 < 24) {
                        path2.moveTo(x2, y2);

                    } else {
                        count2 = count2 + 4;
                        path2.moveTo(x2, y2+count2);
                    }

                    path2.lineTo(x2, y2+m2+25);
                    canvas.drawPath(path2, paint1);

                    m2 = m2 + 4;

                    if(m2 == 100) {
                        m2 = 0;
                        count2 = 0;
                    }

                }

            }

            if(i>=25 && i<=49) {

                // drop 3
                if(m3 < 24) {
                    path3.moveTo(x3, y3);

                } else {
                    count3 = count3 + 4;
                    path3.moveTo(x3, y3+count3);
                }

                path3.lineTo(x3, y3+m3+25);
                canvas.drawPath(path3, paint1);

                m3 = m3 + 4;

                if(m3 == 100) {
                    m3 = 0;
                    count3 = 0;
                }

                // drop21
                if(i<36) {

                    if(m2 < 24) {
                        path2.moveTo(x2, y2);

                    } else {
                        count2 = count2 + 4;
                        path2.moveTo(x2, y2+count2);
                    }

                    path2.lineTo(x2, y2+m2+25);
                    canvas.drawPath(path2, paint1);

                    m2 = m2 + 4;

                    if(m2 == 100) {
                        m2 = 0;
                        count2 = 0;
                    }

                }

            }


        }

        i++;

        if(i == 50) {
            i = 0;
        }

        //fill cloud with white color
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);

        //draw stroke with back color
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);

        invalidate();

    }

    private PointF calculateTriangle(float x1, float y1,
                                     float x2, float y2, boolean left, double count) {

        PointF result = new PointF(0,0);
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle = (float) ((Math.atan2(dy, dx) - Math.PI /2f));
        float sideDist = (float)0.5 * (float) Math.sqrt(dx * dx + dy * dy); //square

        if (left){
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);

        }else{
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }

        return result;

    }

}
