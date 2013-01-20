/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.daniel.framework;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.daniel.framework.graphics.Polygon;
import com.daniel.framework.graphics.ShaderObj;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class AndroidGLRenderer implements GLSurfaceView.Renderer {
   
   
   public AndroidGame androidGame;

    private static final String TAG = "MyGLRenderer";
    private Polygon mTriangle = new Polygon();
    private Polygon mSquare;
    
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjMatrix = new float[16];
    private final float[] mVMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

    // Declare as volatile because we are updating it from another thread
    public volatile float mAngle;
    
    public AndroidGLRenderer(AndroidGame game) {
       androidGame = game;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        

        mTriangle = new Polygon();
        mTriangle.setCoord(
           new float[]{ 
               0.0f,  0.622008459f, 0.0f, 
              -0.5f, -0.311004243f, 0.0f, 
               0.5f, -0.311004243f, 0.0f }
        );
        mTriangle.setColour(0.5f, 0.2f, 0.2f, 1.0f);
        
     
       
        mSquare   = new Polygon();
        mSquare.setCoord(
              new float[]{ -0.5f,  0.5f, 0.0f,   // top left
                  -0.5f, -0.5f, 0.0f,   // bottom left
                   0.5f, -0.5f, 0.0f,   // bottom right
                   0.5f,  0.5f, 0.0f } // top right
        );
        mSquare.setDrawOrder(new short[]{ 0, 1, 2, 0, 2, 3 });
        mSquare.setColour(0.0f, 1.0f, 1.0f, 1.0f);
 }

 @Override
 public void onDrawFrame(GL10 unused) {

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);

        // Draw square
        mSquare.matrix = mMVPMatrix;
        mSquare.render();

        // Create a rotation for the triangle
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);

        // Combine the rotation matrix with the projection and camera view
        Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);

        // Draw triangle
        System.out.println(mAngle);
        
        //mTriangle.setColor( (float)mAngle/360.0f, 0.5f, 0.5f);
        //mTriangle.draw(mMVPMatrix);
        mTriangle.matrix = mMVPMatrix;
        mTriangle.render();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }


    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}

 

