package cn.migu.income.pojo;

public class TMiguIncomeCategories {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_CATEGORIES.INCOME_ID
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    private String incomeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_CATEGORIES.INCOME_PARENT_ID
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    private String incomeParentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_CATEGORIES.INCOME_NAME
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    private String incomeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_CATEGORIES.STATE
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    private String state;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_CATEGORIES.INCOME_ID
     *
     * @return the value of T_MIGU_INCOME_CATEGORIES.INCOME_ID
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    public String getIncomeId() {
        return incomeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_CATEGORIES.INCOME_ID
     *
     * @param incomeId the value for T_MIGU_INCOME_CATEGORIES.INCOME_ID
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId == null ? null : incomeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_CATEGORIES.INCOME_PARENT_ID
     *
     * @return the value of T_MIGU_INCOME_CATEGORIES.INCOME_PARENT_ID
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    public String getIncomeParentId() {
        return incomeParentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_CATEGORIES.INCOME_PARENT_ID
     *
     * @param incomeParentId the value for T_MIGU_INCOME_CATEGORIES.INCOME_PARENT_ID
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    public void setIncomeParentId(String incomeParentId) {
        this.incomeParentId = incomeParentId == null ? null : incomeParentId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_CATEGORIES.INCOME_NAME
     *
     * @return the value of T_MIGU_INCOME_CATEGORIES.INCOME_NAME
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    public String getIncomeName() {
        return incomeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_CATEGORIES.INCOME_NAME
     *
     * @param incomeName the value for T_MIGU_INCOME_CATEGORIES.INCOME_NAME
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName == null ? null : incomeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_CATEGORIES.STATE
     *
     * @return the value of T_MIGU_INCOME_CATEGORIES.STATE
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_CATEGORIES.STATE
     *
     * @param state the value for T_MIGU_INCOME_CATEGORIES.STATE
     *
     * @mbggenerated Fri Jun 09 15:26:39 CST 2017
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

	@Override
	public String toString() {
		return "TMiguIncomeCategories [incomeId=" + incomeId + ", incomeParentId=" + incomeParentId + ", incomeName="
				+ incomeName + ", state=" + state + "]";
	}
    
    
}