package com.daniel.framework;

import android.media.AudioManager;
import android.media.SoundPool;

public class AndroidSoundManager {
   
   public AndroidGame androidGame;
   public SoundPool soundPool;
   
   
   public AndroidSoundManager(AndroidGame game) {
      androidGame = game;
      soundPool = new SoundPool(30, AudioManager.STREAM_MUSIC, 0);
   }

   
   public int loadSound(String fname) {
      int res = -1;
      try { 
        res = soundPool.load(androidGame.getAssets().openFd(fname), 1);
      } catch (Exception e) {}
      
      return res;
   }
   
   public void playSound( int soundID, float leftVolume, float rightVolume) {
      soundPool.play(soundID, leftVolume, rightVolume, 0, 0, 1.0f);    
   }

}
