package cn.migu.income.util;

public class HeadPojo {

	private String token; //秘钥令牌  MD5(APPCODE+上一次与平台交互的时候产生的Token)
	
	private String optType; //待办操作类型 必填只出现数值型字符，分别代表
	//1:add  2:modify 3:delete [Add：新增待办？Modify：修改待办状态？Delete：删除待办？]
	private String appCode;//待办对应的应用系统编号  必填由门户系统事先编制
	
	private String userID; //登录名 当前应用系统用户[两非系统的用户名？]

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
}
