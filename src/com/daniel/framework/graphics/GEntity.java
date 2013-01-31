package com.daniel.framework.graphics;
import com.daniel.framework.graphics.ImageTexture;

import android.opengl.*;
import android.util.Log;

public class GEntity {
   public static float P2R = (float)(Math.PI / 180.0);
   
   
   public GEntity() {
      cx = cy = 0.0f;
      orientation = 0.0f;
      width = 1.0f;
      height = 3.0f;
   }
   
   public boolean intersect(float[] coord) {
      float m[] = new float[16];
      float i[] = new float[]{ coord[0]-cx, coord[1]-cy, 0, 1};
      float r[] = new float[4];
      
      Matrix.setRotateM(m, 0, -orientation, 0, 0, -1.0f);
      Matrix.multiplyMV(r, 0, m, 0, i, 0);
      
      if (Math.abs(r[0]) <= width && Math.abs(r[1]) <= height) {
         return true;   
      }
      return false; 
   }
   
   public String toString() {
      float r[] = new float[4];
      float m[] = new float[16];
      float p1[] = new float[]{-width, -height, 0.0f, 1.0f};
      
      Matrix.setRotateM(m, 0, orientation, 0, 0, -1.0f);
      Matrix.multiplyMV(r, 0, m, 0, p1, 0);
      return "";
   }
   
   public float cx, cy; 
   public float orientation;
   public float width, height;
   public int textureId;
}
