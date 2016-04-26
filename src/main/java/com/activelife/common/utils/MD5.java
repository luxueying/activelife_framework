package com.activelife.common.utils;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    /**
     * 使用MD5加密
     * 
     * @param source
     *            字符串
     * @return 加密后的字符串
     */
    public static String md5(String source) {
        String target = null;
        // 实例化一个MessageDigest对象，返回一个指定算法的MessageDigest对象，指定的算法即为MD5
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // 使用缓冲区
            ByteBuffer buffer = ByteBuffer.wrap(source.getBytes());
            md5.update(buffer);

            // 使用指定的 byte 数组更新摘要。
            // md5.update(source.getBytes());

            // 加密
            byte[] byteSource = md5.digest();
            int length = byteSource.length;
            String temp = "";
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                temp = Integer.toHexString(byteSource[i] & 0XFF);
                if (temp.length() == 1) {
                    builder.append("0").append(temp);
                } else {
                    builder.append(temp);
                }
            }

            target = builder.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return target;
    }
}
