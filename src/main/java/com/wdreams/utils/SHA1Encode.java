/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wdreams.utils;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author victor
 */
public class SHA1Encode {

    public static String codificarSha1Base64(byte[] origen) {
        return new String(Base64.encodeBase64(DigestUtils.sha1(origen)));
    }

}
