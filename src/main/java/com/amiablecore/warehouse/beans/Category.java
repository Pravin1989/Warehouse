package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 *
 */
public class Category {
	private Integer categoryId;
	private String categoryName;
	private Integer whAdminId;
	private boolean active;
	private boolean sync;
	private boolean alreadyPresent;

	public void setAlreadyPresent(boolean alreadyPresent) {
		this.alreadyPresent = alreadyPresent;
	}
	
	public boolean isAlreadyPresent() {
		return alreadyPresent;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isSync() {
		return sync;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

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

	public Integer getWhAdminId() {
		return whAdminId;
	}

	public void setWhAdminId(Integer whAdminId) {
		this.whAdminId = whAdminId;
	}
}
