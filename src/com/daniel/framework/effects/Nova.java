package com.daniel.framework.effects;


////////////////////////////////////////////////////////////////////////////////
// A one time only 
////////////////////////////////////////////////////////////////////////////////
public class Nova extends ParticleCluster {

   public float cx=0, cy=0;
   public float scale = 1.0f;
   
   public Nova(int num) {
      super(1);
   }

   
   @Override
   public void init() {
      p[0] = new Particle();
      p[0].x = cx;
      p[0].y = cy;
      p[0].z = 0;      
      
      p[0].life = 1.0f;
      p[0].fadespeed = (float)(Math.random()/6.0f);      
      
   }

   @Override
   public void update() {
      if (p[0].life > 0) p[0].life -= p[0].fadespeed;
   }
   

}
