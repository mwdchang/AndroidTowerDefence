package com.daniel.framework.graphics;
      
import android.opengl.GLES20;
import android.util.Log;

////////////////////////////////////////////////////////////////////////////////
// Shader object wrapper
// Ported from Daniel's thesis to work with GLES20
////////////////////////////////////////////////////////////////////////////////
public class ShaderObj {
   
   public void bind() { GLES20.glUseProgram(programID); }
   public void unbind() { GLES20.glUseProgram(0); }
   
   
   public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(">>>> ", glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
   }

   public int loadShader(int type, String shaderCode){
      // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
      // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
      int shader = GLES20.glCreateShader(type);
      
      String t = "";
      if (type == GLES20.GL_VERTEX_SHADER) t = "Vertex"; else t = "Frag" ;
      
      // add the source code to the shader and compile it
      GLES20.glShaderSource(shader, shaderCode);
      ShaderObj.checkGlError("SRC " + t + ": ");
      
      GLES20.glCompileShader(shader);
      ShaderObj.checkGlError("Compile");
      
      if (type == GLES20.GL_VERTEX_SHADER) vertexShader = shader;
      if (type == GLES20.GL_FRAGMENT_SHADER) fragmentShader = shader;
      
      return shader;
   }
   
   public int createProgram() {
      programID = GLES20.glCreateProgram();             // create empty OpenGL Program
      GLES20.glAttachShader(programID, vertexShader);   // add the vertex shader to program
      GLES20.glAttachShader(programID, fragmentShader); // add the fragment shader to program
      GLES20.glLinkProgram(programID);                  // create OpenGL program executables
      ShaderObj.checkGlError("Linking");
      return programID;
   }

   
   public int programID;
   public int vertexShader;
   public int fragmentShader;
}
