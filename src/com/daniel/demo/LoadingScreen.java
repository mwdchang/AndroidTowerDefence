package com.daniel.demo;

import java.util.List;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.daniel.framework.AndroidGame;
import com.daniel.framework.AndroidScreen;
import com.daniel.framework.TouchEvent;
import com.daniel.framework.graphics.GEntity;
import com.daniel.framework.graphics.Polygon;

public class LoadingScreen extends AndroidScreen {
   
   public Polygon square;
   public Polygon triangle;
   public float[] mProjMatrix = new float[16];
   public float[] mTranslateMatrix = new float[16];
   public float[] mMVPMatrix = new float[16];
   public float x = 0.0f;
   public float y = 0.0f;
   public float dx, dy;
   
   public GEntity e1;

   public LoadingScreen(AndroidGame game) {
      androidGame = game;
      dy = (float)(Math.random() - 0.5) * 0.15f;
      dx = (float)(Math.random() - 0.5) * 0.15f;
   }
   
   @Override
   public void update(float dTime) {
      List<TouchEvent> list = androidGame.androidTouchHandler.getTouchEvents();
      for (TouchEvent t : list) {
         if (t.type == TouchEvent.TOUCH_UP) {
            androidGame.setScreen( this.next );
            return;
         }
      }
      
      
      x += dx;
      y += dy;
      
      if (x > 1) dx = -dx;
      if (x < -1) dx = -dx;
      if (y > 1) dy = -dy;
      if (y < -1) dy = -dy;
      
      square.setCoord(
            new float[]{ 
                 x,      y+0.1f, 0.0f,   // top left
                 x,      y, 0.0f,   // bottom left 
                 x+0.1f, y, 0.0f,   // bottom right
                 x+0.1f, y+0.1f, 0.0f } // top right
      );      
      square.setDrawOrder(new short[]{ 0, 1, 2, 0, 2, 3 });      
      //square.setDrawOrder(new short[]{ 0, 1, 2});
      square.setColour(1.0f, 0.0f, 0.5f, 1.0f);
   }
   
   
   
   

   @Override
   public void render(float dTime) {
      GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
      GLES20.glViewport(0, 0, androidGame.width, androidGame.height);
      
      Matrix.orthoM(mProjMatrix, 0, -1, 1, -1, 1, -1, 1);
      //Matrix.orthoM(mProjMatrix, 0, -androidGame.width, androidGame.width, -androidGame.height, androidGame.height, 0.1f, 10);
      //Matrix.translateM(mTranslateMatrix, 0, 0, 0, 0);
      
        
      //square.matrix = mProjMatrix;      
      //square.matrix = mMVPMatrix;      
      //square.render();      
      //triangle.matrix = mMVPMatrix;
      triangle.matrix = mProjMatrix;
      square.matrix = mProjMatrix;      
      triangle.render();
      square.render();      
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
      
      e1 = new GEntity();
      e1.cx = 100.0f;
      e1.cy = 100f;
      e1.width = 100;
      e1.height = 100;
      e1.textureId = DemoLoader.TX_DEMO;
      
      androidGame.renderEngine.addObject(e1);
      
      square = new Polygon();
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
      
   }

}
