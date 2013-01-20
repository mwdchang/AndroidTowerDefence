package com.daniel.framework;

////////////////////////////////////////////////////////////////////////////////
// Represents one view/screen
////////////////////////////////////////////////////////////////////////////////
public abstract class AndroidScreen {
   public abstract void update(float dTime);
   public abstract void render(float dTime);
   public abstract void pause();
   public abstract void resume();
   public abstract void displose();
   
   public AndroidGame androidGame;
}
