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

public class CloudThunderView extends View {


    int ctr = 0;
    float thHeight;
    PathPoints[] rightPoints, leftPoints;
    Boolean check;
    private Paint paintCloud,paintThunder;
    private int screenW, screenH;
    private float X, Y;
    private Path pathCloud, thLeftPath, thRightPath, thFillPath;
    private double count;

    public CloudThunderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CloudThunderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    // Initial declaration of the coordinates.
    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w; //getting Screen Width
        screenH = h; // getting Screen Height

        // center point  of Screen
        X = screenW/2;
        Y = (screenH/2);

    }

    private void init() {

        count = 0;
        check = false;
        thHeight = 0;
        thLeftPath = new Path();
        thRightPath = new Path();
        thFillPath = new Path();


        paintCloud = new Paint();
        paintCloud.setColor(Color.BLACK);
        paintCloud.setStrokeWidth((screenW / 25));
        paintCloud.setAntiAlias(true);
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setShadowLayer(0, 0, 0, Color.BLACK);

        paintThunder = new Paint();
        paintThunder.setColor(Color.BLACK);
        paintThunder.setStrokeWidth(10);
        paintThunder.setAntiAlias(true);
        paintThunder.setStrokeCap(Paint.Cap.ROUND);
        paintThunder.setStrokeJoin(Paint.Join.ROUND);
        paintThunder.setStyle(Paint.Style.STROKE);
        paintThunder.setShadowLayer(0, 0, 0, Color.BLACK);

        pathCloud = new Path();

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paintCloud.setStrokeWidth((float) (0.02083 * screenW));
        pathCloud = new Path();

        //incrementing counter for rotation
        count = count+0.5;

        //comparison to check 360 degrees rotation
        int retval = Double.compare(count, 360.00);

        if(retval == 0) {
            //resetting counter on completion of a rotation
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

        // getting points in between coordinates for drawing arc between them
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

        // drawing arcs between coordinates
        pathCloud.moveTo(X1,Y1);
        pathCloud.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, P1X, P1Y);
        pathCloud.cubicTo(P2c1.x, P2c1.y, P2c2.x, P2c2.y, P2X, P2Y);
        pathCloud.cubicTo(P3c1.x, P3c1.y, P3c2.x, P3c2.y, P3X, P3Y);
        pathCloud.cubicTo(P4c1.x, P4c1.y, P4c2.x, P4c2.y, P4X, P4Y);
        pathCloud.cubicTo(P5c1.x, P5c1.y, P5c2.x, P5c2.y, X1, Y1);


        //Setting up the height of thunder from the cloud
        if(thHeight==0)
        {
            thHeight = P2c1.y;
        }
        float startHeight = thHeight-(thHeight*0.1f);

        //Setting up X coordinates of thunder
        float path1StartX = X+(X*0.18f);
        float path2StartX = X-(X*0.14f);

        thRightPath.reset();

        //Calculating coordinates of thunder
        thRightPath.moveTo(path1StartX, startHeight);
        thLeftPath.moveTo(path2StartX, startHeight);

        thRightPath.lineTo(X - (X * 0.02f), startHeight + (startHeight * 0.18f));//1
        thLeftPath.lineTo(X - (X * 0.3f), startHeight + (startHeight * 0.2f)); //1

        thRightPath.lineTo(X + (X * 0.12f), startHeight + (startHeight * 0.16f));
        thLeftPath.lineTo(X - (X * 0.12f), startHeight + (startHeight * 0.18f));

        thRightPath.lineTo(X - (X * 0.32f), startHeight + (startHeight * 0.4f));
        thLeftPath.lineTo(X - (X * 0.32f), startHeight + (startHeight * 0.4f));

        rightPoints = getPoints(thRightPath);
        leftPoints = getPoints(thLeftPath);

        if(ctr<=98) {

            if(check==false) {
                //Filling thunder coordinates
                thFillPath.moveTo(rightPoints[ctr].getX(), rightPoints[ctr].getY());
                thFillPath.lineTo(rightPoints[ctr + 1].getX(), rightPoints[ctr + 1].getY());
                thFillPath.lineTo(leftPoints[ctr + 1].getX(), leftPoints[ctr + 1].getY());
                thFillPath.lineTo(leftPoints[ctr].getX(), leftPoints[ctr].getY());
                thFillPath.close();
            }
            else
            {
                //Once filled, erasing the fill from top to bottom
                thFillPath.reset();
                thFillPath.moveTo(rightPoints[ctr].getX(), rightPoints[ctr].getY());
                for(int i=ctr+1;i< rightPoints.length-1;i++)
                {
                    thFillPath.lineTo(rightPoints[i].getX(), rightPoints[i].getY());
                    thFillPath.lineTo(leftPoints[i].getX(), leftPoints[i].getY());
                    thFillPath.lineTo(leftPoints[i].getX(), leftPoints[i].getY());
                }
                thFillPath.close();
            }
            ctr = ctr+1;
        }
        else
        {
            ctr=0;
            if(check==false)
            {
                check=true;
            }
            else
            {
                check=false;
            }
        }

        canvas.drawPath(thFillPath,paintThunder);

        paintCloud.setStyle(Paint.Style.FILL);
        paintCloud.setColor(Color.WHITE);
        canvas.drawPath(pathCloud, paintCloud);

        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setColor(Color.BLACK);
        canvas.drawPath(pathCloud, paintCloud);

        invalidate();

    }

    private PointF calculateTriangle(float x1, float y1, float x2,
                                     float y2, boolean left) {

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