package cn.migu.income.pojo;

import java.math.BigDecimal;

public class TMiguPriceConfigInfo {

	
	private Long priceConfigInfoId;
	
	private String productId;
	
	private String productName;
	
	private BigDecimal purchasePrice;
	
	private BigDecimal minSellPrice;
	
	private String offerStartTime;
	
	private String illustrate;
	
	private String priceConfigId;
	
	private String offerEndTime;

	private String projectId;
	
	private String projectName;
	
	private String priceConfigNumber;
	
	private String vendorId;
	
	private String vendorName;
	
	private String rn;
	public String getRn() {
		return rn;
	}

	public void setRn(String rn) {
		this.rn = rn;
	}

	public String getPriceConfigNumber() {
		return priceConfigNumber;
	}

	public void setPriceConfigNumber(String priceConfigNumber) {
		this.priceConfigNumber = priceConfigNumber;
	}

	public Long getPriceConfigInfoId() {
		return priceConfigInfoId;
	}

	public String getProjectId() {
		return projectId;
	}

	
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
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


	public void setPriceConfigInfoId(Long priceConfigInfoId) {
		this.priceConfigInfoId = priceConfigInfoId == null ? null : priceConfigInfoId;
	}

	
	public String getProductId() {
		return productId;
	}

	
	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	
	public String getProductName() {
		return productName;
	}

	
	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	
	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	
	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	
	public BigDecimal getMinSellPrice() {
		return minSellPrice;
	}

	
	public void setMinSellPrice(BigDecimal minSellPrice) {
		this.minSellPrice = minSellPrice;
	}

	
	public String getOfferStartTime() {
		return offerStartTime;
	}


	public void setOfferStartTime(String offerStartTime) {
		this.offerStartTime = offerStartTime == null ? null : offerStartTime.trim();
	}

	
	public String getIllustrate() {
		return illustrate;
	}

	
	public void setIllustrate(String illustrate) {
		this.illustrate = illustrate == null ? null : illustrate.trim();
	}

	
	public String getPriceConfigId() {
		return priceConfigId;
	}


	public void setPriceConfigId(String priceConfigId) {
		this.priceConfigId = priceConfigId == null ? null : priceConfigId.trim();
	}

	
	public String getOfferEndTime() {
		return offerEndTime;
	}

	
	public void setOfferEndTime(String offerEndTime) {
		this.offerEndTime = offerEndTime == null ? null : offerEndTime.trim();
	}
}