package com.daniel.demo;

import com.daniel.framework.AndroidGame;

public class DemoGame extends AndroidGame {
   
   public TriangleScreen triangle = new TriangleScreen(this);
   public SquareScreen square = new SquareScreen(this);
   
   public DemoGame() {
      super();
      
      // Setup navigation
      triangle.next = square;
      square.next = square;
      
   }

   
   @Override
   public void setInitScreen() {
      // TODO Auto-generated method stub
      this.androidScreen = triangle;
      System.out.println("Setting initial screen...");
   }
   
   @Override
   public void onStop() {
      super.onStop();
      this.setScreen( new TriangleScreen( this ) );
   }

}
