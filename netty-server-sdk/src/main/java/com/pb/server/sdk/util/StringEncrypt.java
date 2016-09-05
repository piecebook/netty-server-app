package com.pb.server.sdk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringEncrypt {
    final static String SHA256 = "SHA-256";

    final static String MD5 = "MD5";

    final static String SHA1 = "SHA-1";



    public static String SHA256Code(String strSrc){
        return encrypt(strSrc,SHA256);
    }

    /**
     * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256
     *
     * @param strSrc 要加密的字符串
     *
     * @param encName 加密算法的名称
     *
     * @return 哈希码
     */
    private static String encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}