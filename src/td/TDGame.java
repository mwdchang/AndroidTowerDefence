package td;

import java.util.ArrayList;
import java.util.Vector;

import td.util.TDEnemyCreator;


////////////////////////////////////////////////////////////////////////////////
// Main game goes in here, this is a singleton class
////////////////////////////////////////////////////////////////////////////////
public class TDGame {
   
   private TDGame() {
      TDLevel levelTest = new TDLevel();
      TDWave waveTest = new TDWave();
      //waveTest.add( TDEnemyCreator.createNormalEnemy(10), TDWave.WAVE_TOP);
      
      waveTest.add( TDNormalEnemy.class, 15, TDWave.WAVE_TOP);
      levelTest.waveList.add( waveTest );
      levelList.add(levelTest);
   }
    
   public static TDGame inst() {
      if (inst == null) inst = new TDGame();
      return inst; 
   }
   
   // Objects
   public TDPlanet planet;
   public ArrayList<TDTower> towerList = new ArrayList<TDTower>();
   public ArrayList<TDLevel> levelList = new ArrayList<TDLevel>();
   
   
   // Trackers
   public int currentLevel;
   public int currentWave;
   
   private static TDGame inst;
}
