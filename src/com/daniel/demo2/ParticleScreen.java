package com.daniel.demo2;

import java.util.List;

import android.opengl.GLES20;

import com.daniel.demo.DemoLoader;
import com.daniel.framework.AndroidGame;
import com.daniel.framework.AndroidScreen;
import com.daniel.framework.TouchEvent;
import com.daniel.framework.effects.Comet;
import com.daniel.framework.effects.Nova;
import com.daniel.framework.graphics.GEntity;
import com.daniel.framework.graphics.Util;

public class ParticleScreen extends AndroidScreen {
   
   public Comet comet;
   public Nova nova;
   private float distX=0, distY=0;
   private float oldX=0, oldY=0;
   
   
   public ParticleScreen(AndroidGame game) {
      androidGame = game;
      distX = distY = 0;
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }

   @Override
   public void displose() {
   } 

   @Override
   public void init() {
      ParticleAssets.TX_COMET = ParticleAssets.createRadialBlur(); 
      ParticleAssets.TX_FONT = ParticleAssets.createStaticFont(androidGame, "Particle Test");
      ParticleAssets.TX_NOVA = ParticleAssets.loadGLTexture(androidGame, "exp1.JPG");
      comet = new Comet(50);
      comet.scale = 5;
   }

   
   
   
   @Override
   public void update(float dTime) {
      
      ////////////////////////////////////////////////////////////////////////////////
      // Start to update state
      ////////////////////////////////////////////////////////////////////////////////
      List<TouchEvent> list = androidGame.androidTouchHandler.getTouchEvents();
      for (TouchEvent t : list) {
         if (t.type == TouchEvent.TOUCH_DOWN) {
            float point[] = Util.screen2game(androidGame, new float[]{t.x, t.y} );
            distX = point[0];
            distY = point[1];
            oldX = comet.cx;
            oldY = comet.cy;
            
            nova = new Nova(1);
            nova.cx = distX;
            nova.cy = distY;
            nova.scale = 100;
            
            break;
         }
      }      
      
      if (Math.sqrt( (comet.cx-distX)*(comet.cx-distX) + (comet.cy-distY)*(comet.cy-distY)) > 15) {
         comet.cx += (distX-oldX)*0.02;
         comet.cy += (distY-oldY)*0.02;
      }
      comet.update();
      
      if (nova != null && nova.p[0].life > 0) nova.update();
      
      
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
      for (int i=0; i < comet.numParticles; i++) {
         GEntity e = new GEntity();   
         e.cx = comet.p[i].x;
         e.cy = comet.p[i].y;
         e.width  = comet.p[i].life * 50.0f;
         e.height = comet.p[i].life * 50.0f;
         e.textureId = ParticleAssets.TX_COMET;
         e.colour = new float[]{ 0.0f, 0.5f, 0.8f, 0.5f};
         
         androidGame.renderEngine.addObject(e);
      }
      
      if (nova != null && nova.p[0].life > 0) {
         GEntity novaEntity = new GEntity();
         novaEntity.cx = nova.cx;
         novaEntity.cy = nova.cy;
         novaEntity.width = (1.0f - nova.p[0].life)*nova.scale;
         novaEntity.height = (1.0f - nova.p[0].life)*nova.scale;
         novaEntity.textureId = ParticleAssets.TX_NOVA;
         androidGame.renderEngine.addObject(novaEntity); 
      }
      
      
      ////////////////////////////////////////////////////////////////////////////////
      // Second batch
      ////////////////////////////////////////////////////////////////////////////////
      androidGame.renderEngine.newBatch();
      androidGame.renderEngine.setBatchDepthTest(false);
      androidGame.renderEngine.setBatchBlend(true);
      ParticleAssets.TX_FONT.cx = 0;
      ParticleAssets.TX_FONT.cy = 0;
      ParticleAssets.TX_FONT.colour = new float[]{1, 0, 0, 0.5f};
      androidGame.renderEngine.addFont( ParticleAssets.TX_FONT );
      
   }
   

}
