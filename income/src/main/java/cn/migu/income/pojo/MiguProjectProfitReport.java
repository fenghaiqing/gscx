package cn.migu.income.pojo;


public class MiguProjectProfitReport {

    private String projectId;

    private String projectName;
    
    private String deptId;
    
    private String deptName;
    
    private String personId;
    
    private String personName;
    
    private String costAmount;
    
    private String actualIncome;
    
    private String profitAmount;
    
    private String profitRatio;
    
    private String profitRatio2;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(String costAmount) {
		this.costAmount = costAmount;
	}

	public String getActualIncome() {
		return actualIncome;
	}

	public void setActualIncome(String actualIncome) {
		this.actualIncome = actualIncome;
	}

	public String getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(String profitAmount) {
		this.profitAmount = profitAmount;
	}

	public String getProfitRatio() {
		return profitRatio;
	}

	public void setProfitRatio(String profitRatio) {
		this.profitRatio = profitRatio;
	}

	
	public String getProfitRatio2() {
		return profitRatio2;
	}

	public void setProfitRatio2(String profitRatio2) {
		this.profitRatio2 = profitRatio2;
	}

	@Override
	public String toString() {
		return "MiguProjectProfitReport [projectId=" + projectId + ", projectName=" + projectName + ", deptId=" + deptId
				+ ", deptName=" + deptName + ", personId=" + personId + ", personName=" + personName + ", costAmount="
				+ costAmount + ", actualIncome=" + actualIncome + ", profitAmount=" + profitAmount + ", profitRatio="
				+ profitRatio + ", profitRatio2=" + profitRatio2 + "]";
	}



    
    

}