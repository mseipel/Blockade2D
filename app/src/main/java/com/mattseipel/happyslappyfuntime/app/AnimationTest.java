package com.mattseipel.happyslappyfuntime.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


/**
 * TODO: document your custom view class.
 */
public class AnimationTest extends View {
    SpritesheetReader sr;
    Bitmap bowser = BitmapFactory.decodeResource(getResources(), R.drawable.bowser_sprite);

    public AnimationTest(Context context) {
        super(context);
    }

    public AnimationTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        sr = new SpritesheetReader(bowser, 0, 0, bowser.getWidth(), bowser
        .getHeight(), 8, 16);

    }

    public AnimationTest(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    public void update(){
        sr.update(System.currentTimeMillis());
    }
}