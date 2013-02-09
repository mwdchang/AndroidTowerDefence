package com.daniel.framework.graphics;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.daniel.framework.AndroidGame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;


////////////////////////////////////////////////////////////////////////////////
// Generic image texture implementation (With Triangles)
////////////////////////////////////////////////////////////////////////////////
public class BaseTexture {
   public int BYTE_PER_FLOAT = 4;
   public int textureID;
    
   public BaseTexture() {
      //textureID = loadGLTexture(R.drawable.ic_launcher);
      //textureID = loadGLTexture("IMG_5656.JPG");
      initShader();
      colour = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
   }
   

   
   //////////////////////////////////////////////////////////////////////////////// 
   // Set colour
   //////////////////////////////////////////////////////////////////////////////// 
   public void setColour(float f[]) {
      colour = f;
   }
   
   //////////////////////////////////////////////////////////////////////////////// 
   // Set the vertex coordinates
   //////////////////////////////////////////////////////////////////////////////// 
   public void setCoord(float ...v) {
      coord = v;
      ByteBuffer bb = ByteBuffer.allocateDirect( coord.length * BYTE_PER_FLOAT);
      bb.order(ByteOrder.nativeOrder());
      
      // create a floating point buffer from the ByteBuffer
      vertexBuffer = bb.asFloatBuffer();
      
      // add the coordinates to the FloatBuffer
      vertexBuffer.put(coord);
      
      // set the buffer to read the first coordinate
      vertexBuffer.position(0);      
      
      vertexCount = coord.length / 3;    // (x, y, z)
      vertexStride = BYTE_PER_FLOAT * 3; // (x, y, z)
   }
   
   //////////////////////////////////////////////////////////////////////////////// 
   // Set the texture (UV) coordinate
   //////////////////////////////////////////////////////////////////////////////// 
   public void setTex(float...tex) {
      ByteBuffer bb = ByteBuffer.allocateDirect( tex.length * BYTE_PER_FLOAT);
      bb.order(ByteOrder.nativeOrder());
      texcoordBuffer = bb.asFloatBuffer();
      texcoordBuffer.put(tex);
      texcoordBuffer.position(0);      
      texcoordStride = BYTE_PER_FLOAT * 2; // (x, y)
   }
   
   
   //////////////////////////////////////////////////////////////////////////////// 
   // Set the drawing order (If using indexed arrays)
   //////////////////////////////////////////////////////////////////////////////// 
   public void setDrawOrder(short ...order){
      drawCount = order.length;
      ByteBuffer dlb = ByteBuffer.allocateDirect(order.length * 2);
      dlb.order(ByteOrder.nativeOrder());
      drawListBuffer = dlb.asShortBuffer();
      drawListBuffer.put(order);
      drawListBuffer.position(0);
   }
   
   
   public void initShader() {
      shaderObj = new ShaderObj();
      shaderObj.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
      shaderObj.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
      shaderObj.createProgram();
   }
   
   
   
   ////////////////////////////////////////////////////////////////////////////////
   // Renders the texture polygon
   ////////////////////////////////////////////////////////////////////////////////
   public void render() {
      GLES20.glEnable(GLES20.GL_TEXTURE_2D);
      GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
      GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID);
      
      shaderObj.bind();
      positionHandle = GLES20.glGetAttribLocation(shaderObj.programID, "inPosition");
      texcoordHandle = GLES20.glGetAttribLocation(shaderObj.programID, "inTexcoord");
      matrixHandle   = GLES20.glGetUniformLocation(shaderObj.programID, "uMVPMatrix");
      tex1Handle     = GLES20.glGetUniformLocation(shaderObj.programID, "tex1");
      colourHandle   = GLES20.glGetUniformLocation(shaderObj.programID, "uColour");
      
      GLES20.glEnableVertexAttribArray(positionHandle);
      GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
      
      GLES20.glEnableVertexAttribArray(texcoordHandle);
      GLES20.glVertexAttribPointer(texcoordHandle, 2, GLES20.GL_FLOAT, false, texcoordStride, texcoordBuffer);
      
      GLES20.glUniformMatrix4fv(matrixHandle, 1, false, matrix, 0);
      GLES20.glUniform1i(tex1Handle, 0); 
      GLES20.glUniform4fv(colourHandle, 1, colour, 0);
      
      
      if (this.drawCount > 0) {
         GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawCount, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
      } else {
         GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);  
      }
      
      shaderObj.unbind();
   }
   
   

   public ShaderObj shaderObj;
   public FloatBuffer vertexBuffer;
   public FloatBuffer texcoordBuffer;
   public ShortBuffer drawListBuffer = null;
   
   
   public float[] coord;
   public float[] matrix; 
   
   public float[] colour;
   
   public int vertexStride;
   public int texcoordStride;
   public int vertexCount;
   public int drawCount;
   
   // Handle to the shaders
   int positionHandle;
   int texcoordHandle;
   int matrixHandle;
   int colourHandle;
   int tex1Handle;
   
   private final String vertexShaderCode =
         "attribute vec4 inPosition; " +
         "attribute vec2 inTexcoord; " +
         "uniform mat4 uMVPMatrix; " +
         "varying vec2 outTexcoord; " + 
         "void main() {" +
         "  outTexcoord = inTexcoord;" +
         //"  gl_Position = inPosition * uMVPMatrix;" +
         "  gl_Position = uMVPMatrix * inPosition;" +
         "}";

     private final String fragmentShaderCode =
         "precision mediump float;" +
         "varying lowp vec2 outTexcoord; " + 
         "uniform sampler2D tex1;" +
         "uniform vec4 uColour;" +
         "void main() {" +
         //"  gl_FragColor = vec4(outTexcoord.x, 0.4, 0.4, 1.0);" +
         "  gl_FragColor = texture2D(tex1, outTexcoord) * uColour;" +
         "}";
}
