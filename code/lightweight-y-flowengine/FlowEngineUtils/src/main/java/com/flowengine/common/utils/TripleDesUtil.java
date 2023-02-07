package com.flowengine.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

/**
 * @Description:3des加密
 * @author yangzl 2022-02-23
 * @version 1.00.00
 * @history:
 */
public class TripleDesUtil {

	
	// 密钥 
	//private final static String secretKey = "hykjhykjhykjhykjhykjhykj"; 
	// 向量 
	private final static String iv = "01234567"; 
	// 加解密统一使用的编码方式 
	private final static String encoding = "utf-8";
	
	private final static Log _logger = LogFactory.getLog(TripleDesUtil.class);

	public static void main(String[] args) {
		String s = encode("hello world", "hykjhykjhykjhykjhykjhykj");
		System.out.println(s);
		s = decode(s, "hykjhykjhykjhykjhykjhykj");
		System.out.println(s);
	}

	/**
	 * 3DES加密
	 * 
	 * @param plainText 普通文本
	 * @return
	 * @throws Exception 
	 */
	public static String encode(String plainText, String secretKey) {

		try {
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
			return Base64.encode(encryptData);
		} catch (Exception e) {
			_logger.error("", e);
		}
		
		return null;
	}

	/**
	 * 3DES解密
	 * 
	 * @param encryptText 加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText, String secretKey) {
		
		try {
			
			Key deskey = null;
		    DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		    SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		    deskey = keyfactory.generateSecret(spec);
		    Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		    IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		    cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

		    byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));

		    return new String(decryptData, encoding);
			
		} catch (Exception e) {
			_logger.error("", e);
		}
		
		return null;
	}
}
