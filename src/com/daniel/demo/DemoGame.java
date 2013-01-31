package com.daniel.demo;


import android.util.Log;

import com.daniel.framework.AndroidGame;

public class DemoGame extends AndroidGame {
   
   /*
   public TriangleScreen triangle = new TriangleScreen(this);
   public SquareScreen square = new SquareScreen(this);
   */
   public LoadingScreen loader = new LoadingScreen(this);
   public LoadingScreen2 loader2 = new LoadingScreen2(this);
   
   public DemoGame() {
      super();
      
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

}
