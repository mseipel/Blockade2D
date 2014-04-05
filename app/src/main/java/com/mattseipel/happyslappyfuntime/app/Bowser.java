package com.mattseipel.happyslappyfuntime.app;

/**
 * Created by Clayton on 4/4/2014.
 */
public class Bowser extends Enemy{

    final int hitPoints = 200;
    final int attackStrength = 40;
    final double movementSpeed = 0.5;
    final int drawableImage = 0;
    final int pointValue = 1000;

    public Bowser(){
        this.setHitPoints(hitPoints);
        this.setAttackStrength(attackStrength);
        this.setMovementSpeed(movementSpeed);
        this.setDrawableImage(drawableImage);
        this.setPointValue(pointValue);
    }
}
