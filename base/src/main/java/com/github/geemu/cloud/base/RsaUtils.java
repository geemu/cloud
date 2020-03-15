package com.github.geemu.cloud.base;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密、解密、签名、验签、密钥生成
 * @author 陈方明  cfmmail@sina.com
 * @since 2020-02-23 11:17
 */
public final class RsaUtils {

    /** 加密算法 **/
    private static final String ALGORITHM = "RSA";
    /** 签名算法 **/
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 生成密钥对
     * @param keySize 密钥大小
     * @return 密钥对
     * @throws Exception 异常
     */
    public static KeyPair generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom();
        keyPairGen.initialize(keySize, secureRandom);
        return keyPairGen.generateKeyPair();
    }

    /**
     * 加密
     * @param text 待加密的文本
     * @param publicKeyBase64 公钥(BASE64格式)
     * @return 加密后的文本(BASE64格式)
     * @throws Exception 异常
     */
    public static String encryptTextToBase64(String text, String publicKeyBase64) throws Exception {
        return encryptTextToBase64(text, createPublicKey(publicKeyBase64));
    }

    /**
     * 加密
     * @param text 待加密的文本
     * @param publicKey 公钥
     * @return 加密后的文本(BASE64格式)
     * @throws Exception 异常
     */
    public static String encryptTextToBase64(String text, RSAPublicKey publicKey) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(encryptTextToByteArray(text, publicKey));
    }

    /**
     * 从字符串创建公钥
     * @param publicKeyBase64 公钥
     * @return 公钥
     * @throws Exception 异常
     */
    public static RSAPublicKey createPublicKey(String publicKeyBase64) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(publicKeyBase64);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * 加密
     * @param text 待加密的文本
     * @param publicKey 公钥
     * @return 加密后的文本(byte[]格式)
     * @throws Exception 异常
     */
    public static byte[] encryptTextToByteArray(String text, RSAPublicKey publicKey) throws Exception {
        return encryptByteArrayToByteArray(text.getBytes(StandardCharsets.UTF_8), publicKey);
    }

    /**
     * 加密
     * @param byteArray 待加密的文本(byte[]格式)
     * @param publicKey 公钥
     * @return 加密后的文本(byte[]格式)
     * @throws Exception 异常
     */
    public static byte[] encryptByteArrayToByteArray(byte[] byteArray, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        final int maxBlockSize = publicKey.getModulus().bitLength() / 8 - 11;
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return doFinal(byteArray, maxBlockSize, cipher);
    }

    /**
     * 加密或解密
     * @param byteArray 待加密或解密的文本(byte[]格式)
     * @param maxBlockSize 大块（分段）大小，不能小于1
     * @param cipher 负责完成加密或解密工作
     * @return 加密或解密后的数据(byte[]格式)
     * @throws Exception 异常
     */
    private static byte[] doFinal(byte[] byteArray, int maxBlockSize, Cipher cipher) throws Exception {
        // 模长
        final int dataLength = byteArray.length;
        // 不足分段
        if (dataLength <= maxBlockSize) {
            return cipher.doFinal(byteArray, 0, dataLength);
        }
        // 分段加密或解密
        return doFinalWithBlock(byteArray, maxBlockSize, cipher);
    }

    /**
     * 分段加密或解密
     * @param byteArray 待加密或解密的文本(byte[]格式)
     * @param maxBlockSize 最大块（分段）大小，不能小于1
     * @param cipher 负责完成加密或解密工作
     * @return 加密或解密后的数据
     * @throws Exception 异常
     */
    private static byte[] doFinalWithBlock(byte[] byteArray, int maxBlockSize, Cipher cipher) throws Exception {
        final int dataLength = byteArray.length;
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        // 剩余长度
        int remainLength = dataLength;
        int blockSize;
        // 对数据分段处理
        while (remainLength > 0) {
            blockSize = Math.min(remainLength, maxBlockSize);
            out.write(cipher.doFinal(byteArray, offSet, blockSize));
            offSet += blockSize;
            remainLength = dataLength - offSet;
        }
        return out.toByteArray();
    }

    /**
     * 解密
     * @param base64 待解密的文本(BASE64格式)
     * @param privateKey 私钥
     * @return 解密后的文本
     * @throws Exception 异常
     */
    public static String decryptBase64ToText(String base64, RSAPrivateKey privateKey) throws Exception {
        byte[] bytes = (new BASE64Decoder()).decodeBuffer(base64);
        return new String(decrypt(bytes, privateKey), StandardCharsets.UTF_8);
    }

    /**
     * 解密
     * @param byteArray 待解密的文本(byte[]格式)
     * @param privateKey 私钥
     * @return 解密后的文本(byte[]格式)
     * @throws Exception 异常
     */
    public static byte[] decrypt(byte[] byteArray, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        final int maxBlockSize = privateKey.getModulus().bitLength() / 8;
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return doFinal(byteArray, maxBlockSize, cipher);
    }

    /**
     * 签名
     * @param text 待签名的文本
     * @param privateKeyBase64 私钥(BASE64格式)
     * @return 签名后的文本(byte[]格式)
     * @throws Exception 异常
     */
    public static String sign(String text, String privateKeyBase64) throws Exception {
        RSAPrivateKey privateKey = createPrivateKey(privateKeyBase64);
        return sign(text, privateKey);
    }

    /**
     * 从字符串创建私钥
     * @param privateKeyBase64 私钥
     * @return 私钥
     * @throws Exception 异常
     */
    public static RSAPrivateKey createPrivateKey(String privateKeyBase64) throws Exception {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(privateKeyBase64);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 签名
     * @param text 待签名的文本
     * @param privateKey 私钥
     * @return 签名后的文本(byte[]格式)
     * @throws Exception 异常
     */
    public static String sign(String text, RSAPrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(text.getBytes(StandardCharsets.UTF_8));
        return (new BASE64Encoder()).encodeBuffer(signature.sign());
    }

    /**
     * 验签
     * @param base64 待验签的文本(BASE64格式)
     * @param publicKeyBase64 公钥(BASE64格式)
     * @return 验签结果
     * @throws Exception 异常
     */
    public static boolean verify(String base64, String sign, String publicKeyBase64) throws Exception {
        RSAPublicKey publicKey = createPublicKey(publicKeyBase64);
        return verify(base64, sign, publicKey);
    }

    /**
     * 验签
     * @param base64 待验签的文本(BASE64格式)
     * @param publicKey 公钥
     * @return 验签结果
     * @throws Exception 异常
     */
    public static boolean verify(String base64, String sign, RSAPublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update((new BASE64Decoder()).decodeBuffer(base64));
        return signature.verify((new BASE64Decoder()).decodeBuffer(sign));
    }

}
