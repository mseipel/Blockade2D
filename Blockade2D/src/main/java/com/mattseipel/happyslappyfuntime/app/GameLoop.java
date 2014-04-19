package com.mattseipel.happyslappyfuntime.app;

import android.graphics.Canvas;

/**
 * Created by Matt Seipel on 4/13/2014.
 */
public class GameLoop extends Thread{
    static final long FPS = 10;
    private GameBoardCustomView view;
    private boolean running = false;

    public GameLoop(GameBoardCustomView view){
        this.view = view;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    @Override
    public void run(){
        //100 ticks per second
        long tickPS = 1000/FPS;
        long startTime;
        long sleepTime;

        while(running){
            Canvas canvas = null;
            startTime = System.currentTimeMillis();

            try{
                //Make sure nothing else changes the canvas
                canvas = view.getHolder().lockCanvas();

                synchronized (view.getHolder()){
                    //Redraw the canvas
                    view.onDraw(canvas);
                }
            } finally {
                if(canvas != null){
                    //Unlock the canvas
                    view.getHolder().unlockCanvasAndPost(canvas);
                }
            }
            //Determine how long to sleep between ticks
            sleepTime = tickPS - (System.currentTimeMillis() - startTime);
            try{
                if(sleepTime > 0){
                    sleep(sleepTime);
                }else
                    sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void pause(){
        this.pause();
    }

}
