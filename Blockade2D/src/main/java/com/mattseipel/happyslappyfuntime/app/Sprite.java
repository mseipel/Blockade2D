package com.mattseipel.happyslappyfuntime.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Matt Seipel on 4/11/2014.
 */
public class Sprite {
    private int spriteSheetRows;
    private int spriteSheetColumns;
    private int currentRow;
    private static final int MAX_SPEED = 10;
    private int x;
    private int y;
    private int xSpeed;
    int ySpeed;
    int height;
    int width; //Position, speed, and dimensions
    private int frameWidth;
    private int health;
    private float boardWidth, boardHeight;
    private boolean alive;
    private boolean deathComplete;
    Bitmap bm;
    private GameBoardCustomView gameBoard;
    private int currentFrame = 0;   //Current frame of the sprite sheet

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

    public void onDraw(Canvas canvas){
        int srcX = currentFrame * width;
        int srcY = currentRow * height;
//        //int srcY = direction * height;    //reading from example spritesheet rows
        //These rectangles are used to cut out areas on the sprite sheets
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(getX(), getY(), getX() + width, getY() + height);
        canvas.drawBitmap(bm, src, dst, null);

    }

    public void update(){
        boardHeight = getGameBoard().getHeight();
        boardWidth = getGameBoard().getWidth();
        setX(getX() + getxSpeed());

        //Ensure that the current frame never exceeds the number of frames available
        currentFrame = currentFrame++ % spriteSheetColumns;
    }

    public double takeDamage(double damage){
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

    public int getSpriteSheetRows() {
        return spriteSheetRows;
    }

    public void setSpriteSheetRows(int spriteSheetRows) {
        this.spriteSheetRows = spriteSheetRows;
    }

    public int getSpriteSheetColumns() {
        return spriteSheetColumns;
    }

    public void setSpriteSheetColumns(int spriteSheetColumns) {
        this.spriteSheetColumns = spriteSheetColumns;
    }

    public int getCurrentRow() {
        return currentRow;
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

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
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

    public boolean isDeathComplete(){
        return deathComplete;
    }

    public GameBoardCustomView getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoardCustomView gameBoard) {
        this.gameBoard = gameBoard;
    }
}
