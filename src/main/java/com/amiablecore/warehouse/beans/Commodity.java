package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 *
 */
public class Commodity {
	private Integer commodityId;
	private String commodityName;
	private String whAdminId;

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getWhAdminId() {
		return whAdminId;
	}

	public void setWhAdminId(String whAdminId) {
		this.whAdminId = whAdminId;
	}

}
