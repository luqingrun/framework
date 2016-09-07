package com.jiuxian.framework.util.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class MD5 {

    public static String md5(String content){
        if(StringUtils.isBlank(content)){
            return null;
        }else {
            return DigestUtils.md5Hex(content);
        }
    }

    public static String md5(InputStream inputStream){
        if(inputStream == null){
            return null;
        }else{
            try {
                return DigestUtils.md5Hex(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public static String md5(File file){
        if(file == null || !file.exists()){
            return null;
        }else{
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                return md5(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }finally {
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
