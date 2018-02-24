package cn.migu.income.util;

import java.util.List;

import cn.migu.income.pojo.TMiguPriceConfigInfo;

public class PriceConfigPojo {

	private List<TMiguPriceConfigInfo> list;
	
	private String aduitUser;
	
	private String projectId;
	
	private String status;
	
	private String opration;
	
	private String priceConfigId;

	public List<TMiguPriceConfigInfo> getList() {
		return list;
	}

	public void setList(List<TMiguPriceConfigInfo> list) {
		this.list = list;
	}

	public String getAduitUser() {
		return aduitUser;
	}

	public void setAduitUser(String aduitUser) {
		this.aduitUser = aduitUser;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpration() {
		return opration;
	}

	public void setOpration(String opration) {
		this.opration = opration;
	}

	public String getPriceConfigId() {
		return priceConfigId;
	}

	public void setPriceConfigId(String priceConfigId) {
		this.priceConfigId = priceConfigId;
	}
}
