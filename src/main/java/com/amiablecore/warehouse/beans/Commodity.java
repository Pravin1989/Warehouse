package com.amiablecore.warehouse.beans;

/**
 * @author Pravin
 *
 */
public class Commodity {
	private Integer commodityId;
	private String commodityName;
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

	public Integer getWhAdminId() {
		return whAdminId;
	}

	public void setWhAdminId(Integer whAdminId) {
		this.whAdminId = whAdminId;
	}

}
