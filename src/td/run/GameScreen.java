package td.run;

import java.util.ArrayList;
import java.util.List;

import td.TDEnemy;
import td.TDGame;

import android.opengl.GLES20;
import android.util.Log;

import com.androidG.framework.android.AndroidGame;
import com.androidG.framework.android.AndroidScreen;
import com.androidG.framework.android.TouchEvent;
import com.androidG.framework.android.Util;
import com.androidG.framework.graphics.GEntity;
import com.androidG.framework.graphics.effects.Comet;
import com.androidG.framework.graphics.effects.Nova;

public class GameScreen extends AndroidScreen {
   
   public GEntity planet = new GEntity();
   
   
   public GameScreen(AndroidGame game) {
      androidGame = game;
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() { 
      TDGame.inst().init(androidGame);
   }

   @Override
   public void displose() {
   } 

   @Override
   public void init() {
      TDAssets.TX_COMET = TDAssets.createRadialBlur(); 
      TDAssets.TX_FONT = TDAssets.createStaticFont(androidGame, "Particle Test");
      TDAssets.TX_SHIP = TDAssets.loadGLTexture(androidGame, "ship.JPG");
      TDAssets.TX_PLANET = TDAssets.loadGLTexture(androidGame, "planet.JPG"); 
      TDAssets.TX_NOVA = TDAssets.loadGLTexture(androidGame, "exp1.JPG");
      TDAssets.TX_TOWER_TOGGLE = TDAssets.loadGLTexture(androidGame, "towerToggle.JPG");
      TDAssets.TX_TOWER_1 = TDAssets.loadGLTexture(androidGame, "t1.JPG");
      TDAssets.TX_TOWER_2 = TDAssets.loadGLTexture(androidGame, "t2.JPG");
      
      // Initialize label
      TDGame.inst().gameStatusLabel = TDAssets.createStaticFont(androidGame, "Level " + TDGame.inst().currentLevel + " Wave " + TDGame.inst().currentWave);
   } 
 
    
   
   
   @Override
   public void update(float dTime) {
      
      ////////////////////////////////////////////////////////////////////////////////
      // Start to update state
      ////////////////////////////////////////////////////////////////////////////////
      List<TouchEvent> list = androidGame.androidTouchHandler.getTouchEvents();
      
      
      TDGame.inst().updateGame(list, dTime);
      
      
      
      ////////////////////////////////////////////////////////////////////////////////
      // Setting up rendering properties
      ////////////////////////////////////////////////////////////////////////////////
      androidGame.renderEngine.clearEngine();
      androidGame.renderEngine.newBatch();
      androidGame.renderEngine.setBatchBlend(true);
      androidGame.renderEngine.setBatchBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE);
      androidGame.renderEngine.setBatchDepthTest(false);
      
      ////////////////////////////////////////////////////////////////////////////////
      // Send the objects to the render engine
      ////////////////////////////////////////////////////////////////////////////////
      if (TDGame.inst().comet != null) {
         for (int i=0; i < TDGame.inst().comet.numParticles; i++) {
            GEntity e = new GEntity();   
            e.cx = TDGame.inst().comet.p[i].x;
            e.cy = TDGame.inst().comet.p[i].y;
            e.width  = TDGame.inst().comet.p[i].life * 50.0f;
            e.height = TDGame.inst().comet.p[i].life * 50.0f;
            e.textureId = TDAssets.TX_COMET;
            e.colour = new float[]{ 0.0f, 0.5f, 0.8f, 0.5f};
            
            androidGame.renderEngine.addObject(e);
         }
      }
      
      for (TDEnemy enemy  : TDGame.inst().currentEnemies) {
         GEntity e = new GEntity();   
         e.cx = enemy.cx;
         e.cy = enemy.cy;
         e.width = 30;
         e.height = 30;
         e.textureId = TDAssets.TX_SHIP;
         //e.colour = new float[]{1.0f, 0.0f, 0.0f, 0.8f};
         //e.colour = new float[]{1.0f, 1.0f, 1.0f, 0.8f};
         e.colour = new float[]{1.0f, (float)(enemy.life/50.0f), (float)(enemy.life/50.0f), 1.0f};
         androidGame.renderEngine.addObject(e);
      }
       
      
      planet.cx = 0;
      planet.cy = 0;
      planet.width = 80;
      planet.height = 80;
      planet.textureId = TDAssets.TX_PLANET;
      androidGame.renderEngine.addObject( planet );
      
      if (TDGame.inst().nova != null) {
         GEntity novaEntity = new GEntity();
         novaEntity.cx = TDGame.inst().nova.cx;
         novaEntity.cy = TDGame.inst().nova.cy;
         novaEntity.width = (1.0f - TDGame.inst().nova.p[0].life)*TDGame.inst().nova.scale;
         novaEntity.height = (1.0f - TDGame.inst().nova.p[0].life)*TDGame.inst().nova.scale;
         novaEntity.textureId = TDAssets.TX_NOVA;
         androidGame.renderEngine.addObject(novaEntity); 
      }
      
      GEntity label = TDGame.inst().gameStatusLabel;
      label.cx = 000;
      label.cy = 300;
      label.colour = new float[]{1, 0, 1, 0.5f};
      androidGame.renderEngine.addFont(label);
      
      
      
      
      ////////////////////////////////////////////////////////////////////////////////
      // Render UI, this should go last
      ////////////////////////////////////////////////////////////////////////////////
      TDGame.inst().toggleTower.textureId = TDAssets.TX_TOWER_TOGGLE;
      TDGame.inst().toggleTower.width = 50;
      TDGame.inst().toggleTower.height = 50;
      TDGame.inst().toggleTower.cx = -androidGame.width+50;
      TDGame.inst().toggleTower.cy = -androidGame.height+50;
      androidGame.renderEngine.addObject(TDGame.inst().toggleTower);
      
      if (TDGame.inst().showTowerOption == true) {
         TDGame.inst().t1.textureId = TDAssets.TX_TOWER_1;
         TDGame.inst().t1.width = 50;
         TDGame.inst().t1.height = 50;
         TDGame.inst().t1.cx = -androidGame.width+50 + 110;
         TDGame.inst().t1.cy = -androidGame.height+50; 
         androidGame.renderEngine.addObject(TDGame.inst().t1);
         
         TDGame.inst().t2.textureId = TDAssets.TX_TOWER_2;
         TDGame.inst().t2.width = 50;
         TDGame.inst().t2.height = 50;
         TDGame.inst().t2.cx = -androidGame.width+50 + 220;
         TDGame.inst().t2.cy = -androidGame.height+50;
         androidGame.renderEngine.addObject(TDGame.inst().t2);
      }
      
      
      
      ////////////////////////////////////////////////////////////////////////////////
      // Second batch
      ////////////////////////////////////////////////////////////////////////////////
      /*
      androidGame.renderEngine.newBatch();
      androidGame.renderEngine.setBatchDepthTest(false);
      androidGame.renderEngine.setBatchBlend(true);
      ParticleAssets.TX_FONT.cx = 0;
      ParticleAssets.TX_FONT.cy = 0;
      ParticleAssets.TX_FONT.colour = new float[]{1, 0, 0, 0.5f};
      androidGame.renderEngine.addFont( ParticleAssets.TX_FONT );
      */
      
   }
   

}
