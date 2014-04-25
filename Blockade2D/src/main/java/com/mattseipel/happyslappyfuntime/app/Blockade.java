package com.mattseipel.happyslappyfuntime.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Matt on 4/18/2014.
 */
public class Blockade {
    private float x;
    private float y;
    private int cost;
    private Bitmap bm;
    private GameBoardCustomView gameBoard;
    private String type;
    private double health;
    private double dps;
    private boolean stillStanding;

    public Blockade(String type, GameBoardCustomView gameBoard, Bitmap bm, float x){
        this.gameBoard = gameBoard;
        this.bm = bm;
        this.setX(x);
        this.type = type;
        cost = determineCost(type);
        type = determineType(type);
        y = gameBoard.getHeight()/3.2f;
        health = initialHealth(type);
        dps = setBlockadePower(type)/20;    //20 frames per second, divide power by 20 for dps
        stillStanding = true;
    }

    private String determineType(String type){
        if (type.equals("brick")){
            return "brick";  //Tier 1
        }else if(type.equals("concrete")){
            return "concrete";  //Tier 2
        }else if(type.equals("electric")){
            return "electric";  //Tier 3
        }else{
            return "invalid";
        }
    }

    private int determineCost(String type){
        if (type.equals("brick")){
            return 25;  //Brick blockade costs $25
        }else if(type.equals("concrete")){
            return 75;  //$75
        }else if(type.equals("electric")){
            return 200;  //$200
        }
        return -1;
    }

    private int initialHealth(String type){
        if (type.equals("brick")){
            return 50;  //Tier 1
        }else if(type.equals("concrete")){
            return 100;  //Tier 2
        }else if(type.equals("electric")){
            return 200;  //Tier 3
        }else{
            return -1;
        }
    }

    private int setBlockadePower(String type){
        if (type.equals("brick")){
            return 30;  //Tier 1
        }else if(type.equals("concrete")){
            return 45;  //Tier 2
        }else if(type.equals("electric")){
            return 50;  //Tier 3
        }else{
            return -1;
        }
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bm, getX(), getY(), null);
    }

    public void takeDamage(double dps){
        if(health - dps <= 0)
            stillStanding = false;
        health -= dps;
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

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isStillStanding() {
        return stillStanding;
    }

    public void setStillStanding(boolean stillStanding) {
        this.stillStanding = stillStanding;
    }

    public double getDPS() {
        return dps;
    }

    public void setDPS(double dps) {
        this.dps = dps;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
