package td.util;

import td.TDNormalEnemy;


public class TDEnemyCreator {
   
   public static TDNormalEnemy[] createNormalEnemy(int n) {
      TDNormalEnemy[] result = new TDNormalEnemy[n];
      for (int i=0; i < n; i++) {
         result[i] = new TDNormalEnemy();    
      }
      return result;
   }
   
}
