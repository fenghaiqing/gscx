package cn.migu.income.util;

import java.security.MessageDigest;

/**
 * MD5加密工具类（与重庆基地同步数据接口用，其它功能如要用到此加密功能，请慎重）
 * 本代码源头是从统一平台项目中拷贝而来
 */
public class MD5 {
	
	private final static String[] hexDigits =

	{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };
	public static String md5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} 
		byte[] results = md5.digest(inStr.getBytes());
		// 将得到的字节数组变成字符串返回
		String resultString = byteArrayToHexString(results);
		return resultString.toUpperCase();
	}
	
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
}
