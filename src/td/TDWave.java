package td;

import java.util.ArrayList;

////////////////////////////////////////////////////////////////////////////////
// A wave describes the group behaviour of an enemy group
////////////////////////////////////////////////////////////////////////////////
public class TDWave {
   
   public TDWave() {
   }
   
   public void add(Class c, int num, int o) {
      try {
         for (int i=0; i < num ; i++) {
            TDEnemy e = (TDEnemy)c.newInstance();   
            e.cy = 500 + (float)Math.random()*60;      
            e.cx = (float)(Math.random()*400-200);
            enemyList.add( e );
         }
      } catch (Exception e) {}
   }
   
   // Add an arbitrary number of preset enemies
   /*
   public void add( TDEnemy enemies[], int o) {
      for (int i=0; i < enemies.length; i++) {
         if (o == WAVE_TOP) {
            enemies[i].cy = 500;      
            enemies[i].cx = (float)(Math.random()*200-100);
         }
         
         enemyList.add( enemies[i] );
      }
   }
   */
   
   public ArrayList<TDEnemy> enemyList = new ArrayList<TDEnemy>();
   public int origin;
   
   public static final int WAVE_LEFT  = 0;
   public static final int WAVE_RIGHT = 1;
   public static final int WAVE_TOP = 2;
   public static final int WAVE_BOTTOM = 3;
   public static final int WAVE_SURROUND = 4;
}
