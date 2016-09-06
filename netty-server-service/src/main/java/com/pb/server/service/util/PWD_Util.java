package com.pb.server.service.util;

import java.security.SecureRandom;

/**
 * Created by DW on 2016/9/6.
 */
public class PWD_Util {
    public static String getHash(String pwd){
        return StringEncrypt.SHA256Code(pwd);
    }

    public static String getSalt(int num) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789-_";
        SecureRandom secureRandom = new SecureRandom();
        StringBuffer salt = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int random = secureRandom.nextInt(base.length());
            salt.append(base.charAt(random));
        }
        return salt.toString();
    }
}
