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
 * Created by administrator on 25/09/14.
 */
public class Exp extends View {

    Paint paint;
    Path path, path1, spath1, spath2;
    private int screenW, screenH;
    PathPoints[] pathPoints1, pathPoints2;
    int m = 0;


    public Exp(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;


    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.BUTT);

        path = new Path();
        path1 = new Path();

        spath1 = new Path();
        spath2 = new Path();

        path.moveTo(50, 50);
        path.lineTo(20, 100);
        path.lineTo(70, 150);
        path.lineTo(30, 200);

        path1.moveTo(70, 50);
        path1.lineTo(40, 100);
        path1.lineTo(90, 150);
        path1.lineTo(30, 200);

        pathPoints1 = getPoints(path);
        pathPoints2 = getPoints(path1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       if(m<148) {
           spath1.moveTo(pathPoints1[m].getX(), pathPoints1[m].getY());
           spath1.lineTo(pathPoints1[m+1].getX(), pathPoints1[m+1].getY());
           spath1.lineTo(pathPoints2[m+1].getX(), pathPoints2[m+1].getY());
           spath1.lineTo(pathPoints2[m].getX(), pathPoints2[m].getY());
           spath1.close();

       }

        if(m==148) {
            m = 0;
        }

        m = m +2;


        canvas.drawPath(spath1, paint);
       // canvas.drawPath(path1, paint);


        invalidate();

    }

    private PathPoints[] getPoints(Path path) {

        PathPoints[] pointArray = new PathPoints[150];
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float distance = 0f;
        float speed = length / 150;
        int counter = 0;
        float[] aCoordinates = new float[2];

        while ((distance < length) && (counter < 150)) {
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
