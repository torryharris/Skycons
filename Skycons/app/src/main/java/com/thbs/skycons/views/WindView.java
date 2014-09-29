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
 * Created by administrator on 18/09/14.
 */

public class WindView extends View {

    private static Paint paint;
    private int screenW, screenH;
    private float X, Y, X1, Y1, X2, Y2, X3, Y3,X4,Y4,X5,Y5,X6,Y6, X11, Y11, Y21, X21, Xc, Yc;
    private Path tracePath, windPath, leafPath;
    private double count;
    int degrees;
    boolean isFirstPath;
    PathPoints[] points;


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

        isFirstPath = true;
        windPath = new Path();
        leafPath = new Path();
        tracePath = new Path();
        count = 0;
        degrees = 200;
        paint = new Paint();

        paint.setColor(Color.BLACK);
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

        paint.setStrokeWidth(screenW/50);
        count+=1;
        windPath = new Path();
        leafPath = new Path();

        int retval = Double.compare(count, 270.00);

        if (retval > 0) {
            tracePath= new Path();
            count =0 ;
            degrees = 200;
            isFirstPath = !isFirstPath;
        }

        if (isFirstPath) {
            X = 0;
            Y = (float) (screenH / 1.5);
            X2 = (float) (screenW + 50);
            Y2 = (float) (screenH/4);

            tracePath.moveTo(X,Y);
            PointF P1c1 = calculateTriangle(X, Y, X2, Y2, true, 0.2,"CCW");
            PointF P1c2 = calculateTriangle(X, Y, X2, Y2, false, 0.2,"CCW");

            tracePath.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, X2, Y2);

