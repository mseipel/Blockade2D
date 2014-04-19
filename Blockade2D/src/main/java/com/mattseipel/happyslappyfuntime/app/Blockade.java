package com.mattseipel.happyslappyfuntime.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Matt on 4/18/2014.
 */
public class Blockade {
    private float x;
    private float y;
    private float boardWidth;
    private float boardHeight; //Track placement of the blockades
    private Bitmap bm;
    private GameBoardCustomView gameBoard;
    private String type;
    private int health;
    private int power;
    private boolean stillStanding;

    public Blockade(String type, GameBoardCustomView gameBoard, Bitmap bm, float x){
        this.gameBoard = gameBoard;
        this.bm = bm;
        this.setX(x);
        this.type = type;
        type = determineType(type);
        setY(gameBoard.getHeight()/3.2f);
        setHealth(initialHealth(type));
        setPower(setBlockadePower(type));
        setStillStanding(true);
    }

    private String determineType(String type){
        if (type.equals("emerald")){
            return "emerald";  //Tier 1
        }else if(type.equals("concrete")){
            return "concrete";  //Tier 2
        }else if(type.equals("electric")){
            return "electric";  //Tier 3
        }else{
            return "invalid";
        }
    }

    private int initialHealth(String type){
        if (type.equals("emerald")){
            return 50;  //Tier 1
        }else if(type.equals("concrete")){
            return 125;  //Tier 2
        }else if(type.equals("electric")){
            return 200;  //Tier 3
        }else{
            return -1;
        }
    }

    private int setBlockadePower(String type){
        if (type.equals("emerald")){
            return 10;  //Tier 1
        }else if(type.equals("concrete")){
            return 20;  //Tier 2
        }else if(type.equals("electric")){
            return 30;  //Tier 3
        }else{
            return -1;
        }
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bm, getX(), getY(), null);
    }

    public void takeDamage(int power){
        if(health - power <= 0)
            stillStanding = false;
        health -= power;
    }

    private void update(){

    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isStillStanding() {
        return stillStanding;
    }

    public void setStillStanding(boolean stillStanding) {
        this.stillStanding = stillStanding;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
