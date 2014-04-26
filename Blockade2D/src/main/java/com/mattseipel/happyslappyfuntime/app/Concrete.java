package com.mattseipel.happyslappyfuntime.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Matt on 4/25/14.
 * Concrete blockade class for easier access.
 */
public class Concrete extends Blockade{
    private final static int cost = 75;

    public Concrete(Context mContext, float x){
        super(mContext, x, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.templewall));
        setHealth(65);
        setDPS(100);
    }

    public static int getCost(){
        return cost;
    }
}
