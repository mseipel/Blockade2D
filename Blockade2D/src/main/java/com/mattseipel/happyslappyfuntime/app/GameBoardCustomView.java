package com.mattseipel.happyslappyfuntime.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Clayton on 4/10/2014.
 */
public class GameBoardCustomView extends SurfaceView implements View.OnTouchListener{

    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoop gameLoop;
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    private ArrayList<Blockade> blockades = new ArrayList<Blockade>();

    //Avoid concurrent modification exceptions by creating add and remove lists
    private ArrayList<Blockade> blockadesToAdd = new ArrayList<Blockade>();
    private ArrayList<Blockade> blockadesToRemove = new ArrayList<Blockade>();
    private ArrayList<Sprite> spritesToAdd = new ArrayList<Sprite>();
    private ArrayList<Sprite> spritesToRemove = new ArrayList<Sprite>();

    int level = 1;
    Paint p = new Paint();
    private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.level1);
    private float canvasHeight;
    private float canvasWidth;
    GameBoard parent;
    Context mContext;
    GameBoard gb;
    Sprite tempBowser;
    Blockade closestToBowser;
    float x, y, distanceFromBowser;
    private boolean emerald;
    private boolean concrete;
    private boolean electric;

    public GameBoardCustomView(Context context){
        super(context);
        mContext = context;
        gb = (GameBoard)mContext;
        gameLoop = new GameLoop(this);
        holder = getHolder();
        setOnTouchListener(this);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoop.setRunning(true);


                canvasHeight = getHeight();
                canvasWidth = getWidth();

                background = Bitmap.createScaledBitmap(background, (int)canvasWidth, (int)canvasHeight, true);

                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("Ready?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameLoop.start();
                        dialog.cancel();
                    }
                });
                dialog.create();
                dialog.show();

                levelSelect(level);

            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoop.setRunning(false);  //End the thread that the game is running on
                while (retry){
                    try {
                        gameLoop.join();
                        retry = false;
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
//        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.dk_sprite);
//        sprites.add(createSprite(R.drawable.dk_sprite));
        //sprites.add(createSprite(R.drawable.toad_sprite));
    }

    public GameBoardCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        gb = (GameBoard)mContext;
        gameLoop = new GameLoop(this);
        holder = getHolder();
        setOnTouchListener(this);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoop.setRunning(true);
                gameLoop.start();

                canvasHeight = getHeight();
                canvasWidth = getWidth();

                background = Bitmap.createScaledBitmap(background, (int)canvasWidth, (int)canvasHeight - 25, true);
                switch(level){
                    case 1:
                        break;
                    case 2:
                        sprites.add(createSprite(R.drawable.dk_sprite, 50, 5));
                        break;
                    case 3:
                        sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
                        break;
                }



            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoop.setRunning(false);  //End the thread that the game is running on
                while (retry){
                    try {
                        gameLoop.join();
                        retry = false;
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
//        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.dk_sprite);
//        sprites.add(createSprite(R.drawable.dk_sprite));
        //sprites.add(createSprite(R.drawable.toad_sprite));
    }

    @Override
    protected void onDraw(Canvas canvas){
        update();
//        canvas.drawBitmap(scaledBackground, 0, 0, null);
        canvas.drawBitmap(background, 0, 0, null);

        for(Blockade block : blockades){
            block.onDraw(canvas);
        }

        //Draw the sprites contained in sprites list
        for(Sprite sprite : sprites){
            sprite.onDraw(canvas);
        }


    }

    private void update(){
//        if(sprites.size() <= 0){
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setTitle("You win!");
//            if(level <= 2) {
//                builder.setPositiveButton("Next Level?", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        level++;
//                        blockades.clear();
//                        sprites.clear();
//                        switch (level) {
//                            case 1:
//                                sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
//                                break;
//                            case 2:
//                                sprites.add(createSprite(R.drawable.dk_sprite, 50, 5));
//                                break;
//                            case 3:
//                                sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
//                                break;
//                        }
//
//                    }
//                });
//            }
//            else if(level == 3) {
//                builder.setPositiveButton("Start Over?", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        level = 1;
//                        blockades.clear();
//                        sprites.clear();
//                        switch (level) {
//                            case 1:
//                                break;
//                            case 2:
//                                sprites.add(createSprite(R.drawable.dk_sprite, 50, 5));
//                                break;
//                            case 3:
//                                sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
//                                break;
//                        }
//
//                    }
//                });
//            }
//            builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    ((GameBoard)mContext).finish();
//                }
//            });
//            builder.create();
//            builder.show();
//        }

        if(sprites.size() > 0&& blockades.size() > 0) {
            tempBowser = sprites.get(0);
            closestToBowser = blockades.get(0);
            for (Blockade block : blockades) {
                if(!block.isStillStanding())
                    blockadesToRemove.add(block);

                distanceFromBowser = tempBowser.getX() - block.getX();
                if (distanceFromBowser > (block.getX() - closestToBowser.getX())) {
                    closestToBowser = block;
                }
            }

            for(Sprite sprite : sprites){
                if(!sprite.isAlive())
                    spritesToRemove.add(sprite);
            }

            if (tempBowser.getX() + 30 > closestToBowser.getX()) {
                closestToBowser.takeDamage(30);
                tempBowser.takeDamage(closestToBowser.getPower());
                tempBowser.setX((int)Math.floor(closestToBowser.getX()) - 80);
            }

            if(blockadesToRemove.size() > 0){
                for(Blockade block : blockadesToRemove){
                    blockades.remove(block);
                }
            }

            if(spritesToRemove.size() > 0){
                for(Sprite sprite : spritesToRemove){
                    sprites.remove(sprite);
                }
            }

            if(blockadesToAdd.size() > 0){
                for(Blockade block : blockadesToRemove){
                    blockades.add(block);
                }
            }

        }

    }

    /**
     * Create a sprite and return it
     * @param resource - the int reference code to the Sprite sheet used to make the sprite
     * @return - the new Sprite
     */
    private Sprite createSprite(int resource, int health, int spriteColumns){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bm, health, spriteColumns);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(isEmerald()){
                    Blockade emerald = new Blockade("emerald", this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rubblewall), 150, 200, true), x);
                    blockades.add(emerald);
                }else if(isConcrete()){
                    Blockade concrete = new Blockade("concrete", this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.templewall), 150, 200, true), x);
                    blockades.add(concrete);
                }else if(isElectric()){
                    Blockade electric = new Blockade("electric", this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.electricalbarrier), 150, 200, true), x);
                    blockades.add(electric);
                }
                break;
            case MotionEvent.ACTION_UP:
//                x = me.getX();
//                y = me.getY();
                break;
            case MotionEvent.ACTION_MOVE:
//                x = me.getX();
//                y = me.getY();
                break;
        }
        return true;
    }

    private void levelSelect(int level){
        switch(level){
            case 1:
                sprites.add(createSprite(R.drawable.dk_sprite, 25, 5));
                break;
            case 2:
                sprites.add(createSprite(R.drawable.dk_sprite, 50, 5));
                break;
            case 3:
                sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
                break;
        }
    }

    public void setActivity(GameBoard parentActivity){
        parent = parentActivity;
    }

    public boolean isEmerald() {
        return emerald;
    }

    public void setEmerald(boolean emerald) {
        this.emerald = emerald;
    }

    public boolean isConcrete() {
        return concrete;
    }

    public void setConcrete(boolean concrete) {
        this.concrete = concrete;
    }

    public boolean isElectric() {
        return electric;
    }

    public void setElectric(boolean electric) {
        this.electric = electric;
    }
}
