package com.mattseipel.happyslappyfuntime.app;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameBoard extends Activity {

    //Typefaces for the game board
    Typeface gameBoardFont;
    Typeface numberFont;

    //variables for all views on the game board
    TextView moneyLabelTV;
    TextView moneyTV;
    TextView healthLabelTV;
    TextView healthTV;
    Button menuBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);

        this.changeTypeface();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_board, menu);
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
    public void changeTypeface(){
        gameBoardFont = Typeface.createFromAsset(this.getAssets(), "valdemar.ttf");
        numberFont = Typeface.createFromAsset(this.getAssets(), "Blackwood Castle.ttf");

        moneyLabelTV = (TextView)this.findViewById(R.id.moneyLabelTV);
        moneyTV = (TextView)this.findViewById(R.id.moneyTV);
        healthLabelTV = (TextView)this.findViewById(R.id.healthLabelTV);
        healthTV = (TextView)this.findViewById(R.id.healthTV);

        menuBTN = (Button)this.findViewById(R.id.menuBTN);

        //set the typeface for the text views
        moneyLabelTV.setTypeface(gameBoardFont);
        moneyTV.setTypeface(numberFont);
        healthLabelTV.setTypeface(gameBoardFont);
        healthTV.setTypeface(numberFont);

        //set the typeface for the menu button
        menuBTN.setTypeface(gameBoardFont);
    }

    //onClick method for menuBTN
    public void openMenu(View view){

    }

}
