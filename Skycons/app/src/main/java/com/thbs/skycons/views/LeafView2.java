package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 18/09/14.
 */
public class LeafView2 extends View {

    private int screenW, screenH;
    private float X, Y, X1, Y1, X2, Y2;
    private Path path, path1, path2, pointsPath1, pointsPath2;
    double degrees;
    boolean isDrawn;
    List<Point> points;
    PathPoints[] points1, points2;
    float m = 0, n = 0;
    float radius;
    private double count;
    boolean one = true, two = false, three = false, four = false;
    Paint paint, paint1, paint2;

    public LeafView2(Context context) {
        super(context);
        init();
    }

    public LeafView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeafView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {

        isDrawn=false;
        path1 = new Path();
        degrees = 0;
        count = 0;
        paint = new Paint();
        points = new ArrayList<Point>();

        count = 0;

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);

        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(10);
        paint1.setStyle(Paint.Style.STROKE);

        paint2 = new Paint();
        paint2.setAntiAlias(true);
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
        path1.quadTo(X + w * 0.5f, Y + 50, X + w * 0.8f, Y - 20);
        path1.quadTo(X + w * 0.82f, Y - 30, X + w * 1.1f, Y - 40);

        path2.moveTo(X - 5, Y);
        path2.cubicTo(X + w * 0.5f, Y + 30, X + w * 0.7f, Y - 70, X + w * 0.4f, Y - 100);
        path2.cubicTo(X + w * 0.35f, Y - 90, X + w * 0.12f, Y - 40, X + w * 0.5f, Y);
        path2.cubicTo(X + w * 0.7f, Y - 25, X + w * 1.0f, Y - 25, X + w * 1.2f, Y - 10);

        points1 = getPoints(path1);
        points2 = getPoints(path2);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //count = (float) (count + 1);

        int retval = Double.compare(count, 270.00);

        if (retval > 0) {
            count = 0;
        }

       // canvas.drawPath(path2, paint1);

        if (one) {

            if (m == 0) {
                pointsPath1 = new Path();
                pointsPath2 = new Path();
            }

            if (m < points1.length - 2) {

                X = points1[(int) m].getX() + 50;
                Y = points1[(int) m].getY() - 50;
                X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X );
                Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
                X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
                Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);

                PointF P1c1 = calculateTriangle(X, Y, X2, Y2, true, 0);
                PointF P1c2 = calculateTriangle(X, Y, X2, Y2, false, 0);
                PointF P2c1 = calculateTriangle(X2, Y2, X1, Y1, true, 1);
                PointF P2c2 = calculateTriangle(X2, Y2, X1, Y1, false, 1);
                PointF P3c1 = calculateTriangle(X1, Y1, X, Y, true, 2);
                PointF P3c2 = calculateTriangle(X1, Y1, X, Y, false, 2);

                path = new Path();
                path.moveTo(X,Y);
                path.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, X2, Y2);
                path.cubicTo(P2c1.x,P2c1.y,P2c2.x,P2c2.y,X1,Y1);
                path.cubicTo(P3c1.x,P3c1.y,P3c2.x,P3c2.y,X,Y);

                canvas.drawPath(path, paint1);

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
                count = 0;
                pointsPath1.reset();
                pointsPath2.reset();
                three = true;
                two = false;
                invalidate();

            } else {
                n = n + 1;
                invalidate();
            }

        }


        if (three) {

            if (m == 0) {
                pointsPath1 = new Path();
                pointsPath2 = new Path();
            }

            if (m < points2.length - 2) {


                System.out.println("m = " +m);


//                if(m<70) {
//                    X = points1[(int) m].getX() + 50;
//                    Y = points1[(int) m].getY() - 50;
//                    X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X);
//                    Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
//                    X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
//                    Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
//
//                }
//
//                if(m>70 && m<100) {
//                    X = points1[(int) m].getX() - (m-50);
//                    Y = points1[(int) m].getY() - (m+50);
//                    X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X);
//                    Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
//                    X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
//                    Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
//                }
//
//                if(m > 100 && m < 145) {
//                    X = points1[(int) m].getX() + (m-50);
//                    Y = points1[(int) m].getY() - (m-50);
//                    X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X);
//                    Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
//                    X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
//                    Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
//                }
//
//                if(m>145 && m < 248) {
//                    X = points1[(int) m].getX() + 50;
//                    Y = points1[(int) m].getY() - 50;
//                    X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X);
//                    Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
//                    X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
//                    Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
//                }



//                if(m>60 && m<120) {
//                    X = points1[(int) m].getX() - (10+(m-60));
//                    Y = points1[(int) m].getY() - (50+(m-60));
//                    X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X);
//                    Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
//                    X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
//                    Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
//                }
//
//                if(m>120) {
//                    X = points1[(int) m].getX() + 50;
//                    Y = points1[(int) m].getY() - 50;
//                    X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X);
//                    Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
//                    X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
//                    Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
//                }

//                if((count >= 70) && (count <= 94)) {
//
//                    degrees+=10;
//
//                    X = (float) (Math.cos(Math.toRadians(-((count/70) + ( degrees)))) + (points1[(int) m].getX() + 50));
//                    Y = (float) (Math.sin(Math.toRadians(-((count/70) + (degrees)))) +(points1[(int) m].getY() + 50));
//                    X1 = (float) (20 * Math.cos(Math.toRadians(-(((count/70) + ( degrees)))-30)) + (points1[(int) m].getX()));
//                    Y1 = (float) (20 * Math.sin(Math.toRadians(-(((count/70) + (degrees)))-30)) + (points1[(int) m].getY()));
//                    X2 = (float) (50 * Math.cos(Math.toRadians(-((count/70) + ( degrees)))) + (points1[(int) m].getX()));
//                    Y2 = (float) (50 * Math.sin(Math.toRadians(-((count/70) + (degrees)))) + (points1[(int) m].getY()));
//
//                } else {
//                    degrees=0;
//                    X = points1[(int) m].getX() + 50;
//                    Y = points1[(int) m].getY() - 50;
//                    X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X );
//                    Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
//                    X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
//                    Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
//                }


                X = points1[(int) m].getX() + 50;
                Y = points1[(int) m].getY() - 50;
                X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X);
                Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
                X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
                Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);

                PointF P1c1 = calculateTriangle(X, Y, X2, Y2, true, 0);
                PointF P1c2 = calculateTriangle(X, Y, X2, Y2, false, 0);
                PointF P2c1 = calculateTriangle(X2, Y2, X1, Y1, true, 1);
                PointF P2c2 = calculateTriangle(X2, Y2, X1, Y1, false, 1);
                PointF P3c1 = calculateTriangle(X1, Y1, X, Y, true, 2);
                PointF P3c2 = calculateTriangle(X1, Y1, X, Y, false, 2);

                path = new Path();
                path.moveTo(X,Y);
                path.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, X2, Y2);
                path.cubicTo(P2c1.x,P2c1.y,P2c2.x,P2c2.y,X1,Y1);
                path.cubicTo(P3c1.x,P3c1.y,P3c2.x,P3c2.y,X,Y);

                canvas.drawPath(path, paint1);

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

            if (n < points1.length - 2) {
                canvas.drawPath(pointsPath1, paint1);
                pointsPath2.moveTo(points2[(int) n].getX(), points2[(int) n].getY());
                pointsPath2.lineTo(points2[(int) (n + 1)].getX(), points2[(int) (n + 1)].getY());
                canvas.drawPath(pointsPath2, paint2);
            }

            if (n == points1.length - 10) {
                n = 0;
                m = 0;
                count = 0;
                path.reset();
                pointsPath2.reset();
                pointsPath1.reset();
                three = true;
                four = false;
                invalidate();

            } else {
                n = n + 1;
                invalidate();
            }

        }

