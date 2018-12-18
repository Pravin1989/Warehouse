package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 *
 */
public class Category {
	private Integer categoryId;
	private String categoryName;
	private String commodityName;
	private String whAdminId;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
