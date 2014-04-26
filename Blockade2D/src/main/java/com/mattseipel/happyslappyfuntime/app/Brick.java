package com.mattseipel.happyslappyfuntime.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Matt on 4/25/14.
 * Brick blockade class for easier access.
 */
public class Brick extends  Blockade{
    //Price of blockade, static for affordability checking.
    private static int cost = 25;

    public Brick(Context mContext, float x){
        super(mContext, x, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.rubblewall));
        setHealth(150);
        setDPS(50);
    }

    public static int getCost(){
        return cost;
    }
}
