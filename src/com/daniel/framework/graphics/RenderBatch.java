package com.daniel.framework.graphics;

import java.util.ArrayList;

import android.opengl.GLES20;

////////////////////////////////////////////////////////////////////////////////
// Represent a set of objects to be rendered with the same properties
////////////////////////////////////////////////////////////////////////////////
public class RenderBatch {

   public void setGLState() {
      if (useDepthTest == true) GLES20.glEnable(GLES20.GL_DEPTH_TEST); 
      else GLES20.glDisable(GLES20.GL_DEPTH_TEST);
      
      if (useBlend == true) GLES20.glEnable(GLES20.GL_BLEND);
      else GLES20.glDisable(GLES20.GL_BLEND);
      
      GLES20.glBlendFunc(sFactor, dFactor);
   }
   
   
   public boolean useBlend = true;
   public boolean useDepthTest = false;
   public int sFactor = GLES20.GL_SRC_ALPHA;
   public int dFactor = GLES20.GL_ONE_MINUS_SRC_ALPHA;
   
   public ArrayList<GEntity> entityList = new ArrayList<GEntity>();
   public ArrayList<GEntity> fontList = new ArrayList<GEntity>();
}
