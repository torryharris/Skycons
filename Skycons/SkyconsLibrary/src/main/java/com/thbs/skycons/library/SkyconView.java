package com.thbs.skycons.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class SkyconView extends View {
    protected boolean isStatic =false;
    boolean isAnimated = false;
    int strokeColor = Color.parseColor("#000000");
    int bgColor = Color.parseColor("#ffffff");

    public SkyconView(Context context) {
        super(context);
    //    extractAttributes(context);
    }

    public SkyconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        extractAttributes(context,attrs);
    }

    private void extractAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.custom_view);

        // get attributes from layout
        isStatic = a.getBoolean(R.styleable.custom_view_isStatic, this.isStatic);
        strokeColor = a.getColor(R.styleable.custom_view_strokeColor, this.strokeColor);

        if(strokeColor == 0){

            strokeColor = Color.BLACK;
        }

        bgColor = a.getColor(R.styleable.custom_view_bgColor, this.bgColor);

        if(bgColor == 0) {
            bgColor = Color.WHITE;
        }

        a.recycle();
    }


    public boolean isStatic() {
        return isStatic;
    }

    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public void setIsAnimated(boolean isAnimated) {
        this.isAnimated = isAnimated;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        Log.e("strokecolor",""+strokeColor);
        this.strokeColor = strokeColor;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}