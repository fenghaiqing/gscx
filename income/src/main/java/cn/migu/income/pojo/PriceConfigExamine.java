package cn.migu.income.pojo;

import java.math.BigDecimal;

public class PriceConfigExamine {

	private BigDecimal priceConfigId;

    private String priceConfigNumber;
    
    private String projectId;
    
    private String projectDeptName;
    
    private String projectUserName;

    private String submitAuditTime;
    
    private String performAuditTime;

    private String priceConfigAuditUser;

    private String priceConfigAuditResult;

    private String handlingSuggestion;

	public BigDecimal getPriceConfigId() {
		return priceConfigId;
	}

	public void setPriceConfigId(BigDecimal priceConfigId) {
		this.priceConfigId = priceConfigId;
	}

	public String getPriceConfigNumber() {
		return priceConfigNumber;
	}

	public void setPriceConfigNumber(String priceConfigNumber) {
		this.priceConfigNumber = priceConfigNumber;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectDeptName() {
		return projectDeptName;
	}

	public void setProjectDeptName(String projectDeptName) {
		this.projectDeptName = projectDeptName;
	}

	public String getProjectUserName() {
		return projectUserName;
	}

	public void setProjectUserName(String projectUserName) {
		this.projectUserName = projectUserName;
	}

	public String getSubmitAuditTime() {
		return submitAuditTime;
	}

	public void setSubmitAuditTime(String submitAuditTime) {
		this.submitAuditTime = submitAuditTime;
	}

	public String getPerformAuditTime() {
		return performAuditTime;
	}

	public void setPerformAuditTime(String performAuditTime) {
		this.performAuditTime = performAuditTime;
	}

	public String getPriceConfigAuditUser() {
		return priceConfigAuditUser;
	}

	public void setPriceConfigAuditUser(String priceConfigAuditUser) {
		this.priceConfigAuditUser = priceConfigAuditUser;
	}

	public String getPriceConfigAuditResult() {
		return priceConfigAuditResult;
	}

	public void setPriceConfigAuditResult(String priceConfigAuditResult) {
		this.priceConfigAuditResult = priceConfigAuditResult;
	}

	public String getHandlingSuggestion() {
		return handlingSuggestion;
	}

	public void setHandlingSuggestion(String handlingSuggestion) {
		this.handlingSuggestion = handlingSuggestion;
	}

	@Override
	public String toString() {
		return "PriceConfigExamine [priceConfigId=" + priceConfigId + ", priceConfigNumber=" + priceConfigNumber
				+ ", projectId=" + projectId + ", projectDeptName=" + projectDeptName + ", projectUserName="
				+ projectUserName + ", submitAuditTime=" + submitAuditTime + ", performAuditTime=" + performAuditTime
				+ ", priceConfigAuditUser=" + priceConfigAuditUser + ", priceConfigAuditResult="
				+ priceConfigAuditResult + ", handlingSuggestion=" + handlingSuggestion + "]";
	}
    
    

}