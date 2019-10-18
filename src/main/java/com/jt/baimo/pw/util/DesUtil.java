package com.jt.baimo.pw.util;

import com.google.common.html.HtmlEscapers;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.util.Base64;

public class DesUtil {


    public final static String AIR_TICKET_SECRET_KEY = "baimoticket123456789baimo0"; // 密钥


    private final static String IV = "01234567"; // 向量
    private final static String ENCODING = "utf-8";// 加解密统一使用的编码方式
    private final static String INSTANCE_VALUE = "desede";
    private final static String CIPHER_INSTANCE_VALUE = "desede/CBC/PKCS5Padding";

    public static String encode(String key, String plainText) {
        Key deskey = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(INSTANCE_VALUE);
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_VALUE);
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(ENCODING));
            return Base64.getEncoder().encodeToString(encryptData);
        } catch (Exception e) {
            e.printStackTrace();
            return plainText;
        }
    }


    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String key, String encryptText) {
        Key deskey = null;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(INSTANCE_VALUE);
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_VALUE);
            IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.getDecoder().decode(encryptText));
            return new String(decryptData, ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
            return encryptText;
        }
    }




}