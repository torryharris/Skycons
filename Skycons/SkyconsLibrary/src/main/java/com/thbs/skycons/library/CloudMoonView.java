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
 * This view draws cloud with Moon.
 */
public class CloudMoonView extends SkyconView {

    Paint paintCloud, paintMoon;
    Path  pathMoon;
    private int screenW, screenH;
    private float X, Y, X2, Y2;
    PathPoints[] pathPoints;
    float m = 0;
    float radius;
    boolean clockwise = false;
    float a=0, b=0, c=0, d=0;
    private double count;
    Cloud cloud;

    public CloudMoonView(Context context) {
        super(context);
        init();
    }

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
        paintCloud.setShadowLayer(0, 0, 0, strokeColor);

        //Paint for drawing Moon
        paintMoon = new Paint();
        paintMoon.setColor(strokeColor);
        paintMoon.setAntiAlias(true);
        paintMoon.setStrokeCap(Paint.Cap.ROUND);
        paintMoon.setStyle(Paint.Style.STROKE);

        count = 0;
        cloud = new Cloud();

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

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // set canvas background color
        canvas.drawColor(bgColor);

        paintCloud.setStrokeWidth((float)(0.02083*screenW));
        paintMoon.setStrokeWidth((float)(0.02083*screenW));

        count = count+0.5;
        int retval = Double.compare(count, 360.00);

        if(retval == 0) {
            if(!isAnimated) {
                // mark completion of animation
                isAnimated = true;
                //resetting counter on completion of a rotation
                count = 0;
            } else {
                //resetting counter on completion of a rotation
                count = 0;
            }
        }

        // Moon shape
        pathMoon = new Path();
        RectF rectF1 = new RectF();

        PointF P5c1 = cloud.getP5c1(X,Y,screenW,count);
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

        // drawing cloud with fill
        paintCloud.setColor(bgColor);
        paintCloud.setStyle(Paint.Style.FILL);
        canvas.drawPath(cloud.getCloud(X,Y,screenW,count), paintCloud);

        // drawing cloud with stroke
        paintCloud.setColor(strokeColor);
        paintCloud.setStyle(Paint.Style.STROKE);
        canvas.drawPath(cloud.getCloud(X,Y,screenW,count), paintCloud);

        if(!isStatic || !isAnimated) {
            invalidate();
        }

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
