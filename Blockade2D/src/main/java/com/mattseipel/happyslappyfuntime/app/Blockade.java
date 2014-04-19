package com.mattseipel.happyslappyfuntime.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Matt on 4/18/2014.
 */
public class Blockade {
    private float x, y, boardWidth, boardHeight; //Track placement of the blockades
    private Bitmap bm;
    private GameBoardCustomView gameBoard;
    private String type;
    private int health;
    boolean stillStanding;

    public Blockade(String type, GameBoardCustomView gameBoard, Bitmap bm, float x){
        this.gameBoard = gameBoard;
        this.bm = bm;
        this.x = x;
        this.type = type;
        type = determineType(type);
        y = gameBoard.getHeight()/3;
        health = initialHealth(type);
        stillStanding = true;
    }

    private String determineType(String type){
        if (type.equals("brick")){
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
        if (type.equals("brick")){
            return 50;  //Tier 1
        }else if(type.equals("concrete")){
            return 125;  //Tier 2
        }else if(type.equals("electric")){
            return 200;  //Tier 3
        }else{
            return -1;
        }
    }

    private int calcHealth(int damage){
        if(health - damage <= 0){
            stillStanding = false;
            return 0;
        }
        return health -= damage;
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bm, x, y, null);

    }

    private void update(){

    }


}
