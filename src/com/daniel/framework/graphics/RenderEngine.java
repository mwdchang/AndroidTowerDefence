package com.daniel.framework.graphics;

import java.util.ArrayList;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.daniel.framework.AndroidGame;

////////////////////////////////////////////////////////////////////////////////
// 
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
      Matrix.orthoM(mProjMatrix, 0, -w, w, -h, h, -1, 1);
      
      for (GEntity e : entityList) {
         Matrix.setRotateM(mRotationMatrix, 0, e.orientation, 0, 0, -1.0f);
         Matrix.multiplyMM(mMVPMatrix, 0, mRotationMatrix, 0, mProjMatrix, 0);
         texture.textureID = e.textureId;
         texture.setDimension(e.cx-e.width, e.cy-e.height, e.cx+e.width, e.cy+e.height);
         texture.matrix = mMVPMatrix;
         texture.render();
      }
      
   }
   
   public void clearEngine() { 
      Matrix.setIdentityM(mProjMatrix, 0);
      Matrix.setIdentityM(mRotationMatrix, 0);
      Matrix.setIdentityM(mMVPMatrix, 0);
      entityList.clear();
   }
   
   public void addObject(GEntity e) {
      entityList.add(e);
   }
   
   private AndroidGame androidGame;
   private ImageTexture texture;
   private ArrayList<GEntity> entityList = new ArrayList<GEntity>();
   private float mProjMatrix[] = new float[16];
   private float mRotationMatrix[] = new float[16];
   private float mMVPMatrix[] = new float[16];
}
