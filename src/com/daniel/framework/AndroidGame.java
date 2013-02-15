package com.daniel.framework;
import unused.AndroidGraphics;

import com.daniel.framework.graphics.AssetLoader;
import com.daniel.framework.graphics.RenderEngine;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
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
   public AndroidGraphics androidGraphics;
   public AndroidTouchHandler androidTouchHandler;
   public AndroidSoundManager androidSoundManager;
   public RenderEngine renderEngine;
   
   //public WakeLock wakeLock;
   
   public int width;
   public int height;
   
   public int deviceWidth;
   public int deviceHeight;
   
   public long updateInterval = 50;
   
   
   private boolean detectOpenGLES20() {
      ActivityManager am =
          (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
      ConfigurationInfo info = am.getDeviceConfigurationInfo();
      //>>> " + info.reqGlEsVersion );
      return (info.reqGlEsVersion >= 0x20000);
   }
   
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      // Set up window traits
      this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      boolean isPortrait = this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
      
      
      this.deviceWidth = getWindowManager().getDefaultDisplay().getWidth();
      this.deviceHeight = getWindowManager().getDefaultDisplay().getHeight();
      
      // Hardwire for now
      this.width = this.deviceWidth; 
      this.height = this.deviceHeight;
      
      /* API 13
      Point size = new Point();
      this.getWindowManager().getDefaultDisplay().getSize(size);
      this.deviceWidth = size.x;
      this.deviceHeight = size.y;
      */
      
      
      /*
      if (this.detectOpenGLES20()) {
         System.out.println(">>>> OpenGL ES20 detected");   
      } else {
         System.out.println(">>>> OpenGL ES20 NOT detected");   
      }
      */
      
      androidSoundManager = new AndroidSoundManager(this);
      
      renderEngine = new RenderEngine(this);
      androidView = new AndroidGLView(this);
      
      // Setup input and touch handlers
      androidTouchHandler = new AndroidTouchHandler( androidView );
      
      // Set up the rendering screen
      this.setContentView(androidView);
      this.setInitScreen();
      
      // Keep the screen on please
      this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
      
      //PowerManager powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
      //wakeLock = powerManager.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, "AndroidGame");      
      this.detectOpenGLES20();
   }
   
   @Override
   public void onRestart() {
      super.onRestart();
   }
   
   @Override
   public void onResume() {
      super.onResume();
      
      androidScreen.resume();
      androidSoundManager.onResume();
      Log.i("TT", "TT onResume");
   }
   
   @Override
   public void onPause() {
      super.onPause();
      //wakeLock.release();
      androidScreen.pause();
      androidSoundManager.onPause();
      Log.i("TT", "TT onPause");
   }
   
   @Override
   public void onStop() {
      super.onStop();
      androidSoundManager.onStop();
      Log.i("TT", "TT onStop");
   }
   
   
   
   @Override
   public void onDestroy() {
      super.onDestroy();
      androidSoundManager.onDestroy();
   }
   
   
   public void setScreen(AndroidScreen screen) {
      this.androidScreen.pause();
      this.androidScreen.displose();
      
      // On the safe side, flush the engine incase it trys to render
      // non-existent stuff
      this.renderEngine.clearEngine();
      
      screen.resume();
      //screen.update(0);
      this.androidScreen = screen;
   }
   
   // Must be implemented by the child
   public abstract void setInitScreen(); 
   
   public void setUpdateInterval(long t) {
      updateInterval = t;   
   }
      
  
}
