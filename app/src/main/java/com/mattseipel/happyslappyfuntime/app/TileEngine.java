package com.mattseipel.happyslappyfuntime.app;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Matt on 4/8/2014.
 */
public class TileEngine {


    private void drawTiles(int[][] map){
        float tileLocY = 0f;
        float tileLocX = 0f;
        Matrix.translateM(mTMatrix, 0, tileLocX, tileLocT, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mTMatrix, 0);

        for(int x=0; x<10; x++){
            for(int y=0; y<10; y++){
                switch(map[x][y]){
                    case 1:
                        tiles.draw(mMPVMatrix, .75f, .75, spriteSheets, SBG_TILE_PTR);
                        break;
                    case 0:
                        tiles.draw(mMPVMatrix, .75f, .75, spriteSheets, SBG_TILE_PTR);
                        break;
                }
                tileLocY += .50;
            }
            tileLocY = 0f;
            tileLocX += .50;
        }
    }

    public class Renderer implements GLSurfaceView.Renderer{

        private final float[] mTMatrix = new float[16];

        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl10, int i, int i2) {

        }

        @Override
        public void onDrawFrame(GL10 gl10) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
            Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 1.0f, 0.0f);
            Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);
            drawTiles();
        }
    }

}
