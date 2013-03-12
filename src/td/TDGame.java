package td;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import android.util.Log;

import com.androidG.framework.android.AndroidGame;
import com.androidG.framework.android.TouchEvent;
import com.androidG.framework.android.Util;
import com.androidG.framework.graphics.GEntity;
import com.androidG.framework.graphics.effects.Comet;
import com.androidG.framework.graphics.effects.Nova;

import td.run.TDAssets;
import td.util.TDEnemyCreator;


////////////////////////////////////////////////////////////////////////////////
// Main game goes in here, this is a singleton class
////////////////////////////////////////////////////////////////////////////////
public class TDGame {
   
   private TDGame() {
   }
   
   public static TDGame inst() {
      if (inst == null) inst = new TDGame();
      return inst; 
   }
   
   ////////////////////////////////////////////////////////////////////////////////
   // Initialization - Testing level coding for now
   ////////////////////////////////////////////////////////////////////////////////
   public void init(AndroidGame game) {
      androidGame = game;
      
      
      levelList.clear();
      
      // Size the particles
      
      
      TDLevel levelTest = new TDLevel();
      TDWave waveTest = new TDWave();
      waveTest.add( TDNormalEnemy.class, 1, TDWave.WAVE_TOP);
      levelTest.waveList.add( waveTest );
      
      waveTest = new TDWave();
      waveTest.add( TDNormalEnemy.class, 3, TDWave.WAVE_TOP);
      levelTest.waveList.add( waveTest );
      
      waveTest = new TDWave();
      waveTest.add( TDNormalEnemy.class, 5, TDWave.WAVE_TOP);
      levelTest.waveList.add( waveTest );
      
      
      levelList.add(levelTest);
      
      // Test
      //currentEnemies.addAll( levelList.get(0).waveList.get(0).enemyList);
      
      currentWave = 0;
      currentLevel = 0;
      nextWaveTime = 0;
      
   }
   
   
   ////////////////////////////////////////////////////////////////////////////////
   // Update game 
   // Note: If UI has been updated, we don't want to further proceed with the touch 
   // point events.
   ////////////////////////////////////////////////////////////////////////////////
   public void updateGame(List<TouchEvent> list, float dTime) {
      updateUI(list);
      
      if (this.proceedUpdate) updateParticles(list);
      
      updateEnemies(list);
      updateNextWave( dTime );
      cleanup();
      
      proceedUpdate = true;
   }
   
   
   // Check UI
   public void updateUI(List<TouchEvent> list) {
      for (TouchEvent t : list) {
         float point[] = Util.screen2game(androidGame, new float[]{t.x, t.y} );
         if (toggleTower.intersect(point) && t.type == TouchEvent.TOUCH_DOWN) {
            Log.e("TTT", "TTT Touching UI");
            this.showTowerOption = ! this.showTowerOption;   
            this.proceedUpdate = false;
            return;
         }
      }
   }
   
   
   // Increment time counter, check if we should send the next wave of enemies
   public void updateNextWave(float dTime) {
      nextWaveTime += dTime;
      TDWave wave = getCurrentWave();
      if (wave != null && nextWaveTime >= wave.waveTime) {
         currentEnemies.addAll( wave.enemyList);
         nextWaveTime = 0;
         currentWave ++;
         gameStatusLabel = TDAssets.createStaticFont(androidGame, "Level " + currentLevel + " Wave " + currentWave);
      }
   }
   
   
   ////////////////////////////////////////////////////////////////////////////////
   // Remove enemies that are no longer valid
   ////////////////////////////////////////////////////////////////////////////////
   public void cleanup() {
      // Clean up enemies
      Iterator<TDEnemy> iter = currentEnemies.iterator();
      while (iter.hasNext()) {
         if (iter.next().life <= 0) iter.remove();
      }
   }
   
   
   ////////////////////////////////////////////////////////////////////////////////
   // Update enemy position and behaviours, this does not include deletion
   ////////////////////////////////////////////////////////////////////////////////
   public void updateEnemies(List<TouchEvent> list) {
      for (TDEnemy e : currentEnemies ) {
         e.cy -= 0.25f;   
      }
   }
   
   
   public void updateParticles(List<TouchEvent> list) {
      ////////////////////////////////////////////////////////////////////////////////
      // Update the planet's particle effects
      ////////////////////////////////////////////////////////////////////////////////
      for (TouchEvent t : list) {
         // Short cuts
         if (comet != null) break;
         
         if (t.type == TouchEvent.TOUCH_DOWN) {
            float point[] = Util.screen2game(androidGame, new float[]{t.x, t.y} );
            float mag = (float)Math.sqrt( point[0]*point[0] + point[1]*point[1]);
            comet = new Comet(30);
            comet.scale = 4.5f;
            comet.dx = point[0]/mag;
            comet.dy = point[1]/mag;
            distX = point[0];
            distY = point[1];
            break;
         }
      } 
      
      if (comet != null) { 
         if (comet != null && Math.sqrt( (comet.cx-distX)*(comet.cx-distX) + (comet.cy-distY)*(comet.cy-distY)) > 15) {
            comet.cx += comet.dx*8;
            comet.cy += comet.dy*8;
            comet.update();
         } else {
            nova = new Nova(1);
            nova.cx = comet.cx;
            nova.cy = comet.cy;
            nova.scale = 100;
            comet = null;
         }
      }
      
      if (nova != null) {
         nova.update();
         if (nova.p[0].life < 0.0f) {
            checkPlanetFire(nova.cx, nova.cy);
            nova = null;
         }
      }
         
   }
   
   
   ////////////////////////////////////////////////////////////////////////////////
   // Check if the planet's missle/turret hits anything
   ////////////////////////////////////////////////////////////////////////////////
   public void checkPlanetFire(float x, float y) {
      for (TDEnemy e : currentEnemies) {
         if ( Math.sqrt( (e.cx-x)*(e.cx-x) + (e.cy-y)*(e.cy-y)) < 50 ) {
            e.life -= 15;
         }
      }
   }
    
   
   
   ////////////////////////////////////////////////////////////////////////////////
   // Shortcuts
   ////////////////////////////////////////////////////////////////////////////////
   public TDLevel getCurrentLevel() { 
      if (currentLevel >= levelList.size()) return null;
      return levelList.get(currentLevel); 
   }
   public TDWave  getCurrentWave() { 
      if (getCurrentLevel() == null) return null;
      if (currentWave >= getCurrentLevel().waveList.size()) return null;
      return getCurrentLevel().waveList.get(currentWave); 
   }
   
   
   
   
   
   
   
   // Timer
   public float nextWaveTime = 0;
   
   // Objects
   public Comet comet = null;
   public float distX = 0;
   public float distY = 0;
   public Nova nova = null;
   public GEntity gameStatusLabel;
   
   // UI Objects
   public GEntity toggleTower = new GEntity();
   public GEntity t1 = new GEntity();
   public GEntity t2 = new GEntity();
   
   
   // Flags
   public boolean showTowerOption = false;
   
   public boolean proceedUpdate = true;
   
   
   public TDPlanet planet;
   public ArrayList<TDTower> towerList = new ArrayList<TDTower>();
   public ArrayList<TDLevel> levelList = new ArrayList<TDLevel>();
   
   
   // Trackers
   public int currentLevel;
   public int currentWave;
   public ArrayList<TDEnemy> currentEnemies = new ArrayList<TDEnemy>();
   
   private static TDGame inst;
   public AndroidGame androidGame;
}
