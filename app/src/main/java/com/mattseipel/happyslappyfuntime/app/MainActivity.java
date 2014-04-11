package com.mattseipel.happyslappyfuntime.app;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Type;
import java.util.concurrent.ThreadPoolExecutor;

//Clayton Gillis, Matt Seipel, Drew Scott
//Lane Defense Game
//Final Project for Mobile Computing: Android


public class MainActivity extends Activity {

    //references to the start menu buttons
    Button startGameBTN;
    Button rulesBTN;
    Button quitBTN;

    //typeface for the start menu buttons
    Typeface startMenuFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.changeTypeFace();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    //changes the typeface of various elements
    public void changeTypeFace(){
        startMenuFont = Typeface.createFromAsset(this.getAssets(), "valdemar.ttf");

        startGameBTN = (Button)this.findViewById(R.id.startGameBTN);
        rulesBTN = (Button)this.findViewById(R.id.rulesBTN);
        quitBTN = (Button)this.findViewById(R.id.quitBTN);

        //set the typeface of each button
        startGameBTN.setTypeface(startMenuFont);
        rulesBTN.setTypeface(startMenuFont);
        quitBTN.setTypeface(startMenuFont);
    }

    //onClick method for startGameBTN
    public void startGame(View view){

    }

    //onClick method for rulesBTN
    public void showRules(View view){

    }

    //onClick method for quitBTN
    public void quitApplication(View view){

    }
}
