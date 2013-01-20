package com.daniel.demo;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.daniel.framework.AndroidGame;
import com.daniel.framework.AndroidScreen;
import com.daniel.framework.graphics.Polygon;

public class SquareScreen extends AndroidScreen {
   
   public Polygon square;
   public float[] mMVPMatrix = new float[16];
   public float[] mProjMatrix = new float[16];
   public float[] mVMatrix = new float[16];
   public float[] mRotationMatrix = new float[16];
   public float mAngle = 90.0f;   
   
   public SquareScreen(AndroidGame game) {
      this.androidGame = game;
      square= new Polygon();   
      square   = new Polygon();
      square.setCoord(
            new float[]{ -0.5f,  0.5f, 0.0f,   // top left
                -0.5f, -0.5f, 0.0f,   // bottom left
                 0.5f, -0.5f, 0.0f,   // bottom right
                 0.5f,  0.5f, 0.0f } // top right
      );
      square.setDrawOrder(new short[]{ 0, 1, 2, 0, 2, 3 });
      //mSquare.setDrawOrder(new short[]{ 0, 1, 2});
      square.setColour(0.0f, 1.0f, 1.0f, 1.0f);      
   }   

   @Override
   public void update(float dTime) {
      // TODO Auto-generated method stub
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

      //mTriangle.setColor( (float)mAngle/360.0f, 0.5f, 0.5f);
      //mTriangle.draw(mMVPMatrix);
      square.matrix = mMVPMatrix;      
      square.render();
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
