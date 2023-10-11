/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

/**
 *
 * @author Danny
 */
public class KeySchedule {
    int[][] inputkey = new int[4][4];
    int[][] keywords = new int[44][4];
    int[][] encryptkeys = new int[4][44];
    int[][] decryptkeys = new int[4][44];
    int[][] roundconstant = new int[10][4];
    KeySchedule() {
        roundconstant[0][0] = 1;
        for(int i=1;i<8;i++){
            roundconstant[i][0] = roundconstant[i-1][0]*2;
        }
        roundconstant[8][0] = 27;
        roundconstant[9][0] = 54;
    }
    int[][] getKeyScheduleEncryption(int[][] key){
    int i,j;
    //transpose
    for(i=0;i<4;i++){
        for(j=0;j<4;j++){
            inputkey[i][j]=key[j][i];
        }
    }
  
    for(i=0;i<4;i++){
        for(j=0;j<4;j++) {
                keywords[i][j] = inputkey[i][j];
            }
         }  
        for(i=4;i<44;i++){
            if(i%4==0)
            {
                int[] newword = new int[4];
                newword = gee(i-1);
                for(int x=0;x<4;x++)
                    keywords[i][x] = newword[x] ^ keywords[i-4][x];                
            }
            else {
                for(int x=0;x<4;x++)
                    keywords[i][x] = keywords[i-1][x] ^ keywords[i-4][x]; 
            }    
        }
        // transposing
        for(i=0;i<44;i++){
            for(j=0;j<4;j++){
                
                encryptkeys[j][i] = keywords[i][j];
            }
        }
        return encryptkeys;
    }
    int[][] getKeyScheduleDecryption(int[][] key){
        int[][] temp_encryptkeys = getKeyScheduleEncryption(key);
        for(int k=0;k<4;k++){
            for(int i=0;i<44;i=i+4){
                int x=40-i;
                for(int j=i;j<i+4;j++){
                    decryptkeys[k][x++]= temp_encryptkeys[k][j];
                 }
            }
        }
        return decryptkeys;
    }
    int[] gee(int i){
        //circular shift
        int[] newword = new int[4];
         System.arraycopy(keywords[i], 0, newword, 0, 4);
        int temp[] = new int[1];
        temp[0] = newword[0];
        for(int k=0;k<3;k++){
            newword[k] = newword[k+1];
        }
        newword[3]=temp[0];
       // substitube bytes
       SubBytes sb = new SubBytes();
       for(int j=0;j<4;j++){
            String b=(String)Integer.toHexString(newword[j]);
            newword[j]=sb.getSubBytesEncryption(b);
       } 
      // System.out.println("Round Constant:");
      // printarray(roundconstant,10,4);
      for(int x=0;x<4;x++)
            newword[x] = newword[x] ^ roundconstant[i/4][x];
      /*System.out.println("new word:");
      for(int x=0;x<4;x++)
          System.out.print(newword[x]+",");
      System.out.println();*/
      return newword;
     }  
}
        
