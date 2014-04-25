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

    int defaultSpeed = 3;
    int startingPosition = -200;

    int walkSpriteRow = 0;
    int walkSpriteRowFrameCount = 16;

    int attackSpriteRow = 1;
    int attackSpriteRowFrameCount = 5;

    int dyingSpriteRow = 2;
    int dyingSpriteRowFrameCount = 16;


    public Bowser(Context mContext, GameBoardCustomView gameBoard){
        super(gameBoard, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bowser_sprite), 1000, 3, 16);
        this.mContext = mContext;
        setX(-200);
        setDps(50/20);   //20 frames per second locked into GameLoop, this will be 10 damage per second.
    }

    public void attackAnimation(){
        //Setting the boolean variables for attacking
        walking = false;
        attacking = true;

        //Set the attack row to current, and set number of frames
        setxSpeed(0);
        setCurrentRow(attackSpriteRow);
        setSpriteSheetColumns(attackSpriteRowFrameCount);
    }

    public void walkAnimation(){
        //Set booleans
        attacking = false;
        walking = true;

        //Set the walking row to current, and set number of frames
        setxSpeed(defaultSpeed);
        setCurrentRow(walkSpriteRow);
        setSpriteSheetColumns(walkSpriteRowFrameCount);
    }

    public void deathAnimation(){
        //Set booleans
        attacking = false;
        walking = false;
        dying = true;
        //+2 to protect against the gameLoop moving faster than the following statements.
        if((getCurrentFrame() + 2) % getSpriteSheetColumns() == 0){
            setDeathComplete(true);
            setAlive(false);
            getGameBoard().win = true;
        }
        //Set death row to current, and set number of frames
        setxSpeed(0);
        setCurrentRow(dyingSpriteRow);
        setSpriteSheetColumns(dyingSpriteRowFrameCount);
    }

    public void update(){
        if(getHealth() <= 0){
            deathAnimation();

            if(getCurrentFrame() == getSpriteSheetColumns()){
                setAlive(false);

            }
        }

        setX(getX() + getxSpeed());

        //Ensure that the current frame never exceeds the number of frames available
        setCurrentFrame((getCurrentFrame() + 1) % getSpriteSheetColumns());
    }


    public void onDraw(Canvas canvas){
        this.update();
        super.onDraw(canvas);
    }

}
