package com.mattseipel.happyslappyfuntime.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Clayton on 4/10/2014.
 */
public class GameBoardCustomView extends SurfaceView {

    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoop gameLoop;
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    Paint p = new Paint();



    public GameBoardCustomView(Context context){
        super(context);

        gameLoop = new GameLoop(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoop.setRunning(true);
                gameLoop.start();
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
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.dk_sprite);
        sprites.add(createSprite(R.drawable.bowser_walk));
        sprites.add(createSprite(R.drawable.dk_sprite));
        //sprites.add(createSprite(R.drawable.toad_sprite));
    }
    public GameBoardCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        gameLoop = new GameLoop(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoop.setRunning(true);
                gameLoop.start();
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
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.dk_sprite);
        sprites.add(createSprite(R.drawable.bowser_walk));
        sprites.add(createSprite(R.drawable.dk_sprite));
        //sprites.add(createSprite(R.drawable.toad_sprite));
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //Draw the sprites contained in sprites list
        for(Sprite sprite : sprites){
            sprite.onDraw(canvas);
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
}
