package cn.migu.income.pojo;

import java.math.BigDecimal;

public class MiguProjectBudget {

    private String projectId;


    private String budgetYear;


    private String budgetDeptDraw;


    private String budgetDeptUse;


    private String budgetProjectNumber;

 
    private String budgetProjectName;

 
    private String budgetSubjectCode;

  
    private BigDecimal budgetAvailableAmount;

 
    private BigDecimal budgetTotalAmount;


    private String budgetResultId;

    private String page;
    
    private String rows;
    
    public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public String getRows() {
		return rows;
	}


	public void setRows(String rows) {
		this.rows = rows;
	}


	public String getProjectId() {
        return projectId;
    }


    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }


    public String getBudgetYear() {
        return budgetYear;
    }


    public void setBudgetYear(String budgetYear) {
        this.budgetYear = budgetYear == null ? null : budgetYear.trim();
    }


    public String getBudgetDeptDraw() {
        return budgetDeptDraw;
    }


    public void setBudgetDeptDraw(String budgetDeptDraw) {
        this.budgetDeptDraw = budgetDeptDraw == null ? null : budgetDeptDraw.trim();
    }


    public String getBudgetDeptUse() {
        return budgetDeptUse;
    }


    public void setBudgetDeptUse(String budgetDeptUse) {
        this.budgetDeptUse = budgetDeptUse == null ? null : budgetDeptUse.trim();
    }


    public String getBudgetProjectNumber() {
        return budgetProjectNumber;
    }


    public void setBudgetProjectNumber(String budgetProjectNumber) {
        this.budgetProjectNumber = budgetProjectNumber == null ? null : budgetProjectNumber.trim();
    }

 
    public String getBudgetProjectName() {
        return budgetProjectName;
    }


    public void setBudgetProjectName(String budgetProjectName) {
        this.budgetProjectName = budgetProjectName == null ? null : budgetProjectName.trim();
    }


    public String getBudgetSubjectCode() {
        return budgetSubjectCode;
    }

  
    public void setBudgetSubjectCode(String budgetSubjectCode) {
        this.budgetSubjectCode = budgetSubjectCode == null ? null : budgetSubjectCode.trim();
    }

   
    public BigDecimal getBudgetAvailableAmount() {
        return budgetAvailableAmount;
    }

   
    public void setBudgetAvailableAmount(BigDecimal budgetAvailableAmount) {
        this.budgetAvailableAmount = budgetAvailableAmount;
    }

   
    public BigDecimal getBudgetTotalAmount() {
        return budgetTotalAmount;
    }

   
    public void setBudgetTotalAmount(BigDecimal budgetTotalAmount) {
        this.budgetTotalAmount = budgetTotalAmount;
    }

   
    public String getBudgetResultId() {
        return budgetResultId;
    }

   
    public void setBudgetResultId(String budgetResultId) {
        this.budgetResultId = budgetResultId == null ? null : budgetResultId.trim();
    }
}