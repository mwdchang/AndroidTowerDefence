package com.daniel.demo;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.daniel.framework.AndroidGame;
import com.daniel.framework.AndroidScreen;
import com.daniel.framework.graphics.ImageTexture;
import com.daniel.framework.graphics.Polygon;
import com.daniel.framework.graphics.BaseTexture;

public class SquareScreen extends AndroidScreen {
   
   public ImageTexture img;
   public float[] mMVPMatrix = new float[16];
   public float[] mProjMatrix = new float[16];
   public float[] mVMatrix = new float[16];
   public float[] mRotationMatrix = new float[16];
   public float mAngle = 90.0f;   
   
   public SquareScreen(AndroidGame game) {
      this.androidGame = game;
   }   
   
   public void init() {
      img= new ImageTexture();   
      img.setDimension(0.2f, 0.2f, 1.0f, 1.0f);
      img.textureID = DemoLoader.TX_DEMO;
            
      //img.loadGLTexture("IMG_5656.JPG");
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
      Matrix.setLookAtM(mVMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

      // Calculate the projection and view transformation
      Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mVMatrix, 0);

      img.matrix = mMVPMatrix;      
      img.render();
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
