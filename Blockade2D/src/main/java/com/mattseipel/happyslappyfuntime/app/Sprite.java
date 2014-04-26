package com.mattseipel.happyslappyfuntime.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Matt Seipel on 4/11/2014.
 */
public class Sprite {
    //Sprite sheet variables
    private int spriteSheetRows;
    private int spriteSheetColumns;
    private int currentRow;
    private int currentFrame = 0;   //Current frame of the sprite sheet


    //X and Y coordinates for moving
    private int x;
    private int y;

    //Movement speed
    private int xSpeed;

    //Height and width of the sprite frames
    int height;
    int width;

    //Health variables
    private int health;

    //Living variables
    private boolean alive;
    private boolean deathComplete;

    //Sprite sheet bitmap
    private Bitmap bm;

    //Game board reference
    private GameBoardCustomView gameBoard;

    /**
     * Create the sprite object.
     * @param gameBoard
     * @param bm
     * @param health
     * @param spriteSheetRows
     * @param spriteSheetColumns
     */
    public Sprite(GameBoardCustomView gameBoard, Bitmap bm, int health, int spriteSheetRows, int spriteSheetColumns){
        this.gameBoard = gameBoard;
        this.bm = bm;
        this.spriteSheetColumns = spriteSheetColumns;
        this.spriteSheetRows = spriteSheetRows;
        this.width = bm.getWidth() / spriteSheetColumns;
        this.height = bm.getHeight() / spriteSheetRows;
        this.health = health;
        setY(gameBoard.getHeight()/3);
        setX(-200);
        xSpeed = 3;
        setAlive(true);
    }

    /**
     * Redraw the sprite, changing frames each time.
     * @param canvas
     */
    public void onDraw(Canvas canvas){
        int srcX = currentFrame * width;
        int srcY = currentRow * height;

        //These rectangles are used to cut out areas on the sprite sheets
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(getX(), getY(), getX() + width, getY() + height);

        //Draw cut out frame
        canvas.drawBitmap(bm, src, dst, null);

    }

    /**
     * Update the X coordinate of the sprite.
     */
    public void update(){
        setX(getX() + getxSpeed());

        //Ensure that the current frame never exceeds the number of frames available
        currentFrame = currentFrame++ % spriteSheetColumns;
    }

    /**
     * Take damage and subtract from health
     * @param damage
     * @return
     */
    public double takeDamage(double damage){
        health -= damage;
        return health;
    }

    //------------GETTERS AND SETTERS-------------------
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getSpriteSheetColumns() {
        return spriteSheetColumns;
    }

    public void setSpriteSheetColumns(int spriteSheetColumns) {
        this.spriteSheetColumns = spriteSheetColumns;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setDeathComplete(boolean deathComplete){
        this.deathComplete = deathComplete;
    }

    public GameBoardCustomView getGameBoard() {
        return gameBoard;
    }
}
