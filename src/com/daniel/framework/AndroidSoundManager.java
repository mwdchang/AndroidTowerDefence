package com.daniel.framework;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

////////////////////////////////////////////////////////////////////////////////
// Manages and play sound files
//    Sound effects should use SoundPool
//    Background music should use MediaPlayer 
////////////////////////////////////////////////////////////////////////////////
public class AndroidSoundManager {
   
   public AndroidGame androidGame;
   public SoundPool soundPool;
   public MediaPlayer mediaPlayer;
   
   
   public AndroidSoundManager(AndroidGame game) {
      androidGame = game;
      soundPool = new SoundPool(30, AudioManager.STREAM_MUSIC, 0);
      mediaPlayer = new MediaPlayer();
   }
   
   
   
   public int loadMusic(String fname) {
      try {
         AssetFileDescriptor afd = androidGame.getAssets().openFd(fname);
         mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
         afd.close();
         
         mediaPlayer.prepare();
         mediaPlayer.start();
      } catch (Exception e) {}
      return 0;
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
   
   public void onPause() {
      mediaPlayer.pause();
   }
   
   public void onResume() {
      mediaPlayer.start();
   }
   
   
   public void onStop() {
      mediaPlayer.stop();
   }
   
   public void onDestroy() {
      mediaPlayer.stop();
   }



}
