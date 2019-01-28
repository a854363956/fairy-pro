package com.fairy.models.common;

import java.security.MessageDigest;

/**
 * 采用32位变种的Hash算法,避免被暴力破解Hash,采用32轮加密
 */
public class Md5Variant {
	
	static protected String encryption(String txt) {
		try {
			char hexDigits[] = { '!', '/', '|', '~', '`', '^', '\'', '*', '?', '/', '-', '_', '=', '+', '.', ',' };
			byte[] btInput = txt.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	static public String strongEncryption(String txt)  {
		String hash = txt;
		for(int i=0;i<32;i++) {
			hash=encryption(hash);
		}
		return hash;
	}
}
