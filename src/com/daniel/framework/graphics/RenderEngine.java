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
   }
   
   public void render() {
      GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
      GLES20.glViewport(0, 0, androidGame.width, androidGame.height);
      float w = androidGame.width;
      float h = androidGame.height;
      Matrix.orthoM(mProj, 0, -w, w, -h, h, -1, 1);
      
      for (GEntity e : entityList) {
         Matrix.setIdentityM(mModel, 0); 
         Matrix.translateM(mModel, 0, e.cx, e.cy, 0);
         Matrix.rotateM(mModel, 0, e.orientation, 0, 0, 1);
         
         //Matrix.setRotateM(mRotationMatrix, 0, e.orientation, 0, 0, -1.0f);
         
         //Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mProjMatrix, 0);
         
         Matrix.multiplyMM(mMVP, 0, mProj, 0, mModel, 0);
         
         texture.textureID = e.textureId;
         //texture.setDimension(e.cx-e.width, e.cy-e.height, e.cx+e.width, e.cy+e.height);
         texture.setDimension(-e.width, -e.height, e.width, e.height);
         texture.matrix = mMVP;
         //texture.matrix = mProj;
         texture.render();
      }
      
   }
   
   public void clearEngine() { 
      Matrix.setIdentityM(mProj, 0);
      Matrix.setIdentityM(mMVP, 0);
      Matrix.setIdentityM(mModel, 0);
      entityList.clear();
   }
   
   public void addObject(GEntity e) {
      entityList.add(e);
   }
   
   private AndroidGame androidGame;
   private ImageTexture texture;
   private ArrayList<GEntity> entityList = new ArrayList<GEntity>();
   
   private float mProj[] = new float[16];    // Projection matrix
   private float mModel[] = new float[16];   // Model matrix
   private float mMVP[] = new float[16];     // Model-view-projection matrix
}
