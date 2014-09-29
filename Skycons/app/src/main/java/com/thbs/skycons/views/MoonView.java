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
 * Created by administrator on 17/09/14.
 */
public class MoonView extends View {

    Paint paint;
    Path path;
    private int screenW, screenH;
    private float X, Y;
    PathPoints[] pathPoints;
    float m = 0;
    float radius;
    boolean clockwise = false;
    float a=0, b=0, c=0, d=0;

    public MoonView(Context context, AttributeSet attrs) {
        super(context, attrs);

        String num1[] = attrs.getAttributeValue(0).split("\\.");
        String num2[] = attrs.getAttributeValue(1).split("\\.");

        screenW = Integer.valueOf(num1[0]);
        screenH = Integer.valueOf(num2[0]);

        X = screenW/2;
        Y = (screenH/2);

        radius = screenW/3;

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path = new Path();

        RectF rectF1 = new RectF();

        if(!clockwise) {

            rectF1.set(X-radius, Y-radius, X+radius, Y+radius);
            path.addArc(rectF1, 65-(m/2), 275);

            pathPoints = getPoints(path);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF P1c1 = calculateTriangle(a, b, c, d, true);
            PointF P1c2 = calculateTriangle(a, b, c, d, false);

            path.moveTo(a, b);
            path.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, c, d);

            canvas.drawPath(path, paint);

            m = m + 0.5f;

            if(m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        } else {

            rectF1.set(X-radius, Y-radius, X+radius, Y+radius);
            path.addArc(rectF1, 15+(m/2), 275);

            pathPoints = getPoints(path);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF P1c1 = calculateTriangle(a, b , c, d, true);
            PointF P1c2 = calculateTriangle(a, b, c, d, false);

            path.moveTo(a, b);
            path.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, c, d);

            canvas.drawPath(path, paint);

            m = m + 0.5f;

            if(m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        }

        invalidate();

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

    private PointF calculateTriangle(float x1, float y1, float x2, float y2, boolean left) {

        PointF result = new PointF(0,0);
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle = (float) ((Math.atan2(dy, dx) - Math.PI /2f));
        float sideDist = (float)  - 0.6 * (float) Math.sqrt(dx * dx + dy * dy); //square

        if (left){
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);

        } else {
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }

        return result;
    }

}
