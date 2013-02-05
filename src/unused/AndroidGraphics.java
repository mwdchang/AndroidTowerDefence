package unused;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class AndroidGraphics {
   public AssetManager assetManager;
   public Canvas canvas;
   public Bitmap frameBuffer;
   public Paint paint;
   public AndroidGraphics androidGraphics;
   
   
   public AndroidGraphics(AssetManager am, Bitmap fb) {
      assetManager = am;      
      frameBuffer = fb;
      canvas = new Canvas( frameBuffer );
      paint = new Paint();
   }
   
   public void drawString(String text, int x, int y, Paint paint) {
      canvas.drawText(text, x, y, paint);   
   }

}
