package com.daniel.demo;

import com.daniel.framework.AndroidGame;
import com.daniel.framework.AndroidScreen;

import android.graphics.Color;
import android.graphics.Paint;

public class SplashScreen extends AndroidScreen {
   
   public float Y = 0;
   
   public SplashScreen(AndroidGame game) {
      this.androidGame = game;
      paint = new Paint();
      paint.setTextSize(40);
      paint.setColor(Color.WHITE);
   }

   @Override
   public void update(float dTime) {
      // TODO Auto-generated method stub
   }

   @Override
   public void render(float dTime) {
      // TODO Auto-generated method stub
      Y+=5;
      if (Y > androidGame.androidGraphics.canvas.getHeight()) Y = 0;
      androidGame.androidGraphics.canvas.drawColor(Color.BLACK);
      androidGame.androidGraphics.drawString("Hellow", 200, (int)Y, paint);
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
   
   
   public Paint paint;

}
