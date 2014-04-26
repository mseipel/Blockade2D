package com.mattseipel.happyslappyfuntime.app;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Clayton on 4/4/2014.
 */
public class Bowser extends Enemy{

    Context mContext;
    boolean dying = false;
    boolean attacking = false;
    boolean walking = true;

    //Default speed and the starting x value
    int defaultSpeed = 3;
    int startingPosition = -200;

    //Rows and frame length of corresponding sprites
    int walkSpriteRow = 0;
    int walkSpriteRowFrameCount = 16;

    int attackSpriteRow = 1;
    int attackSpriteRowFrameCount = 5;

    int dyingSpriteRow = 2;
    int dyingSpriteRowFrameCount = 16;

    /**
     * Bowser constructor
     * @param mContext
     * @param gameBoard
     * @param health
     */
    public Bowser(Context mContext, GameBoardCustomView gameBoard, int health){
        super(gameBoard, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bowser_sprite), health, 3, 16);
        this.mContext = mContext;
        setX(startingPosition);
        setDps(50/20);   //20 frames per second locked into GameLoop, this will be 10 damage per second.
    }

    /**
     * Change Bowser's animation to attack
     */
    public void attackAnimation(){
        //Setting the boolean variables for attacking
        walking = false;
        attacking = true;

        //Set the attack row to current, and set number of frames
        setxSpeed(0);
        setCurrentRow(attackSpriteRow);
        setSpriteSheetColumns(attackSpriteRowFrameCount);
    }

    /**
     * Change Bowser's animation to walk.
     */
    public void walkAnimation(){
        //Set booleans
        attacking = false;
        walking = true;

        //Set the walking row to current, and set number of frames
        setxSpeed(defaultSpeed);
        setCurrentRow(walkSpriteRow);
        setSpriteSheetColumns(walkSpriteRowFrameCount);
    }

    /**
     * Change Bowser's animation to die.
     */
    public void deathAnimation(){
        //Set booleans
        attacking = false;
        walking = false;
        dying = true;

        //+2 to protect against the gameLoop moving faster than the following statements.
        if((getCurrentFrame() + 2) % getSpriteSheetColumns() == 0){
            setDeathComplete(true);
            setAlive(false);
        }
        //Set death row to current, and set number of frames
        setxSpeed(0);
        setCurrentRow(dyingSpriteRow);
        setSpriteSheetColumns(dyingSpriteRowFrameCount);
    }

    /**
     * Update the position of Bowser.
     */
    public void update(){
        //If 0 health, start the death animation.
        if(getHealth() <= 0){
            deathAnimation();
        }

        //Move Bowser across the screen
        setX(getX() + getxSpeed());

        //Ensure that the current frame never exceeds the number of frames available
        setCurrentFrame((getCurrentFrame() + 1) % getSpriteSheetColumns());
    }

    /**
     * Redraw Bowser.
     * @param canvas
     */
    public void onDraw(Canvas canvas){
        this.update();
        super.onDraw(canvas);
    }

}
