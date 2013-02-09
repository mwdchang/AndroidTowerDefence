package com.daniel.demo2;

import com.daniel.framework.AndroidGame;

public class ParticleDemo extends AndroidGame {
   
   ParticleScreen pscreen = new ParticleScreen(this);

   public ParticleDemo() {
      super();
      this.setUpdateInterval(20);
   }
   
   
   @Override
   public void setInitScreen() {
      this.androidScreen = pscreen;
   }

}
