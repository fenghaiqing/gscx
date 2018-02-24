package cn.migu.income.util;

import java.util.HashMap;
import java.util.Map;

public class RetCode {

		public  final static String SUCCESS="1";
	
		public final static String FAIL="0";
	
		public final static String SUCCESS_INF="操作成功！";
		
		public final static String FAIL_INF="操作异常！";
		
		public final static String PRICE_CONFIG_ERROR_A="结束时间不能小于开始时间！";
		
		public final static String PRICE_CONFIG_ERROR_B="同一报价周期同一产品编号在同一项目内只能有一条定价信息！";
		
		public final static String PRICE_CONFIG_ERROR_C="产品编号长度不能超过20个字节！";
	
		
		public final static String PRICE_CONFIG_ERROR_D="产品名称长度不能超过30个字节！";
		
		public final static String PRICE_CONFIG_ERROR_E="说明不能超过200个字节！";
		
		public final static String PRICE_CONFIG_ERROR_F="价格不符合规范，规范为10位数，其中2位小数！";
		
		public final static String PRICE_CONFIG_ERROR_G="厂商编码长度不能超过20个字节！";
		
		public final static String PRICE_CONFIG_ERROR_H="厂商名称长度不能超过200个字节！";
		
		public final static String INCOME_MANAGER_ERROR_A="该业务账期已新增预估收入！";
		
		public final static String INCOME_MANAGER_ERROR_B="说明不能超过2000个字节！";
		
		public final static String INCOME_MANAGER_ERROR_C="文件名称过长，文件路径不能超过200个字节！";
		
	 public static Map<String, Object> map =null;
	
	 public static Map<String, Object> success(){
		map=new HashMap<>();
		map.put("status", SUCCESS);
		map.put("msg", SUCCESS_INF);
		return map;
	}
	
	public static Map<String, Object> success(String msg){
		map=new HashMap<>();
		map.put("status", SUCCESS);
		map.put("msg", msg);
		return map;
	}
	
	public static Map<String, Object> success(String key,Object value){
		map=new HashMap<>();
		map.put("status", SUCCESS);
		map.put(key, value);
		return map;
	}
	
	public static Map<String, Object> success(Object obj){
		map=new HashMap<>();
		map.put("status", SUCCESS);
		map.put("result", obj);
		return map;
	}
	public static Map<String, Object> serverError(){
		map=new HashMap<>();
		map.put("status", FAIL);
		map.put("msg", FAIL_INF);
		return map;
	}
	public static Map<String, Object> serverError(String msg){
		map=new HashMap<>();
		map.put("status", FAIL);
		map.put("msg", msg);
		return map;
	}
	
}
