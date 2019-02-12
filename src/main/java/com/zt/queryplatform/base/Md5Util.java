package com.zt.queryplatform.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: Md5Util
 * @date: 2018-09-15
 * @author: linzj
 */
public class Md5Util {
	
	/**
	 * 获取MD5加密码
	 * @return
	 */
	public static String MD5Encode(String strSrc, String key) {
		String md5code = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(strSrc.getBytes("UTF8")); 
			
			byte[] temp;
			temp = md5.digest(key.getBytes("UTF8"));
			for (int i = 0; i < temp.length; i++) {
				md5code += Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);  
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace(); 
		}catch (Exception e) {     
			e.printStackTrace();
		}
		return md5code;
	}
}
