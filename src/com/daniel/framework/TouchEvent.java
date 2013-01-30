package com.daniel.framework;

public class TouchEvent {
   public int type;
   public int x, y;
   public int pointer;
   
   // No particular reason here, they could be any integers 
   public static final int TOUCH_DOWN = 0x0001;
   public static final int TOUCH_UP   = 0x0010;
   public static final int TOUCH_DRAGGED = 0x0100;
   public static final int TOUCH_HOLD = 0x1000;
   
}
