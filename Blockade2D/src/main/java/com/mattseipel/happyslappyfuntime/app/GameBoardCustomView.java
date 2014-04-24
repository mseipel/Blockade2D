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
    Toolbar toolbar;

    //Instance variables for the game thread
    private SurfaceHolder holder;   //Used to create callback methods when the SurfaceView is created/changes
    private GameLoop gameLoop;      //The loop that runs the game, technically the engine

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
    private Context mContext;           //Store the GameBoard context for later use (assigned in constructor)

    //Variables essential to collision detection.
    private Sprite tempEnemy;
    private Blockade closestToBowser;
    float x, y, distanceFromBowser;

    //Current level, determines the enemy
    private int level = 1;

    //Store the height and width of the canvas, used for scaling the background
    private float canvasHeight;
    private float canvasWidth;

    //Boolean variables to determine which type of blockade is being used.
    private boolean brick;
    private boolean concrete;
    private boolean electric;

    /**
     * Constructor for the GameBoardCustomView
     * Accepts only Context as parameters.
     * <p/>
     * Store the context.  Initiate the GameLoop.  Store the Holder.  Assign OnTouchListener.
     * Create callbacks with the holder (**surface created**, changed, and destroyed).
     * <p/>
     * Create an alert dialog to allow the player to say they are ready.  This allows the thread to
     * start when OK is pressed so the thread does not start immediately causing frames to be skipped.
     *
     * @param context
     */
    public GameBoardCustomView(Context context) {
        super(context);
        mContext = context;
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
                        dialog.cancel();    //Close the dialog
                    }
                });
                dialog.create();
                dialog.show();      //Show the 'Ready?' dialog.

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
     * Constructor for the GameBoardCustomView
     * Accepts only Context as parameters.
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
                        dialog.cancel();    //Close the dialog
                    }
                });
                dialog.create();
                dialog.show();      //Show the 'Ready?' dialog.

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


    }

    /**
     * Where the magic happens.
     * Update all the animations, check collisions, and take damage.
     */
    private void update() {
//        Get the first (only for now) sprite.
        tempEnemy = sprites.get(0);
        //
//        if(sprites.size() <= 0){
//            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//            builder.setTitle("You win!");
//            if(level <= 2) {
//                builder.setPositiveButton("Next Level?", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        level++;
//                        blockades.clear();
//                        sprites.clear();
//                        switch (level) {
//                            case 1:
//                                sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
//                                break;
//                            case 2:
//                                sprites.add(createSprite(R.drawable.dk_sprite, 50, 5));
//                                break;
//                            case 3:
//                                sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
//                                break;
//                        }
//
//                    }
//                });
//            }
//            else if(level == 3) {
//                builder.setPositiveButton("Start Over?", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        level = 1;
//                        blockades.clear();
//                        sprites.clear();
//                        switch (level) {
//                            case 1:
//                                break;
//                            case 2:
//                                sprites.add(createSprite(R.drawable.dk_sprite, 50, 5));
//                                break;
//                            case 3:
//                                sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
//                                break;
//                        }
//
//                    }
//                });
//            }
//            builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    ((GameBoard)mContext).finish();
//                }
//            });
//            builder.create();
//            builder.show();
//        }

        //Make sure that each list actually contains something, avoids divide by zero exceptions
        if (sprites.size() > 0 && blockades.size() > 0) {
            //Assign to the first blockade.
            closestToBowser = blockades.get(0);

            //Iterate through the blockades list
            for (Blockade block : blockades) {
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

            //Check if the enemy is still alive, remove if not.
            for (Sprite sprite : sprites) {
                if (!sprite.isAlive())
                    spritesToRemove.add(sprite);
            }

            //Check if an enemy has collided with a blockade
            if (tempEnemy.getX() + 30 > closestToBowser.getX()) {
                //Take damage, both enemy and blockade
                ((Bowser) tempEnemy).attackAnimation();
                closestToBowser.takeDamage(30);
                tempEnemy.takeDamage(closestToBowser.getPower());
                tempEnemy.setX((int) Math.floor(closestToBowser.getX()) - 80);
            } else {
                ((Bowser) tempEnemy).walkAnimation();
            }

            //If there are blockades to remove, do so.
            if (blockadesToRemove.size() > 0) {
                for (Blockade block : blockadesToRemove) {
                    blockades.remove(block);
                    blockadesToRemove.remove(block);
                }
            }

            //If there are sprites to remove, do so.
            if (spritesToRemove.size() > 0) {
                for (Sprite sprite : spritesToRemove) {
                    sprites.remove(sprite);
                    spritesToRemove.remove(sprite);
                }
            }

            //If there are blockades to add, do so.
            if (blockadesToAdd.size() > 0) {
                for (Blockade block : blockadesToAdd) {
                    blockades.add(block);
                    blockadesToAdd.remove(block);
                }
            }

        }

    }

    /**
     * Create a sprite and return it
     * @param resource - the int reference code to the Sprite sheet used to make the sprite
     * @param health - passed to the constructor
     * @param spriteColumns - passed to the constructor
     * @return - the new Sprite
     */
//    private Sprite createSprite(int resource, int health, int spriteColumns){
//        //Create the bitmap and send to the constructor
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), resource);
//        return new Sprite(this, bm, health, spriteColumns);
//    }

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
                if (y < canvasHeight - 150 && y > 150) {
                    //Create a blockade at the clicked location, locked Y location.
                    if (((GameplayActivity) mContext).isBrick()) {
                        if (blockades.isEmpty()) {
                            if (blockadeInPath(x, y)) {
                                Blockade brick = new Blockade("brick", this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                                        R.drawable.rubblewall), 150, 200, true), x);
                                blockades.add(brick);
                            }
                        } else {
                            if (!blockadeInArea(x, y)) {
                                if (blockadeInPath(x, y)) {
                                    Blockade brick = new Blockade("brick", this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                                            R.drawable.rubblewall), 150, 200, true), x);
                                    blockades.add(brick);
                                }
                            }
                        }
                    } else if (((GameplayActivity) mContext).isConcrete()) {
                        if (blockades.isEmpty()) {
                            if (blockadeInPath(x, y)) {
                                Blockade concrete = new Blockade("concrete", this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                                        R.drawable.templewall), 150, 200, true), x);
                                blockades.add(concrete);
                            }
                        } else {
                            if (!blockadeInArea(x, y) && blockadeInPath(x, y)) {
                                Blockade concrete = new Blockade("concrete", this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                                        R.drawable.templewall), 150, 200, true), x);
                                blockades.add(concrete);
                            }
                        }
                    } else if (((GameplayActivity) mContext).isElectric()) {
                        if (blockades.isEmpty() && blockadeInPath(x, y)) {
                            Blockade electric = new Blockade("electric", this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                                    R.drawable.electricalbarrier), 150, 200, true), x);
                            blockades.add(electric);
                        } else {
                            if (!blockadeInArea(x, y) && blockadeInPath(x, y)) {
                                Blockade electric = new Blockade("electric", this, Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                                        R.drawable.electricalbarrier), 150, 200, true), x);
                                blockades.add(electric);
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
                if (x < block.getX() + 100 && x > block.getX() - 100) {
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
     * Create the appropriate sprite for different levels.
     *
     * @param level
     */
    private void levelSelect(int level) {
        switch (level) {
            case 1:
                sprites.add(new Bowser(mContext, this));
                break;
            case 2:
//                sprites.add(createSprite(R.drawable.dk_sprite, 50, 5));
                break;
            case 3:
//                sprites.add(createSprite(R.drawable.bowser_sprite, 100, 16));
                break;
        }
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
