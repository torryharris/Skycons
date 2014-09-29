package com.thbs.skycons.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by administrator on 29/09/14.
 */
public class Exp extends View {

    private static Paint paint, paint1;
    private int screenW, screenH;
    private float X, Y;
    private Path path;
    int x1=0, y1=0, x2=0, y2=0, x3=0, y3=0;
    float m = 0;
    boolean drop1 = true, drop2 = false, drop3 = false;
    private double count;

    public Exp(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        paint1 = new Paint();
        paint1.setColor(Color.BLACK);
        paint1.setStrokeWidth(8);
        paint1.setAntiAlias(true);
        paint1.setStrokeCap(Paint.Cap.ROUND);
        paint1.setStyle(Paint.Style.FILL);

        path = new Path();
    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenW = w;
        screenH = h;

        X = screenW/2;
        Y = (screenH/2);

        x1 = 100;
        y1 = 100;

    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path = new Path();

//        path.moveTo(x1, y1);
//        path.addArc(new RectF(x1-5, (y1-5)+m, x1+5, y1+5+m), 180, -180);
//        path.lineTo(x1, (y1-10)+m);
//        path.close();
//        canvas.drawPath(path, paint1);

        path.moveTo(x1, y1);
        path.lineTo(x1, y1+50+m);
        path.lineTo(x1+30, y1+20+m);
        path.close();

        canvas.drawPath(path, paint1);

        m = m+2.5f;

        if(m==100) {
            m=0;
            path.reset();
            path.moveTo(0, 0);
        }

       // invalidate();

    }

}
