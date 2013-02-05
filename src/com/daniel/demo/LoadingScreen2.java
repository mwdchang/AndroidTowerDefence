package com.daniel.demo;

import java.util.List;

import android.util.Log;

import com.daniel.framework.AndroidGame;
import com.daniel.framework.AndroidScreen;
import com.daniel.framework.TouchEvent;
import com.daniel.framework.graphics.GEntity;
import com.daniel.framework.graphics.Util;

public class LoadingScreen2 extends AndroidScreen {
   
   public LoadingScreen2(AndroidGame game) {
      this.androidGame = game;   
   }

   @Override
   public void update(float dTime) {
      List<TouchEvent> list = androidGame.androidTouchHandler.getTouchEvents();
      for (TouchEvent t : list) {
         if (t.type == TouchEvent.TOUCH_UP) {
            float point[] = Util.screen2game(androidGame, new float[]{t.x, t.y} );
            
            Log.i("", "TTT1 " + t.x + ", " + t.y);
            Log.i("", "TTT2 " + point[0] + ", " + point[1]);
            
            if (e1.intersect(point) == true) {
               e1.height -= 4;    
            } else if (e2.intersect(point) == true) {
               androidGame.setScreen( this.next );
               return;
            } else {
               e1.height = 200;
            }
            
         }
      }      
      
      androidGame.renderEngine.clearEngine();
      androidGame.renderEngine.addObject(e1);
      androidGame.renderEngine.addObject(e2);
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
   }

   @Override
   public void init() {
      System.out.println(">>>>> in Loading Screen2 Init");
      // TODO Auto-generated method stub
      e1 = new GEntity();
      e1.width = androidGame.width/2;
      e1.height = 200;
      e1.cx = 0;
      e1.cy = -210;
      e1.textureId = DemoLoader.TX_DEMO;
      
      e2 = new GEntity();
      e2.width = androidGame.width/2;
      e2.height = 200;
      e2.cx = 0;
      e2.cy = 210;
      e2.textureId = DemoLoader.TX_DEMO2;
   }
   
   public GEntity e1;
   public GEntity e2;

}
