package com.haotu369.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/19
 */
public class EncryptionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtils.class);

    private static final String HEX_DIGITS = "0123456789abcdef";
    private static final String SALT = "5fbd161e52367473bebcee98d473d425";
    private static final String ALGORITHM = "MD5";

    /**
     * 加密明文
     *
     * @param raw
     * @return
     */
    public static String encryption(String raw) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            byte[] bytes = messageDigest.digest(mergeRawAndSalt(raw, SALT).getBytes("UTF-8"));

            return byteArrayToHex(bytes);

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("暂不支持该类加密算法 {}", e.getMessage());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("无效的编码方式 {}", e.getMessage());
        }
        return null;
    }

    /**
     * 判断明文与数据库存储的密文是否一致
     *
     * @param raw
     * @param cipher
     * @return
     */
    public static boolean equals(String raw, String cipher) {
        return encryption(raw).equals(cipher);
    }
    
    private static String byteArrayToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            builder.append(byteToHex(bytes[i]));
        }
        return builder.toString();
    }

    private static String byteToHex(byte b) {
        StringBuilder builder = new StringBuilder();
        builder.append(HEX_DIGITS.charAt((b >> 4) & 0x0f)); // 位运算取高四位
        builder.append(HEX_DIGITS.charAt(b & 0x0f)); //去低四位

        return builder.toString();
    }

    private static String mergeRawAndSalt(String raw, String salt) {
        if (null == raw) {
            raw = "";
        }
        if (StringUtils.isEmpty(salt)) {
            return raw;
        }
        return raw + "{" + salt + "}";
    }
}
