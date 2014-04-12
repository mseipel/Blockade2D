package com.mattseipel.happyslappyfuntime.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.mattseipel.happyslappyfuntime.app.SurfaceViewExample.SpecialView;

/**
 * Created by Matt Seipel on 4/11/2014.
 */
public class Sprite {
    int x,y, xSpeed, ySpeed, height, width; //Position, speed, and dimensions
    Bitmap bm;
    SpecialView sv;
    int currentFrame = 0;   //Current frame of the spritesheet
    int direction = 0;      //direction of sprite
    int ssFrameWidth, ssFrameHeight;

    public Sprite(SpecialView sv, Bitmap bm, int ssFrameWidth, int ssFrameHeight){
        this.bm = bm;      //Store bitmap that is passed in
        this.sv = sv;      //Store the view
        this.ssFrameHeight = ssFrameHeight;     //Frames (height) in spritesheet
        this.ssFrameWidth = ssFrameWidth;       //Frames (width) in spritesheet
        height = bm.getHeight() / ssFrameHeight;    //height of each individual sprite
        width = bm.getWidth() / ssFrameWidth;       //width of each individual sprite
        x = y = 0;      //Set position
        xSpeed = 5;
        ySpeed = 0;
    }

    public void onDraw(Canvas canvas){
        update();
        int srcX = currentFrame * width;
        //int srcY = direction * height;    //reading from example spritesheet rows
        Rect src = new Rect(srcX, 0, srcX + width, height);
        Rect dst = new Rect(x, y, x+width, y+height);
        canvas.drawBitmap(bm, src, dst, null);

    }

    public void update(){
        //Facing down
        if(x > sv.getWidth() - width - xSpeed){ //Collision with the right bound
            xSpeed = 0;
            ySpeed = 5;
            //direction = 0;    //To change the direction of the sprite if need be
        }
        //Going left
        if(y > sv.getHeight() - height - ySpeed){  //Collision with bottom bound
            xSpeed = -5;
            ySpeed = 0;
            //direction = 1;    //To change the direction of the sprite if need be
        }
        //Facing up
        if(x + xSpeed < 0){     //Collision with left bound
            x = xSpeed = 0;
            ySpeed = -5;
            //direction = 2;    //To change the direction of the sprite if need be
        }
        //Going right
        if(y + ySpeed < 0){     //Collision with top bound
            y = ySpeed = 0;
            xSpeed = 5;
            //direction = 3;    //To change the direction of the sprite if need be
        }

        try{
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        currentFrame = ++currentFrame % 16;  //%16 ensures we loop back to the beginning of the spritesheet
        x += xSpeed;
        y += ySpeed;
    }
}
