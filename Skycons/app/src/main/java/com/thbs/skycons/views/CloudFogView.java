package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by administrator on 12/09/14.
 */
public class CloudFogView extends View {

    private static Paint paintCloud, paintFog;
    private int screenW, screenH;
    private float X, Y, L1=0, H1=0, L2=0, H2=0;
    private Path pathCloud, path1, path2;
    private double count;
    boolean move = true, compress = false, expand = false;
    float m=0;
    float backupL11, backupL12, backupL21, backupL22;

    public CloudFogView(Context context) {
        super(context);
        init();
    }

    public CloudFogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CloudFogView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        count = 0;

        paintCloud = new Paint();
        paintFog = new Paint();

        paintCloud.setColor(Color.BLACK);
        paintCloud.setStrokeWidth(10);
        paintCloud.setAntiAlias(true);
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setShadowLayer(0, 0, 0, Color.BLACK);

        paintFog.setColor(Color.BLACK);
        paintFog.setStrokeWidth(10);
        paintFog.setAntiAlias(true);
        paintFog.setStrokeCap(Paint.Cap.ROUND);
        paintFog.setStrokeJoin(Paint.Join.ROUND);
        paintFog.setStyle(Paint.Style.STROKE);
        paintFog.setShadowLayer(0, 0, 0, Color.BLACK);

        pathCloud = new Path();
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;

        X = screenW/2;
        Y = (screenH/2);

        if(L2 == 0) {
            L1 =  screenW * 0.2f;
            H1 =  screenH * 0.88f;

            L2 =  screenW * 0.78f;
            H2 =  screenH * 0.98f;

        }

        pathCloud.moveTo(X, Y);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        pathCloud = new Path();
        path1 = new Path();
        path2 = new Path();

        count = count+0.5;

        int retval = Double.compare(count, 360.00);

        if(retval > 0) {

        } else if(retval < 0) {

        } else {
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

        pathCloud.moveTo(X1,Y1);
        PointF P1c1 = calculateTriangle(X1, Y1, P1X, P1Y, true);
        PointF P1c2 = calculateTriangle(X1, Y1, P1X, P1Y, false);
        PointF P2c1 = calculateTriangle(P1X, P1Y, P2X, P2Y, true);
        PointF P2c2 = calculateTriangle(P1X, P1Y, P2X, P2Y, false);
        PointF P3c1 = calculateTriangle(P2X, P2Y, P3X, P3Y, true);
        PointF P3c2 = calculateTriangle(P2X, P2Y, P3X, P3Y, false);
        PointF P4c1 = calculateTriangle(P3X, P3Y, P4X, P4Y, true);
        PointF P4c2 = calculateTriangle(P3X, P3Y, P4X, P4Y, false);
        PointF P5c1 = calculateTriangle(P4X, P4Y, X1, Y1, true);
        PointF P5c2 = calculateTriangle(P4X, P4Y, X1,Y1, false);

        pathCloud.moveTo(X1,Y1);
        pathCloud.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, P1X, P1Y);
        pathCloud.cubicTo(P2c1.x, P2c1.y, P2c2.x, P2c2.y, P2X, P2Y);
        pathCloud.cubicTo(P3c1.x, P3c1.y, P3c2.x, P3c2.y, P3X, P3Y);
        pathCloud.cubicTo(P4c1.x, P4c1.y, P4c2.x, P4c2.y, P4X, P4Y);
        pathCloud.cubicTo(P5c1.x, P5c1.y, P5c2.x, P5c2.y, X1, Y1);

        canvas.drawPath(pathCloud, paintCloud);


        path1 = new Path();
        path2 = new Path();

        if(move) {

            path1.moveTo((L1+25)+m*0.15f, H1);
            path1.lineTo((L2-25)+m*0.15f, H1);
            canvas.drawPath(path1, paintFog);

            path2.moveTo((L1+25)-m*0.15f, H2);
            path2.lineTo((L2-25)-m*0.15f, H2);
            canvas.drawPath(path2, paintFog);

            if(m==50) {
               compress = true;
               move = false;

               backupL11 = (L1+25)+m*0.15f;
               backupL12 = (L2-25)+m*0.15f;

               backupL21 = (L1+25)-m*0.15f;
               backupL22 = (L2-25)-m*0.15f;

               m=0;
            }

        }

        if(compress) {

            path1.moveTo(backupL11+m*0.1f, H1);
            path1.lineTo(backupL12-m*0.3f, H1);
            canvas.drawPath(path1, paintFog);

            path2.moveTo(backupL21-m*0.3f, H2);
            path2.lineTo(backupL22+m*0.1f, H2);
            canvas.drawPath(path2, paintFog);

            if(m==50) {
                expand = true;
                move = false;
                compress = false;

                backupL11 = backupL11+m*0.1f;
                backupL12 = backupL12-m*0.3f;

                backupL21 = backupL21-m*0.3f;
                backupL22 = backupL22+m*0.1f;

                m=0;
            }

        }

        if(expand) {

            path1.moveTo(backupL11-m*0.15f-m/10, H1);
            path1.lineTo(backupL12+m*0.3f-m/10, H1);
            canvas.drawPath(path1, paintFog);

            path2.moveTo(backupL21+m*0.3f-m/10, H2);
            path2.lineTo(backupL22, H2);
            canvas.drawPath(path2, paintFog);

            if(m==50) {
                move = true;
                compress = false;
                expand = false;

                m=0;
            }

        }


        m = m + 0.5f;

        invalidate();

    }

    private PointF calculateTriangle(float x1, float y1, float x2, float y2, boolean left) {

        PointF result = new PointF(0,0);
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle = (float) ((Math.atan2(dy, dx) - Math.PI /2f));
        float sideDist = (float)0.5 * (float) Math.sqrt(dx * dx + dy * dy);

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
