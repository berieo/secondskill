package com.spring.secondskill.util;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String md5(String src){
        //加密
        return DigestUtils.md5Hex(src);
    }

    //客户端输入密码加salt后MD5机密
    private static final String salt = "1a2b3c4d";
    private static final String saltDB = "1a2b3c4d";
    public static String inputPassFormPass(String inputPass){
        String str = salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    //服务端拿到密码加salt后MD5加密
    public static String formPassToDBPass(String formPass, String salt){
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    //客户端输入生成到数据库
    public static String inputPassToDBPass(String inputPass, String saltDB){
        String formPass = inputPassFormPass(inputPass);
        String dbPass = formPassToDBPass(inputPass, saltDB);
        return md5(dbPass);
    }


    public static void main(String args[]){
        System.out.println(inputPassFormPass("123456"));

        System.out.println(formPassToDBPass(inputPassFormPass("123456"),salt));
    }
}
