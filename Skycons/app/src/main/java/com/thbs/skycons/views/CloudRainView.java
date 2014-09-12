package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by administrator on 09/09/14.
 */
public class CloudRainView extends View {

    private static Paint paint, paint1;
    private int screenW, screenH;
    private float X, Y;
    private Path path, path1, path2, path3;
    int m=0, x1=0, y1=0, x2=0, y2=0, x3=0, y3=0;
    boolean drop1 = true, drop2 = false, drop3 = false;
    private double count;

    public CloudRainView(Context context) {
        super(context);
        init();
    }

    public CloudRainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CloudRainView(Context context, AttributeSet attrs, int defStyle) {
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
        paint1.setStrokeWidth(8);
        paint1.setStyle(Paint.Style.FILL_AND_STROKE);

        path = new Path();
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;
        X = screenW/2;
        Y = (screenH/2);

        path.moveTo(X, Y);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path = new Path();
        path1 = new Path();
        path2 = new Path();
        path3 = new Path();

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

        path.moveTo(X1,Y1);
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

        path.moveTo(X1,Y1);
        path.cubicTo(P1c1.x,P1c1.y,P1c2.x,P1c2.y,P1X,P1Y);
        path.cubicTo(P2c1.x,P2c1.y,P2c2.x,P2c2.y,P2X,P2Y);
        path.cubicTo(P3c1.x,P3c1.y,P3c2.x,P3c2.y,P3X,P3Y);
        path.cubicTo(P4c1.x,P4c1.y,P4c2.x,P4c2.y,P4X,P4Y);
        path.cubicTo(P5c1.x,P5c1.y,P5c2.x,P5c2.y,X1,Y1);

        canvas.drawPath(path, paint);

        if(drop1) {

            path1 = new Path();

            if(x1==0) {
                x1 = (int) P1c2.x + 10;
            }
            if(y1==0) {
                float value = (int) P1c2.y-((P1c1.y+P1Y)/2);
                y1 = (int) (P1c2.y-value/2) + 10;
            }

            path1.moveTo(x1, y1);

            path1.addArc(new RectF(x1-5, (y1-5)+m, x1+5, y1+5+m), 180, -180);
            path1.lineTo(x1, (y1-10)+m);
            path1.close();

            canvas.drawPath(path1, paint1);

            m = m+2;
            if(m==100) {
                m=0;
                path1.reset();
                path1.moveTo(0, 0);
                canvas.clipPath(path1);
                drop2 = true;
                drop1 = false;
                drop3 = false;
            }
        }

        if(drop2) {
            path1 = new Path();

            if(x2==0) {
                x2 = (int) P2c2.x + 10;
            }
            if(y2==0) {
                float value = (int) P2c2.y-((P2c1.y+P2Y)/2);
                y2 = (int) (P2c2.y-value/2) + 10;
            }

            path1.moveTo(x2, y2);

            path1.addArc(new RectF(x2-5, (y2-5)+m, x2+5, y2+5+m), 180, -180);
            path1.lineTo(x2, (y2-10)+m);
            path1.close();

            canvas.drawPath(path1, paint1);

            m = m+2;
            if(m==100) {
                m=0;
                path1.reset();
                path1.moveTo(0, 0);
                canvas.clipPath(path1);
                drop1 = false;
                drop2 = false;
                drop3 = true;
            }
        }

        if(drop3) {
            path1 = new Path();

            if(x3==0) {
                x3 = (x1+x2)/2 + 10;
            }
            if(y3==0) {
                y3 = (y1+y2)/2;
            }

            path1.moveTo(x3, y3);

            path1.addArc(new RectF(x3-5, (y3-5)+m, x3+5, y3+5+m), 180, -180);
            path1.lineTo(x3, (y3-10)+m);
            path1.close();

            canvas.drawPath(path1, paint1);


            m = m+2;
            if(m==100) {
                m=0;
                path1.reset();
                path1.moveTo(0, 0);
                drop1 = true;
                drop2 = false;
                drop3 = false;
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

}
