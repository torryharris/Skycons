package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 18/09/14.
 */

public class WindView extends View {

    private static Paint paint;
    private int screenW, screenH;
    private float X, Y, X1, Y1, X2, Y2;
    private Path path, path1;
    private double count;
    double degrees;
    boolean isDrawn;
    List<Point> points;


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

    public void init() {
        isDrawn=false;
        path1 = new Path();
        degrees = 0;
        count = 0;
        paint = new Paint();
        points = new ArrayList<Point>();


        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setShadowLayer(0, 0, 0, Color.BLACK);
        path = new Path();

    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;

        X=0;
        Y = (float) (screenH /1.5);
        path.moveTo(X, Y);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        count = (float) (count + 1);
       // System.out.println("count: "+count);
        int retval = Double.compare(count, 270.00);

        if (retval > 0) {
            X=0;
            Y=(float)(screenH/1.5);
            path1 = new Path();
            points = new ArrayList<Point>();
            count = 0;
            isDrawn = !isDrawn;
        }

        if(!isDrawn){
            X += (screenW / 135);
            Y -= 0.5;
            X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X );
            Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
            X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
            Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);

        } else {

            if((count >= 70) && (count <= 94)){

                degrees+=10;

                X = (float) (Math.cos(Math.toRadians(-((count/70) + ( degrees)))) + (X+(screenW/135)));
                Y = (float) (Math.sin(Math.toRadians(-((count/70) + (degrees)))) +(Y+0.5));
                X1 = (float) (20 * Math.cos(Math.toRadians(-(((count/70) + ( degrees)))-30)) + (X));
                Y1 = (float) (20 * Math.sin(Math.toRadians(-(((count/70) + (degrees)))-30)) + (Y));
                X2 = (float) (50 * Math.cos(Math.toRadians(-((count/70) + ( degrees)))) + (X));
                Y2 = (float) (50 * Math.sin(Math.toRadians(-((count/70) + (degrees)))) + (Y));

               // System.out.println("X, Y in if , "+X+":"+Y);

            } else {
                degrees=0;
                //System.out.println("X, Y in else , "+X+":"+Y);
                X += (screenW / 135);
                Y -= 0.5;
                X1 = (float) (20 * Math.cos(Math.toRadians(count-30)) + X );
                Y1 = (float) (20 * Math.sin(Math.toRadians(count-30)) + Y);
                X2 = (float) (50 * Math.cos(Math.toRadians(count)) + X);
                Y2 = (float) (50 * Math.sin(Math.toRadians(count)) + Y);
            }

        }

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

        points.add(new Point(Math.round(X2), Math.round(Y2)));

        if(points.size() ==20) {
            path1.moveTo(0, (float) (screenH / 1.5));
            path1.lineTo(points.get(points.size() - 20).x, points.get(points.size() - 20).y);
        }

        if((points.size() >20 ) && (points.size())<60) {
            path1.moveTo(points.get(points.size() - 20).x, points.get(points.size() - 20).y);
            path1.lineTo(points.get(points.size() - 19).x, points.get(points.size() - 19).y);
        }

        if(points.size()== 60 ) {
            path1.moveTo(points.get(points.size() - 20).x, points.get(points.size() - 20).y);
            path1.lineTo(points.get(points.size() - 19).x, points.get(points.size() - 19).y);
        }

        if(points.size() > 60 ) {

            path1 = new Path();

            for(int i=20;i<60;i++) {

                path1.moveTo(points.get(points.size() - i).x, points.get(points.size() - i).y);
                path1.lineTo(points.get(points.size() - (i-1)).x, points.get(points.size() - (i-1)).y);

            }

        }

        canvas.drawPath(path, paint);
        canvas.drawPath(path1, paint);

        invalidate();

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
        }else {
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


}