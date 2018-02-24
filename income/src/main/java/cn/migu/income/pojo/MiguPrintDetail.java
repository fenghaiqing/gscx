package cn.migu.income.pojo;


public class MiguPrintDetail {

    private String projectId;//项目编号
    
    private String incomeClass;//收入大类
    
    private String incomeSection;//收入小类
    
    private String projectUser; //项目责任人
    
    private String projectDept;//项目责任部门

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getIncomeClass() {
		return incomeClass;
	}

	public void setIncomeClass(String incomeClass) {
		this.incomeClass = incomeClass;
	}

	public String getIncomeSection() {
		return incomeSection;
	}

	public void setIncomeSection(String incomeSection) {
		this.incomeSection = incomeSection;
	}

	public String getProjectUser() {
		return projectUser;
	}

	public void setProjectUser(String projectUser) {
		this.projectUser = projectUser;
	}

	public String getProjectDept() {
		return projectDept;
	}

	public void setProjectDept(String projectDept) {
		this.projectDept = projectDept;
	}

    
}