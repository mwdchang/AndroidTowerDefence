package com.daniel.framework;


import android.content.Context;
import android.opengl.GLSurfaceView;

public class AndroidGLView extends GLSurfaceView {
   

   public AndroidGLView(Context context, AndroidGLRenderer renderer) {
      super(context);
      
      // Create an OpenGL ES 2.0 context.
      setEGLContextClientVersion(2);

      // Set the Renderer for drawing on the GLSurfaceView
      super.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
      setRenderer(renderer);

      // Render the view only when there is a change in the drawing data
      setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);      
   }

}
