/**
 * EncryptUtil.java
 * Created at 2021-08-26
 * Created by xieyingbin
 * Copyright (C) 2020 nikki, All rights reserved.
 */
package com.nikki.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加密工具类
 * 
 * @author xieyingbin
 *
 */
public class EncryptUtil {

    //RSA私钥
    private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIRhZevqMQTIUmGLhsoduux0ao/+53xea2MoR8MDSOD4i3N99ye6LPcIGryNFoz3DG4XuUkJCLio8mvwD2lnIyc2StmIY4L8CxNV1FCAx62nouaTBYr2JRC/R/sLU9Cn6bJH9FCeQKf5QRCmkAOTaVCR5dz/mVaoVFqLwFL6/COjAgMBAAECgYAXLkb+rJ+wyrNCSYBs3A/jX+9ZD7XRXWdIkcEfMPAOFPe2XZRt7Hf7OfwUkFAmlwwiKx1S4LYaP9tTc1H/jwPklWWmjYSRsKAjbFN+mQJxThdgLfLO0HRrnpIPghke8yP3I3UtOpurFv/ULsiXx7PeHtyozsWB3n3wYcK+x/kUQQJBAL7XkUzGD0O/17/T0pd8tzjs+1uoWZNr9Edeamdh2F5V+HrlvVIlsiY3OHHB/bN03+gDCoZkq4Dyn13u2qjdLkkCQQCxlAfT3EmTqz4pylp50lyWq5tTfnVlJjzKxO84aMzqe35jC4a4f9dWaJ/u663jQ6KpHDRIKr/xwk2Fup14C/KLAkA2ukb2x02VViye3DXmL2OztXIXxN17Q1gYJ2FIKG0OEIVjaQKzzg+amfdb2A/TKM8VD9oy1CfCoNFG9LpL9rHRAkEApWNHfwxWApCkzutGJNvFnBasDbFjyRc522hbnOAOqfzmk3yytrdqaXH8MtuSEm512qcIEoauSo47AWfzLVGDTQJAQh1lQvykboZe7DX3IKf7Bd8uRKcZPT9IemEcZcuILvT5gWN9KXnomx1bRbfjJnvrZSl81x2hnJywcSBYgE117g==";

    //RSA公钥
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEYWXr6jEEyFJhi4bKHbrsdGqP/ud8XmtjKEfDA0jg+Itzffcnuiz3CBq8jRaM9wxuF7lJCQi4qPJr8A9pZyMnNkrZiGOC/AsTVdRQgMetp6LmkwWK9iUQv0f7C1PQp+myR/RQnkCn+UEQppADk2lQkeXc/5lWqFRai8BS+vwjowIDAQAB";

    /**
     * 密码加密
     *
     * @param password 原文密码
     * @return 加密结果
     */
    public static String encodePassword(String password) {
        return (new BCryptPasswordEncoder()).encode(password);
    }

    /**
     * 密码对比
     * 
     * @param rawPassword 原生密码
     * @param encodedPassword 加密后密码
     * @return true: 相同，false: 不相同
     */
    public static boolean matchPassword(String rawPassword, String encodedPassword) {
    	return (new BCryptPasswordEncoder()).matches(rawPassword, encodedPassword);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @return
     */
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        //加密内容使用base64进行编码
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }

    /**
     * RSA解密
     * @param data 待解密数据
     * @return
     */
    public static String decrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
        byte[] dataBytes = Base64.decodeBase64(data);
        byte[] decryptedData = cipher.doFinal(dataBytes);
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        // 解密由base64编码的私钥
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        //实例化rsa转换字节数组
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        // 取私钥匙对象
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

//    public static void main(String[] args) throws Exception {
//        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
//        generator.initialize(1024);
//        //获取RSA密钥对
//        KeyPair keyPair = generator.generateKeyPair();
//        //获取私钥
//        String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
//        //获取公钥
//        String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
//        System.out.println("privateKey = " + privateKey);
//        System.out.println("publicKey = " + publicKey);
//        String encodeRSAPassword = encrypt("P@ss123456");
//        System.out.println("RSAEncryptPassword = " + encrypt("P@ss123456"));  
//        System.out.println("RSADecryptPassword = " + decrypt(encodeRSAPassword));  
//        System.out.println("BCryptPasswordEncod = " + encodePassword("P@ss123456"));
//    }
}
