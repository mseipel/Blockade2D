package com.mattseipel.happyslappyfuntime.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class GameplayActivity extends Activity {
    Toolbar toolbar;

    private boolean brick;
    private boolean concrete;
    private boolean electric;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        toolbar = new Toolbar();
        fragmentTransaction.add(R.id.toolbar_fragment, toolbar);
        fragmentTransaction.commit();
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


    public void brickClicked(View view){
        brick = true;
        concrete = false;
        electric = false;
    }

    public void concreteClicked(View view){
        brick = false;
        concrete = true;
        electric = false;
    }

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
}
