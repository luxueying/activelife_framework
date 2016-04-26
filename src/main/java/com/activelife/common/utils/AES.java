package com.activelife.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
public class AES {
	// ECB（Electronic Code Book，电子密码本）模式
	// CBC（Cipher Block Chaining，加密块链）模式
	// CFB（Cipher FeedBack Mode，加密反馈）模式
	// OFB（Output FeedBack，输出反馈）模式
	/**
	 * PKCS7Padding java 支持需要使用第三方包bcprov-jdk15on key；
	 * 
	 * @author user超过128 需要Unlimited Strength Jurisdiction Policy Files
	 *         local_policy.jar、US_export_policy.jar
	 */
	public static String AES_TYPE = "AES/ECB/PKCS5Padding";
	public static byte[] AES_IV = {0x30, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	public static boolean initialized = false;
	static {
		initialize();
	}
	/**
	 * 加密
	 * 
	 * @param keyStr
	 * @param plainText
	 * @return
	 */
	public static String AES_Encrypt(String keyStr, String plainText) {
		byte[] encrypt = null;
		try {
			Key key = generateKey(keyStr);
			// initialize();
			Cipher cipher = Cipher.getInstance(AES_TYPE, "BC");
			// 若选择非ECB模式需要一个向量iv，可增加加密算法的强度
			if (AES_TYPE.indexOf("ECB") > 0) {
				cipher.init(Cipher.ENCRYPT_MODE, key);
			} else {
				IvParameterSpec iv = new IvParameterSpec(AES_IV);
				cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			}
			encrypt = cipher.doFinal(plainText.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(encrypt));
	}
	/**
	 * 解密
	 * 
	 * @param keyStr
	 * @param encryptData
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String AES_Decrypt(String keyStr, String encryptData)
			throws UnsupportedEncodingException {
		byte[] decrypt = Base64.decodeBase64(encryptData);
		try {
			Key key = generateKey(keyStr);
			// initialize();
			Cipher cipher = Cipher.getInstance(AES_TYPE, "BC");
			// 若选择非ECB模式需要一个向量iv，可增加加密算法的强度
			if (AES_TYPE.indexOf("ECB") > 0) {
				cipher.init(Cipher.DECRYPT_MODE, key);
			} else {
				IvParameterSpec iv = new IvParameterSpec(AES_IV);
				cipher.init(Cipher.DECRYPT_MODE, key, iv);
			}
			decrypt = cipher.doFinal(decrypt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(decrypt, "UTF-8");
	}

	private static Key generateKey(String key) throws Exception {
		try {
			Key keySpec = null;
			// 使用 SecureRandom 生成 key 有时间限制，过期不能解密
			// KeyGenerator kgen = KeyGenerator.getInstance("AES");
			// kgen.init(128, new SecureRandom(key.getBytes("UTF-8")));
			// keySpec = kgen.generateKey();
			//
			keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
			return keySpec;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public static void initialize() {
		if (initialized)
			return;
		Security.addProvider(new BouncyCastleProvider());
		initialized = true;
	}

}
