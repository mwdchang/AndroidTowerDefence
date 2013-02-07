package com.daniel.demo;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.util.Log;

import com.daniel.framework.AndroidGame;
import com.daniel.gamelogic.GameLogic;

public class DemoGame extends AndroidGame {
   
   /*
   public TriangleScreen triangle = new TriangleScreen(this);
   public SquareScreen square = new SquareScreen(this);
   */

   public LoadingScreen loader = new LoadingScreen(this);
   public LoadingScreen2 loader2 = new LoadingScreen2(this);
   
   private Timer m_timer = new Timer();
   private GameLogic m_gameLogic = new GameLogic();
   
   public DemoGame() {
      super();
      this.setUpdateInterval(25);
      
      // Setup navigation
      loader.next = loader2;
      loader2.next = loader;
      

   }

   
   @Override
   public void setInitScreen() {
      // TODO Auto-generated method stub
      this.androidScreen = loader;
      System.out.println("Setting initial screen...");
      /*
      GameObj c = new GameObj();
      Log.i("", "TTT " + c.intersect( new float[]{0, 3}));
      c.orientation = 90;
      c.cy = 100;
      Log.i("", "TTT " + c.intersect( new float[]{0, 102}));
      Log.i("", "TTT " + c.intersect( new float[]{-3, 100}));
      */
   }
   
   @Override
   public void onStop() {
      super.onStop();
      this.setScreen( loader );
   }
   
   //Update GameLogic
   class TaskUpdateGameLogic extends TimerTask {

        @Override
        public void run() {
        	m_gameLogic.Update();
        }
   };   

   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       m_timer = new Timer();
       m_timer.schedule(new TaskUpdateGameLogic(), 0,500);
   }   
   
}
