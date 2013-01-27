package com.daniel.framework.graphics;

import java.io.InputStream;

import com.daniel.framework.AndroidGame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;


////////////////////////////////////////////////////////////////////////////////   
// Used to store static assets
////////////////////////////////////////////////////////////////////////////////   
public class AssetLoader {
   
   ////////////////////////////////////////////////////////////////////////////////   
   // Load a texture by dynamically specifying a name
   // Note: case matters, the specified filename must match the resource name
   ////////////////////////////////////////////////////////////////////////////////   
   public static int loadGLTexture(AndroidGame androidGame, String filename) {
      int[] textures = new int[1];
      
      // loading texture
      InputStream is = null;
      try {
         is = androidGame.getAssets().open(filename);
      } catch (Exception e) {}
      
      Bitmap bitmap = BitmapFactory.decodeStream( is );
      
      // generate one texture pointer
      GLES20.glGenTextures(1, textures, 0);
      // ...and bind it to our array
      GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
      
      // create nearest filtered texture
      GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
      GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
      
      // Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
      GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
      
      // Clean up
      bitmap.recycle();
      
      return textures[0];
   }
   
   ////////////////////////////////////////////////////////////////////////////////   
   // Load a texture by a static resource identifier
   ////////////////////////////////////////////////////////////////////////////////   
   public static int loadGLTexture(AndroidGame androidGame, int resourceID) {
      int[] textures = new int[1];
      
      // loading texture
      Bitmap bitmap = BitmapFactory.decodeResource(androidGame.getResources(), resourceID);
      
      // generate one texture pointer
      GLES20.glGenTextures(1, textures, 0);
      // ...and bind it to our array
      GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
      
      // create nearest filtered texture
      GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
      GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
      
      // Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
      GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
      
      // Clean up
      bitmap.recycle();
      return textures[0];
   }
}
