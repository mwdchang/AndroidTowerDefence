package com.daniel.demo;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.daniel.framework.AndroidGame;
import com.daniel.framework.AndroidScreen;
import com.daniel.framework.graphics.Polygon;

public class TriangleScreen extends AndroidScreen {
   
   public Polygon triangle;
   public float[] mMVPMatrix = new float[16];
   public float[] mProjMatrix = new float[16];
   public float[] mVMatrix = new float[16];
   public float[] mRotationMatrix = new float[16];
   public float mAngle = 90.0f;
   
   public TriangleScreen(AndroidGame game) {
      this.androidGame = game;
   }
   
   
   public void init() {
      triangle = new Polygon();   
      triangle.setCoord(
            new float[]{ 
                0.0f,  0.622008459f, 0.0f, 
               -0.5f, -0.311004243f, 0.0f, 
                0.5f, -0.311004243f, 0.0f }
      );
      triangle.setColour(0.5f, 0.2f, 0.2f, 1.0f);      
     
   }
   

   @Override
   public void update(float dTime) {
   }

   @Override
   public void render(float dTime) {
      // Draw background color
      GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

      // Set the camera position (View matrix)
      //Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
      Matrix.orthoM(mProjMatrix, 0, -1, 1, -1, 1, -1, 1);
      Matrix.setLookAtM(mVMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

      // Calculate the projection and view transformation
      Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);


      // Create a rotation for the triangle
      Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, -1.0f);

      // Combine the rotation matrix with the projection and camera view
      Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mMVPMatrix, 0);

      //mTriangle.setColor( (float)mAngle/360.0f, 0.5f, 0.5f);
      //mTriangle.draw(mMVPMatrix);
      triangle.matrix = mMVPMatrix;
      triangle.render();      
   }
   

   @Override
   public void pause() {
      // TODO Auto-generated method stub
   }

   @Override
   public void resume() {
      // TODO Auto-generated method stub
   }

   @Override
   public void displose() {
      // TODO Auto-generated method stub
   }

}
