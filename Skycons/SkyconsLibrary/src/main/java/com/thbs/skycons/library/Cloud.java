package com.thbs.skycons.library;

import android.graphics.Path;
import android.graphics.PointF;

/**
 * Created by administrator on 10/1/14.
 */
public class Cloud {

    Path path;

    public Path getCloud(float centerX, float centerY, int dimension, double count) {


        //different radius values for the cloud coordinates

        path = new Path();

        int r1 = (int) (0.1875 * dimension);
        int r2 = (int) (0.1041667 * dimension);
        double offset = 0.00023125 * dimension;

        // cloud coordinates from the center of the screen
        float X1 = (float) (r1 * Math.cos(Math.toRadians(0 + (0.222 * count))) + centerX); //x value of coordinate 1 at radius r1 from center of Screen and angle incremented with counter
        float Y1 = ((float) (r2 * Math.sin(Math.toRadians(0 + (0.222 * count))) + centerY));//y value of coordinate 1 at radius r2 from center of Screen and angle incremented with counter
        float P1X = (float) (r1 * Math.cos(Math.toRadians(80 + (0.111 * count))) + centerX);//x value of coordinate 2 at radius r1 from center of Screen and angle incremented with counter
        float P1Y = ((float) (r2 * Math.sin(Math.toRadians(80 + (0.111 * count))) + centerY));//y value of coordinate 2 at radius r2 from center of Screen and angle incremented with counter
        float P2X = (float) (r1 * Math.cos(Math.toRadians(120 + (0.222 * count))) + centerX);//x value of coordinate 3 at radius r1 from center of Screen and angle incremented with counter
        float P2Y = ((float) ((r2 + (offset * count)) * Math.sin(Math.toRadians(120 + (0.222 * count))) + centerY));//y value of coordinate 3 at varying radius from center of Screen and angle incremented with counter
        float P3X = (float) (r1 * Math.cos(Math.toRadians(200 + (0.222 * count))) + centerX);//x value of coordinate 4 at radius r1 from center of Screen and angle incremented with counter
        float P3Y = ((float) (r1 * Math.sin(Math.toRadians(200 + (0.222 * count))) + centerY));//y value of coordinate 4 at radius r1 from center of Screen and angle incremented with counter
        float P4X = (float) (r1 * Math.cos(Math.toRadians(280 + (0.222 * count))) + centerX);//x value of coordinate 5 at radius r1 from center of Screen and angle incremented with counter
        float P4Y = ((float) (r1 * Math.sin(Math.toRadians(280 + (0.222 * count))) + centerY));//y value of coordinate 5 at radius r1 from center of Screen and angle incremented with counter


        path.moveTo(X1, Y1);

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
        PointF P5c2 = calculateTriangle(P4X, P4Y, X1, Y1, false);

        // drawing arcs between coordinates
        path.moveTo(X1, Y1);
        path.cubicTo(P1c1.x, P1c1.y, P1c2.x, P1c2.y, P1X, P1Y);
        path.cubicTo(P2c1.x, P2c1.y, P2c2.x, P2c2.y, P2X, P2Y);
        path.cubicTo(P3c1.x, P3c1.y, P3c2.x, P3c2.y, P3X, P3Y);
        path.cubicTo(P4c1.x, P4c1.y, P4c2.x, P4c2.y, P4X, P4Y);
        path.cubicTo(P5c1.x, P5c1.y, P5c2.x, P5c2.y, X1, Y1);

        return path;
    }


