package com.mattseipel.happyslappyfuntime.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;


public class SurfaceViewExample extends Activity implements View.OnTouchListener{
    SpecialView v;
    Bitmap bm;
    float x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new SpecialView(this);
        v.setOnTouchListener(this);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.dk_sprite);
        x = y = 0;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_board);
    }

    @Override
    protected void onPause(){
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        v.resume();
    }

    public class SpecialView extends SurfaceView implements Runnable{
        Thread thread = null;
        SurfaceHolder holder;
        boolean running = false;
        Sprite sprite;

        public SpecialView(Context context){
            super(context);
            holder = getHolder();
        }

        public void run(){
//            sprite = new Sprite(SpecialView.this, bm, 5, 1);

            while(running){
                //performs canvas drawing
                if(!holder.getSurface().isValid()){
                    continue;
                }

                Canvas canvas = holder.lockCanvas();
                onDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }

        protected void onDraw(Canvas canvas){
            canvas.drawARGB(255,0,0,0);
//            canvas.drawBitmap(bm, x-(bm.getWidth()/2), y-(bm.getHeight()/2), null);  //Draws the spritesheet
            sprite.onDraw(canvas);
        }

        public void pause(){
            running = false;
            while(true){  //End the thread
                try{
                    thread.join();  //Join threads
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }
            thread = null;  //Null the thread
        }

        public void resume(){
            running = true;
            thread = new Thread(this); //Calls run method
            thread.start();
        }

        public boolean onTouch(View v, MotionEvent me){
            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            switch(me.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = me.getX();
                    y = me.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x = me.getX();
                    y = me.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    x = me.getX();
                    y = me.getY();
                    break;
            }
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.surface_view_example, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
