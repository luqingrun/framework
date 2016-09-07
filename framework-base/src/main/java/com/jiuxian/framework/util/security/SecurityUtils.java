package com.jiuxian.framework.util.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

public class SecurityUtils {
    private static Logger logger = Logger.getLogger(SecurityUtils.class);
    /**
     * 默认的秘钥
     */
    private static final String DEFAULT_PASSWORD = "0kghz@I*";

    public static ThreadLocal<String> securityKey = new ThreadLocal();


    public static String encrypt(Object content) {
        if(content instanceof Number || content instanceof CharSequence){
            return encrypt(content.toString());
        }else{
            throw new IllegalArgumentException("unsupported class "+ content.getClass());
        }
    }

    public static String encrypt(Object content, String key) {
        if(content instanceof Number || content instanceof CharSequence){
            return encrypt(content.toString(), key);
        }else{
            throw new IllegalArgumentException("unsupported class "+ content.getClass());
        }
    }

    public static Long decryptToLong(String content, String key) {
        return Long.valueOf(decrypt(content, key));
    }

    public static Long decryptToLong(String content) {
        return Long.valueOf(decrypt(content));
    }

    public static Integer decryptToInt(String content, String key) {
        return Integer.valueOf(decrypt(content, key));
    }

    public static Integer decryptToInt(String content) {
        return Integer.valueOf(decrypt(content));
    }

    public static String encrypt(String content) {
        String key = securityKey.get();
        if (StringUtils.isBlank(key)) {
            key = DEFAULT_PASSWORD;
        }
        return AES.encode(content, key);
    }

    public static String encrypt(String content, String key) {
        return AES.encode(content, key);
    }

    public static String decrypt(String content, String key) {
        return AES.decode(content, key);
    }

    public static String decrypt(String content) {
        String key = securityKey.get();
        if (StringUtils.isBlank(key)) {
            key = DEFAULT_PASSWORD;
        }
        return AES.decode(content, key);
    }

    public static void main(String[] args) {
        String key = "jyhtre";
        String content = "哈市的按时发送的";
        for(int i=0;i<100;i++) {
            String encrypt = SecurityUtils.encrypt(content, key);
            System.out.println(encrypt);
            System.out.println(SecurityUtils.decrypt(encrypt,key));
        }
    }

}