package com.daniel.framework.effects;

////////////////////////////////////////////////////////////////////////////////
// A cluster of particles
////////////////////////////////////////////////////////////////////////////////
public abstract class ParticleCluster {
   public ParticleCluster(int num) {
      numParticles = num;
      p = new Particle[num];
      init();
   }
   
   public abstract void init(); 
   public abstract void update();
   
   public Particle p[];
   public int numParticles;
}
