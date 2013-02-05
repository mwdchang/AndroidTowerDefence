package com.daniel.framework;


import unused.SquareScreen;

import com.daniel.framework.graphics.Util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

////////////////////////////////////////////////////////////////////////////////
// The default View for the AndroidGame
// All work are delegated to AndroidGLRenderer and AndoidTouchHandler
////////////////////////////////////////////////////////////////////////////////
public class AndroidGLView extends GLSurfaceView {
   
   public AndroidGLRenderer renderer;
   public AndroidGame androidGame;
   

   public AndroidGLView(AndroidGame game) {
      super(game);
      
      androidGame = game;
      renderer = new AndroidGLRenderer( androidGame );
      
      // Create an OpenGL ES 2.0 context.
      setEGLContextClientVersion(2);

      // Set the Renderer for drawing on the GLSurfaceView
      super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
      setRenderer(renderer);

      // Render the view only when there is a change in the drawing data
      //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);      
      
   }
   
   

   
   @Override
   public boolean onTouchEvent(MotionEvent e) {
      // Do Not touch !!!
      /*
      switch(e.getAction()) {
         case MotionEvent.ACTION_UP: 
            androidGame.setScreen( androidGame.androidScreen.next );
            float ncoord[] = Util.screen2normal(androidGame, new float[]{ e.getX(), e.getY()});
            Log.i("Touch", ncoord[0] + ", " + ncoord[1]);
      }
      */
      
      //requestRender();
      return true;   
   }

}
