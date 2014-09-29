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
 * Created by administrator on 12/09/14.
 */
public class CloudFogView extends View {

    private static Paint paintCloud, paintFog;
    Path npth1,npth,secpath1,secpath2;
    static int midPt = 49;
    boolean expanding = false;
    boolean moving = true;
    boolean secMove = false;

    static float ctr = 0;
    static int ctr2 = 99;
    static float i,j;
    private int screenW, screenH;
    PathPoints[] ppts,ppts2;
    Boolean check;
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
        i=0f;
        j=(int)0.5;
        check = false;
        npth1 = new Path();
        npth = new Path();
        secpath1 = new Path();
        secpath2 = new Path();
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


        float line1Y = (float) (Y+ Y*70.0/100.0);
        float line2Y = (float) (Y+Y*85.0/100.0);

        float lineStartX = (float)(X-X*50.0/100.0);

        float lineEndX = (float) (X+X*50.0/100);

        float tempLength =  lineEndX - lineStartX;
        float fogLength = tempLength * (float)80.0/(float)100.0;
        float temp = (lineEndX-lineStartX)*(float)95.0/(float)100;
        float temp2 = lineEndX - (float)100.0;



        path1.moveTo(lineStartX,line1Y);
        path1.lineTo(lineStartX+temp,line1Y);

        path2.moveTo(lineEndX,line2Y);
        path2.lineTo(lineEndX-temp,line2Y);

        if(moving&&(lineStartX+temp+ctr)<=lineEndX)
        {

            path1.reset();
            path1.moveTo(lineStartX+ctr+i,line1Y);
            path1.lineTo(lineStartX+ctr+temp+i,line1Y);

            path2.reset();
            path2.moveTo(lineEndX-ctr+i+i,line2Y);
            path2.lineTo(lineEndX-ctr-temp+i-i,line2Y);

            ctr = ctr+(float)0.1;
            if((lineStartX+temp+ctr)>lineEndX)
            {
                expanding = true;
                moving = false;
            }
        }

        if(expanding)
        {

            if(i<=5f)
            {
                i=i+0.1f;
                path1.reset();
                path1.moveTo(lineStartX+ctr+temp+i,line1Y);
                path1.lineTo(lineStartX+ctr-i,line1Y);

                path2.reset();
                path2.moveTo(lineEndX-ctr-temp+i,line2Y);
                path2.lineTo(lineEndX-ctr-i,line2Y);


            }
            else
            {

                path1.reset();
                path1.moveTo(lineStartX + ctr +temp + i, line1Y);
                path1.lineTo(lineStartX + ctr - i , line1Y);

                path2.reset();
                path2.moveTo(lineEndX-ctr-temp+i,line2Y);
                path2.lineTo(lineEndX-ctr-i,line2Y);

                ctr = ctr - 0.1f;
                if (lineStartX + ctr <= lineStartX) {
                    expanding = false;
                    compress = true;
                    ctr = 0.0f;
                }
            }
        }

        if(compress)
        {

            System.out.println("In compress");
             if(i>0.0f) {
                 i = i - 0.1f;
                 path1.reset();
                 path1.moveTo(lineStartX + ctr - i, line1Y);
                 path1.lineTo(lineStartX + ctr + temp + i, line1Y);

                 path2.reset();
                 path2.moveTo(lineEndX-ctr-i,line2Y);
                 path2.lineTo(lineEndX-ctr-temp+i,line2Y);

             }
            else {
                 compress = false;
                 moving = true;
             }

        }








        PathPoints[] path1ppts = getPoints(path1);
        PathPoints[] path2ppts = getPoints(path2);



       // npth.moveTo(path1ppts[0].getX(),path1ppts[0].getY());


//        if(ctr+20+i<path1ppts.length&&ctr-i>=0&&expanding)
//        {
//            if(ctr+20+i<=path1ppts.length&&ctr-i>=0||exp1)
//            {
//                i = i+1;
//
//           }
//            else
//            {
//
//                i = 0;
//                if(ctr+20+i<=ctr+21)
//                {
//                    exp1=true;
//                }
//            }
//            npth.reset();
//            if(ctr-1>0)
//            {
//                npth.moveTo(path1ppts[ctr-1].getX(),path1ppts[ctr-1].getY());
//            }
//           else{
//                npth.moveTo(path1ppts[ctr].getX(),path1ppts[ctr].getY());
//            }
//            npth.lineTo(path1ppts[ctr+20+i].getX(),path1ppts[ctr+20+i].getY());
//            ctr++;
//            ctr2=99;
//        }
//        else
//        {
//            expanding = false;
//            if(ctr2-20-i>=0) {
//                i=i+1;
//                npth.reset();
//                npth.moveTo(path1ppts[ctr2].getX(), path1ppts[ctr2].getY());
//                npth.lineTo(path1ppts[ctr2-20-i].getX(), path1ppts[ctr2-20-i].getY());
//                ctr2--;
//            }
//            else
//            {
//                expanding = true;
//                ctr=0;
//                //i=0;
//            }
//        }

//        if(ctr==0) {
//            npth.lineTo(path1ppts[midPt].getX(), path1ppts[midPt].getY());
//            npth1.lineTo(path2ppts[midPt].getX(), path2ppts[midPt].getY());
//            ctr++;
//        }
//        else if(ctr>0&&ctr<49&&expanding)
//        {
//
//                npth.moveTo(path1ppts[ctr].getX(),path1ppts[0].getY());
//                npth1.moveTo(path2ppts[path2ppts.length -ctr].getX(),path2ppts[path2ppts.length -1].getY());
//                npth.lineTo(path1ppts[midPt + ctr].getX(), path1ppts[midPt].getY());
//                npth1.lineTo(path2ppts[midPt - ctr].getX(), path2ppts[midPt].getY());
//                ctr++;
//                if (ctr == 48) {
//                    expanding = false;
//                    System.out.println("Value of Counter is 48");
//                }
//
//        }
//        else if(!expanding)
//        {
//
//
//
//                System.out.println("In not expanding code " + ctr);
//                ctr--;
//                npth.lineTo(path1ppts[midPt - ctr].getX(), path1ppts[midPt].getY());
//                npth1.lineTo(path2ppts[midPt + ctr].getX(), path2ppts[midPt].getY());
//           if(ctr==0)
//            {
//                expanding = true;
//            }
//        }


        canvas.drawPath(path1,paintFog);
        canvas.drawPath(path2,paintFog);


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
