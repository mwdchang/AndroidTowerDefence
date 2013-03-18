package com.androidG.framework.android;



public class Util {
   
   // Run of the mill distance calculation
   public static float dist(float x1, float y1, float x2, float y2) {
      return (float) Math.sqrt( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));   
   }
   
   // Convert screen coordinate into normalized coord in the range of [-1, 1]
   public static float[] screen2normal(AndroidGame game, float[] coord) {
      /*
      float nx = coord[0] - game.width;
      float ny = game.height - coord[1];
      return new float[]{ nx /(game.width), ny/(game.height)};
      */
      float nx = coord[0] - 0.5f*game.deviceWidth;
      coord[1] = game.deviceHeight - coord[1];
      float ny = coord[1] - 0.5f*game.deviceHeight;
      return new float[]{ nx /(0.5f*game.deviceWidth), ny/(0.5f*game.deviceHeight)};
      
   }
   
   // Take a normalize coord [-1, 1] to the game's coordinate 
   public static float[] normal2game(AndroidGame game, float[] coord) {
      return new float[]{ coord[0]*game.width, coord[1]*game.height};
   }
   
   // Convert a screen coordinate into a game coordinate
   public static float[] screen2game(AndroidGame game, float[] coord) {
      return normal2game(game, screen2normal(game, coord));
   }
   
   
   // 2D polygon tesselation 
   
}
