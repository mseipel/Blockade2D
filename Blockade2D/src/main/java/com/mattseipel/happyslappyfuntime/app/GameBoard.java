package com.mattseipel.happyslappyfuntime.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GameBoard extends Activity {

    //variables for all views on the game board
    TextView moneyLabelTV;
    TextView moneyTV;
    TextView healthLabelTV;
    TextView healthTV;
    FrameLayout gameContainer;
    GameBoardCustomView gameView;
    LinearLayout menuBar;

    //Buttons for blockades
    ToggleButton brickBTN;
    ToggleButton concreteBTN;
    ToggleButton electricBTN;

    //Variables to get device height and width
    Display display;
    Point size = new Point();
    int deviceWidth, deviceHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Store device dimensions
        display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        deviceWidth = size.x;
        deviceHeight = size.y;


    }

    @Override
    protected void onStart(){
        super.onStart();
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

    //onClick method for menuBTN
    public void openMenu(View view){

    }

}
