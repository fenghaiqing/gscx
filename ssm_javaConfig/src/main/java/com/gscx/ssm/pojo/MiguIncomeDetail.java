package com.gscx.ssm.pojo;

import java.math.BigDecimal;

public class MiguIncomeDetail {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_DETAIL.INCOME_DETAIL_ID
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    private BigDecimal incomeDetailId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_DETAIL.INCOME_MANAGER_ID
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    private String incomeManagerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_DETAIL.PRICE_CONFIG_INFO_ID
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    private String priceConfigInfoId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_DETAIL.SELLING_PRICE
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    private BigDecimal sellingPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_DETAIL.PORDUCT_NUMBER
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    private BigDecimal porductNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column T_MIGU_INCOME_DETAIL.TYPE
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    private String type;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_DETAIL.INCOME_DETAIL_ID
     *
     * @return the value of T_MIGU_INCOME_DETAIL.INCOME_DETAIL_ID
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public BigDecimal getIncomeDetailId() {
        return incomeDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_DETAIL.INCOME_DETAIL_ID
     *
     * @param incomeDetailId the value for T_MIGU_INCOME_DETAIL.INCOME_DETAIL_ID
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public void setIncomeDetailId(BigDecimal incomeDetailId) {
        this.incomeDetailId = incomeDetailId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_DETAIL.INCOME_MANAGER_ID
     *
     * @return the value of T_MIGU_INCOME_DETAIL.INCOME_MANAGER_ID
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public String getIncomeManagerId() {
        return incomeManagerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_DETAIL.INCOME_MANAGER_ID
     *
     * @param incomeManagerId the value for T_MIGU_INCOME_DETAIL.INCOME_MANAGER_ID
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public void setIncomeManagerId(String incomeManagerId) {
        this.incomeManagerId = incomeManagerId == null ? null : incomeManagerId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_DETAIL.PRICE_CONFIG_INFO_ID
     *
     * @return the value of T_MIGU_INCOME_DETAIL.PRICE_CONFIG_INFO_ID
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public String getPriceConfigInfoId() {
        return priceConfigInfoId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_DETAIL.PRICE_CONFIG_INFO_ID
     *
     * @param priceConfigInfoId the value for T_MIGU_INCOME_DETAIL.PRICE_CONFIG_INFO_ID
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public void setPriceConfigInfoId(String priceConfigInfoId) {
        this.priceConfigInfoId = priceConfigInfoId == null ? null : priceConfigInfoId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_DETAIL.SELLING_PRICE
     *
     * @return the value of T_MIGU_INCOME_DETAIL.SELLING_PRICE
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_DETAIL.SELLING_PRICE
     *
     * @param sellingPrice the value for T_MIGU_INCOME_DETAIL.SELLING_PRICE
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_DETAIL.PORDUCT_NUMBER
     *
     * @return the value of T_MIGU_INCOME_DETAIL.PORDUCT_NUMBER
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public BigDecimal getPorductNumber() {
        return porductNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_DETAIL.PORDUCT_NUMBER
     *
     * @param porductNumber the value for T_MIGU_INCOME_DETAIL.PORDUCT_NUMBER
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public void setPorductNumber(BigDecimal porductNumber) {
        this.porductNumber = porductNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column T_MIGU_INCOME_DETAIL.TYPE
     *
     * @return the value of T_MIGU_INCOME_DETAIL.TYPE
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column T_MIGU_INCOME_DETAIL.TYPE
     *
     * @param type the value for T_MIGU_INCOME_DETAIL.TYPE
     *
     * @mbg.generated Mon Jul 17 11:21:09 CST 2017
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}