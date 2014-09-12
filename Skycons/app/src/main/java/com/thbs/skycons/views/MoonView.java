package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by administrator on 11/09/14.
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
    RectF backupRectF;
    float a=0, b=0, c=0, d=0;

    public MoonView(Context context) {
        super(context);
        init();
    }

    public MoonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;
        X = screenW/2;
        Y = (screenH/2);

        radius = screenW/7;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path = new Path();
        RectF rectF1 = new RectF();
        RectF rectF2 = new RectF();

        if(!clockwise) {

            rectF1.set(X-radius, Y-radius, X+radius, Y+radius);
            path.addArc(rectF1, 65-(m/2), 275);

            pathPoints = getPoints(path);

            if(a == 0) {
                a = pathPoints[0].getX()-radius*0.3f;
                b = pathPoints[999].getY()-radius*0.015f;
                c = pathPoints[999].getX()+radius*0.3f;
                d = pathPoints[0].getY()+radius*0.015f;
            }

            rectF2.set(a-(m/9.4f), b-(m/3f), c+(m/9.8f), d-(m/3f));

            path.addArc(rectF2, 112-(m/2), 190);

            canvas.drawPath(path, paint);

            m = m + 0.5f;

            if(m == 100) {
                m = 0;
                backupRectF = rectF2;
                clockwise = !clockwise;
            }

        } else {

            rectF1.set(X-radius, Y-radius, X+radius, Y+radius);
            path.addArc(rectF1, 15+(m/2), 275);

            pathPoints = getPoints(path);

            rectF2.set(backupRectF.left+(m/9.4f), backupRectF.top+(m/3f),
                    backupRectF.right-(m/9.8f), backupRectF.bottom+(m/3f));

            path.addArc(rectF2, 62+(m/2), 190);

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