    private PointF calculateTriangle(float x1, float y1, float x2,
                                     float y2, boolean left) {

        PointF result = new PointF(0, 0);
        // finding center point between the coordinates
        float dy = y2 - y1;
        float dx = x2 - x1;
        float dangle = 0;
        // calculating angle and the distance between center and the two points
        if (left) {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI / 3f));
        } else {
            dangle = (float) ((Math.atan2(dy, dx) - Math.PI / 1.5f));
        }
        float sideDist = (float) 0.45 * (float) Math.sqrt(dx * dx + dy * dy); //square

        // sideDist = sideDist + sideDist/10;

        if (left) {
            //point from center to the left
            result.x = (int) (Math.cos(dangle) * sideDist + x1);
            result.y = (int) (Math.sin(dangle) * sideDist + y1);

        } else {
            //point from center to the right
            result.x = (int) (Math.cos(dangle) * sideDist + x2);
            result.y = (int) (Math.sin(dangle) * sideDist + y2);
        }

        return result;

    }


   public PointF getP1c1(float centerX, float centerY, int dimension, double count){
       int r1 = (int) (0.1875 * dimension);
       int r2 = (int) (0.1041667 * dimension);
       double offset = 0.00023125 * dimension;

       // cloud coordinates from the center of the screen
       float X1 = (float) (r1 * Math.cos(Math.toRadians(0 + (0.222 * count))) + centerX); //x value of coordinate 1 at radius r1 from center of Screen and angle incremented with counter
       float Y1 = ((float) (r2 * Math.sin(Math.toRadians(0 + (0.222 * count))) + centerY));//y value of coordinate 1 at radius r2 from center of Screen and angle incremented with counter
       float P1X = (float) (r1 * Math.cos(Math.toRadians(80 + (0.111 * count))) + centerX);//x value of coordinate 2 at radius r1 from center of Screen and angle incremented with counter
       float P1Y = ((float) (r2 * Math.sin(Math.toRadians(80 + (0.111 * count))) + centerY));//y value of coordinate 2 at radius r2 from center of Screen and angle incremented with counter

       // getting points in between coordinates for drawing arc between them
       PointF P1c1 = calculateTriangle(X1, Y1, P1X, P1Y, true);

       return P1c1;
   }

    public PointF getP1c2(float centerX, float centerY, int dimension, double count){
        int r1 = (int) (0.1875 * dimension);
        int r2 = (int) (0.1041667 * dimension);
        double offset = 0.00023125 * dimension;

        // cloud coordinates from the center of the screen
        float X1 = (float) (r1 * Math.cos(Math.toRadians(0 + (0.222 * count))) + centerX); //x value of coordinate 1 at radius r1 from center of Screen and angle incremented with counter
        float Y1 = ((float) (r2 * Math.sin(Math.toRadians(0 + (0.222 * count))) + centerY));//y value of coordinate 1 at radius r2 from center of Screen and angle incremented with counter
        float P1X = (float) (r1 * Math.cos(Math.toRadians(80 + (0.111 * count))) + centerX);//x value of coordinate 2 at radius r1 from center of Screen and angle incremented with counter
        float P1Y = ((float) (r2 * Math.sin(Math.toRadians(80 + (0.111 * count))) + centerY));//y value of coordinate 2 at radius r2 from center of Screen and angle incremented with counter

        PointF P1c2 = calculateTriangle(X1, Y1, P1X, P1Y, false);

        return P1c2;
    }


    public PointF getP2c1(float centerX, float centerY, int dimension, double count){
        int r1 = (int) (0.1875 * dimension);
        int r2 = (int) (0.1041667 * dimension);
        double offset = 0.00023125 * dimension;

        // cloud coordinates from the center of the screen
        float P1X = (float) (r1 * Math.cos(Math.toRadians(80 + (0.111 * count))) + centerX);//x value of coordinate 2 at radius r1 from center of Screen and angle incremented with counter
        float P1Y = ((float) (r2 * Math.sin(Math.toRadians(80 + (0.111 * count))) + centerY));//y value of coordinate 2 at radius r2 from center of Screen and angle incremented with counter
        float P2X = (float) (r1 * Math.cos(Math.toRadians(120 + (0.222 * count))) + centerX);//x value of coordinate 3 at radius r1 from center of Screen and angle incremented with counter
        float P2Y = ((float) ((r2 + (offset * count)) * Math.sin(Math.toRadians(120 + (0.222 * count))) + centerY));//y value of coordinate 3 at varying radius from center of Screen and angle incremented with counter

        PointF P2c1 = calculateTriangle(P1X, P1Y, P2X, P2Y, true);

        return P2c1;
    }


    public PointF getP2c2(float centerX, float centerY, int dimension, double count){
        int r1 = (int) (0.1875 * dimension);
        int r2 = (int) (0.1041667 * dimension);
        double offset = 0.00023125 * dimension;

        float P1X = (float) (r1 * Math.cos(Math.toRadians(80 + (0.111 * count))) + centerX);//x value of coordinate 2 at radius r1 from center of Screen and angle incremented with counter
        float P1Y = ((float) (r2 * Math.sin(Math.toRadians(80 + (0.111 * count))) + centerY));//y value of coordinate 2 at radius r2 from center of Screen and angle incremented with counter
        float P2X = (float) (r1 * Math.cos(Math.toRadians(120 + (0.222 * count))) + centerX);//x value of coordinate 3 at radius r1 from center of Screen and angle incremented with counter
        float P2Y = ((float) ((r2 + (offset * count)) * Math.sin(Math.toRadians(120 + (0.222 * count))) + centerY));//y value of coordinate 3 at varying radius from center of Screen and angle incremented with counter

        PointF P2c2 = calculateTriangle(P1X, P1Y, P2X, P2Y, false);

        return P2c2;
    }

    public PointF getP5c1(float centerX, float centerY, int dimension, double count){
        int r1 = (int) (0.1875 * dimension);
        int r2 = (int) (0.1041667 * dimension);
        double offset = 0.00023125 * dimension;

        // cloud coordinates from the center of the screen
        float X1 = (float) (r1 * Math.cos(Math.toRadians(0 + (0.222 * count))) + centerX); //x value of coordinate 1 at radius r1 from center of Screen and angle incremented with counter
        float Y1 = ((float) (r2 * Math.sin(Math.toRadians(0 + (0.222 * count))) + centerY));//y value of coordinate 1 at radius r2 from center of Screen and angle incremented with counter
        float P4X = (float) (r1 * Math.cos(Math.toRadians(280 + (0.222 * count))) + centerX);//x value of coordinate 5 at radius r1 from center of Screen and angle incremented with counter
        float P4Y = ((float) (r1 * Math.sin(Math.toRadians(280 + (0.222 * count))) + centerY));//y value of coordinate 5 at radius r1 from center of Screen and angle incremented with counter

        PointF P5c1 = calculateTriangle(P4X, P4Y, X1, Y1, true);

        return P5c1;
    }


}