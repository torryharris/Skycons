package com.thbs.skycons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * This view draws the Moon.
 */
public class MoonView extends SkyconView {

    Paint paint;
    Path path;
    private int screenW, screenH;
    private float X, Y;
    PathPoints[] pathPoints;
    float m = 0;
    float radius;
    boolean clockwise = false;
    float a=0, b=0, c=0, d=0;

    int count = 0; //counter for stopping animation

    public MoonView(Context context,boolean isStatic,boolean isAnimated, int strokeColor , int backgroundColor) {
        super(context);

        this.isStatic = isStatic;
        this.isAnimated = isAnimated;
        this.strokeColor = strokeColor;
        this.bgColor = backgroundColor;

        init();
    }

    public MoonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //Paint for drawing Moon
        paint = new Paint();
        paint.setColor(strokeColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);

        isAnimated = true;
    }

    // Initial declaration of the coordinates.
    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;

        X = screenW/2;
        Y = (screenH/2);

        radius = (int)(0.1458 * screenW);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // set canvas background color
        canvas.drawColor(bgColor);

        paint.setStrokeWidth((float) (0.02083 * screenW));

        path = new Path();

        RectF rectF1 = new RectF();

        if(!clockwise) {//Anticlockwise rotation

            // First arc of the Moon.
            rectF1.set(X-radius, Y-radius, X+radius, Y+radius);
            path.addArc(rectF1, 65-(m/2), 275);

            pathPoints = getPoints(path);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF P1c1 = cubic2Points(a, b, c, d, true);
            PointF P1c2 = cubic2Points(a, b, c, d, false);

            // Second arc of the Moon in opposite face.
            path.moveTo(a, b);
            path.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, c, d);

            canvas.drawPath(path, paint);

            m = m + 0.5f;

            if(m == 100) {
                m = 0;
                clockwise = !clockwise;
            }

        } else {//Clockwise rotation

            // First arc of the Moon.
            rectF1.set(X-radius, Y-radius, X+radius, Y+radius);
            path.addArc(rectF1, 15+(m/2), 275);

            pathPoints = getPoints(path);

            a = pathPoints[999].getX();
            b = pathPoints[999].getY();
            c = pathPoints[0].getX();
            d = pathPoints[0].getY();

            PointF P1c1 = cubic2Points(a, b , c, d, true);
            PointF P1c2 = cubic2Points(a, b, c, d, false);

            // Second arc of the Moon in opposite face.
            path.moveTo(a, b);
            path.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, c, d);

            canvas.drawPath(path, paint);

            m = m + 0.5f;

            if(m == 100) {
                m = 0;
                clockwise = !clockwise;

                if(!isAnimated) {
                    count ++;
                }

            }

        }

        if(!isStatic || !isAnimated) {

            if(count < 3) {
                // invalidate if not static or not animating
                invalidate();

            } else {
                count = 0;
            }

        }

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

    private PointF cubic2Points(float x1, float y1, float x2, float y2, boolean left) {

        PointF result = new PointF(0,0);
        // finding center point between the coordinates
        float dy = y2 - y1;
        float dx = x2 - x1;
        // calculating angle and the distance between center and the two points
        float dangle = (float) ((Math.atan2(dy, dx) - Math.PI /2f));
        float sideDist = (float)  - 0.6 * (float) Math.sqrt(dx * dx + dy * dy); //square

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
                // start animation if it is not animating
                if(isStatic && isAnimated) {
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