            points = getPoints(tracePath);

        } else {


            X = 0;
            Y = (float) (screenH / 1.5);

            X1 = (float) (screenW / 2);
            Y1 = (float) (screenH / 3);

            X2 = (float) ((screenW / 2) -20);
            Y2 = (float) ((screenH / 3)- 40);

            X3 = (float) ((screenW / 2)-40 );
            Y3 = (float) ((screenH / 3)+10);

//            X4 = (float) ((screenW / 2) +30);
//            Y4 = (float) ((screenH / 3 ) -40);
//
//            X5 = (float) ((screenW / 2)+50);
//            Y5 = (float) ((screenH / 3)-40);
//
            X6 = (float) (screenW + 50);
           Y6 = (float) (screenH / 2);

            tracePath.moveTo(X - 5, Y);
            tracePath.cubicTo(X + 70, Y + 10, X + 110, Y - 40, X + 80, Y - 70);
            tracePath.cubicTo(X + 50, Y - 80, X + 30, Y - 40, X + 100, Y);
            tracePath.cubicTo(X + 140, Y + 25, X + 190, Y - 25, X6, Y6);

//            tracePath.moveTo(X,Y);
//            PointF P1c1 = calculateTriangle(X, Y, X1, Y1, true, 0.2,"CCW");
//            PointF P1c2 = calculateTriangle(X, Y, X1, Y1, false, 0.2,"CCW");
//            PointF P3c1 = calculateTriangle(X1, Y1, X2, Y2, true, 0.1,"CCW");
//            PointF P3c2 = calculateTriangle(X1, Y1, X2, Y2, false, 0.1,"CCW");
//            PointF P4c1 = calculateTriangle(X2, Y2, X3, Y3, true, 0.1,"CCW");
//            PointF P4c2 = calculateTriangle(X2, Y2, X3, Y3, false, 0.1,"CCW");
//            PointF P2c1 = calculateTriangle(X3, Y3, X6, Y6, true, 0.4,"CCW");
//            PointF P2c2 = calculateTriangle(X3, Y3, X6, Y6, false, 0.4,"CCW");
//            tracePath.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, X1, Y1);
//            //tracePath.cubicTo(P3c1.x, P3c1.y, P3c2.x, P3c2.y, X4, Y4);
//
//            //tracePath.cubicTo(P4c1.x, P4c1.y, P4c2.x, P4c2.y, X3, Y3);
//            tracePath.quadTo(X2,Y2,X3,Y3);
////            tracePath.quadTo(X4,Y4,X5,Y5);
//            tracePath.cubicTo(P2c1.x, P2c1.y, P2c2.x, P2c2.y, X6, Y6);
//
//            tracePath.lineTo(X6,Y6);
            points = getPoints(tracePath);

        }

        if (count <= 20) {



        } else if ((count >20)&&(count <= 80)) {


            for (int i = 0; i < (count - 20); i++) {

                windPath.moveTo(points[i].getX(), points[i].getY());
                windPath.lineTo(points[i + 1].getX(), points[i + 1].getY());

            }
        } else if (count >= 249) {

           // int i = (int)count - 20;

            System.out.println("count value:"+count);
            for (int i = (int)count - 20 ; i <= 248; i++) {

                System.out.println("i value:"+i);
                windPath.moveTo(points[i].getX(), points[i].getY());
                windPath.lineTo(points[i + 1].getX(), points[i + 1].getY());

            }

        } else {

            for (int i = (int) (count - 80); i < (count - 20); i++) {

                windPath.moveTo(points[i].getX(), points[i].getY());
                windPath.lineTo(points[i + 1].getX(), points[i + 1].getY());

            }

        }



        canvas.drawPath(windPath, paint);

        if((int) count <250 ) {
            Xc = points[(int) (count)].getX();
            Yc = points[(int) (count)].getY();
            X11 = (float) (((screenW * 8) / 100) * Math.cos(Math.toRadians((degrees + count) - 30)) + Xc);
            Y11 = (float) (((screenW * 8) / 100) * Math.sin(Math.toRadians((degrees + count) - 30)) + Yc);
            X21 = (float) (((screenW * 20) / 100) * Math.cos(Math.toRadians((degrees + count))) + Xc);
            Y21 = (float) (((screenW * 20) / 100) * Math.sin(Math.toRadians((degrees + count))) + Yc);


            PointF P11c1 = calculateTriangle(Xc, Yc, X21, Y21, true, 0.7,"CW");
            PointF P11c2 = calculateTriangle(Xc, Yc, X21, Y21, false, 0.7,"CW");
            PointF P21c1 = calculateTriangle(X21, Y21, X11, Y11, true, 0.8,"CW");
            PointF P21c2 = calculateTriangle(X21, Y21, X11, Y11, false, 0.8,"CW");
            PointF P31c1 = calculateTriangle(X11, Y11, Xc, Yc, true, 0.2,"CCW");
            PointF P31c2 = calculateTriangle(X11, Y11, Xc, Yc, false, 0.2,"CCW");

            leafPath.moveTo(Xc, Yc);
            leafPath.cubicTo(P11c1.x, P11c1.y, P11c2.x, P11c2.y, X21, Y21);
            leafPath.cubicTo(P21c1.x, P21c1.y, P21c2.x, P21c2.y, X11, Y11);
            leafPath.cubicTo(P31c1.x, P31c1.y, P31c2.x, P31c2.y, Xc, Yc);

            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(leafPath, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawPath(leafPath, paint);
        }

        invalidate();
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
    private PointF calculateTriangle(float x1, float y1, float x2, float y2, boolean left, double distOffset,String dir) {
        PointF result = new PointF(0, 0);
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle = 0;
        float sideDist = 0;

        if (dir == "CW") {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI / 2f));
            sideDist = (float) distOffset * (float) Math.sqrt(dx * dx + dy * dy); //square
        } else if (dir == "CCW") {
            dangle = (float) ((Math.atan2(dy, dx) + Math.PI / 2f));
            sideDist = (float) distOffset * (float) Math.sqrt(dx * dx + dy * dy); //square
        }

        if (left) {
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);
        } else {
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }
        return result;
    }

}
