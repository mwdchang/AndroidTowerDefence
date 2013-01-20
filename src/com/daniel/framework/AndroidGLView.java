package com.daniel.framework;


import com.daniel.demo.SquareScreen;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

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
      setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);      
   }
   
   
   @Override
   public boolean onTouchEvent(MotionEvent e) {
      switch(e.getAction()) {
         case MotionEvent.ACTION_UP: 
         //this.renderer.androidGame.setScreen( this.renderer.squareScreen );
         this.renderer.androidGame.setScreen( new SquareScreen(null) );
         requestRender();
      }
      return true;   
   }

}
