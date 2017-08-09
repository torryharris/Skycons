package com.thbs.skycons.library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * This view draws cloud with rain.
 */
public class CloudRainView extends SkyconView {

    private static Paint paintCloud, paintRain;
    private int screenW, screenH;
    private float X, Y;
    private Path  pathRain;
    int x1=0, y1=0, x2=0, y2=0, x3=0, y3=0;
    float m = 0;
    boolean drop1 = true, drop2 = false, drop3 = false;
    private double count;
    Cloud cloud;

    public CloudRainView(Context context,boolean isStatic,boolean isAnimated, int strokeColor , int backgroundColor) {
        super(context);

        this.isStatic = isStatic;
        this.isAnimated = isAnimated;
        this.strokeColor = strokeColor;
        this.bgColor = backgroundColor;

        init();
    }

    public CloudRainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        count = 0;

        paintCloud = new Paint();
        paintRain = new Paint();

        //Paint for drawing cloud
        paintCloud.setColor(strokeColor);
        paintCloud.setStrokeWidth(10);
        paintCloud.setAntiAlias(true);
        paintCloud.setStrokeCap(Paint.Cap.ROUND);
        paintCloud.setStrokeJoin(Paint.Join.ROUND);
        paintCloud.setStyle(Paint.Style.STROKE);
        paintCloud.setShadowLayer(0, 0, 0, strokeColor);

        //Paint for drawing rain drops
        paintRain.setColor(strokeColor);
        paintRain.setAntiAlias(true);
        paintRain.setStrokeCap(Paint.Cap.ROUND);
        paintRain.setStyle(Paint.Style.FILL);

        cloud = new Cloud();

