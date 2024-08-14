package com.ninza.api.genericutility;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * private Key : Ac03tEam@j!tu_#1
 */
public class EncrypAndDecryptUtility {
	public String encrypt(String input,String secretKey) throws Exception {
	    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
	    IvParameterSpec ivParameterSpec = new IvParameterSpec("4234567890123456".getBytes());
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
	    byte[] encrypted = cipher.doFinal(input.getBytes());
	    return Base64.getEncoder().encodeToString(encrypted);
	}

	public String decrypt(String input,String secretKey) throws Exception {
	    SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
	    IvParameterSpec ivParameterSpec = new IvParameterSpec("4234567890123456".getBytes());
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
	    byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(input));
	    return new String(decrypted);
	}   

}
