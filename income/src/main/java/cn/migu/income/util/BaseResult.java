package cn.migu.income.util;

public class BaseResult {
	
	private int retCode ;
	
	
	private String retMsg ;


	public int getRetCode() {
		return retCode;
	}


	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}


	public String getRetMsg() {
		return retMsg;
	}


	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}


	@Override
	public String toString() {
		return "BaseResult [retCode=" + retCode + ", retMsg=" + retMsg + "]";
	}


	public BaseResult(int retCode, String retMsg) {
		super();
		this.retCode = retCode;
		this.retMsg = retMsg;
	}
	
}
