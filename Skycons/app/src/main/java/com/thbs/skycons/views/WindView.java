package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by administrator on 16/09/14.
 */
public class WindView extends View {

    Paint paint1, paint2;
    Path path1, path2, pointsPath1, pointsPath2;
    private int screenW, screenH;
    private float X, Y;
    PathPoints[] points1, points2;
    float m = 0, n = 0;
    float radius;
    boolean one = true, two = false, three = false, four = false;

    public WindView(Context context) {
        super(context);
        init();
    }

    public WindView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WindView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint1 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(10);
        paint1.setStyle(Paint.Style.STROKE);

        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStrokeWidth(12);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);

        path1 = new Path();
        path2 = new Path();

    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;
        X = 0;
        Y = screenH/2;

        radius = screenW / 7;

        path1.moveTo(X - 5, Y);
        path1.quadTo(X + 95, Y + 50, X + 170, Y - 20);
        path1.quadTo(X + 200, Y - 40, X + 230, Y - 20);

        path2.moveTo(X - 5, Y);
        path2.cubicTo(X + 70, Y + 10, X + 110, Y - 40, X + 80, Y - 70);
        path2.cubicTo(X + 50, Y - 80, X + 30, Y - 40, X + 100, Y);
        path2.cubicTo(X + 140, Y + 25, X + 190, Y - 25, X + 220, Y - 10);

        points1 = getPoints(path1);
        points2 = getPoints(path2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // canvas.drawPath(path2, paint1);

        if (one) {

            if (m == 0) {
                pointsPath1 = new Path();
                pointsPath2 = new Path();
            }

            if (m < points1.length - 2) {
                pointsPath1.moveTo(points1[(int) m].getX(), points1[(int) m].getY());
                pointsPath1.lineTo(points1[(int) (m + 1)].getX(), points1[(int) (m + 1)].getY());
                canvas.drawPath(pointsPath1, paint1);
            }

            if(m>110) {
                pointsPath2.moveTo(points1[(int) n].getX(), points1[(int) n].getY());
                pointsPath2.lineTo(points1[(int) (n + 1)].getX(), points1[(int) (n + 1)].getY());
                canvas.drawPath(pointsPath2, paint2);
                n = n + 1;
            }

            if (m == points1.length - 10) {
                m = 0;
                one = false;
                two = true;

            } else {
                m = m + 1;
                invalidate();
            }

        }

        if(two) {

            if (n < points1.length - 2) {
                canvas.drawPath(pointsPath1, paint1);
                pointsPath2.moveTo(points1[(int) n].getX(), points1[(int) n].getY());
                pointsPath2.lineTo(points1[(int) (n + 1)].getX(), points1[(int) (n + 1)].getY());
                canvas.drawPath(pointsPath2, paint2);
            }

            if (n == points1.length - 10) {
                n = 0;
                m = 0;
                two = false;
                three = true;
                invalidate();

            } else {
                n = n + 1;
                invalidate();
            }

        }


        if (three) {

            if (m == 0) {
                pointsPath1 = new Path();
            }

            if(m%50==0) {
                pointsPath2 = new Path();
            }

            if (m < points2.length - 2) {
                pointsPath1.moveTo(points2[(int) m].getX(), points2[(int) m].getY());
                pointsPath1.lineTo(points2[(int) (m + 1)].getX(), points2[(int) (m + 1)].getY());
                canvas.drawPath(pointsPath1, paint1);
            }

            if(m>70) {
                pointsPath2.moveTo(points2[(int) n].getX(), points2[(int) n].getY());
                pointsPath2.lineTo(points2[(int) (n + 1)].getX(), points2[(int) (n + 1)].getY());
                canvas.drawPath(pointsPath2, paint2);
                n = n + 1;
            }

            if (m == points1.length - 10) {
                m = 0;
                three = false;
                four = true;

            } else {
                m = m + 1;
                invalidate();
            }

        }

        if(four) {

            if(n%50==0) {
                pointsPath2 = new Path();
            }

            if (n < points1.length - 2) {
                canvas.drawPath(pointsPath1, paint1);
                pointsPath2.moveTo(points2[(int) n].getX(), points2[(int) n].getY());
                pointsPath2.lineTo(points2[(int) (n + 1)].getX(), points2[(int) (n + 1)].getY());
                canvas.drawPath(pointsPath2, paint2);
            }

            if (n == points1.length - 10) {
                n = 0;
                m = 0;
                one = true;
                four = false;
                invalidate();

            } else {
                n = n + 1;
                invalidate();
            }

        }


    }


    private PathPoints[] getPoints(Path path) {

        PathPoints[] pointArray = new PathPoints[250];
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float distance = 0f;
        float speed = length / 250;
        int counter = 0;
        float[] aCoordinates = new float[2];

        while ((distance < length) && (counter < 250)) {
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
