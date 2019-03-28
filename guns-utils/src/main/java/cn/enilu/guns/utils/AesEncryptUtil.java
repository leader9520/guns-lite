package cn.enilu.guns.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * AES 128bit 加密解密工具类
 * 
 * @author dufy
 */
public class AesEncryptUtil {

	// 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
	private static String KEY = "1234567890123456";

	private static String IV = "1234567890123456";

	/**
	 * 加密方法
	 * 
	 * @param data 要加密的数据
	 * @param key  加密key
	 * @param iv   加密iv
	 * @return 加密的结果
	 * @throws Exception
	 */
	public static String encrypt(String data, String key, String iv) throws Exception {
		try {

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// "算法/模式/补码方式"NoPadding PkcsPadding
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);

			return new Base64().encodeToString(encrypted);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解密方法
	 * 
	 * @param data 要解密的数据
	 * @param key  解密key
	 * @param iv   解密iv
	 * @return 解密的结果
	 * @throws Exception
	 */
	public static String desEncrypt(String data, String key, String iv) throws Exception {
		try {
			byte[] encrypted1 = new Base64().decode(data);

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用默认的key和iv加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		return encrypt(data, KEY, IV);
	}

	/**
	 * 使用默认的key和iv解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String desEncrypt(String data) throws Exception {
		return desEncrypt(data, KEY, IV);
	}
	
	/**
	 * 使用默认的iv解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String desEncrypt(String data, String key) throws Exception {
		return desEncrypt(data, key, IV);
	}


	/**
	 * 测试
	 */
	public static void main(String args[]) throws Exception {

		String test1 = "admin";
		String test = new String(test1.getBytes(), "UTF-8");
		String data = null;
		String key = KEY;
		key="21232F297A57A5A743894A0E4A801FC3";
		String iv = IV;
		// /g2wzfqvMOeazgtsUVbq1kmJawROa6mcRAzwG1/GeJ4=
		//VdQz57xg7IBtcmef+3ddlA==
		data = encrypt(test, key, iv);
		System.out.println("数据：" + test);
		System.out.println("加密：" + data);
		String jiemi = desEncrypt(data, key, iv).trim();
		System.out.println("解密：" + jiemi);

		System.out.println(MD5.getMD5String("1234"));
	}
}
