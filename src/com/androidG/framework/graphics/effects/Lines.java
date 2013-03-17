package com.androidG.framework.graphics.effects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.androidG.framework.graphics.ShaderObj;

import android.opengl.GLES20;

////////////////////////////////////////////////////////////////////////////////
// Polygon implementation, with triangles
////////////////////////////////////////////////////////////////////////////////
public class Lines extends GEffect {
   public int BYTE_PER_FLOAT = 4;
    
   public Lines(float ...v) {
      //setCoord(v);
   }
   
   public void init() {
      initShader();
      doneInit = true;
   }
   
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
   
   public void setDrawOrder(short ...order){
      drawCount = order.length;
      ByteBuffer dlb = ByteBuffer.allocateDirect(order.length * 2);
      dlb.order(ByteOrder.nativeOrder());
      drawListBuffer = dlb.asShortBuffer();
      drawListBuffer.put(order);
      drawListBuffer.position(0);
   }
   
   
   public void setColour(float r, float g, float b, float a) {
      colour = new float[]{r, g, b, a};   
   }
   
   public void initShader() {
      //System.out.println(">>>>> In polygon init shader");
      shaderObj = new ShaderObj();
      shaderObj.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
      shaderObj.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
      shaderObj.createProgram();
   }
   
   
   public void render() {
      shaderObj.bind();
      positionHandle = GLES20.glGetAttribLocation(shaderObj.programID, "vPosition");
      colourHandle   = GLES20.glGetUniformLocation(shaderObj.programID, "vColor");
      matrixHandle   = GLES20.glGetUniformLocation(shaderObj.programID, "uMVPMatrix");
      
      GLES20.glEnableVertexAttribArray(positionHandle);
      GLES20.glVertexAttribPointer(positionHandle, 3, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
      GLES20.glUniform4fv(colourHandle, 1, colour, 0);
      GLES20.glUniformMatrix4fv(matrixHandle, 1, false, matrix, 0);
      
      //GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
      
      if (drawListBuffer == null) {
         //System.out.println(">>> Draw array");
        GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, vertexCount);
      } else {
         //System.out.println(">>> Draw element");
        GLES20.glDrawElements(GLES20.GL_LINE_STRIP, drawCount, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
      }
      
      GLES20.glDisableVertexAttribArray(positionHandle);
      shaderObj.unbind();
   }
   
   

   public ShaderObj shaderObj;
   public FloatBuffer vertexBuffer;
   public ShortBuffer drawListBuffer = null;
   
   
   public float[] coord;
   public float[] colour = new float[]{1, 1, 1, 1};
   
   public int vertexStride;
   public int vertexCount;
   public int drawCount;
   
   // Handle to the shaders
   int positionHandle;
   int matrixHandle;
   int colourHandle;
   
   private final String vertexShaderCode =
         "attribute vec4 vPosition; " +
         "uniform mat4 uMVPMatrix; " +
         "void main() { " +
         "  gl_Position = uMVPMatrix * vPosition; " +
         "} ";
   
   

     private final String fragmentShaderCode =
         //"precision mediump float;" +
         "precision mediump float;" +
         "uniform vec4 vColor;" +
         "void main() {" +
         "  gl_FragColor = vColor;" +
         "}";
}
