/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;

import java.util.Scanner;

/**
 *
 * @author Danny
 */
public class DecryptMain {
    public static void main(String[] args) {
        //Decryption
        System.out.println("---AES Decryption 128 bit---");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the 128 bit cipher text: ");
        String ciphertext = sc.nextLine();
        System.out.println("cipher Text : "+ciphertext);
        if(ciphertext.length()>16)
        {
            System.out.println("Cipher Text length can't be above 128 bit 16 characters");
            System.exit(1);
        }
        System.out.println("Enter a key : ");
        String key=sc.nextLine();
        System.out.println("Key : "+key);
        if(key.length()>16)
        {
            System.out.println("Key length is above 128");
            System.exit(1);
        } 
               //converting ciphertext into words
        char ch[];
        int[][] input = new int[4][4];
        int i=0,j=0,count=0;
        ch = ciphertext.toCharArray();
        for(int k=0;k<ch.length;k++){
            if(count==4){
                i=0; j++;count=0;
            }    
             input[i++][j]= ch[k];
            count++;
        }
        AESdecryption aes = new AESdecryption(input,key,ciphertext.length());
        System.out.println("Initial Input State Array:");
        aes.printarray(aes.input,4,4);
        System.out.println("Input Key Array:");
        aes.printarrayHexa(aes.inputkey,4,4);
        KeySchedule ks = new KeySchedule();
        aes.decryptkeys = ks.getKeyScheduleDecryption(aes.inputkey);
        System.out.println("Expanded Encrypt keys:");
        aes.printarrayHexa(ks.encryptkeys,4,44);
        System.out.println("Expanded Decrypt keys:");
        aes.printarrayHexa(ks.decryptkeys, 4, 44);
        aes.addroundkey(0);  
        //copy output state into input state
        aes.copyoutputtoinput();
        System.out.println("Before Rounds start State array:");
        aes.printarrayHexa(aes.input,4,4);
        for(int roundno=1;roundno<11;roundno++) {
            aes.tranformations(roundno);
        }
        String p_text = "";
        for(i=0;i<4;i++){
            for(j=0;j<4;j++){
                p_text +=(char) aes.input[j][i];
            }
        }
        System.out.println("Plain text: "+p_text);
    }
}
