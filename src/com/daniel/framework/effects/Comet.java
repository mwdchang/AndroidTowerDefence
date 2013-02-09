package com.daniel.framework.effects;


////////////////////////////////////////////////////////////////////////////////
// A comet/meteor like effect
////////////////////////////////////////////////////////////////////////////////
public class Comet extends ParticleCluster {
   
   public float cx=0, cy=0;
   public float scale = 1.0f;

   public Comet(int num) {
      super(num);
   }

   
   @Override
   public void init() {
      for (int i=0; i < this.numParticles; i++) {
         double velocity = Math.random()*5 + 0.001;            
         double angle    = Math.random()*360 * Math.PI / 180.0;
         
         p[i] = new Particle();
         p[i].x = cx;
         p[i].y = cy;
         p[i].z = 0;
         
         p[i].dx = (float)(Math.cos(angle)*velocity);
         p[i].dy = (float)(Math.sin(angle)*velocity);
         p[i].dz = (float)(Math.random());
         
         p[i].life = 1.0f;
         p[i].fadespeed = (float)(Math.random()/6.0f);
      }
   }

   @Override
   public void update() {
      double size; // = 0.05f;
      double slowX = 0.99f;
      double slowY = 0.99f;
      double slowZ = 0.99f;

      for (int i=0; i < this.numParticles; i++) {

         p[i].x += p[i].dx * scale;
         p[i].y += p[i].dy * scale;

         p[i].dx *= slowX;
         p[i].dy *= slowY;
         p[i].dz *= slowZ;
         p[i].life -= p[i].fadespeed;


         if (p[i].life < 0.05f) {       
            double velocity = Math.random() + 0.001f;            
            double angle    = Math.random()*360 * Math.PI / 180.0;
            
            p[i] = new Particle();
            p[i].x = cx;
            p[i].y = cy;
            p[i].z = 0;
            
            p[i].dx = (float)(Math.cos(angle)*velocity);
            p[i].dy = (float)(Math.sin(angle)*velocity);
            p[i].dz = (float)(Math.random());
            
            p[i].life = 1.0f;
            p[i].fadespeed = (float)(Math.random()/6.0f);
         }
      }      
   }
   
}
