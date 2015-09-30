package com.thbs.skycons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by administrator on 18/09/14.
 */

public class WindView extends SkyconView {

    private static Paint paint;
    private int screenW, screenH;
    private float X, Y, X2, Y2,  X11, Y11, Y21, X21, Xc, Yc;
    private Path tracePath, windPath, leafPath;
    private double count;
    int degrees;
    boolean isFirstPath;
    PathPoints[] points;
    boolean isFirst;

    public WindView(Context context) {
        super(context);
        init();
    }

    public WindView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {

        // initialize default values
        isFirstPath = true;
        windPath = new Path();
        leafPath = new Path();
        tracePath = new Path();
        count = 0;
        degrees = 200;

        isAnimated = true;
        isFirst = true;
        paint = new Paint();

        paint.setColor(strokeColor);
        paint.setStrokeWidth(screenW/25);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;

        X = 0;
        Y = (float) (screenH / 1.5);
        tracePath.moveTo(X, Y);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // set canvas background color
        canvas.drawColor(bgColor);
        // set stroke width
        paint.setStrokeWidth((float)(0.02083*screenW));

        //initializing the paths
        windPath = new Path();
        leafPath = new Path();
        int retval;

        //comparison to check 360 degrees rotation
        retval = Double.compare(count, 310.00);

        if (retval > 0) {

            //resetting counter and initializing values on completion of a rotation
                tracePath = new Path(); //reinitializing tracepath
                count = 0; //resetting counter
                degrees = 200;//resetting leaf's starting angle
                isFirstPath = !isFirstPath;//resetting path flag

        }

        if (isFirstPath) {

            int retval1 = Double.compare(count, 130.00);
            if (retval1 == 0) {
                if(!isAnimated) {
                    if(!isFirst){
                        // mark completion of animation
                        isAnimated =true;
                    } else{
                        // mark continuation of animation
                        isFirst = false;
                        isAnimated = false;
                    }
                }
            }

            if(isStatic && isAnimated) {
                // center position if static
                count =130;
            } else {
                //incrementing counter for rotation
                count += 1;

            }



            // trace for first path
            X = 0;
            Y = (float) (screenH / 1.5);
            X2 = (float) (screenW + 50);
            Y2 = (float) (screenH/4);

            tracePath.moveTo(X,Y);
            PointF P1c1 = calculateTriangle(X, Y, X2, Y2, true, 0.2,"CCW");
            PointF P1c2 = calculateTriangle(X, Y, X2, Y2, false, 0.2,"CCW");
            tracePath.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, X2, Y2);

            //getting points from the trace path
            points = getPoints(tracePath);

        } else {

                if (count < 150) {
                    //incrementing counter faster
                    count += 1.5;

                } else {
                    //incrementing counter slower
                    count += 1;
                }


            // trace for second path
            X = 0;
            Y = (float) (screenH / 1.5);
            X2 = (float) (screenW / 2);
            Y2 = (float) (screenH / 3);
            Xc = (float) (screenW + 10);
            Yc = (float) (screenH / 2);

            tracePath.moveTo(X - 5, Y);
            tracePath.cubicTo(X + (screenW/3.2f), Y + (screenW/12), X + (screenW/2.0f), Y - (screenW/3.2f), X + (screenW/2.5f), Y - (screenW/2.8235f));
            tracePath.cubicTo(X + (screenW/2.7f), Y - (screenW/2.7428f), X + (screenW/3.2f), Y - (screenW/3f), X + (screenW/3.0f), Y - (screenW/3.6923f));
            tracePath.cubicTo(X + (screenW/2.6f), Y - (screenW/6), X + (screenW/1.5f), Y - (screenW/9.6f), screenW+50, Y - (screenW/2.4f));

            //getting points from the trace path
            points = getPoints(tracePath);

        }

        if (count <= 20) {
            // draw nothing

        } else if ((count >20)&&(count <= 60)) {
            // draw initial path of length 60
            for (int i = 0; i < (count - 20); i++) {

                windPath.moveTo(points[i].getX(), points[i].getY());
                windPath.lineTo(points[i + 1].getX(), points[i + 1].getY());

            }
        } else if (count >= 249) {
            // draw path of decrementing length from last

            for (int i = (int)count - 60 ; i <= 248; i++) {

                windPath.moveTo(points[i].getX(), points[i].getY());
                windPath.lineTo(points[i + 1].getX(), points[i + 1].getY());

            }

        } else {
            // move initial path of length 60
            for (int i = (int) (count - 60); i < (count - 20); i++) {

                windPath.moveTo(points[i].getX(), points[i].getY());
                windPath.lineTo(points[i + 1].getX(), points[i + 1].getY());

            }

        }

