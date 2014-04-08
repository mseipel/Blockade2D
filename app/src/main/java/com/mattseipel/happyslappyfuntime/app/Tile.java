package com.mattseipel.happyslappyfuntime.app;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Matt on 4/8/2014.
 */
public class Tile {
    private final String vertexShaderCode = "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "attribute vec2 TexCoordIn;" +
            "varying vec2 TexCoordOut;" +
            "void man() {" +
            " gl_Position = uMVPMatrix * vPosition" +
            " TexCoorOut = TexCoordIn;" +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "uniform sampler2D TexCoordIn;" +
                    "uniform float posX;" +
                    "uniform float posY;" +
                    "varying vec2 TexCoordOut;" +
                    "void main() {" +
                    " gl_FragColor = texture2D(TexCoordIn, vec2(TexCoordOut.x + posX,TexCoordOut.y + posY));"+
            "}";
    private float texture[] = {
            0f, 0f,
            .25f, 0f,
            .25f, .25f,
            0f, .25f,
    };

    private int[] textures = new int[1];
    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final FloatBuffer textureBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mMVPMatrixHandle;

    static final int COORDS_PER_VERTEX = 3;
    static final int COORDS_PER_TEXTURE = 2;
    static float squareCoords[] = { -1f, 1f, 0.0f,
                                    -1f, -1f, 0.0f
                                     1f, -1f, 0.0f
                                     1f, 1f, 0.0f};
    private final short drawOrder[] = {0,1,2,0,2,3};
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    public static int textureStride = COORDS_PER_TEXTURE * 4;

    public Tile(){
        ByteBuffer bb = ByteBuffer.allocateDirect(bb.order(ByteOrder.nativeOrder()));
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        bb = ByteBuffer.allocateDirect(texture.length * 4);
        bb.order(ByteOrder.nativeOrder());
        textureBuffer = bb.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position();

        ByteBuffer dlb = ByteBuffer.allocateDirect(dlb.order(ByteOrder.nativeOrder()));
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        int vertexShader = SBGGameRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = SBGGameRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }
    }
}
