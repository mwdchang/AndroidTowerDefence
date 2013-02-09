package com.daniel.framework.graphics;

import java.util.ArrayList;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.daniel.framework.AndroidGame;

////////////////////////////////////////////////////////////////////////////////
// Rendering engine.
// The rendering engine takes in a list of rendering objects, interpret them
// into OpenGL commands and sends them through to the graphics card.
//
// Currently supported objects:
// - GEntity
////////////////////////////////////////////////////////////////////////////////
public class RenderEngine {
   
   
   public RenderEngine(AndroidGame game) {
      androidGame = game;
   }
   
   public void init() {
      texture = new ImageTexture();    
      font = new FontTexture();
      
      GLES20.glDisable(GLES20.GL_DEPTH_TEST);
      GLES20.glEnable(GLES20.GL_BLEND);
      GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
   }
   
   
   public void render() {
      GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
      GLES20.glViewport(0, 0, androidGame.width, androidGame.height);
      
      float w = androidGame.width;
      float h = androidGame.height;
      Matrix.orthoM(mProj, 0, -w, w, -h, h, -1, 1);
      
      for (RenderBatch batch : batchList) {
         batch.setGLState();
         
         for (GEntity e : batch.entityList) {
            Matrix.setIdentityM(mModel, 0); 
            Matrix.translateM(mModel, 0, e.cx, e.cy, 0);
            Matrix.rotateM(mModel, 0, e.orientation, 0, 0, 1);
            Matrix.multiplyMM(mMVP, 0, mProj, 0, mModel, 0);
            texture.textureID = e.textureId;
            texture.colour = e.colour;
            texture.setDimension(-e.width, -e.height, e.width, e.height);
            texture.matrix = mMVP;
            texture.render();
         }
         
         for (GEntity e : batch.fontList) {
            Matrix.setIdentityM(mModel, 0); 
            Matrix.translateM(mModel, 0, e.cx, e.cy, 0);
            Matrix.rotateM(mModel, 0, e.orientation, 0, 0, 1);
            Matrix.multiplyMM(mMVP, 0, mProj, 0, mModel, 0);
            font.textureID = e.textureId;
            font.colour = e.colour;
            font.setDimension(-e.width, -e.height, e.width, e.height);
            font.matrix = mMVP;
            font.render();
         }
      } // end batchList
      
      GLES20.glFlush();
     
   }
   
   public void clearEngine() { 
      Matrix.setIdentityM(mProj, 0);
      Matrix.setIdentityM(mMVP, 0);
      Matrix.setIdentityM(mModel, 0);
      batchList.clear();
   }
   
   public void newBatch() {
      batchList.add( new RenderBatch());
   }
   
   public void setBatchDepthTest(boolean b) {
      batchList.get(batchList.size()-1).useDepthTest = b;   
   }
   
   public void setBatchBlend(boolean b) {
      batchList.get(batchList.size()-1).useBlend = b;   
   }
   
   public void setBatchBlendFunc(int s, int d) {
      batchList.get(batchList.size()-1).sFactor = s;
      batchList.get(batchList.size()-1).dFactor = d;
   }
   
   public void addObject(GEntity e) {
      batchList.get(batchList.size()-1).entityList.add(e);
   }
   
   public void addFont(GEntity e) {
      batchList.get(batchList.size()-1).fontList.add(e);
   }
   
   
   private AndroidGame androidGame;
   private ImageTexture texture;
   private FontTexture font;
   
   private ArrayList<RenderBatch> batchList = new ArrayList<RenderBatch>();
   
   
   private float mProj[] = new float[16];    // Projection matrix
   private float mModel[] = new float[16];   // Model matrix
   private float mMVP[] = new float[16];     // Model-view-projection matrix
}