        isAnimated = true;

    }

    // Initial declaration of the coordinates.
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

        // set canvas background color
        canvas.drawColor(bgColor);

        paintCloud.setStrokeWidth((float)(0.02083*screenW));
        paintRain.setStrokeWidth((float)(0.015*screenW));

        pathRain = new Path(); // pathCloud for drop

        count = count+0.5;

        //comparison to check 360 degrees rotation
        int retval = Double.compare(count, 360.00);

        if(retval == 0) {
            if(!isAnimated) {
                // mark completion of animation
                isAnimated = true;
                //resetting counter on completion of a rotation
                count = 0;
            } else {
                //resetting counter on completion of a rotation
                count = 0;
            }
        }

        PointF P1c2 = cloud.getP1c2(X,Y,screenW,count);
        PointF P1c1 = cloud.getP1c2(X,Y,screenW,count);
        float P1Y = ((float) ((int) (0.1041667 * screenW) *
                Math.sin(Math.toRadians(80 + (0.111 * count))) + Y));

        PointF P2c2 = cloud.getP2c2(X,Y,screenW,count);
        PointF P2c1 = cloud.getP2c2(X,Y,screenW,count);
        float P2Y = ((float) (((int) (0.1041667 * screenW) + ((0.00023125 * screenW) * count))
                * Math.sin(Math.toRadians(120 + (0.222 * count))) + Y));

        if(isAnimated && isStatic) { //Initial static view

            pathRain = new Path();

            // 1st drop
            if(x1==0) {
                x1 = (int) P1c2.x;
            }
            if(y1==0) {
                float value = (int) P1c2.y-((P1c1.y+P1Y)/2);
                y1 = (int) (P1c2.y-value/2);
            }

            m = 95;
            // Shape for rain drop
            pathRain.moveTo(x1, y1);
            pathRain.addArc(new RectF(x1 - 5, (y1 - 5) + m, x1 + 5, y1 + 5 + m), 180, -180);
            pathRain.lineTo(x1, (y1 - 10) + m);
            pathRain.close();


            // 2nd drop
            if(x2==0) {
                x2 = (int) P2c2.x;
            }
            if(y2==0) {
                float value = (int) P2c2.y-((P2c1.y+P2Y)/2);
                y2 = (int) (P2c2.y-value/2);
            }

            pathRain.moveTo(x2, y2);
            pathRain.addArc(new RectF(x2 - 5, (y2 - 5) + m, x2 + 5, y2 + 5 + m), 180, -180);
            pathRain.lineTo(x2, (y2 - 10) + m);
            pathRain.close();

            // 3rd drop
            if(x3==0) {
                x3 = (x1+x2)/2;
            }
            if(y3==0) {
                y3 = (y1+y2)/2;
            }

            pathRain.moveTo(x3, y3);
            pathRain.addArc(new RectF(x3 - 5, (y3 - 5) + m/2, x3 + 5, y3 + 5 + m/2), 180, -180);
            pathRain.lineTo(x3, (y3 - 10) + m/2);
            pathRain.close();


        } else { // Animating view

            if(drop1) { // Drop 1 of the rain

                pathRain = new Path();

                if(x1==0) {
                    x1 = (int) P1c2.x;
                }
                if(y1==0) {
                    float value = (int) P1c2.y-((P1c1.y+P1Y)/2);
                    y1 = (int) (P1c2.y-value/2);
                }

                // Shape for rain drop
                pathRain.moveTo(x1, y1);
                pathRain.addArc(new RectF(x1 - 5, (y1 - 5) + m, x1 + 5, y1 + 5 + m), 180, -180);
                pathRain.lineTo(x1, (y1 - 10) + m);
                pathRain.close();

                if(m==100) {
                    m=0;
                    pathRain.reset();
                    pathRain.moveTo(0, 0);
                    drop2 = true;
                    drop1 = false;
                }
            }

            if(drop2) { // Drop 2 of the rain
                pathRain = new Path();

                if(x2==0) {
                    x2 = (int) P2c2.x;
                }
                if(y2==0) {
                    float value = (int) P2c2.y-((P2c1.y+P2Y)/2);
                    y2 = (int) (P2c2.y-value/2);
                }

                pathRain.moveTo(x2, y2);
                pathRain.addArc(new RectF(x2 - 5, (y2 - 5) + m, x2 + 5, y2 + 5 + m), 180, -180);
                pathRain.lineTo(x2, (y2 - 10) + m);
                pathRain.close();

                if(m==100) {
                    m=0;
                    pathRain.reset();
                    pathRain.moveTo(0, 0);
                    drop2 = false;
                    drop3 = true;
                }
            }

            if(drop3) { // Drop 3 of the rain
                pathRain = new Path();

                if(x3==0) {
                    x3 = (x1+x2)/2;
                }
                if(y3==0) {
                    y3 = (y1+y2)/2;
                }

                pathRain.moveTo(x3, y3);
                pathRain.addArc(new RectF(x3 - 5, (y3 - 5) + m, x3 + 5, y3 + 5 + m), 180, -180);
                pathRain.lineTo(x3, (y3 - 10) + m);
                pathRain.close();

                if(m==100) {
                    m=0;
                    pathRain.reset();
                    pathRain.moveTo(0, 0);
                    // animate = false;
                    drop3 = false;
                    drop1 = true;
                }
            }

        }

        // First fill the shape with paint
        paintRain.setStyle(Paint.Style.FILL);
        canvas.drawPath(pathRain, paintRain);

        // Then, draw the same pathCloud with paint stroke
        paintRain.setStyle(Paint.Style.STROKE);
        canvas.drawPath(pathRain, paintRain);

        m = m+2.5f;

        // drawing cloud with fill
        paintCloud.setColor(bgColor);
        paintCloud.setStyle(Paint.Style.FILL);
        canvas.drawPath(cloud.getCloud(X,Y,screenW,count), paintCloud);

        // drawing cloud with stroke
        paintCloud.setColor(strokeColor);
        paintCloud.setStyle(Paint.Style.STROKE);
        canvas.drawPath(cloud.getCloud(X,Y,screenW,count), paintCloud);

        if(!isStatic || !isAnimated) {
            invalidate();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                // nothing to do
                return true;

            case MotionEvent.ACTION_MOVE:
                // nothing to do
                break ;

            case MotionEvent.ACTION_UP:
                // start animation if it is not animating
                if(isStatic && isAnimated) {
                    isAnimated = false;
                }
                break;

            default:
                return false;
        }

        // Schedules a repaint.
        if(!isAnimated) {
            invalidate();
        }

        return true;

    }


}

