package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 *
 */
public class Category {
	private Integer categoryId;
	private Integer categoryName;
	private Commodity commodity;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(Integer categoryName) {
		this.categoryName = categoryName;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}
}
