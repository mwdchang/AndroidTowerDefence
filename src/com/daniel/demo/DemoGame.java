package com.daniel.demo;

import com.daniel.framework.AndroidGame;

public class DemoGame extends AndroidGame {

   @Override
   public void setInitScreen() {
      // TODO Auto-generated method stub
      this.androidScreen = new SplashScreen( this );
      System.out.println("Setting initial screen...");
   }

}
