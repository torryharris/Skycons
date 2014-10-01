package com.thbs.skycons.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * This view draws cloud with heavy rain.
 */
public class CloudHvRainView extends View {

    private static Paint paintCloud, paintRain;
    private int screenW, screenH;
    private float X, Y;
    private Path pathCloud, path1, path2, path3;
    int m1=0, m2=0, m3=0, x1=0, y1=0, x2=0, y2=0, x3=0, y3=0;
    int count1 = 0, count2 = 0, count3 = 0, i=0;
    private double count;
    boolean pointsStored = false;
    double radius1, radius2;

    public CloudHvRainView(Context context, AttributeSet attrs) {
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
        paintRain.setStyle(Paint.Style.FILL_AND_STROKE);

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

        radius1 = 90;
        radius2 = 50;

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pathCloud = new Path(); // pathCloud for cloud
        path1 = new Path();// pathCloud for drop 1
        path2 = new Path();// pathCloud for drop 2
        path3 = new Path();// pathCloud for drop 3

        count = count+0.5;

        paintCloud.setStrokeWidth((float) (0.02083 * screenW));
        paintRain.setStrokeWidth((float) (0.015 * screenW));

        int retval = Double.compare(count, 360.00);

        if(retval == 0) {
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

        pathCloud.moveTo(X1,Y1);
        pathCloud.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, P1X, P1Y);
        pathCloud.cubicTo(P2c1.x, P2c1.y, P2c2.x, P2c2.y, P2X, P2Y);
        pathCloud.cubicTo(P3c1.x, P3c1.y, P3c2.x, P3c2.y, P3X, P3Y);
        pathCloud.cubicTo(P4c1.x, P4c1.y, P4c2.x, P4c2.y, P4X, P4Y);
        pathCloud.cubicTo(P5c1.x, P5c1.y, P5c2.x, P5c2.y, X1, Y1);


        // Store starting x, y coordinates of rain drops
        if(!pointsStored) {
            x1 = (int) P1c2.x;
            x2 = (int) P2c2.x;
            x3 = (x1+x2)/2;

            float value = (int) P1c2.y-((P1c1.y+P1Y)/2);
            y1 = y2 = y3 = (int) (P1c2.y-value/2) - 20;

            pointsStored = true;

        }

        if(i<=2*49) {

            if(i<2*25) {

                //drop11 logic
                if (m1 < 24) {
                    path1.moveTo(x1, y1);

                } else {
                    count1 = count1 + 4;
                    path1.moveTo(x1, y1 + count1);
                }

                path1.lineTo(x1, y1 + m1 + (float)(Y*0.1));
                canvas.drawPath(path1, paintRain);

                m1 = m1 + 4;

                if (m1 == 100) {
                    m1 = 0;
                    count1 = 0;
                }

                //drop21 logic
                if(i>2*10) {

                    if(m2 < 24) {
                        path2.moveTo(x2, y2);

                    } else {
                        count2 = count2 + 4;
                        path2.moveTo(x2, y2+count2);
                    }

                    path2.lineTo(x2, y2+m2+(float)(Y*0.1));
                    canvas.drawPath(path2, paintRain);

                    m2 = m2 + 4;

                    if(m2 == 100) {
                        m2 = 0;
                        count2 = 0;
                    }

                }

            }

            if(i>=2*25 && i<=2*49) {

                // drop 3
                if(m3 < 24) {
                    path3.moveTo(x3, y3);

                } else {
                    count3 = count3 + 4;
                    path3.moveTo(x3, y3+count3);
                }

                path3.lineTo(x3, y3+m3+(float)(Y*0.1));
                canvas.drawPath(path3, paintRain);

                m3 = m3 + 4;

                if(m3 == 100) {
                    m3 = 0;
                    count3 = 0;
                }

                // drop21
                if(i<2*36) {

                    if(m2 < 24) {
                        path2.moveTo(x2, y2);

                    } else {
                        count2 = count2 + 4;
                        path2.moveTo(x2, y2+count2);
                    }

                    path2.lineTo(x2, y2+m2+(float)(Y*0.1));
                    canvas.drawPath(path2, paintRain);

                    m2 = m2 + 4;

                    if(m2 == 100) {
                        m2 = 0;
                        count2 = 0;
                    }

                }

            }


        }

        i+=2;

        if(i == 2*50) {
            i = 0;
        }

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

    //
    private PointF calculateTriangle(float x1, float y1,
                                     float x2, float y2, boolean left, double count) {

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
