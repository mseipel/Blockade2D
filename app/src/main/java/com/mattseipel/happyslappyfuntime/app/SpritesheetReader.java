package com.mattseipel.happyslappyfuntime.app;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;


/**
 * Created by Matt Seipel on 4/10/2014.
 */
public class SpritesheetReader {
    private Bitmap bitmap;      //animation sequence
    private Rect sourceRect;    //rectangle to be drawn from bitmap
    private int frameNum;      //# frames in animation
    private int currentFrame;   //current frame
    private long frameUpdate;   //time of last frame update
    private int frameTime;      //milliseconds between each frame (1000/fps)

    private int spriteHeight;   //height of sprite
    private int spriteWidth;    //width of sprite

    private int x;              //the X coordinate of the object
    private int y;              //top left

    public SpritesheetReader(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount){
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        frameNum = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;   //Chop the sprites into individual pieces
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0,0, spriteWidth, spriteHeight);
        frameTime = 1000/fps;
        frameUpdate = 0l;
    }

    public void update(long gameTime){
        if(gameTime > frameUpdate + frameUpdate){
            frameUpdate = gameTime;
            //increment the frame
            currentFrame++;
            if(currentFrame >= frameNum){
                currentFrame = 0;
            }
        }
        //cut out the sprite with the rectangle
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
    }










}
