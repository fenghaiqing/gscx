package cn.migu.income.pojo;


public class MiguIncomeManagerHis {
   
	private String incomeManagerHisId;

    private String miguIncomeManagerId;

    private String auditPerson;

    private String auditDept;

    private String createDate;

    private String dealResult;

    private String dealOptions;
    
    private String options;
    
    private String billingKey;

    public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	private String type;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIncomeManagerHisId() {
        return incomeManagerHisId;
    }

    public void setIncomeManagerHisId(String incomeManagerHisId) {
        this.incomeManagerHisId = incomeManagerHisId;
    }

    public String getMiguIncomeManagerId() {
        return miguIncomeManagerId;
    }

    public void setMiguIncomeManagerId(String miguIncomeManagerId) {
        this.miguIncomeManagerId = miguIncomeManagerId == null ? null : miguIncomeManagerId.trim();
    }

    public String getAuditPerson() {
        return auditPerson;
    }

    public void setAuditPerson(String auditPerson) {
        this.auditPerson = auditPerson == null ? null : auditPerson.trim();
    }

    public String getAuditDept() {
        return auditDept;
    }

    public void setAuditDept(String auditDept) {
        this.auditDept = auditDept == null ? null : auditDept.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult == null ? null : dealResult.trim();
    }

    public String getDealOptions() {
        return dealOptions;
    }

    public void setDealOptions(String dealOptions) {
        this.dealOptions = dealOptions == null ? null : dealOptions.trim();
    }

	public String getBillingKey() {
		return billingKey;
	}

	public void setBillingKey(String billingKey) {
		this.billingKey = billingKey;
	}
    
    
}