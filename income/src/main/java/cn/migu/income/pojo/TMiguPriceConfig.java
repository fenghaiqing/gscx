package cn.migu.income.pojo;

import java.math.BigDecimal;

public class TMiguPriceConfig {
  
    private Long priceConfigId;

  
    private String priceConfigNumber;


    private String submitAuditTime;

 
    private String priceConfigAuditUser;

  
    private String priceConfigAuditResult;

   
    private String projectId;

   
    private String performAuditTime;

   
    private String handlingSuggestion;
    
    private String priceConfigAuditUserName;
    
    
    public String getPriceConfigAuditUserName() {
		return priceConfigAuditUserName;
	}

	public void setPriceConfigAuditUserName(String priceConfigAuditUserName) {
		this.priceConfigAuditUserName = priceConfigAuditUserName;
	}

	private String rn;

    public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_PRICE_CONFIG.PRICE_CONFIG_ID
     *
     * @return the value of T_MIGU_PRICE_CONFIG.PRICE_CONFIG_ID
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public Long getPriceConfigId() {
        return priceConfigId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_PRICE_CONFIG.PRICE_CONFIG_ID
     *
     * @param priceConfigId the value for T_MIGU_PRICE_CONFIG.PRICE_CONFIG_ID
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public void setPriceConfigId(Long priceConfigId) {
        this.priceConfigId = priceConfigId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_PRICE_CONFIG.PRICE_CONFIG_NUMBER
     *
     * @return the value of T_MIGU_PRICE_CONFIG.PRICE_CONFIG_NUMBER
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public String getPriceConfigNumber() {
        return priceConfigNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_PRICE_CONFIG.PRICE_CONFIG_NUMBER
     *
     * @param priceConfigNumber the value for T_MIGU_PRICE_CONFIG.PRICE_CONFIG_NUMBER
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public void setPriceConfigNumber(String priceConfigNumber) {
        this.priceConfigNumber = priceConfigNumber == null ? null : priceConfigNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_PRICE_CONFIG.SUBMIT_AUDIT_TIME
     *
     * @return the value of T_MIGU_PRICE_CONFIG.SUBMIT_AUDIT_TIME
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public String getSubmitAuditTime() {
        return submitAuditTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_PRICE_CONFIG.SUBMIT_AUDIT_TIME
     *
     * @param submitAuditTime the value for T_MIGU_PRICE_CONFIG.SUBMIT_AUDIT_TIME
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public void setSubmitAuditTime(String submitAuditTime) {
        this.submitAuditTime = submitAuditTime == null ? null : submitAuditTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_PRICE_CONFIG.PRICE_CONFIG_AUDIT_USER
     *
     * @return the value of T_MIGU_PRICE_CONFIG.PRICE_CONFIG_AUDIT_USER
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public String getPriceConfigAuditUser() {
        return priceConfigAuditUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_PRICE_CONFIG.PRICE_CONFIG_AUDIT_USER
     *
     * @param priceConfigAuditUser the value for T_MIGU_PRICE_CONFIG.PRICE_CONFIG_AUDIT_USER
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public void setPriceConfigAuditUser(String priceConfigAuditUser) {
        this.priceConfigAuditUser = priceConfigAuditUser == null ? null : priceConfigAuditUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_PRICE_CONFIG.PRICE_CONFIG_AUDIT_RESULT
     *
     * @return the value of T_MIGU_PRICE_CONFIG.PRICE_CONFIG_AUDIT_RESULT
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public String getPriceConfigAuditResult() {
        return priceConfigAuditResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_PRICE_CONFIG.PRICE_CONFIG_AUDIT_RESULT
     *
     * @param priceConfigAuditResult the value for T_MIGU_PRICE_CONFIG.PRICE_CONFIG_AUDIT_RESULT
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public void setPriceConfigAuditResult(String priceConfigAuditResult) {
        this.priceConfigAuditResult = priceConfigAuditResult == null ? null : priceConfigAuditResult.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_PRICE_CONFIG.PROJECT_ID
     *
     * @return the value of T_MIGU_PRICE_CONFIG.PROJECT_ID
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_PRICE_CONFIG.PROJECT_ID
     *
     * @param projectId the value for T_MIGU_PRICE_CONFIG.PROJECT_ID
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_PRICE_CONFIG.PERFORM_AUDIT_TIME
     *
     * @return the value of T_MIGU_PRICE_CONFIG.PERFORM_AUDIT_TIME
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public String getPerformAuditTime() {
        return performAuditTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_PRICE_CONFIG.PERFORM_AUDIT_TIME
     *
     * @param performAuditTime the value for T_MIGU_PRICE_CONFIG.PERFORM_AUDIT_TIME
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public void setPerformAuditTime(String performAuditTime) {
        this.performAuditTime = performAuditTime == null ? null : performAuditTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_PRICE_CONFIG.HANDLING_SUGGESTION
     *
     * @return the value of T_MIGU_PRICE_CONFIG.HANDLING_SUGGESTION
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public String getHandlingSuggestion() {
        return handlingSuggestion;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_PRICE_CONFIG.HANDLING_SUGGESTION
     *
     * @param handlingSuggestion the value for T_MIGU_PRICE_CONFIG.HANDLING_SUGGESTION
     *
     * @mbg.generated Tue Jun 20 16:38:12 CST 2017
     */
    public void setHandlingSuggestion(String handlingSuggestion) {
        this.handlingSuggestion = handlingSuggestion == null ? null : handlingSuggestion.trim();
    }
}