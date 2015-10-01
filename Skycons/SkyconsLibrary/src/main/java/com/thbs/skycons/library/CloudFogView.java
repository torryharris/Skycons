package com.thbs.skycons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by administrator on 12/09/14.
 */
public class CloudFogView extends SkyconView {

    Paint paintCloud, paintFog;
    boolean expanding = false;
    boolean moving = true;

    float ctr = 0;
    float i,j;
    private int screenW, screenH;
    Boolean check;
    private float X, Y;
    private Path  path1, path2;
    private double count;
    Cloud cloud;
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


    private void init() {

        count = 0;
        i=0f;
        j=(int)0.5;
        check = false;

        if(isStatic)
        {
            isAnimated = false;
        }
        else
        {
            isAnimated = true;
        }
        paintCloud = new Paint();
        paintFog = new Paint();

        //Setting paint for cloud
        paintCloud.setColor(strokeColor);
        paintCloud.setAntiAlias(true);
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setShadowLayer(0, 0, 0, Color.BLACK);

        //Setting paint for fog
        paintFog.setColor(strokeColor);
        paintFog.setAntiAlias(true);
        paintFog.setStrokeCap(Paint.Cap.ROUND);
        paintFog.setStrokeJoin(Paint.Join.ROUND);
        paintFog.setStyle(Paint.Style.FILL_AND_STROKE);
        paintFog.setShadowLayer(0, 0, 0, Color.BLACK);

        cloud = new Cloud();
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;

        X = screenW/2;
        Y = (screenH/2);


    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(bgColor);

        paintCloud.setStrokeWidth((float)(0.02083*screenW));
        paintFog.setStrokeWidth((float)(0.02083*screenW));

        path1 = new Path();
        path2 = new Path();

        count = count+0.5;

        int retval = Double.compare(count, 360.00);

        if(retval == 0) {
            count = 0;
        }


        canvas.drawPath(cloud.getCloud(X,Y,screenW,count), paintCloud);
        PointF P1c2 = cloud.getP1c2(X,Y,screenW,count);

        path1 = new Path();
        path2 = new Path();

        if(line1Y == 0) {
            line1Y = P1c2.y + (float)(0.1042 * screenW);   //Calculating Y coordinate for foglines.
            line2Y = P1c2.y + (float)(0.15625 * screenW);

            lineStartX = (float)(X-X*50.0/100.0);  //Calculating X coordinate for foglines.
            lineEndX = (float) (X+X*50.0/100);
        }


        float temp = (lineEndX-lineStartX)*(float)95.0/(float)100; //Calculating fogline length

        path1.moveTo(lineStartX, line1Y);
        path1.lineTo(lineStartX + temp, line1Y);

        path2.moveTo(lineEndX, line2Y);
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
                if(isStatic)
                {
                    isAnimated = false;
                }

            }

        }


        canvas.drawPath(path1,paintFog);
        canvas.drawPath(path2,paintFog);

        if(isAnimated) {
            invalidate();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (isStatic) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    isAnimated = true;
                    invalidate();
            }

        }

        return true;
    }
}