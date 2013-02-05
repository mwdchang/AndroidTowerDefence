package unused;

import com.daniel.framework.AndroidGame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidView extends SurfaceView implements Runnable {

   public AndroidView(AndroidGame game, Bitmap fb) { 
      super(game);   
      androidGame = game;
      frameBuffer = fb;
      holder = this.getHolder();
   }

   
   public void resume() {
      running = true;   
      renderThread = new Thread(this);
      renderThread.start();
   }
   
   
   public void pause() {
      running = false;
      try {
         renderThread.join();
      } catch (Exception e) {}
   }
   
   
   @Override
   public void run() {
      Rect dstRect = new Rect();
      long startTime = System.nanoTime();
      while (running) {
         if ( ! holder.getSurface().isValid()) continue;
         
         float dTime = (System.nanoTime() - startTime) / 10000000.000f;
         startTime = System.nanoTime();         
         
         androidGame.androidScreen.update(dTime);
         //androidGame.androidScreen.render(dTime);
         
         
         Canvas canvas = holder.lockCanvas();
         canvas.getClipBounds(dstRect);
         canvas.drawBitmap(frameBuffer, null, dstRect, null);                           
         holder.unlockCanvasAndPost(canvas);         
         
      }
   }
   
   
   
   
   public AndroidGame androidGame;
   public Bitmap frameBuffer;
   public SurfaceHolder holder;
   public boolean running;
   public Thread renderThread = null;

}
