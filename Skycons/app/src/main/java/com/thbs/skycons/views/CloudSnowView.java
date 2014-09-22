package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by administrator on 09/09/14.
 */
public class CloudSnowView extends View {

    private static Paint paint, paint1;
    PathPoints[] pathPoints11, pathPoints12, pathPoints21, pathPoints22;
    private int screenW, screenH;
    private float X, Y;

    private Path cloudPath, path11, path12, path13,
                 path21, path22, path23,
                 cubicPath11, cubicPath12,
                 cubicPath21, cubicPath22;

    int m=0, n=0, x1=0, y1=0, x2=0, y2=0;

    boolean drop1 = true, drop2 = false, pointsStored = false;
    boolean drop1Enable = false, drop2Enable = false;
    private double count;

    public CloudSnowView(Context context) {
        super(context);
        init();
    }

    public CloudSnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CloudSnowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        count = 0;

        paint = new Paint();
        paint1 = new Paint();

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);

        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(5);
        paint1.setStyle(Paint.Style.STROKE);

        cloudPath = new Path();
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;
        X = screenW/2;
        Y = (screenH/2);

        cloudPath.moveTo(X, Y);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        cloudPath = new Path();
        count = count+0.5;

        int retval = Double.compare(count, 360.00);

        if(retval > 0) {

        }
        else if(retval < 0) {

        }
        else {
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

        cloudPath.moveTo(X1,Y1);
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

        cloudPath.moveTo(X1,Y1);
        cloudPath.cubicTo(P1c1.x,P1c1.y,P1c2.x,P1c2.y,P1X,P1Y);
        cloudPath.cubicTo(P2c1.x,P2c1.y,P2c2.x,P2c2.y,P2X,P2Y);
        cloudPath.cubicTo(P3c1.x,P3c1.y,P3c2.x,P3c2.y,P3X,P3Y);
        cloudPath.cubicTo(P4c1.x,P4c1.y,P4c2.x,P4c2.y,P4X,P4Y);
        cloudPath.cubicTo(P5c1.x,P5c1.y,P5c2.x,P5c2.y,X1,Y1);

        //fill cloud with white color
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(cloudPath, paint);

        //draw stroke with back color
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(cloudPath, paint);

        if(x1==0) {
            x1 = (int) P1c2.x + 10;
        }
        if(y1==0) {
            float value = (int) P1c2.y-((P1c1.y+P1Y)/2);
            y1 = (int) (P1c2.y-value/2) + 5;
        }

        if(x2==0) {
            x2 = (int) P2c2.x + 10;
        }
        if(y2==0) {
            float value = (int) P2c2.y-((P2c1.y+P2Y)/2);
            y2 = (int) (P2c2.y-value/2) + 5;
        }

        if(!pointsStored) {
            cubicPath11 = new Path();
            cubicPath11.moveTo(x1, y1);
            cubicPath11.cubicTo(x1-10, y1+20, x1-20, y1+40, x1-30, y1+60);
            pathPoints11 = getPoints(cubicPath11);

            cubicPath21 = new Path();
            int x = x1-5;
            cubicPath21.moveTo(x, y1);
            cubicPath21.cubicTo(x+10, y1+20, x+15, y1+40, x-5, y1+60);
            pathPoints21 = getPoints(cubicPath21);

            cubicPath12 = new Path();
            cubicPath12.moveTo(x2, y2);
            cubicPath12.cubicTo(x2+10, y2+20, x2+20, y2+40, x2+30, y2+60);
            pathPoints12 = getPoints(cubicPath12);

            cubicPath22 = new Path();
            int xx= x2+5;
            cubicPath22.moveTo(xx, y2);
            cubicPath22.cubicTo(xx-10, y2+20, xx-15, y2+40, xx+5, y2+60);
            pathPoints22 = getPoints(cubicPath22);

            pointsStored = true;
        }

        if(drop1) {

            //1st drop
            path11 = new Path();
            path12 = new Path();
            path13 = new Path();

            path11.moveTo(pathPoints11[m].getX(), pathPoints11[m].getY()+2);
            path12.moveTo(pathPoints11[m].getX()-15, pathPoints11[m].getY()+2);
            path13.moveTo(pathPoints11[m].getX() - 7, pathPoints11[m].getY() - 2);

            path11.lineTo(pathPoints11[m].getX()-15, (pathPoints11[m].getY()+13));
            path12.lineTo(pathPoints11[m].getX(), (pathPoints11[m].getY() + 13));
            path13.lineTo(pathPoints11[m].getX() - 8, (pathPoints11[m].getY() + 17));

            canvas.drawPath(path11, paint1);
            canvas.drawPath(path12, paint1);
            canvas.drawPath(path13, paint1);

            m = m+1;

            if(m > 75) {

                //2nd drop
                path21 = new Path();
                path22 = new Path();
                path23 = new Path();

                path21.moveTo(pathPoints21[n].getX()-5, pathPoints21[n].getY()+2);
                path22.moveTo(pathPoints21[n].getX()-20, pathPoints21[n].getY()+2);
                path23.moveTo(pathPoints21[n].getX() - 12, pathPoints21[n].getY() - 2);

                path21.lineTo(pathPoints21[n].getX() - 20, (pathPoints21[n].getY() + 13));
                path22.lineTo(pathPoints21[n].getX()-5, (pathPoints21[n].getY() + 13));
                path23.lineTo(pathPoints21[n].getX() - 13, (pathPoints21[n].getY() + 17));

                n = n+1;

                canvas.drawPath(path21, paint1);
                canvas.drawPath(path22, paint1);
                canvas.drawPath(path23, paint1);
            }

            if(m==100) {
                m=0;
                path11.reset();
                path11.moveTo(0, 0);
                path12.reset();
                path12.moveTo(0, 0);
                path13.reset();
                path13.moveTo(0, 0);

                canvas.clipPath(path11);
                canvas.clipPath(path12);
                canvas.clipPath(path13);

                x1=0;
                y1=0;

                x2=0;
                y2=0;

                drop1Enable = true;
                drop1 = false;

            }
        }

        if(drop1Enable) {

            path21 = new Path();
            path22 = new Path();
            path23 = new Path();

            path21.moveTo(pathPoints21[n].getX()-5, pathPoints21[n].getY()+2);
            path22.moveTo(pathPoints21[n].getX()-20, pathPoints21[n].getY()+2);
            path23.moveTo(pathPoints21[n].getX() - 12, pathPoints21[n].getY() - 2);

            path21.lineTo(pathPoints21[n].getX() - 20, (pathPoints21[n].getY() + 13));
            path22.lineTo(pathPoints21[n].getX()-5, (pathPoints21[n].getY() + 13));
            path23.lineTo(pathPoints21[n].getX() - 13, (pathPoints21[n].getY() + 17));

            canvas.drawPath(path21, paint1);
            canvas.drawPath(path22, paint1);
            canvas.drawPath(path23, paint1);

            n = n+1;

            if(n==100) {
                m=0;
                n=0;
                path21.reset();
                path21.moveTo(0, 0);
                path22.reset();
                path22.moveTo(0, 0);
                path23.reset();
                path23.moveTo(0, 0);

                canvas.clipPath(path21);
                canvas.clipPath(path22);
                canvas.clipPath(path23);

                x1=0;
                y1=0;

                drop2 = true;
                drop1 = false;
                drop1Enable = false;

            }

        }

        if(drop2) {
            path11 = new Path();
            path12 = new Path();
            path13 = new Path();

            path11.moveTo(pathPoints12[m].getX(), pathPoints12[m].getY()+2);
            path12.moveTo(pathPoints12[m].getX()-15, pathPoints12[m].getY()+2);
            path13.moveTo(pathPoints12[m].getX() - 7, pathPoints12[m].getY() - 2);

            path11.lineTo(pathPoints12[m].getX() - 15, (pathPoints12[m].getY() + 13));
            path12.lineTo(pathPoints12[m].getX(), (pathPoints12[m].getY() + 13));
            path13.lineTo(pathPoints12[m].getX() - 8, (pathPoints12[m].getY() + 17));

            canvas.drawPath(path11, paint1);
            canvas.drawPath(path12, paint1);
            canvas.drawPath(path13, paint1);

            m=m+1;

            if(m > 75) {

                //2nd drop
                path21 = new Path();
                path22 = new Path();
                path23 = new Path();

                path21.moveTo(pathPoints22[n].getX()-5, pathPoints22[n].getY()+2);
                path22.moveTo(pathPoints22[n].getX()-20, pathPoints22[n].getY()+2);
                path23.moveTo(pathPoints22[n].getX() - 12, pathPoints22[n].getY() - 2);

                path21.lineTo(pathPoints22[n].getX() - 20, (pathPoints22[n].getY() + 13));
                path22.lineTo(pathPoints22[n].getX()-5, (pathPoints22[n].getY() + 13));
                path23.lineTo(pathPoints22[n].getX() - 13, (pathPoints22[n].getY() + 17));

                n = n+1;

                canvas.drawPath(path21, paint1);
                canvas.drawPath(path22, paint1);
                canvas.drawPath(path23, paint1);
            }

            if(m==100) {
                m=0;
                path11.reset();
                path11.moveTo(0, 0);
                path12.reset();
                path12.moveTo(0, 0);
                path13.reset();
                path13.moveTo(0, 0);

                canvas.clipPath(path11);
                canvas.clipPath(path12);
                canvas.clipPath(path13);

                x1=0;
                y1=0;

                drop2Enable = true;
                drop2 = false;
            }

        }

        if(drop2Enable) {

            path21 = new Path();
            path22 = new Path();
            path23 = new Path();

            path21.moveTo(pathPoints22[n].getX()-5, pathPoints22[n].getY()+2);
            path22.moveTo(pathPoints22[n].getX()-20, pathPoints22[n].getY()+2);
            path23.moveTo(pathPoints22[n].getX() - 12, pathPoints22[n].getY() - 2);

            path21.lineTo(pathPoints22[n].getX() - 20, (pathPoints22[n].getY() + 13));
            path22.lineTo(pathPoints22[n].getX()-5, (pathPoints22[n].getY() + 13));
            path23.lineTo(pathPoints22[n].getX() - 13, (pathPoints22[n].getY() + 17));

            canvas.drawPath(path21, paint1);
            canvas.drawPath(path22, paint1);
            canvas.drawPath(path23, paint1);

            n = n+1;

            if(n==100) {
                m=0;
                n=0;
                path21.reset();
                path21.moveTo(0, 0);
                path22.reset();
                path22.moveTo(0, 0);
                path23.reset();
                path23.moveTo(0, 0);

                canvas.clipPath(path21);
                canvas.clipPath(path22);
                canvas.clipPath(path23);

                x1=0;
                y1=0;

                drop1 = true;
                drop1Enable = false;
                drop2 = false;
                drop2Enable = false;

            }

        }

        invalidate();

    }

    private PointF calculateTriangle(float x1, float y1, float x2, float y2, boolean left, double count) {
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

    private PathPoints[] getPoints(Path path) {
        PathPoints[] pointArray = new PathPoints[100];
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        float distance = 0f;
        float speed = length / 100;
        int counter = 0;
        float[] aCoordinates = new float[2];

        while ((distance < length) && (counter < 100)) {
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


