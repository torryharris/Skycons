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
 * This view draws cloud with Moon.
 */
public class CloudMoonView extends View {

    Paint paintCloud, paintMoon;
    Path pathCloud, pathMoon;
    private int screenW, screenH;
    private float X, Y, X2, Y2;
    PathPoints[] pathPoints;
    float m = 0;
    float radius;
    boolean clockwise = false;
    float a=0, b=0, c=0, d=0;
    private double count;


    public CloudMoonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }


    private void init() {
        //Paint for drawing cloud
        paintCloud = new Paint();
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setAntiAlias(true);
        paintCloud.setShadowLayer(0, 0, 0, Color.BLACK);

        //Paint for drawing Moon
        paintMoon = new Paint();
        paintMoon.setColor(Color.BLACK);
        paintMoon.setAntiAlias(true);
        paintMoon.setStrokeCap(Paint.Cap.ROUND);
        paintMoon.setStyle(Paint.Style.STROKE);

        count = 0;

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paintCloud.setStrokeWidth((float)(0.02083*screenW));
        paintMoon.setStrokeWidth((float)(0.02083*screenW));

        // Cloud shape
        pathCloud = new Path();
        count = count+0.5;
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

        // Moon shape
        pathMoon = new Path();
        RectF rectF1 = new RectF();

        if(X2 == 0) {
            X2 = P5c1.x;
            Y2 = P5c1.y + (int)(0.042 * screenW);

            radius = (int)(0.1042 * screenW);
        }

        if(!clockwise) { //Anticlockwise rotation

            // First arc of the Moon.
            rectF1.set(X2-radius, Y2-radius, X2+radius, Y2+radius);
            pathMoon.addArc(rectF1, 65 - (m / 2), 275);

            pathPoints = getPoints(pathMoon);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF p1 = cubic2Points(a, b, c, d, true);
            PointF p2 = cubic2Points(a, b, c, d, false);

            // Second arc of the Moon in opposite face.
            pathMoon.moveTo(a, b);
            pathMoon.cubicTo(p1.x, p1.y, p2.x, p2.y, c, d);

            canvas.drawPath(pathMoon, paintMoon);

            m = m + 0.5f;

            if(m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        } else { //Clockwise rotation

            // First arc of the Moon.
            rectF1.set(X2-radius, Y2-radius, X2+radius, Y2+radius);
            pathMoon.addArc(rectF1, 15 + (m / 2), 275);

            pathPoints = getPoints(pathMoon);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF p1 = cubic2Points(a, b, c, d, true);
            PointF p2 = cubic2Points(a, b, c, d, false);

            // Second arc of the Moon in opposite face.
            pathMoon.moveTo(a, b);
            pathMoon.cubicTo(p1.x, p1.y, p2.x, p2.y, c, d);

            canvas.drawPath(pathMoon, paintMoon);

            m = m + 0.5f;

            if(m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        }

        // Draw cloud
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

    // Used to get cubic 2 points between staring & end coordinates.
    private PointF cubic2Points(float x1, float y1, float x2,
                                     float y2, boolean left) {

        PointF result = new PointF(0,0);
        // finding center point between the coordinates
        float dy = y2 - y1;
        float dx = x2 - x1;
        // calculating angle and the distance between center and the two points
        float dangle = (float) ((Math.atan2(dy, dx) - Math.PI /2f));
        float sideDist = (float)  - 0.5 * (float) Math.sqrt(dx * dx + dy * dy); //square

        if (left){
            //point from center to the left
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);

        } else {
            //point from center to the right
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }

        return result;
    }


    // Used to fetch points from given path.
    private PathPoints[] getPoints(Path path) {

        //Size of 1000 indicates that, 1000 points
        // would be extracted from the path
        PathPoints[] pointArray = new PathPoints[1000];
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float distance = 0f;
        float speed = length / 1000;
        int counter = 0;
        float[] aCoordinates = new float[2];

        while ((distance < length) && (counter < 1000)) {
            pm.getPosTan(distance, aCoordinates, null);
            pointArray[counter] = new PathPoints(aCoordinates[0], aCoordinates[1]);
            counter++;
            distance = distance + speed;
        }

        return pointArray;
    }

    // Class for fetching path coordinates.
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
