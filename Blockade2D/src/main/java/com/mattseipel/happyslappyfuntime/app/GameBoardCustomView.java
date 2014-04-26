package com.mattseipel.happyslappyfuntime.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Clayton on 4/10/2014.
 */
public class GameBoardCustomView extends SurfaceView implements View.OnTouchListener {

    //Instance variables for the game thread
    private SurfaceHolder holder;   //Used to create callback methods when the SurfaceView is created/changes
    private GameLoop gameLoop;      //The loop that runs the game, technically the engine
    private GameplayActivity gameActivity;  //Used as a reference via context.
    AlertDialog ready;              //Alert dialog to start the game
    AlertDialog gameOver;           //Alert dialog for end of game
    boolean win;                    //Determine the outcome of the game.
    boolean endGame;

    //ArrayLists used for drawing and updating the objects
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    private ArrayList<Blockade> blockades = new ArrayList<Blockade>();

    //Avoid concurrent modification exceptions by creating add and remove lists
    private ArrayList<Blockade> blockadesToAdd = new ArrayList<Blockade>();
    private ArrayList<Blockade> blockadesToRemove = new ArrayList<Blockade>();
    //    private ArrayList<Sprite> spritesToAdd = new ArrayList<Sprite>();     //Currently unused
    private ArrayList<Sprite> spritesToRemove = new ArrayList<Sprite>();

