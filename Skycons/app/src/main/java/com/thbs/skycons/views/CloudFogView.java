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

    Paint paintCloud, paintFog;
    Path npth1,npth,secpath1,secpath2;
    boolean expanding = false;
    boolean moving = true;

    float ctr = 0;
    float i,j;
    private int screenW, screenH;
    Boolean check;
    private float X, Y;
    private Path pathCloud, path1, path2;
    private double count;
    boolean compress = false;
    float line1Y = 0, line2Y = 0,lineStartX, lineEndX;

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
        i=0f;
        j=(int)0.5;
        check = false;
        npth1 = new Path();
        npth = new Path();
        secpath1 = new Path();
        secpath2 = new Path();
        paintCloud = new Paint();
        paintFog = new Paint();

        //Setting paint for cloud
        paintCloud.setColor(Color.BLACK);
        paintCloud.setAntiAlias(true);
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setShadowLayer(0, 0, 0, Color.BLACK);

        //Setting paint for fog
        paintFog.setColor(Color.BLACK);
        paintFog.setAntiAlias(true);
        paintFog.setStrokeCap(Paint.Cap.ROUND);
        paintFog.setStrokeJoin(Paint.Join.ROUND);
        paintFog.setStyle(Paint.Style.FILL_AND_STROKE);
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

        pathCloud.moveTo(X, Y);

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paintCloud.setStrokeWidth((float)(0.02083*screenW));
        paintFog.setStrokeWidth((float)(0.02083*screenW));

        pathCloud = new Path();
        path1 = new Path();
        path2 = new Path();

        count = count+0.5;

        int retval = Double.compare(count, 360.00);

        if(retval == 0) {
            count = 0;
        }

        //different radius values for the cloud coordinates
        int r1 = (int)(0.1875 * screenW);
        int r2 = (int)(0.1041667 * screenW);
        double offset = 0.00023125 * screenW;

        // cloud coordinates from the center of the screen
        float X1 = (float)(r1 * Math.cos(Math.toRadians(0+(0.222*count))) + X); //x value of coordinate 1 at radius r1 from center of Screen and angle incremented with counter
        float Y1 = ((float)(r2 * Math.sin(Math.toRadians(0+(0.222*count))) + Y));//y value of coordinate 1 at radius r2 from center of Screen and angle incremented with counter
        float P1X = (float)(r1 * Math.cos(Math.toRadians(80+(0.111*count))) + X);//x value of coordinate 2 at radius r1 from center of Screen and angle incremented with counter
        float P1Y = ((float)(r2 * Math.sin(Math.toRadians(80+(0.111*count))) + Y));//y value of coordinate 2 at radius r2 from center of Screen and angle incremented with counter
        float P2X = (float)(r1 * Math.cos(Math.toRadians(120+(0.222*count))) + X);//x value of coordinate 3 at radius r1 from center of Screen and angle incremented with counter
        float P2Y = ((float)((r2+(offset*count)) * Math.sin(Math.toRadians(120+(0.222*count))) + Y));//y value of coordinate 3 at varying radius from center of Screen and angle incremented with counter
        float P3X = (float)(r1 * Math.cos(Math.toRadians(200+(0.222*count))) + X);//x value of coordinate 4 at radius r1 from center of Screen and angle incremented with counter
        float P3Y = ((float)(r1 * Math.sin(Math.toRadians(200+(0.222*count))) + Y));//y value of coordinate 4 at radius r1 from center of Screen and angle incremented with counter
        float P4X =(float)(r1 * Math.cos(Math.toRadians(280+(0.222*count))) + X);//x value of coordinate 5 at radius r1 from center of Screen and angle incremented with counter
        float P4Y = ((float)(r1 * Math.sin(Math.toRadians(280+(0.222*count))) + Y));//y value of coordinate 5 at radius r1 from center of Screen and angle incremented with counter

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

        if(line1Y == 0) {
            line1Y = P1c2.y + (float)(0.1042 * screenW);   //Calculating Y coordinate for foglines.
            line2Y = P1c2.y + (float)(0.15625 * screenW);

            lineStartX = (float)(X-X*50.0/100.0);  //Calculating X coordinate for foglines.
            lineEndX = (float) (X+X*50.0/100);
        }


        float temp = (lineEndX-lineStartX)*(float)95.0/(float)100; //Calculating fogline length

        path1.moveTo(lineStartX,line1Y);
        path1.lineTo(lineStartX+temp,line1Y);

        path2.moveTo(lineEndX,line2Y);
        path2.lineTo(lineEndX-temp,line2Y);

        //Code to move foglines from one point to another

        if(moving&&(lineStartX+temp+ctr)<=lineEndX) {

            path1.reset();
            path1.moveTo(lineStartX+ctr+i,line1Y);
            path1.lineTo(lineStartX+ctr+temp+i,line1Y);

            path2.reset();
            path2.moveTo(lineEndX-ctr+i+i,line2Y);
            path2.lineTo(lineEndX-ctr-temp+i-i,line2Y);

            ctr = ctr+(float)0.5;
            if((lineStartX+temp+ctr)>lineEndX) {
                expanding = true;
                moving = false;
            }
        }


        //Code to expand foglines

        if(expanding) {

            if(i<=5f) {
                i=i+0.1f;
                path1.reset();
                path1.moveTo(lineStartX+ctr+temp+i,line1Y);
                path1.lineTo(lineStartX+ctr-i,line1Y);

                path2.reset();
                path2.moveTo(lineEndX-ctr-temp+i,line2Y);
                path2.lineTo(lineEndX-ctr-i,line2Y);


            } else {
                //Moving the fogline to the other end after expanding

                path1.reset();
                path1.moveTo(lineStartX + ctr +temp + i, line1Y);
                path1.lineTo(lineStartX + ctr - i , line1Y);

                path2.reset();
                path2.moveTo(lineEndX-ctr-temp+i,line2Y);
                path2.lineTo(lineEndX-ctr-i,line2Y);

                ctr = ctr - 0.2f;
                if (lineStartX + ctr <= lineStartX) {
                    expanding = false;
                    compress = true;
                    ctr = 0.0f;
                }
            }
        }


        //Compressing the fogline to normal length
        if(compress) {

            if(i>0.0f) {
                i = i - 0.1f;
                path1.reset();
                path1.moveTo(lineStartX + ctr - i, line1Y);
                path1.lineTo(lineStartX + ctr + temp + i, line1Y);

                path2.reset();
                path2.moveTo(lineEndX-ctr-i,line2Y);
                path2.lineTo(lineEndX-ctr-temp+i,line2Y);

            } else {
                compress = false;
                moving = true;
            }

        }


        canvas.drawPath(path1,paintFog);
        canvas.drawPath(path2,paintFog);


        invalidate();

    }

    private PointF calculateTriangle(float x1, float y1, float x2, float y2, boolean left) {

        PointF result = new PointF(0,0);
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle;
        float sideDist = (float)0.5 * (float) Math.sqrt(dx * dx + dy * dy);

        if (left) {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI /3f));
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);

        } else {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI /1.5f));
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }

        return result;

    }


}