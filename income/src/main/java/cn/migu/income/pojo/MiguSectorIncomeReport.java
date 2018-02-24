package cn.migu.income.pojo;


public class MiguSectorIncomeReport {
	private String monthId;
    
    private String deptId;
    
    private String deptName;//部门名称
    
    private String className;//收入大类
    
    private String sectionName;//收入小类
    
    private String estimateTax;//总预估收入不含税
    
    private String realTax;//总实际收入不含税
    
    private String actualIncome;//总实际收款含税
    
    private String costAmountTotal;//总成本金额
    
    private String profitAmount;//利润金额
    
    private String profitRatio;//成本利润率

    private String profitRatio2;//销售利润率

	public String getMonthId() {
		return monthId;
	}

	public void setMonthId(String monthId) {
		this.monthId = monthId;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getEstimateTax() {
		return estimateTax;
	}

	public void setEstimateTax(String estimateTax) {
		this.estimateTax = estimateTax;
	}

	public String getRealTax() {
		return realTax;
	}

	public void setRealTax(String realTax) {
		this.realTax = realTax;
	}

	public String getActualIncome() {
		return actualIncome;
	}

	public void setActualIncome(String actualIncome) {
		this.actualIncome = actualIncome;
	}

	public String getCostAmountTotal() {
		return costAmountTotal;
	}

	public void setCostAmountTotal(String costAmountTotal) {
		this.costAmountTotal = costAmountTotal;
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
    
    

}