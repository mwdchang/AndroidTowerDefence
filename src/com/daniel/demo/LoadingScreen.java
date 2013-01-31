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
   public float dx2, dy2;
   
   public GEntity e1;
   public GEntity e2;

   public LoadingScreen(AndroidGame game) {
      androidGame = game;
      dy = (float)(Math.random() - 0.5)*1; 
      dx = (float)(Math.random() - 0.5)*1; 
      dy2 = (float)(Math.random() - 0.5)*1; 
      dx2 = (float)(Math.random() - 0.5)*1; 
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
      
      androidGame.renderEngine.clearEngine();
      e1.textureId = DemoLoader.TX_DEMO;
      e1.orientation += 2;
      androidGame.renderEngine.addObject(e1);
     
      e2.textureId = DemoLoader.TX_DEMO2;
      e2.orientation -= 2;
      androidGame.renderEngine.addObject(e2);
      
      e1.cx += dx;
      e1.cy += dy;
      
      e2.cx += dx2;
      e2.cy += dy2;
      
      if (e1.cx > androidGame.width) dx = -dx;
      if (e1.cx < -androidGame.width) dx = -dx;
      if (e1.cy > androidGame.height) dy = -dy;
      if (e1.cy < -androidGame.height) dy = -dy;
      
      if (e2.cx > androidGame.width) dx2 = -dx2;
      if (e2.cx < -androidGame.width) dx2 = -dx2;
      if (e2.cy > androidGame.height) dy2 = -dy2;
      if (e2.cy < -androidGame.height) dy2 = -dy2;
      
   }
   
   
   
   

   @Override
   public void render(float dTime) {
      GLES20.glViewport(0, 0, androidGame.width, androidGame.height);
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }

   @Override
   public void displose() {
      this.doneInit = false;
   }

   
   @Override
   public void init() {
      System.out.println(">>>>> in Loading Screen Init");
      DemoLoader.TX_DEMO = DemoLoader.loadGLTexture(androidGame, "IMG_5656.JPG");         
      DemoLoader.TX_DEMO2 = DemoLoader.loadGLTexture(androidGame, "IMG_5790.JPG");         
      
      e1 = new GEntity();
      e1.cx = 100.0f;
      e1.cy = 100f;
      e1.width = 100;
      e1.height = 100;
      
      e2 = new GEntity();
      e2.cx = 100.0f;
      e2.cy = 100f;
      e2.width = 50;
      e2.height = 50;
    
      this.doneInit = true;      
   }

}
