package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by administrator on 11/09/14.
 */
public class CloudMoonView extends View {

    Paint paintCloud, paintMoon;
    Path pathCloud, pathMoon;
    private int screenW, screenH;
    private float X, Y, X1, Y1;
    PathPoints[] pathPoints;
    float m = 0;
    float radius;
    boolean clockwise = false;
    float a=0, b=0, c=0, d=0;
    private double count;

    public CloudMoonView(Context context) {
        super(context);
        init();
    }

    public CloudMoonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CloudMoonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paintCloud = new Paint();
        paintCloud.setStrokeWidth(10);
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setAntiAlias(true);
        paintCloud.setShadowLayer(0, 0, 0, Color.BLACK);

        paintMoon = new Paint();
        paintMoon.setColor(Color.BLACK);
        paintMoon.setAntiAlias(true);
        paintMoon.setStrokeWidth(10);
        paintMoon.setStrokeCap(Paint.Cap.ROUND);
        paintMoon.setStyle(Paint.Style.STROKE);

        count = 0;
        pathCloud = new Path();

    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;
        X = screenW/2;
        Y = (screenH/2);

        X1 = screenW*0.7f;
        Y1 = (screenH*0.23f);

        radius = screenW/7;

        pathCloud.moveTo(X, Y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Moon shape
        pathMoon = new Path();
        RectF rectF1 = new RectF();

        if(!clockwise) {

            rectF1.set(X1-radius, Y1-radius, X1+radius, Y1+radius);
            pathMoon.addArc(rectF1, 65 - (m / 2), 275);

            pathPoints = getPoints(pathMoon);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF P1c1 = calculateTriangle(a, b, c, d, true);
            PointF P1c2 = calculateTriangle(a, b, c, d, false);

            pathMoon.moveTo(a, b);
            pathMoon.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, c, d);

            canvas.drawPath(pathMoon, paintMoon);

            m = m + 0.5f;

            if(m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        } else {

            rectF1.set(X1-radius, Y1-radius, X1+radius, Y1+radius);
            pathMoon.addArc(rectF1, 15 + (m / 2), 275);

            pathPoints = getPoints(pathMoon);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF P1c1 = calculateTriangle(a, b, c, d, true);
            PointF P1c2 = calculateTriangle(a, b, c, d, false);

            pathMoon.moveTo(a, b);
            pathMoon.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, c, d);

            canvas.drawPath(pathMoon, paintMoon);

            m = m + 0.5f;

            if(m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        }

        // Cloud shape
        pathCloud = new Path();
        count = count+0.5;
        int retval = Double.compare(count, 360.00);

        if(retval > 0) {

        } else if(retval < 0) {

        } else {
            count = 0;
        }

        float X1 = (float)(90 * Math.cos(Math.toRadians(0+(0.222*count))) + X);
        float Y1 = ((float)(50 * Math.sin(Math.toRadians(0+(0.222*count))) + Y));
        float P1X = (float)(90 * Math.cos(Math.toRadians(80+(0.111*count))) + X);
        float P1Y = ((float)(50 * Math.sin(Math.toRadians(80+(0.111*count))) + Y));
        float P2X = (float)(90 * Math.cos(Math.toRadians(120+(0.222*count))) + X);
        float P2Y = ((float)((50+(0.111*count)) * Math.sin(Math.toRadians(120+(0.222*count))) + Y));
        float P3X = (float)(90 * Math.cos(Math.toRadians(200+(0.222*count))) + X);
        float P3Y = ((float)(90 * Math.sin(Math.toRadians(200+(0.222*count))) + Y));
        float P4X =(float)(90 * Math.cos(Math.toRadians(280+(0.222*count))) + X);
        float P4Y = ((float)(90 * Math.sin(Math.toRadians(280+(0.222*count))) + Y));

        pathCloud.moveTo(X1, Y1);

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

        pathCloud.moveTo(X1, Y1);
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

    private PointF calculateTriangle(float x1, float y1, float x2, float y2, boolean left) {

        PointF result = new PointF(0,0);
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle = (float) ((Math.atan2(dy, dx) - Math.PI /2f));
        float sideDist = (float)  - 0.5 * (float) Math.sqrt(dx * dx + dy * dy); //square

        if (left){
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);

        } else {
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }

        return result;
    }

    private PathPoints[] getPoints(Path path) {

        PathPoints[] pointArray = new PathPoints[1000];
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float distance = 0f;
        float speed = length / 1000;
        int counter = 0;
        float[] aCoordinates = new float[2];

        while ((distance < length) && (counter < 1000)) {
            // get point from the pathMoon
            pm.getPosTan(distance, aCoordinates, null);
            pointArray[counter] = new PathPoints(aCoordinates[0], aCoordinates[1]);
            counter++;
            distance = distance + speed;
        }

        return pointArray;
    }

    class PathPoints {

        float x, y;

        public PathPoints(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

    }

}
