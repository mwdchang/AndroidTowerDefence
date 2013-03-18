package td;

import com.androidG.framework.graphics.effects.Lines;

////////////////////////////////////////////////////////////////////////////////
// Tower/ship definition
////////////////////////////////////////////////////////////////////////////////
public abstract class TDTower {
   public float cx, cy;
   public float orientation;
   
   public float cooldown;
   public float range;
   public float baseDamage;
   
   public int textureID;
   
   public int enemy = -1;
   
   public Lines fireEffect = null;
   
}