    //Store the background for scaling and drawing
    private Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.level1);
    private Bitmap endGameScreen;
    private Context mContext;           //Store the GameplayActivty context for later use (assigned in constructor)

    //Variables essential to collision detection.
    private static Sprite tempEnemy;
    private Blockade closestToBowser;
    float x, y, distanceFromBowser;

    //Current level, determines the enemy
    private int level = 1;
    private int bowsersKilled = 0;

    //Store the height and width of the canvas, used for scaling the background
    private float canvasHeight;
    private float canvasWidth;

    //Boolean variables to determine which type of blockade is being used.
    private boolean brick;
    private boolean concrete;
    private boolean electric;

    double moneyAmount = 100;

    /**
     * Constructor for the GameBoardCustomView
     * <p/>
     * Store the context.  Initiate the GameLoop.  Store the Holder.  Assign OnTouchListener.
     * Create callbacks with the holder (**surface created**, changed, and destroyed).
     * <p/>
     * Create an alert dialog to allow the player to say they are ready.  This allows the thread to
     * start when OK is pressed so the thread does not start immediately causing frames to be skipped.
     *
     * @param context
     */
    public GameBoardCustomView(Context context, AttributeSet attrs) {
        super(context);
        mContext = context;
        gameActivity = ((GameplayActivity)mContext);
        gameLoop = new GameLoop(this);
        holder = getHolder();       //Get the holder from the surface view
        setOnTouchListener(this);   //This class implements OnTouchListener, assign it.
        holder.addCallback(new SurfaceHolder.Callback() {
            /**
             * When the surface is created, scale the background, and pop up the alert.
             * @param holder
             */
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //Set the thread to run when started.
                gameLoop.setRunning(true);

                //Get the height and the width for scaling.
                canvasHeight = getHeight();
                canvasWidth = getWidth();

                //Scale the background image.
                background = Bitmap.createScaledBitmap(background, (int) canvasWidth, (int) canvasHeight, true);

                //Create and show the 'Ready?' dialog.
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("Ready?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    /**
                     * Start the thread (game) upon clicking 'OK'.
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameLoop.start();
                        ready.cancel();    //Close the dialog
                    }
                });
                //Force the user to click "OK"
                dialog.setCancelable(false);
                ready = dialog.create();
                ready.show();      //Show the 'Ready?' dialog.

                //Originally levels with different enemies, now it is just where Bowser is created.
                //The method still exists for future use.
                levelSelect(level);     //Add the appropriate sprite to the map.
            }

            //Not used for anything.
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            /**
             * When the suface view is destroyed, join the threads, and pray for no crash.
             * @param holder
             */
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoop.setRunning(false);  //End the thread that the game is running on
                while (retry) {
                    try {
                        gameLoop.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Redraw the sprites, blockades, and background.
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //Check if the game is over
        if(endGame)
            endGame();

        //Update the animations
        update();

        //Redraw the background
        canvas.drawBitmap(background, 0, 0, null);

        //Draw the blockades.
        for (Blockade block : blockades) {
            block.onDraw(canvas);
        }

        //Draw the sprites contained in sprites list
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }

        //Determine which image will pop up. Centered.
        if (win) {
            endGame = true;
            endGameScreen = BitmapFactory.decodeResource(getResources(), R.drawable.youwin);
            canvas.drawBitmap(endGameScreen, gameActivity.getDeviceWidth()/2 - endGameScreen.getWidth()/2, gameActivity.getDeviceHeight()/3.5f, null);
        } else if (tempEnemy.getX() > gameActivity.getDeviceWidth()) {
            endGame = true;
            endGameScreen = BitmapFactory.decodeResource(getResources(), R.drawable.youlose);
            canvas.drawBitmap(endGameScreen,gameActivity.getDeviceWidth()/2 - endGameScreen.getWidth()/2, gameActivity.getDeviceHeight()/3.5f, null);
        }

    }

    /**
     * Where the magic happens.
     * Update all the animations, check collisions, and take damage.
     */
    private void update() {
        //Get the first (only for now) sprite, if one exists.
        if (!sprites.isEmpty()) {
            tempEnemy = sprites.get(0);
            gameActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameActivity.setHealth(tempEnemy.getHealth());
                    gameActivity.setCash(moneyAmount);
                    gameActivity.setScore(bowsersKilled);
                }
            });

            //Make sure that blockades actually contains something, avoids divide by zero exceptions
            if (!blockades.isEmpty()) {
                //Assign to the first blockade.
                closestToBowser = blockades.get(0);

                //Iterate through the blockades list
                for (Blockade block : blockades) {
                    //Determine closestToBowser
                    if(block.getX() - tempEnemy.getX() < closestToBowser.getX() - tempEnemy.getX())
                        closestToBowser = block;

                    //Check if the blockade has been destroyed by the enemy, if so add to the remove list.
                    if (!block.isStillStanding())
                        blockadesToRemove.add(block);

                    //Check how far the current blockade is from Bowser/enemy
                    distanceFromBowser = tempEnemy.getX() - block.getX();

                    //Store the closest blockade in the Enemy's path
                    if (distanceFromBowser > (block.getX() - closestToBowser.getX()) && distanceFromBowser > 0) {
                        closestToBowser = block;
                    }
                }

                //Check if an enemy has collided with a blockade
                if (tempEnemy.getX() + 35 > closestToBowser.getX()) {
                    if (tempEnemy.getHealth() <= 0) {
                        ((Bowser) tempEnemy).deathAnimation();
                    } else {
                        //Take damage, both enemy and blockade
                        ((Bowser) tempEnemy).attackAnimation();
                        closestToBowser.takeDamage(((Bowser) tempEnemy).getDps());
                        tempEnemy.takeDamage(closestToBowser.getDPS());

                        //Scale money of electric so it isn't over powered
                        if(gameActivity.isElectric()){
                            moneyAmount += closestToBowser.getDPS() * 0.25; //1/4 of damage dealt
                        }else{
                            moneyAmount += closestToBowser.getDPS() * 0.5;  //1/2 of damage dealt
                        }

                        tempEnemy.setX((int) Math.floor(closestToBowser.getX()) - 30);
                        if (!closestToBowser.isStillStanding())
                            ((Bowser) tempEnemy).walkAnimation();
                    }
                } else {
                    ((Bowser) tempEnemy).walkAnimation();
                }




            }

            //Check if the enemy is still alive, remove if not.
            for (Sprite sprite : sprites) {
                if (!sprite.isAlive())
                    spritesToRemove.add(sprite);
            }

            //If there are blockades to remove, do so.
            if (!blockadesToRemove.isEmpty()) {
                for (Blockade block : blockadesToRemove) {
                    blockades.remove(block);
                    blockadesToRemove.remove(block);
                }
            }

            //If there are sprites to remove, do so.
            if (!spritesToRemove.isEmpty()) {
                for (Sprite sprite : spritesToRemove) {
                    sprites.remove(sprite);
                    spritesToRemove.remove(sprite);
                    bowsersKilled++;
                    if(bowsersKilled < 5){
                        respawn();
                    } else{
                        win = true;
                    }
                }
            }

            //If there are blockades to add, do so.
            if (!blockadesToAdd.isEmpty()) {
                for (Blockade block : blockadesToAdd) {
                    blockades.add(block);
                    blockadesToAdd.remove(block);
                }
            }
        }
    }

    /**
     * When the user clicks, check if any of the blockade buttons are activated.  If so, create them.
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Block off the very top and bottom (fragment area) of the map from touches
                if (y < canvasHeight - 150 && y > 150) {
                    //Create a blockade at the clicked location, locked Y location.
                    if (gameActivity.isBrick()) {
                        //If there are currently no blockades,
                        if (blockades.isEmpty()) {
                            if (blockadeInPath(x, y)) {
                                if(affordable('b')){
                                    Brick brick = new Brick(mContext, x);
                                    blockadesToAdd.add(brick);
                                    moneyAmount -= brick.getCost();
                                }
                            }
                        } else {
                            if (!blockadeInArea(x, y)) {
                                if (blockadeInPath(x, y)) {
                                    if(affordable('b')){
                                        Brick brick = new Brick(mContext, x);
                                        blockadesToAdd.add(brick);
                                        moneyAmount -= brick.getCost();
                                    }
                                }
                            }
                        }
                    } else if (gameActivity.isConcrete()) {
                        if (blockades.isEmpty()) {
                            if (blockadeInPath(x, y)) {
                                if(affordable('c')){
                                    Concrete concrete = new Concrete(mContext, x);
                                    blockadesToAdd.add(concrete);
                                    moneyAmount -=concrete.getCost();
                                }
                            }
                        } else {
                            if (!blockadeInArea(x, y) && blockadeInPath(x, y)) {
                                if(affordable('c')){
                                    Concrete concrete = new Concrete(mContext, x);
                                    blockadesToAdd.add(concrete);
                                    moneyAmount -= concrete.getCost();
                                }
                            }
                        }
                    } else if (gameActivity.isElectric()) {
                        if (blockades.isEmpty() && blockadeInPath(x, y)) {
                            if(affordable('e')){
                                Electric electric = new Electric(mContext, x);
                                blockadesToAdd.add(electric);
                                moneyAmount -= electric.getCost();
                            }
                        } else {
                            if (!blockadeInArea(x, y) && blockadeInPath(x, y)) {
                                if(affordable('e')){
                                    Electric electric = new Electric(mContext, x);
                                    blockadesToAdd.add(electric);
                                    moneyAmount -= electric.getCost();
                                }
                            }
                        }

                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }

        return true;
    }

    /**
     * Check if a blockade is within range of another.
     *
     * @param x
     * @param y
     * @return blocked
     */
    private boolean blockadeInArea(float x, float y) {
        boolean blocked = false;
        //Check if there is a blockade in the same general area.
        if (!blockades.isEmpty()) {
            for (Blockade block : blockades) {
                if (x < block.getX() +  gameActivity.getDeviceWidth()/20 &&
                        x > block.getX() - gameActivity.getDeviceWidth()/20) {
                    Toast.makeText(mContext, "You cannot place a blockade this close to another.", Toast.LENGTH_SHORT).show();
                    blocked = true;
                }
            }
        }
        return blocked;
    }

    /**
     * Checks to make sure that the blockade is in front of the enemy.
     *
     * @param x
     * @param y
     * @return
     */
    private boolean blockadeInPath(float x, float y) {
        if (x < tempEnemy.getX()) {
            Toast.makeText(mContext, "You must place blockades in front of the enemy.", Toast.LENGTH_SHORT).show();
            return false;  //Blockade not in enemy's path
        }

        return true;
    }

    /**
     * Terminate the game loop and finish the activity in 5 seconds
     */
    public void endGame() {
        gameLoop.terminate();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameActivity.finish();
    }

    /**
     * Create the appropriate sprite for different levels.
     *
     * @param level
     */
    private void levelSelect(int level) {
        switch (level) {
            case 1:
                sprites.add(new Bowser(mContext, this, 1000));
                break;
            case 2:
//                sprites.add(createSprite(R.drawable.dk_sprite, 50, 5));
                break;
            case 3:
//                sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
                break;
        }
    }

    /**
     * Check if the player can afford the selected barrier
     * @param type
     * @return
     */
    public boolean affordable(char type){
        switch (type){
            case 'b':
                if(moneyAmount >= Brick.getCost()){
                    return true;
                }else{
                    Toast.makeText(mContext, "You need $" + (int)(Brick.getCost()-moneyAmount) + " more dollars to buy this.",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            case 'c':
                if (moneyAmount >= Concrete.getCost()){
                    return true;
                }else{
                    Toast.makeText(mContext, "You need $" + (int)(Concrete.getCost()-moneyAmount) + " more dollars to buy this.",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            case 'e':
                if(moneyAmount >= Electric.getCost()){
                    return true;
                }else{
                    Toast.makeText(mContext, "You need $" + (int)(Electric.getCost()-moneyAmount) + " more dollars to buy this.",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            default:
                return false;
        }
    }

    /**
     * Respawn Bowser with 1000 more health than before.
     */
    public void respawn(){
        sprites.add(new Bowser(mContext, this, (bowsersKilled+1)*1000));
    }

    //Getters and Setters below.
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
