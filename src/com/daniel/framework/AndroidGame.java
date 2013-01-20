package com.daniel.framework;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;


////////////////////////////////////////////////////////////////////////////////
// Android level control for game behaviour
//
// Derived from : http://www.kilobolt.com/day-6-the-android-game-framework-part-ii.html
////////////////////////////////////////////////////////////////////////////////
public abstract class AndroidGame extends Activity {
   
   public AndroidScreen androidScreen;
   public AndroidGLView androidView;
   public AndroidGLRenderer androidRenderer;
   public AndroidGraphics androidGraphics;
   public WakeLock wakeLock;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      // Set up window traits
      this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      boolean isPortrait = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
      
      // Setting up for the canvas
      int frameBufferWidth = isPortrait ? 800: 1280;
      int frameBufferHeight = isPortrait ? 1280: 800;      
      Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
      androidGraphics = new AndroidGraphics( this.getAssets(), frameBuffer);
      
      androidRenderer = new AndroidGLRenderer(this);
      androidView = new AndroidGLView(this, androidRenderer);
      
      // Set up the rendering screen
      this.setInitScreen();
      this.setContentView(androidView);
      
      // Keep the screen on please
      this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      
      //PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
      //wakeLock = powerManager.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "AndroidGame");      
   }
   
   @Override
   public void onRestart() {
      super.onRestart();
   }
   
   @Override
   public void onResume() {
      super.onResume();
      //wakeLock.acquire();
      if (androidScreen == null) System.out.println("Oh crap!!!");
      androidScreen.resume();
      //androidView.resume();
   }
   
   @Override
   public void onPause() {
      super.onPause();
      //wakeLock.release();
      androidScreen.pause();
      //androidView.pause();
   }
   
   @Override
   public void onStop() {
      super.onStop();
   }
   
   
   @Override
   public void onDestroy() {
      super.onDestroy();
   }
   
   
   public void setScreen(AndroidScreen screen) {
      this.androidScreen.pause();
      this.androidScreen.displose();
      
      screen.resume();
      screen.update(0);
      this.androidScreen = screen;
   }
   
   // Must be implemented by the child
   public abstract void setInitScreen(); 
      
  
}
