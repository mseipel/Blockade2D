package com.mattseipel.happyslappyfuntime.app;

/**
 * Created by Clayton on 4/4/2014.
 */
public class Mario extends Enemy{

    final int hitPoints = 12;
    final int attackStrength = 8;
    final double movementSpeed = 0.8;
    final int drawableImage = 0;
    final int pointValue = 10;

    public Mario(){
        this.setHitPoints(hitPoints);
        this.setAttackStrength(attackStrength);
        this.setMovementSpeed(movementSpeed);
        this.setDrawableImage(drawableImage);
        this.setPointValue(pointValue);
    }
}
