package com.mattseipel.happyslappyfuntime.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Matt on 4/25/14.
 * Electric class for easier access.
 */
public class Electric extends Blockade {
    //Price of blockade, static for affordability checking.
    private final static int cost = 200;

    public Electric(Context mContext, float x){
        super(mContext, x, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.electricalbarrier));
        setHealth(50);
        setDPS(500);
    }

    public static int getCost(){
        return cost;
    }
}
