package com.daniel.demo;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.daniel.framework.AndroidGame;
import com.daniel.framework.AndroidScreen;
import com.daniel.framework.graphics.Polygon;

public class LoadingScreen extends AndroidScreen {
   
   public Polygon square;
   public Polygon triangle;
   public float[] mProjMatrix = new float[16];
   public float[] mTranslateMatrix = new float[16];
   public float[] mMVPMatrix = new float[16];
   public float x = 0.0f;

   public LoadingScreen(AndroidGame game) {
      androidGame = game;
   }
   
   @Override
   public void update(float dTime) {
      /*
      x += 0.5f;
      if (x > 20) x = 20.0f;
      
      square.setCoord(
            new float[]{ 
                 0.0f,   10.0f, 0.0f,   // top left
                 0.0f,   0.0f, 0.0f,   // bottom left 
                 10.0f,  0.0f, 0.0f,   // bottom right
                 10.0f,  10.0f, 0.0f } // top right
      );      
      //square.setDrawOrder(new short[]{ 0, 1, 2, 0, 2, 3 });      
      square.setDrawOrder(new short[]{ 0, 1, 2});
      square.setColour(1.0f, 0.0f, 0.5f, 1.0f);
      */
   }

   @Override
   public void render(float dTime) {
      GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
      
      GLES20.glViewport(0, 0, androidGame.width, androidGame.height);
      
      Matrix.orthoM(mProjMatrix, 0, -0.0f, 1, -1, 1, -1, 1);
      //Matrix.orthoM(mProjMatrix, 0, -androidGame.width, androidGame.width, -androidGame.height, androidGame.height, 0.1f, 10);
      //Matrix.translateM(mTranslateMatrix, 0, 0, 0, 0);
      Matrix.setIdentityM(mTranslateMatrix, 0);
      
      
      Matrix.multiplyMM(mMVPMatrix, 0, mTranslateMatrix, 0, mProjMatrix, 0);
        
      //square.matrix = mProjMatrix;      
      //square.matrix = mMVPMatrix;      
      //square.render();      
      //triangle.matrix = mMVPMatrix;
      triangle.matrix = mProjMatrix;
      triangle.render();
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }

   @Override
   public void displose() {
   }

   
   @Override
   public void init() {
      System.out.println(">>>>> in Loading Screen Init");
      DemoLoader.TX_DEMO = DemoLoader.loadGLTexture(androidGame, "IMG_5656.JPG");         
      
      triangle = new Polygon();   
      triangle.setCoord(
            new float[]{ 
                  0.0f,  0.0f, 0.0f, 
                  0.5f,  0.0f, 0.0f, 
                  0.5f,  0.5f, 0.0f 
                  /*
                  50.0f, 0.0f, 0.0f,
                  50.0f, 0.0f, 0.0f,
                  50.0f, 50.0f, 0.0f
                  */
      });
      triangle.setColour(1.0f, 1.0f, 0.2f, 1.0f);      
      this.doneInit = true;      
      
      
      /*
      square = new Polygon();
      triangle = new Polygon();
      triangle.setCoord(
            new float[]{ 
                  0.0f,  0.622008459f, 0.0f, 
                 -0.5f, -0.311004243f, 0.0f, 
                  0.5f, -0.311004243f, 0.0f 
      });
      triangle.setColour(0.0f, 1.0f, 0.0f, 1.0f);
      
      DemoLoader.TX_DEMO = DemoLoader.loadGLTexture(androidGame, "IMG_5656.JPG");         
      this.doneInit = true;
      */
      
   }

}