        // draw windpath

        canvas.drawPath(windPath, paint);

        if((int) count <250 ) {

            // initialize coordinates for leaf
            Xc = points[(int) (count)].getX();
            Yc = points[(int) (count)].getY();
            X11 = (float) (((screenW * 4) / 100) * Math.cos(Math.toRadians
                    ((degrees + count) - 30)) + Xc);
            Y11 = (float) (((screenW * 4) / 100) * Math.sin(Math.toRadians((degrees + count) - 30)) + Yc);
            X21 = (float) (((screenW * 12) / 100) * Math.cos(Math.toRadians((degrees + count))) + Xc);
            Y21 = (float) (((screenW * 12) / 100) * Math.sin(Math.toRadians((degrees + count))) + Yc);

            // getting points in between coordinates for leaf shape
            PointF P11c1 = calculateTriangle(Xc, Yc, X21, Y21, true, 0.7,"CW");
            PointF P11c2 = calculateTriangle(Xc, Yc, X21, Y21, false, 0.7,"CW");
            PointF P21c1 = calculateTriangle(X21, Y21, X11, Y11, true, 0.8,"CW");
            PointF P21c2 = calculateTriangle(X21, Y21, X11, Y11, false, 0.8,"CW");
            PointF P31c1 = calculateTriangle(X11, Y11, Xc, Yc, true, 0.2,"CCW");
            PointF P31c2 = calculateTriangle(X11, Y11, Xc, Yc, false, 0.2,"CCW");

            // drawing arcs between coordinates
            leafPath.moveTo(Xc, Yc);
            leafPath.cubicTo(P11c1.x, P11c1.y, P11c2.x, P11c2.y, X21, Y21);
            leafPath.cubicTo(P21c1.x, P21c1.y, P21c2.x, P21c2.y, X11, Y11);
            leafPath.cubicTo(P31c1.x, P31c1.y, P31c2.x, P31c2.y, Xc, Yc);

            // drawing leaf on canvas
            paint.setColor(bgColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(leafPath, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokeColor);
            canvas.drawPath(leafPath, paint);
        }



        if(!isStatic || !isAnimated) {
            // invalidate if not static or not animating
            invalidate();
        }


    }

    private PathPoints[] getPoints(Path path) {

        // getting points from path

        PathPoints[] pointArray = new PathPoints[250]; // initialize array of length 250
        PathMeasure pm = new PathMeasure(path, false);// measure path size
        float length = pm.getLength();// get length
        float distance = 0f;
        float speed = length / 250;// to get 250 points
        int counter = 0;
        float[] aCoordinates = new float[2];

        // iterate through path for points
        while ((distance < length) && (counter < 250)) {
            pm.getPosTan(distance, aCoordinates, null);
            pointArray[counter] = new PathPoints(aCoordinates[0], aCoordinates[1]);
            counter++;
            distance = distance + speed;
        }

        return pointArray;
    }


    class PathPoints {

        // class to hold coordinates
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
    private PointF calculateTriangle(float x1, float y1, float x2, float y2, boolean left, double distOffset,String dir) {

        PointF result = new PointF(0, 0);
        // finding center point between the coordinates
        float dy = y2 - y1;
        float dx = x2 - x1;


        float dangle = 0;
        float sideDist = 0;

        // calculating angle and the distance between center and the two points with direction
        if (dir == "CW") {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI / 2f));
            sideDist = (float) distOffset * (float) Math.sqrt(dx * dx + dy * dy); //square
        } else if (dir == "CCW") {
            dangle = (float) ((Math.atan2(dy, dx) + Math.PI / 2f));
            sideDist = (float) distOffset * (float) Math.sqrt(dx * dx + dy * dy); //square
        }

        if (left) {
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // nothing to do
                return true;
            case MotionEvent.ACTION_MOVE:
                // nothing to do
                break ;
            case MotionEvent.ACTION_UP:

                if(isStatic && isAnimated) {
                    // start animation if it is not animating
                    isFirst = true;
                    isAnimated = false;
                }

                break;
            default:
                return false;
        }

        // Schedules a repaint.
        if(!isAnimated) {
            invalidate();
        }

        return true;
    }

}
