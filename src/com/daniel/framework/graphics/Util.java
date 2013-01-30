package com.daniel.framework.graphics;

import com.daniel.framework.AndroidGame;

public class Util {
   
   // Convert screen coordinate into normalized coord in the range of [-1, 1]
   public static float[] screen2normal(AndroidGame game, float[] coord) {
      float nx = coord[0] - 0.5f*game.width;
      float ny = 0.5f*game.height - coord[1];
      return new float[]{ nx /(0.5f*game.width), ny/(0.5f*game.height)};
   }
   
   // TODO
   public static float[] normal2game(AndroidGame game, float[] coord) {
      return new float[]{0, 0};
   }
   
   // Convert a screen coordinate into a game coordinate
   public static float[] screen2game(AndroidGame game, float[] coord) {
      return normal2game(game, screen2normal(game, coord));
   }
   
}
