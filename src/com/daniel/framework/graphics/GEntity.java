package com.daniel.framework.graphics;
import android.opengl.*;


////////////////////////////////////////////////////////////////////////////////
// This act as a simple textured entity that can be send to the rendering
// engine.
////////////////////////////////////////////////////////////////////////////////
public class GEntity {
   public static float P2R = (float)(Math.PI / 180.0);
   
   
   public GEntity() {
      cx = cy = 0.0f;
      orientation = 0.0f;
   }
   
   
   ////////////////////////////////////////////////////////////////////////////////
   // Point-Quad intersection
   ////////////////////////////////////////////////////////////////////////////////
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
   
   /*
   public String toString() {
      float r[] = new float[4];
      float m[] = new float[16];
      float p1[] = new float[]{-width, -height, 0.0f, 1.0f};
      
      Matrix.setRotateM(m, 0, orientation, 0, 0, -1.0f);
      Matrix.multiplyMV(r, 0, m, 0, p1, 0);
      return "";
   }
   */
   
   public float colour[] = new float[]{1, 1, 1, 1};
   public float cx, cy;          // Center X,Y
   public float orientation;     // Heading
   public float width, height;   // The width and height of the texture
   public int textureId;         // The texture identifier
}
