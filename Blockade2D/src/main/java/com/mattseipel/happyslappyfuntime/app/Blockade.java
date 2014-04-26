package com.mattseipel.happyslappyfuntime.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Matt on 4/18/2014.
 * Super class for all blockades
 */
public abstract class Blockade {
    //Store context for references back to the GameplayActivity used in subclasses.
    private Context mContext;
    //The image of the blockades
    private Bitmap bitmap;
    //Coordinates of the user's touch
    private float x;
    private float y;
    //Hitpoints
    private double health;
    //Damage per second, value is divided by 20 (20 FPS) so DPS is accurate.
    private double dps;

    private boolean stillStanding;

    /**
     * Builds the blockade.
     * @param mContext
     * @param x
     * @param y
     * @param bitmap
     */
    public Blockade(Context mContext, float x, Bitmap bitmap) {
        this.x = x;
        y = ((GameplayActivity)mContext).getDeviceHeight()/4.5f;
        this.mContext = mContext;
        this.bitmap = bitmap;
        stillStanding = true;
    }

    /**
     * Take damage using the DPS of the enemy.
     * @param dps
     */
    public void takeDamage(double dps) {
        if (health - dps <= 0)
            stillStanding = false;
        health -= dps;
    }

    /**
     * Draw the blockades
     * @param canvas
     */
    public void onDraw(Canvas canvas){
        canvas.drawBitmap(bitmap, x, y, null);
    }

    //---------------------GETTERS AND SETTERS---------------------
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getDPS() {
        return dps;
    }

    public void setDPS(double dpsIn) {
        dps = dpsIn/GameLoop.FPS;
    }

    public boolean isStillStanding() {
        return stillStanding;
    }

    public void setStillStanding(boolean stillStanding) {
        this.stillStanding = stillStanding;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
}
