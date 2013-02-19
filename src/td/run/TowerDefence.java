package td.run;

import td.TDGame;

import com.androidG.framework.android.AndroidGame;

public class TowerDefence extends AndroidGame {
   
   GameScreen pscreen = new GameScreen(this);

   public TowerDefence() {
      super();
      this.setUpdateInterval(20);
      TDGame.inst();
   }
   
   
   @Override
   public void setInitScreen() {
      this.androidScreen = pscreen;
   }

}
