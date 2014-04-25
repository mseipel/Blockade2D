package com.mattseipel.happyslappyfuntime.app;

import android.graphics.Bitmap;

/**
 * Created by Clayton Gillis on 3/16/14.
 */

//This is the abstract super class for all enemy objects in the game

public abstract class Enemy extends Sprite{

    //instance variables
    private double hitPoints;
    private double dps;
    private double movementSpeed;

    //This is the image used to represent the enemy unit while playing the game
    private int drawableImage;

    //This is the amount of points awarded to the player for killing the enemy unit
    private int pointValue;

    public Enemy(GameBoardCustomView gameBoard, Bitmap bm, int health, int spriteSheetRows, int spriteSheetColumns) {
        super(gameBoard, bm, health, spriteSheetRows, spriteSheetColumns);
    }

    //Getters and setters
    public double getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public int getDrawableImage() {
        return drawableImage;
    }

    public void setDrawableImage(int drawableImage) {
        this.drawableImage = drawableImage;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public double getDps() {
        return dps;
    }

    public void setDps(double dps) {
        this.dps = dps;
    }
}
