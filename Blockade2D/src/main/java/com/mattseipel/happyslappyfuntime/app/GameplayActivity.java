package com.mattseipel.happyslappyfuntime.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class GameplayActivity extends Activity {
    //Fragment variables.
    Toolbar toolbar;
    Score scoreFrag;

    //Blockade selectors and win variable
    private boolean brick;
    private boolean concrete;
    private boolean electric;
    private boolean win;

    //TextView variables
    TextView healthTV;
    TextView moneyTV;
    TextView bowsersKilledTV;

    //Money, health, and amount of bowsers killed
    private double cash = 100;
    private int health = 100;
    private int score = 0;

    //Variables to get device height and width
    Display display;
    Point size = new Point();
    private int deviceWidth;
    private int deviceHeight;

    /**
     * Create the activity
     * Load the fragments
     * Load in the text views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        //Store device dimensions
        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        deviceWidth = size.x;
        deviceHeight = size.y;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        toolbar = new Toolbar();
        scoreFrag = new Score();
        fragmentTransaction.add(R.id.toolbar_fragment, toolbar);
        fragmentTransaction.add(R.id.score_fragment, scoreFrag);
        fragmentTransaction.commit();

        healthTV = (TextView)findViewById(R.id.health);
        moneyTV = (TextView)findViewById(R.id.money);
        bowsersKilledTV = (TextView)findViewById(R.id.scoreTV);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gameplay, menu);
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

    /**
     * Update the money text view
     * @param moneyIn
     */
    public void setMoneyToolbar(double moneyIn){
        moneyTV.setText(String.valueOf((int)Math.floor(cash)));
    }

    /**
     * Update the health text view
     * @param healthIn
     */
    public void setHealthToolbar(int healthIn){
        healthTV.setText(String.valueOf(health));
    }

    /**
     * Update the score text view
     * @param scoreIn
     */
    public void setScoreCount(int scoreIn){
        bowsersKilledTV.setText(String.valueOf(scoreIn));
    }

    /**
     * Set booleans to corresponding values for brick
     * @param view
     */
    public void brickClicked(View view){
        brick = true;
        concrete = false;
        electric = false;
    }

    /**
     * Set booleans to corresponding values for concrete
     * @param view
     */
    public void concreteClicked(View view){
        brick = false;
        concrete = true;
        electric = false;
    }

    /**
     * Set booleans to corresponding values for electric
     * @param view
     */
    public void electricClicked(View view){
        brick = false;
        concrete = false;
        electric = true;
    }

    public boolean isBrick() {
        return brick;
    }

    public void setBrick(boolean brick) {
        this.brick = brick;
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

    public double getCash() {
        return cash;
    }

    //Set cash and call the update text view method.  Only way this would work.  Something something threading rules.
    public void setCash(double cash) {
        this.cash = cash;
        setMoneyToolbar(cash);
    }

    public int getHealth() {
        return health;
    }

    //Set health and call the update text view method.  Only way this would work.  Something something threading rules.
    public void setHealth(int health) {
        this.health = health;
        setHealthToolbar(health);
    }

    //Set score and call the update text view method.  Only way this would work.  Something something threading rules.
    public void setScore(int score){
        this.score = score;
        setScoreCount(score);
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getDeviceWidth() {
        return deviceWidth;
    }

    public void setDeviceWidth(int deviceWidth) {
        this.deviceWidth = deviceWidth;
    }

    public int getDeviceHeight() {
        return deviceHeight;
    }

    public void setDeviceHeight(int deviceHeight) {
        this.deviceHeight = deviceHeight;
    }
}
