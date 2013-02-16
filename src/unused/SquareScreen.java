package unused;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.androidG.framework.android.AndroidGame;
import com.androidG.framework.android.AndroidScreen;
import com.androidG.framework.graphics.BaseTexture;
import com.androidG.framework.graphics.ImageTexture;
import com.androidG.framework.graphics.Polygon;

import demos.demo1.DemoLoader;

public class SquareScreen extends AndroidScreen {
   
   public ImageTexture img;
   public float[] mMVPMatrix = new float[16];
   public float[] mProjMatrix = new float[16];
   public float[] mVMatrix = new float[16];
   public float[] mRotationMatrix = new float[16];
   public float mAngle = 90.0f;   
   
   public SquareScreen(AndroidGame game) {
      this.androidGame = game;
   }   
   
   public void init() {
      img= new ImageTexture();   
      img.setDimension(0.2f, 0.2f, 1.0f, 1.0f);
      img.textureID = DemoLoader.TX_DEMO;
            
      //img.loadGLTexture("IMG_5656.JPG");
   }

   @Override
   public void update(float dTime) {
      // TODO Auto-generated method stub
   }


   @Override
   public void pause() {
      // TODO Auto-generated method stub
   }

   @Override
   public void resume() {
      // TODO Auto-generated method stub
   }

   @Override
   public void displose() {
      // TODO Auto-generated method stub
   }

}
