package demos.demo2;

import td.TDGame;

import com.androidG.framework.android.AndroidGame;

public class ParticleDemo extends AndroidGame {
   
   ParticleScreen pscreen = new ParticleScreen(this);

   public ParticleDemo() {
      super();
      this.setUpdateInterval(20);
      TDGame.inst();
   }
   
   
   @Override
   public void setInitScreen() {
      this.androidScreen = pscreen;
   }

}
