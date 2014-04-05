package com.mattseipel.happyslappyfuntime.app;

/**
 * Created by Clayton on 4/4/2014.
 */
public class Sonic extends Enemy{

    final int hitPoints = 12;
    final int attackStrength = 10;
    final double movementSpeed = 2.0;
    final int drawableImage = 0;
    final int pointValue = 20;

    public Sonic(){
        this.setHitPoints(hitPoints);
        this.setAttackStrength(attackStrength);
        this.setMovementSpeed(movementSpeed);
        this.setDrawableImage(drawableImage);
        this.setPointValue(pointValue);
    }
}
