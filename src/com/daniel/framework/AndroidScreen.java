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
   
   
   // Any openGL related initializations should go here,
   // Graphics related initializations are to be
   // called on a thread with openGL context
   public abstract void init();
   
   public AndroidScreen next;
   public AndroidScreen prev;
   
   
   public AndroidGame androidGame;
   public boolean doneInit = false;
}
