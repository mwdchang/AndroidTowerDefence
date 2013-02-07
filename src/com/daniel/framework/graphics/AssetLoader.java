package com.daniel.framework.graphics;

import java.io.InputStream;

import com.daniel.framework.AndroidGame;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;


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
      GLES20.glTexParameterf( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE );  // Set U Wrapping
      GLES20.glTexParameterf( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE );  // Set V Wrapping
      
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
      GLES20.glTexParameterf( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE );  // Set U Wrapping
      GLES20.glTexParameterf( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE );  // Set V Wrapping
      
      // Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
      GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
      
      // Clean up
      bitmap.recycle();
      return textures[0];
   }
   
   public static GEntity createStaticFont(AndroidGame game, String str) {
      Typeface tf = Typeface.createFromAsset(game.getAssets(), "din1451m.ttf");
      Paint paint = new Paint();   
      paint.setAntiAlias(true);
      paint.setTextSize(80);
      paint.setARGB(255, 255, 255, 255);
      paint.setTypeface(tf);
      paint.setStyle(Style.FILL_AND_STROKE);
      
      float bottom = paint.getFontMetrics().bottom;
      Log.i("TT", "BOttom: " + bottom);
      
      // get font metrics
      Rect bounds = new Rect();
      paint.getTextBounds(str, 0, str.length(), bounds);
      
      //Bitmap bitmap = Bitmap.createBitmap( bounds.width(), bounds.height(), Bitmap.Config.ALPHA_8 );
      Bitmap bitmap = Bitmap.createBitmap( bounds.width(), bounds.height(), Config.ARGB_8888);
      Log.i("Test", "Mutable : " + bitmap.isMutable()); 
      //bitmap.eraseColor( 0x000FF00 );                // Set Transparent Background (ARGB)
      
      Canvas canvas = new Canvas();           // Create Canvas for Rendering to Bitmap
      canvas.setBitmap(bitmap);
      //canvas.drawCircle(0, 0, 1000, paint);
      canvas.drawText(str, 0, bounds.height()-bottom, paint);
      
      
      // generate a new texture
      int[] textures = new int[1];                  // Array to Get Texture Id
      
      GLES20.glEnable(GLES20.GL_TEXTURE_2D);
      GLES20.glGenTextures( 1, textures, 0 );           // Generate New Texture

      // setup filters for texture
      GLES20.glBindTexture( GLES20.GL_TEXTURE_2D, textures[0] );  // Bind Texture
      GLES20.glTexParameterf( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST );  // Set Minification Filter
      GLES20.glTexParameterf( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR );  // Set Magnification Filter
      GLES20.glTexParameterf( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE );  // Set U Wrapping
      GLES20.glTexParameterf( GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE );  // Set V Wrapping

      // load the generated bitmap onto the texture
      GLUtils.texImage2D( GLES20.GL_TEXTURE_2D, 0, bitmap, 0 );  // Load Bitmap to Texture
      GLES20.glBindTexture( GLES20.GL_TEXTURE_2D, 0 );      // Unbind Texture

      // release the bitmap
      bitmap.recycle();      

      Log.i("Test", "bounds " + bounds.width() + ", " + bounds.height() + " : " + textures[0]);
      
      GEntity g = new GEntity();
      g.width = bounds.width()/2;
      g.height = bounds.height()/2;
      g.textureId = textures[0];
      
      return g;
   }
   
}
