package com.mattseipel.happyslappyfuntime.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

//Clayton Gillis, Matt Seipel, Drew Scott
//Lane Defense Game
//Final Project for Mobile Computing: Android


public class MainActivity extends Activity {
    //This game runs well on a phone or phone emulators, tablets disorient the toolbar fragment.
    //For best results run on a Nexus 5 or HTC One, or a device running a 1920x1080 display.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    //onClick method for startGameBTN
    public void startGame(View view){
        Intent gameIntent = new Intent(this, GameplayActivity.class);
        startActivity(gameIntent);
    }

    //onClick method for rulesBTN
    public void showRules(View view){
        Intent rulesIntent = new Intent(this, RulesPage.class);
        startActivity(rulesIntent);
    }

    //onClick method for quitBTN
    public void quitApplication(View view){
        this.finish();
    }
}
