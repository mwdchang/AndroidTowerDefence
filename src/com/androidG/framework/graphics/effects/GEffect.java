package com.androidG.framework.graphics.effects;

public abstract class GEffect {
   public abstract void render();
   public abstract void init();
   
   public float[] matrix; 
   public boolean doneInit = false;
}
