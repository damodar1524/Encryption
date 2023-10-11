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
public class AESencryption {
     int[][] input=new int[4][4];
     int[][] inputkey=new int[4][4];
     int[][] output=new int[4][4];
     int[][] encryptkeys=new int[4][44];
     String[] roundkeys =new String[11];
     int[][] mixvalues = new int[][]{{2,3,1,1},{1,2,3,1},{1,1,2,3},{3,1,1,2}};
     SubBytes sb = new SubBytes();
     int[][] decrptkeys = new int[4][44];
     AESencryption(String plaintext,String key)
     {
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                input[i][j] = 48;
                inputkey[i][j] = 48;
            }
        }
        //converting plaintext into words
        char ch[];
        ch = plaintext.toCharArray();
        int i=0,j=0,count=0;
        for(int k=0;k<ch.length;k++){
            if(count==4){
                i=0; j++;count=0;
            }    
             input[i++][j]= ch[k];
            count++;
        }
        //converting key into words
        ch = key.toCharArray();
        i=0; j=0; count=0;
        for(int k=0;k<ch.length;k++){
            if(count==4){
                i=0; j++;count=0;
            }    
            inputkey[i++][j]= ch[k];
            count++;
        }
     }
     void addroundkey(int roundno){
         for(int i=roundno*4;i<(roundno*4+4);i++) {
            for(int j=0;j<4;j++){
                 output[j][i%4]=input[j][i%4]^encryptkeys[j][i];
            }
        }
    }
    void tranformations(int roundno) {
       // substitube bytes
       for(int i=0;i<4;i++){
        for(int j=0;j<4;j++){
            String b=(String)Integer.toHexString(input[i][j]);
            output[i][j]=sb.getSubBytesEncryption(b);
        }
       }
       copyoutputtoinput();
       System.out.println("Round "+roundno+" State array after Sub bytes:");
       printarrayHexa(input,4,4);   
       //shift rows - left circular rotation
        for(int i=1;i<4;i++){
           for(int j=1;j<=i;j++){
                shiftrows(i);
         }
       }
       copyoutputtoinput();
       System.out.println("Round "+roundno+"  State array  after shift rows:");
       printarrayHexa(input,4,4); 
       if(roundno!=10) {
            mixcolumns();
            copyoutputtoinput();
            System.out.println("Round "+roundno+"  State after mix columns:");
            printarrayHexa(input,4,4); 
       }
       addroundkey(roundno);
       copyoutputtoinput();
       System.out.println("Round "+roundno+" Output State add round key :");
       printarrayHexa(input,4,4); 
       copyoutputtoinput(); // input for next round
       System.out.println("Cipher Text : ");
       printarray(output,4,4); 
    }
    void shiftrows(int rowno){
        int temp=-1,j=0;
        temp = output[rowno][j];
        for(;j<3;j++){
           output[rowno][j] = output[rowno][j+1];
        }
        output[rowno][3] = temp;
    }
    void mixcolumns(){
        int i,j,k=0,temp=0;
        for(k=0;k<4;k++){
            //System.out.println("k: "+k);
            for(i=0;i<4;i++){
                temp = 0;
                for(j=0;j<4;j++) {
                    //System.out.println("i: "+i+" j: "+j);
                    temp = temp ^ gf(mixvalues[i][j],input[j][k]);
                }
                //System.out.println("temp:"+temp+" - " + Integer.toHexString(temp));
                output[i][k]=temp;
            }
        }
    }
    int gf(int a, int b) {
        int r=0;
        a = a%256;
        b = b%256;
        for(int i=0;i<8;i++){
          r = r ^ ((a & 1) * b);
          b = (b<<1) ^ (b>>7) * 283;
          a = a>>1;
        }
        return r;
    }  
    void copyoutputtoinput() {
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++){
                input[i][j]=output[i][j];
            }
        } 
    }
    void printarray(int[][] arr,int m, int n){
         int i,j;
         for(i=0;i<m;i++){
            System.out.print("w"+i+" : [ ");
            for(j=0;j<n;j++){
                System.out.print(arr[i][j]);
                if(j==3)
                    continue;
                System.out.print(",");
            }
            System.out.println(" ]");
        }
     }
     void printarrayHexa(int[][] arr,int m, int n){
         int i,j;
         for(i=0;i<m;i++){
            System.out.print("w"+i+" : [ ");
            for(j=0;j<n;j++){
                System.out.print(Integer.toHexString(arr[i][j]));
                if(j==3)
                    continue;
                System.out.print(",");
            }
            System.out.println(" ]");
        }
     }   
}
