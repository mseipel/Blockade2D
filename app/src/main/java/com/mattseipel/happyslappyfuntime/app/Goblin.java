package com.mattseipel.happyslappyfuntime.app;

/**
 * Created by Clayton Gillis on 3/16/14.
 */

//This class is responsible for creating and managing Goblins, the weakest enemy unit

public class Goblin extends Enemy {

    final int hitPoints = 8;
    final int attackStrength = 5;
    final double movementSpeed = 2.0;
    final int drawableImage = 0;
    final int pointValue = 5;

    //Constructor
    public Goblin(){
        this.setHitPoints(hitPoints);
        this.setAttackStrength(attackStrength);
        this.setMovementSpeed(movementSpeed);
        this.setDrawableImage(drawableImage);
        this.setPointValue(pointValue);
    }
}
