package com.daniel.framework.graphics;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


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
      float top = paint.getFontMetrics().top;
      
      // get font metrics
      Rect bounds = new Rect();
      paint.getTextBounds(str, 0, str.length(), bounds);
      
      int txHeight = (int)(Math.abs(top) + Math.abs(bottom));
      int txWidth = bounds.width() + 8;
      
      //Bitmap bitmap = Bitmap.createBitmap( bounds.width(), bounds.height(), Bitmap.Config.ALPHA_8 );
      Bitmap bitmap = Bitmap.createBitmap( txWidth, txHeight, Config.ARGB_8888);
      Log.i("Test", "Mutable : " + bitmap.isMutable()); 
      //bitmap.eraseColor( 0x000FF00 );                // Set Transparent Background (ARGB)
      
      Canvas canvas = new Canvas();           // Create Canvas for Rendering to Bitmap
      canvas.setBitmap(bitmap);
      //canvas.drawCircle(0, 0, 1000, paint);
      canvas.drawText(str, 0, txHeight-bottom, paint);
      
      
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

      Log.i("Test", "txbounds " + txWidth + ", " + txHeight + " : " + textures[0]);
      
      GEntity g = new GEntity();
      g.width = txWidth/2;
      g.height = txHeight/2;
      g.textureId = textures[0];
      
      return g;
   }
   
   
   
   ////////////////////////////////////////////////////////////////////////////////
   // Create a radial blur as a texture
   ////////////////////////////////////////////////////////////////////////////////
   public static int createRadialBlur() {
      int h = 256;
      int w = 256;
      int h2 = 128;
      int w2 = 128;
      int channel = 4;
      int texId[] = new int[1];
      
      //ByteBuffer b = GLBuffers.newDirectByteBuffer(h*w*channel);
      ByteBuffer b = ByteBuffer.allocateDirect(h*w*channel*4);
      b.order(ByteOrder.nativeOrder());
      
      for (int x=0; x < w; x++) {
         for (int y=0; y < h; y++) {
            int d = (int)Math.sqrt(  (x-w2)*(x-w2) + (y-h2)*(y-h2) );   
            float c = 255-(d*2);
            if (c < 0) c = 0;
            b.put((byte)c);
            b.put((byte)c);
            b.put((byte)c);
            b.put((byte)c);
         }
      }
      b.position(0);
      
      
      GLES20.glEnable(GLES20.GL_TEXTURE_2D);
      GLES20.glGenTextures(1, texId, 0);
      GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId[0]);
      GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
      GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
      GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, w, h, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, b);
      //GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, 3, w, h, 0, GLES20.GL_RGBA, GLES20.GL_FLOAT, fb);
      
      Log.i("Test", "Texture Blur ID: " + texId[0]);
      return texId[0];
   }
   
   
}
