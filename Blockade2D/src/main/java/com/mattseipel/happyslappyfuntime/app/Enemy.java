package com.mattseipel.happyslappyfuntime.app;

/**
 * Created by Clayton Gillis on 3/16/14.
 */

//This is the abstract super class for all enemy objects in the game

public abstract class Enemy {

    //instance variables
    private int hitPoints;
    private int attackStrength;
    private double movementSpeed;

    //This is the image used to represent the enemy unit while playing the game
    private int drawableImage;

    //This is the amount of points awarded to the player for killing the enemy unit
    private int pointValue;

    //Getters and setters
    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
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
}
