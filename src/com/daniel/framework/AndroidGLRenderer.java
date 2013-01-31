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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.daniel.demo.SquareScreen;
import com.daniel.demo.TriangleScreen;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

public class AndroidGLRenderer implements GLSurfaceView.Renderer {
   
   
   public AndroidGame androidGame;

   private static final String TAG = "MyGLRenderer";
    
    
   public AndroidGLRenderer(AndroidGame game) {
      androidGame = game;
   }
   
   @Override 
   public void onSurfaceCreated(GL10 unused, EGLConfig config) {
      // Set the background frame color
      GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
      androidGame.renderEngine.init(); 
   }
   
   
   @Override
   public void onDrawFrame(GL10 unused) {
      // Check if anything needs initialization
      if (false == androidGame.androidScreen.doneInit) {
         androidGame.androidScreen.init();
         androidGame.androidScreen.doneInit = true;
      }
      
      if (androidGame.androidScreen.doneInit) 
         androidGame.androidScreen.update(0);
      
      if (androidGame.androidScreen.doneInit) 
         androidGame.renderEngine.render();
         //androidGame.androidScreen.render(0);   
   }


   @Override 
   public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        androidGame.width = width;
        androidGame.height = height;
        
        System.out.println("=== " + androidGame.width);
        System.out.println("=== " + androidGame.height);
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

 

