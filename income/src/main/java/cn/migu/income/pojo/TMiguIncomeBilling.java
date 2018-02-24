package cn.migu.income.pojo;

import java.math.BigDecimal;

/**
 * Created with IDEA
 * 开票信息表
 * User:lushengpeng
 * Date:2017/8/28
 * Time:15:36
 */
public class TMiguIncomeBilling {
    String billingKey;

    String cycle;

    String projectId;

    String projectName;

    String billType;

    String invoiceCode;

    String invoiceNumber;

    String billingTime;

    String billingStatus;

    BigDecimal total;

    String userId;

    private String  className;
    private String  sectionName;
    private String  deptName;
    private String  userName;
    
    private String bzCycle;
    int page;

    int rows;
    
    BigDecimal billingTotal;

    public String getBillingKey() {
        return billingKey;
    }

    public void setBillingKey(String billingKey) {
        this.billingKey = billingKey;
    }

    public String getCycle() {
        return cycle;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCycle(String cycle) {
        this.cycle = cycle;
    }

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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getBillingTime() {
        return billingTime;
    }

    public void setBillingTime(String billingTime) {
        this.billingTime = billingTime;
    }

    public String getBillingStatus() {
        return billingStatus;
    }

    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public TMiguIncomeBilling() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

	public BigDecimal getBillingTotal() {
		return billingTotal;
	}

	public void setBillingTotal(BigDecimal billingTotal) {
		this.billingTotal = billingTotal;
	}

	public String getBzCycle() {
		return bzCycle;
	}

	public void setBzCycle(String bzCycle) {
		this.bzCycle = bzCycle;
	}

	@Override
	public String toString() {
		return "TMiguIncomeBilling [billingKey=" + billingKey + ", cycle=" + cycle + ", projectId=" + projectId
				+ ", projectName=" + projectName + ", billType=" + billType + ", invoiceCode=" + invoiceCode
				+ ", invoiceNumber=" + invoiceNumber + ", billingTime=" + billingTime + ", billingStatus="
				+ billingStatus + ", total=" + total + ", userId=" + userId + ", className=" + className
				+ ", sectionName=" + sectionName + ", deptName=" + deptName + ", userName=" + userName + ", bzCycle="
				+ bzCycle + ", page=" + page + ", rows=" + rows + ", billingTotal=" + billingTotal + "]";
	}

	
}
