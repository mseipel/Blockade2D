package com.mattseipel.happyslappyfuntime.app;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Clayton on 4/4/2014.
 */
public class Bowser extends Sprite{
    Context mContext;
    boolean dying = false;
    boolean attacking = false;
    boolean walking = true;

    int startingPosition = -200;

    int walkSpriteRow = 0;
    int walkSpriteRowFrameCount = 16;

    int attackSpriteRow = 1;
    int attackSpriteRowFrameCount = 5;

    int dyingSpriteWidth = 81;
    int dyingSpriteRow = 2;
    int dyingSpriteRowFrameCount = 10;

    int xSpeed = 3;

    public Bowser(Context mContext, GameBoardCustomView gameBoard){
        super(gameBoard, BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bowser_sprite), 500, 3, 16);
        this.mContext = mContext;
        setX(-200);
    }

    public void attackAnimation(){
        //Setting the boolean variables for attacking
        walking = false;
        attacking = true;

        //Set the attack row to current, and set number of frames
        setCurrentRow(1);
        setSpriteSheetColumns(5);
    }

    public void walkAnimation(){
        //Set booleans
        attacking = false;
        walking = true;

        //Set the walking row to current, and set number of frames
        setCurrentRow(0);
        setSpriteSheetColumns(16);
    }

    public void deathAnimation(){
        //Set booleans
        attacking = false;
        walking = false;
        dying = true;

        //Set death row to current, and set number of frames
        setCurrentRow(2);
        setSpriteSheetColumns(10);
    }

    public void update(){
        if(getHealth() <= 0){
            deathAnimation();

            if(getCurrentFrame() == getSpriteSheetColumns()){
                setAlive(false);
            }
        }

        setX(getX() + xSpeed);

        //Ensure that the current frame never exceeds the number of frames available
        setCurrentFrame((getCurrentFrame() + 1) % getSpriteSheetColumns());
    }


    public void onDraw(Canvas canvas){
        this.update();
        super.onDraw(canvas);
    }

}
