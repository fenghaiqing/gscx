package cn.migu.income.util;

public class IDGenUtil {

	public static String UUID(){
		
		String originalUUID = java.util.UUID.randomUUID().toString();
				
		return originalUUID.replaceAll("-", "");
	}
	
}