//        count = (float) (count + 1);
//
//        int retval = Double.compare(count, 270.00);
//
//        if (retval > 0) {
//            X=0;
//            Y=(float)(screenH/1.5);
//            path1 = new Path();
//            points = new ArrayList<Point>();
//            count = 0;
//            isDrawn = !isDrawn;
//        }
//
//        if(!isDrawn) {
//            X += (screenW / 135);
//            Y -= 0.5;
//            X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X );
//            Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
//            X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
//            Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
//
//        } else {
//
//            if((count >= 70) && (count <= 94)) {
//
//                degrees+=10;
//
//                X = (float) (Math.cos(Math.toRadians(-((count/70) + ( degrees)))) + (X+(screenW/135)));
//                Y = (float) (Math.sin(Math.toRadians(-((count/70) + (degrees)))) +(Y+0.5));
//                X1 = (float) (20 * Math.cos(Math.toRadians(-(((count/70) + ( degrees)))-30)) + (X));
//                Y1 = (float) (20 * Math.sin(Math.toRadians(-(((count/70) + (degrees)))-30)) + (Y));
//                X2 = (float) (50 * Math.cos(Math.toRadians(-((count/70) + ( degrees)))) + (X));
//                Y2 = (float) (50 * Math.sin(Math.toRadians(-((count/70) + (degrees)))) + (Y));
//
//                // System.out.println("X, Y in if , "+X+":"+Y);
//
//            } else {
//                degrees=0;
//                //System.out.println("X, Y in else , "+X+":"+Y);
//                X += (screenW / 135);
//                Y -= 0.5;
//                X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X );
//                Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
//                X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
//                Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
//            }
//
//        }
//
//        PointF P1c1 = calculateTriangle(X, Y, X2, Y2, true, 0);
//        PointF P1c2 = calculateTriangle(X, Y, X2, Y2, false, 0);
//        PointF P2c1 = calculateTriangle(X2, Y2, X1, Y1, true, 1);
//        PointF P2c2 = calculateTriangle(X2, Y2, X1, Y1, false, 1);
//        PointF P3c1 = calculateTriangle(X1, Y1, X, Y, true, 2);
//        PointF P3c2 = calculateTriangle(X1, Y1, X, Y, false, 2);
//
//        path = new Path();
//        path.moveTo(X,Y);
//        path.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, X2, Y2);
//        path.cubicTo(P2c1.x,P2c1.y,P2c2.x,P2c2.y,X1,Y1);
//        path.cubicTo(P3c1.x,P3c1.y,P3c2.x,P3c2.y,X,Y);
//
//        canvas.drawPath(path, paint);
//
//        invalidate();

    }


    private PointF calculateTriangle(float x1, float y1, float x2, float y2, boolean left, double count) {
        PointF result = new PointF(0, 0);
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle ;
        float sideDist = 0;

        if(count ==0 ) {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI / 2f));
            sideDist = (float) 0.7 * (float) Math.sqrt(dx * dx + dy * dy); //square

        } else if(count == 1){
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI / 2f));
            sideDist = (float) 0.8 * (float) Math.sqrt(dx * dx + dy * dy); //square

        } else {
            dangle = (float) ((Math.atan2(dy, dx) + Math.PI / 2f));
            sideDist = (float) 0.2 * (float) Math.sqrt(dx * dx + dy * dy); //square
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
