package com.daniel.framework.graphics;
      
import android.opengl.GLES20;

////////////////////////////////////////////////////////////////////////////////
// Shader object wrapper
// Ported from Daniel's thesis to work with GLES20
////////////////////////////////////////////////////////////////////////////////
public class ShaderObj {
   
   public void bind() { GLES20.glUseProgram(programID); }
   public void unbind() { GLES20.glUseProgram(0); }

   public int loadShader(int type, String shaderCode){
      // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
      // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
      int shader = GLES20.glCreateShader(type);
      
      // add the source code to the shader and compile it
      GLES20.glShaderSource(shader, shaderCode);
      GLES20.glCompileShader(shader);
      
      if (type == GLES20.GL_VERTEX_SHADER) vertexShader = shader;
      if (type == GLES20.GL_FRAGMENT_SHADER) fragmentShader = shader;
      
      return shader;
   }
   
   public int createProgram() {
      programID = GLES20.glCreateProgram();             // create empty OpenGL Program
      GLES20.glAttachShader(programID, vertexShader);   // add the vertex shader to program
      GLES20.glAttachShader(programID, fragmentShader); // add the fragment shader to program
      GLES20.glLinkProgram(programID);                  // create OpenGL program executables
      return programID;
   }

   
   public int programID;
   public int vertexShader;
   public int fragmentShader;
}
