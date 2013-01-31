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
   public float mAngle = 0.0f;
   
   public TriangleScreen(AndroidGame game) {
      this.androidGame = game;
   }
   
   
   public void init() {
      triangle = new Polygon();   
      triangle.setCoord(
            /*
            new float[]{ 
                0.0f,  0.622008459f, 0.0f, 
               -0.5f, -0.311004243f, 0.0f, 
                0.5f, -0.311004243f, 0.0f }
                */
            new float[]{ 
                0.0f,  62.2008459f, 0.0f, 
               -50f, -31.1004243f, 0.0f, 
                50f, -31.1004243f, 0.0f }
      );
      triangle.setColour(0.5f, 0.2f, 0.2f, 1.0f);      
      this.doneInit = true;
   }
   

   @Override
   public void update(float dTime) {
   }

   @Override
   public void render(float dTime) {
      // Draw background color 
      GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

      // Set the camera position (View matrix)
      float ratio = (float)androidGame.height/(float)androidGame.width;
      float w = androidGame.width;
      float h = androidGame.height;
      //Matrix.orthoM(mProjMatrix, 0, -ratio, ratio, -1, 1, -1, 1);
      //Matrix.orthoM(mProjMatrix, 0, -1, 1, -h/w, h/w, -1, 1);
      //Matrix.orthoM(mProjMatrix, 0, -1, 1, -20, 20, -1, 1);
      //Matrix.orthoM(mProjMatrix, 0, -w, w, -h, h, -1, 1);
      Matrix.orthoM(mProjMatrix, 0, -w, w, -h, h, -1, 1);
      //Matrix.orthoM(mProjMatrix, 0, 0, w, 0, h, -1, 1);
      //Matrix.orthoM(mProjMatrix, 0, -1, 1, -1, 1, -1, 1);
      


      Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
      Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mProjMatrix, 0);

      triangle.matrix = mMVPMatrix;
      //triangle.matrix = mProjMatrix;
      triangle.render();      
      mAngle += 1.0f;
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
