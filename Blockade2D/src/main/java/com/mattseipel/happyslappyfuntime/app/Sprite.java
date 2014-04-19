package com.mattseipel.happyslappyfuntime.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Matt Seipel on 4/11/2014.
 */
public class Sprite {
    private static final int SPRITE_SHEET_ROWS = 1;
    private int spriteSheetColumns;
    private static final int MAX_SPEED = 10;
    private int x;
    private int y;
    int xSpeed;
    int ySpeed;
    int height;
    int width; //Position, speed, and dimensions
    private int health = 50;
    private float boardWidth, boardHeight;
    private boolean alive;
    Bitmap bm;
//    SpecialView sv;
    GameBoardCustomView gameBoard;
    int currentFrame = 0;   //Current frame of the sprite sheet
//    int direction = 0;      //direction of sprite
    int numFramesWide, numFramesHigh;

    public Sprite(GameBoardCustomView gameBoard, Bitmap bm, int health, int spriteSheetColumns){
        this.gameBoard = gameBoard;
        this.bm = bm;
        this.spriteSheetColumns = spriteSheetColumns;
        this.width = bm.getWidth() / spriteSheetColumns;
        this.height = bm.getHeight() / SPRITE_SHEET_ROWS;
        this.health = health;
//        Random rnd = new Random();
//        y = rnd.nextInt(500 - height);
//        xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        setY(gameBoard.getHeight()/3);
        setX(-200);
        xSpeed = 4;
        setAlive(true);
    }
//
//    public Sprite(SpecialView sv, Bitmap bm, int numFramesWide, int numFramesHigh){
//        this.bm = bm;      //Store bitmap that is passed in
//        this.sv = sv;      //Store the view
//        this.numFramesHigh = numFramesHigh;     //Frames (height) in spritesheet
//        this.numFramesWide = numFramesWide;       //Frames (width) in spritesheet
//        height = bm.getHeight() / numFramesHigh;    //height of each individual sprite
//        width = bm.getWidth() / numFramesWide;       //width of each individual sprite
//        x = y = 0;      //Set position
//        xSpeed = 5;
//        ySpeed = 0;
//    }
//
//    public Sprite(SpecialView sv, Bitmap bm, int x, int y, int xSpeed, int numFramesWide, int numFramesHigh){
//        this.bm = bm;      //Store bitmap that is passed in
//        this.sv = sv;      //Store the view
//        this.numFramesHigh = numFramesHigh;     //Frames (height) in spritesheet
//        this.numFramesWide = numFramesWide;       //Frames (width) in spritesheet
//        height = bm.getHeight() / numFramesHigh;    //height of each individual sprite
//        width = bm.getWidth() / numFramesWide;       //width of each individual sprite
//        this.x = x;      //Set position
//        this.y = y;
//        this.xSpeed = xSpeed;
//    }

    public void onDraw(Canvas canvas){
        update();
        int srcX = currentFrame * width;
//        //int srcY = direction * height;    //reading from example spritesheet rows
        //These rectangles are used to cut out areas on the sprite sheets
        Rect src = new Rect(srcX, 0, srcX + width, height);
        Rect dst = new Rect(getX(), getY(), getX() +width, getY() +height);
        canvas.drawBitmap(bm, src, dst, null);

    }

    public void update(){
        //GameBoardCustomView
        if(health <= 0)
            setAlive(false);

        boardHeight = gameBoard.getHeight();
        boardWidth = gameBoard.getWidth();
//        if(x > boardWidth - bm.getWidth()/SPRITE_SHEET_COLUMNS - xSpeed) xSpeed = -5;
//        if(x + xSpeed < 0) xSpeed = 5;
        setX(getX() + xSpeed);
//        currentFrame = ++currentFrame % SPRITE_SHEET_COLUMNS;
        //For the SurfaceViewExample class
        //Facing down
//        if(x > sv.getWidth() - width - xSpeed){ //Collision with the right bound
//            xSpeed = 0;
//            ySpeed = 5;
//            //direction = 0;    //To change the direction of the sprite if need be
//        }
//        //Going left
//        if(y > sv.getHeight() - height - ySpeed){  //Collision with bottom bound
//            xSpeed = -5;
//            ySpeed = 0;
//            //direction = 1;    //To change the direction of the sprite if need be
//        }
//        //Facing up
//        if(x + xSpeed < 0){     //Collision with left bound
//            x = xSpeed = 0;
//            ySpeed = -5;
//            //direction = 2;    //To change the direction of the sprite if need be
//        }
//        //Going right
//        if(y + ySpeed < 0){     //Collision with top bound
//            y = ySpeed = 0;
//            xSpeed = 5;
//            //direction = 3;    //To change the direction of the sprite if need be
//        }
//
        currentFrame = ++currentFrame % spriteSheetColumns;
//        try{
//            Thread.sleep(100);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//
//        currentFrame = ++currentFrame % numFramesWide;  //%16 ensures we loop back to the beginning of the spritesheet
//        x += xSpeed;
//        y += ySpeed;
    }

    public int takeDamage(int damage){
        health -= damage;
        return health;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
