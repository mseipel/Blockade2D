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
    Paint p = new Paint();
    private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.level1);
    private Bitmap brick = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
    private float canvasHeight;
    private float canvasWidth;
    GameBoard parent;
    Context mContext;
    GameBoard gb;
    float x, y;
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


                sprites.add(createSprite(R.drawable.bowser_sprite));



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
                sprites.add(createSprite(R.drawable.bowser_sprite));



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
//        canvas.drawBitmap(scaledBackground, 0, 0, null);
        canvas.drawBitmap(background, 0, 0, null);
        //Draw the sprites contained in sprites list
        for(Sprite sprite : sprites){
            sprite.onDraw(canvas);
        }

        for(Blockade block : blockades){
            block.onDraw(canvas);
        }
    }

    /**
     * Create a sprite and return it
     * @param resource - the int reference code to the Sprite sheet used to make the sprite
     * @return - the new Sprite
     */
    private Sprite createSprite(int resource){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bm);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(isEmerald()){
                    Blockade brick = new Blockade("emerald", this, BitmapFactory.decodeResource(getResources(), R.drawable.brick), x);
                    blockades.add(brick);
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
