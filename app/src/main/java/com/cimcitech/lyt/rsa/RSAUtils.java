package com.cimcitech.lyt.rsa;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 *
 * @author 00014092
 * @date 2017年5月5日 下午4:39:00
 * @version 1.0
 */
public class RSAUtils {
	
	/**
	 * 使用公钥对明文进行加密，返回BASE64编码的字符串
	 * @param plainText
	 * @param strPubKey
	 * @return
	 */
	public static String encrypt(String plainText, String strPubKey){
		try {		
			
			java.security.spec.X509EncodedKeySpec bobSpec = new java.security.spec.X509EncodedKeySpec((new BASE64Decoder()).decodeBuffer(strPubKey));

			java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(bobSpec);
			
			//必须是"RSA/ECB/PKCS1Padding"，不可以是单独的RSA
			Cipher cip = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cip.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] enBytes = cip.doFinal(plainText.getBytes());
			return (new BASE64Encoder()).encode(enBytes);
			
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 使用私钥对明文密文进行解密
	 * @param enStr
	 * @return
	 */
	public static String decrypt(String enStr, String strPriKey){
		try {
			java.security.spec.PKCS8EncodedKeySpec bobSpec = new java.security.spec.PKCS8EncodedKeySpec((new BASE64Decoder()).decodeBuffer(strPriKey));

			java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
			PrivateKey privateKey = keyFactory.generatePrivate(bobSpec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] deBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(enStr));
			return new String(deBytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
