/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author victor
 */
public class MD5Encode {
    
    public static String encode(String data) throws NoSuchAlgorithmException{
 
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
 
        return  sb.toString();
    }
    
}