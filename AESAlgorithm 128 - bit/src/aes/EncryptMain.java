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
public class EncryptMain {
   
    public static void main(String[] args) {
        System.out.println("---AES Encryption 128 bit---");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the 128 bit plain text: ");
        String plaintext = sc.nextLine();
        System.out.println("Plain Text : "+plaintext);
        if(plaintext.length()>16)
        {
            System.out.println("Palin Text length can't be above 128 bit 16 characters");
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
        AESencryption aes = new AESencryption(plaintext,key);
        System.out.println("Initial Input State Array:");
        aes.printarrayHexa(aes.input,4,4);
        System.out.println("Input Key Array:");
        aes.printarrayHexa(aes.inputkey,4,4);
        KeySchedule ks = new KeySchedule();
        aes.encryptkeys = ks.getKeyScheduleEncryption(aes.inputkey);
        System.out.println("Expanded Input Key Array:");
        aes.printarrayHexa(aes.encryptkeys,4,44);
        
        aes.addroundkey(0);  
       //copy output state into input state
        aes.copyoutputtoinput();
        System.out.println("Before Rounds start State array:");
        aes.printarrayHexa(aes.input,4,4);
        for(int roundno=1;roundno<11;roundno++) {
            aes.tranformations(roundno);
        }
        String c_text = "";
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                c_text +=(char) aes.input[j][i];
            }
        }
        System.out.println("Cipher text: "+c_text);
    }
}
