package com.mattseipel.happyslappyfuntime.app;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class AnimationTesting extends Activity {

    ImageView bowser;
    Button start;
    Button automate;
    ObjectAnimator obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_testing);

        bowser = (ImageView) findViewById(R.id.imageView);
        bowser.setTag(R.drawable.bowser_step1);
        start = (Button) findViewById(R.id.start);
        automate = (Button) findViewById(R.id.automate_clicks);
    }

    @Override
    protected void onStart() {
        super.onStart();

        automate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    automateClicks();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move10X();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.animation_testing, menu);
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

    public void move10X() {
        obj = ObjectAnimator.ofFloat(bowser, "x", bowser.getX(), bowser.getX() + 10);
        obj.setDuration(100);
        obj.start();
        obj.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                if (getImageResource(bowser) == R.drawable.bowser_step1) {
                    bowser.setImageResource(R.drawable.bowser_step2);
                    bowser.setTag(R.drawable.bowser_step2);
                } else if (getImageResource(bowser) == R.drawable.bowser_step2) {
                    bowser.setImageResource(R.drawable.bowser_step1);
                    bowser.setTag(R.drawable.bowser_step1);
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    public void automateClicks(){
        for(int i = 0; i<100; i++) {
            start.performClick();
            start.setPressed(true);
            start.invalidate();
            start.setPressed(false);
            start.invalidate();
        }
    }

    public int getImageResource(ImageView iv) {
        return (Integer) iv.getTag();
    }
}
