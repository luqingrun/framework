package com.jiuxian.framework.util.security;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES {

    public static final String algorithmStr = "AES/ECB/PKCS5Padding";

    private static byte[] getSecretKey(String key){
        if(StringUtils.isBlank(key)){
            throw new IllegalArgumentException("key must be not null");
        }
        return MD5.md5(key).substring(8,24).getBytes();
    }

    private static byte[] encode(byte[] content, byte[] keyBytes) {
        Key key = new SecretKeySpec(keyBytes, "AES");
        try {
            Cipher cipher = Cipher.getInstance(algorithmStr);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    public static byte[] decode(byte[] content, byte[] keyBytes) {
        try {
            Key key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance(algorithmStr);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static String decode(String content, String key) {
        content = content.replace("~", "=");
        byte[] keyByte = getSecretKey(key);
        byte[] contentByte = Base64.getUrlDecoder().decode(content);
        return new String(decode(contentByte, keyByte));
    }

    public static String encode(String content, String key) {
        byte[] keyByte = getSecretKey(key);
        byte[] secretContent = encode(content.getBytes(), keyByte);
        return Base64.getUrlEncoder().encodeToString(secretContent).replace("=","~");
    }

}