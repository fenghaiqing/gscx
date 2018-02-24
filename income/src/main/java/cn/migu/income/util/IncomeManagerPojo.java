package cn.migu.income.util;

import java.util.List;

import cn.migu.income.pojo.MiguIncomeDetail;
import cn.migu.income.pojo.MiguIncomeManager;

public class IncomeManagerPojo {

	private MiguIncomeManager incomeManager;
	
	private String type;
	
	private String operation;
	
	private List<MiguIncomeDetail> list;

	public MiguIncomeManager getIncomeManager() {
		return incomeManager;
	}

	public void setIncomeManager(MiguIncomeManager incomeManager) {
		this.incomeManager = incomeManager;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public List<MiguIncomeDetail> getList() {
		return list;
	}

	public void setList(List<MiguIncomeDetail> list) {
		this.list = list;
	}
	
	
}
