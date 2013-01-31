package com.daniel.framework.graphics;

import com.daniel.framework.AndroidGame;

////////////////////////////////////////////////////////////////////////////////
// An image quad
////////////////////////////////////////////////////////////////////////////////
public class ImageTexture extends BaseTexture {

   public ImageTexture() {
      super();
      setTex(new float[]{
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, 0.0f
      });
      setDrawOrder(new short[]{ 0, 1, 2, 0, 2, 3 });      
   }
   
   
   ////////////////////////////////////////////////////////////////////////////////
   // Set the image dimension
   // (x1, y1) -> bottom left
   // (x2, y2) -> top right
   ////////////////////////////////////////////////////////////////////////////////
   public void setDimension(float x1, float y1, float x2, float y2) {
      setCoord(new float[] {
            x1, y2, 0,
            x1, y1, 0,
            x2, y1, 0,
            x2, y2, 0
      });
   }
   
   

}
